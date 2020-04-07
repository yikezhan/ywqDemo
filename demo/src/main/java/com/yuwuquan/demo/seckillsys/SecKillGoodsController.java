package com.yuwuquan.demo.seckillsys;

import com.yuwuquan.demo.common.ResponseDTO;
import com.yuwuquan.demo.orm.model.SysUserInfo;
import com.yuwuquan.demo.util.RedisUtil;
import com.yuwuquan.demo.util.common.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 1、页面静态化。（暂不考虑）
 * 2、加大带宽支持。（暂不考虑）
 * 3、前台nginx负载。（undo）
 * 4、秒杀链接需要到点才开始，秒杀结束或者商品售完key失效。（doing）
 * 5、使用消息队列削峰。
 * 6、防止库存多扣。
 * 7、读写分离。
 * 8、根据ip限制用户刷新频率。
 * 9、防重复提交。
 */
@Api(tags="秒杀系统")
@RestController
@RequestMapping(value = "/ seckill")
public class SecKillGoodsController {
    private final static Integer oClock = 18;//秒杀活动18点开始，5分钟后结束

    @Autowired
    RedisUtil redisUtil;
    @ApiOperation(value = "下单入口")
    @PostMapping(value = "/orderbooking")
    public ResponseDTO orderBooking(@RequestBody SecKillRequest secKillRequest){
        if(secKillRequest.getKey() != null){
            String key = redisUtil.get(SecKillEnum.SEC_KILL_START_KEY.getValue()).toString();
            if(secKillRequest.getKey().equals(key)){
                // TODO: 2020/4/3 进入秒杀队列
//                secKillRequest.getSku();
//                secKillRequest.getUserId();
                if(false) {//秒杀成功
                    return ResponseDTO.success();
                }else{
                    return ResponseDTO.systemFail("已售罄");//秒杀失败
                }
            }
        }
        return ResponseDTO.systemFail();//时间未开始
    }
    @ApiOperation(value = "获取秒杀开始key")
    @PostMapping(value = "/getKey")
    public ResponseDTO getKey(){
        String key = redisUtil.get(SecKillEnum.SEC_KILL_START_KEY.getValue()).toString();
        if(key == null){
            return ResponseDTO.systemFail();
        }else{
            return ResponseDTO.success(key);
        }
    }
}
