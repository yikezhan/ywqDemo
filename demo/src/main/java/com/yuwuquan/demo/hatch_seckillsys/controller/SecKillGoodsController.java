package com.yuwuquan.demo.hatch_seckillsys.controller;

import com.yuwuquan.demo.activemq.message.MessageCreateUtil;
import com.yuwuquan.demo.activemq.message.template.MessageDetail;
import com.yuwuquan.demo.activemq.send.SendMessage;
import com.yuwuquan.demo.common.ResponseDTO;
import com.yuwuquan.demo.exception.ApplicationException;
import com.yuwuquan.demo.hatch_seckillsys.SecKillEnum;
import com.yuwuquan.demo.hatch_seckillsys.SecKillStatusEnum;
import com.yuwuquan.demo.hatch_seckillsys.dto.SecKillGoodsDTO;
import com.yuwuquan.demo.hatch_seckillsys.job.SetRedisKeyJobHandler;
import com.yuwuquan.demo.util.RedisUtil;
import com.yuwuquan.demo.util.common.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 使用的措施
 * 1、秒杀链接需要到点才开始，秒杀结束或者商品售完key失效。(do)
 * 2、使用redis缓存,一个用户只能提交一次，sku校验，用户校验等。(do)
 * 3、使用mq队列削峰，同一个商品要限制入队的数目。(do)
 * 4、防止库存多扣。(do)
 *
 *
 * 其他措施（条件原因，暂不处理）
 * 1、页面静态化。(不考虑)
 * 2、加大带宽支持。(不考虑)
 * 3、读写分离。
 * 4、nginx做ip点击限制，nginx+lua访问redis过滤黑名单
 *
 *
 * 流程：
 * 1、到点后前端获取到链接有效key--->传入到下单url--->前1w访问，设置redis健值为秒杀ing，进入mq。后1w请求，跳转到售罄页面。
 * 2、mq消费，秒杀成功则下单，并更新redis值为秒杀成功，秒杀失败则更新redis值为秒杀失败。
 * 3、前端不断查询redis内的值，只至结果返回秒杀失败/成功。成功跳转到订单付款页面，失败跳转到售罄页面。
 */
@Api(tags="秒杀系统")
@RestController
@RequestMapping(value = "/seckill")
public class SecKillGoodsController {
    private final static Integer oClock = 18;//秒杀活动18点开始，5分钟后结束
    @Autowired
    SendMessage sendMessage;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    SetRedisKeyJobHandler setRedisKeyJobHandler;

    private static final Logger logger = LoggerFactory.getLogger(SecKillGoodsController.class);

    @ApiOperation(value = "下单测试入口")
    @GetMapping(value = "/orderBookingTest")
    public ResponseDTO orderBookingTest(Long userId, @RequestParam(value = "key",defaultValue = "bingo",required = false) String key){
        SecKillRequest secKillRequest = new SecKillRequest();
        SecKillGoodsDTO secKillGoodsDTO = new SecKillGoodsDTO();
        secKillGoodsDTO.setSku("100abc");
        secKillGoodsDTO.setUserId(userId);
        secKillGoodsDTO.setKey(key);
        secKillRequest.setSecKillGoodsDTO(secKillGoodsDTO);
        return orderBooking(secKillRequest);
    }
    @ApiOperation(value = "下单入口")
    @PostMapping(value = "/orderBooking")
    public ResponseDTO orderBooking(@RequestBody SecKillRequest secKillRequest){
        SecKillGoodsDTO secKillGoodsDTO = secKillRequest.getSecKillGoodsDTO();
        try{

            if(checkKey(secKillGoodsDTO.getKey()) && checkSKU(secKillGoodsDTO.getSku())){//检查是否到时间点，商品是否是秒杀商品
                checkUser(secKillGoodsDTO.getUserId());//检查用户是否点击过
                if(checkMQNum(secKillGoodsDTO.getSku())){//检查同一个商品最多入队数目，超出则直接返回售罄
                    boolean res = redisUtil.setIfAbsent(SecKillEnum.SEC_KILL_QUEUE_USER_PRE.getKey() + secKillGoodsDTO.getUserId(), SecKillStatusEnum.SecKillDoing.getValue(),SecKillEnum.SEC_KILL_QUEUE_USER_PRE.getTime());//秒杀中
                    if(!res) throw new ApplicationException("您已秒杀过了",-1);// TODO: 2020/4/8 细节1：双重检查，避免同一用户重复下单
                    MessageDetail<SecKillGoodsDTO> messageDetail  = MessageCreateUtil.createSecKillInfo(secKillGoodsDTO);
                    String result = sendMessage.sendMsg(messageDetail);
                    if(!"true".equalsIgnoreCase(result)){
                        logger.warn("mq消息发送失败");
                    }
                    return ResponseDTO.success();
                }else{
                    return ResponseDTO.systemFail("已售罄");//秒杀失败
                }
            }
        }catch (ApplicationException ae){
            return ResponseDTO.systemFail(ae);
        }catch (Exception e){
            return ResponseDTO.systemFail();
        }
        return ResponseDTO.systemFail();//时间未开始
    }
    //检查活动是否开始
    private boolean checkKey(String key){
        if(key == null) throw new ApplicationException("传入的key不能为空",-1);

        String redisValue = StringUtil.valueOf(redisUtil.get(SecKillEnum.SEC_KILL_START_KEY.getKey()));//秒杀开始才能获取到的key值
        if(redisValue != null && key.equals(redisValue)) {
            return true;
        }else{
            throw new ApplicationException("秒杀活动尚未开始",-1);
        }
    }
    //过滤黑名单用户,是否已点击过等
    private boolean checkUser(Long userId){
        Long[] userIds = {11L,22L,33L};
        if(userId == null) return false;
        Object val = redisUtil.get(SecKillEnum.SEC_KILL_QUEUE_USER_PRE.getKey() + userId);
        if(val == null){
            return true;
        } else{
            throw new ApplicationException("您已秒杀过",-1);
        }
    }
    //检查sku值是否是符合的商品，秒杀商品的sku直接写在内存中，这里先写死
    private boolean checkSKU(String sku){
        String[] skus = {"121","111","122"};
        if(StringUtil.isNotBlank(sku)){
            return true;
        }else{
            throw new ApplicationException("商品sku不符合规则",-1);
        }
    }
    private boolean checkMQNum(String sku){
        long num = redisUtil.incr(SecKillEnum.SEC_KILL_QUEUE_NUM_PRE.getKey()+sku,1);//记录该商品进mq中的数量
        if(num < 10000){//mq最多进1w个请求。入队数目可以考虑在mq中间件本身上设置
            return true;
        }else{
            throw new ApplicationException("商品已售罄",-1);//进入队列失败
        }
    }

    @ApiOperation(value = "获取秒杀开始key")
    @PostMapping(value = "/getKey")
    public ResponseDTO getKey(){
        String key = StringUtil.valueOf(redisUtil.get(SecKillEnum.SEC_KILL_START_KEY.getKey()));
        return ResponseDTO.success(key);
    }
    @ApiOperation(value = "获取秒杀结果")
    @PostMapping(value = "/getRes")
    public ResponseDTO getRes(Long userId){
        String res = StringUtil.valueOf(redisUtil.get(SecKillEnum.SEC_KILL_QUEUE_USER_PRE.getKey() + userId));
        SecKillStatusEnum secKillStatusEnum =SecKillStatusEnum.getEnumByValue(res);
        return ResponseDTO.success(secKillStatusEnum);
    }

    @ApiOperation(value = "手动触发job,塞活动开始key，10min有效")
    @PostMapping(value = "/setRedisKeyJobHandler")
    public ResponseDTO setRedisKeyJobHandler(String key){
        try {
            setRedisKeyJobHandler.execute(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseDTO.success();
    }

}
