package com.yuwuquan.demo.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.yuwuquan.demo.config.UsualMultiThreadConfig;
import com.yuwuquan.demo.dubbo.consumer.impl.TestConsumerImpl;
import com.yuwuquan.demo.job.DemoJobHandler;
import com.yuwuquan.demo.activemq.message.MessageCreateUtil;
import com.yuwuquan.demo.activemq.message.template.MessageDetail;
import com.yuwuquan.demo.activemq.send.impl.SendMessageImpl;
import com.yuwuquan.demo.orm.dto.ElasticsearchObject;
import com.yuwuquan.demo.orm.dto.MongoUserObject;
import com.yuwuquan.demo.orm.model.User;
import com.yuwuquan.demo.service.ElasticsearchRepositoryInter;
import com.yuwuquan.demo.service.GoodsService;
import com.yuwuquan.demo.service.MongoRepositoryInter;
import com.yuwuquan.demo.service.UserService;
import com.yuwuquan.demo.designpatterns.specialstrategy.SaveStrategyServiceInter;
import com.yuwuquan.demo.designpatterns.specialstrategy.pojo.Order;
import com.yuwuquan.demo.util.RedisUtil;
import com.yuwuquan.demo.util.common.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping(value = "/demo")
@Api(tags="测试用的Dome类")
public class DomeController{
    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;
    private static final Logger logger = LoggerFactory.getLogger(DomeController.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private DemoJobHandler demoJobHandler;//测试代码中调度用，不推荐这样使用
    @Resource(name= UsualMultiThreadConfig.BEANNAME)
    private ExecutorService executorService;
    @Autowired
    SendMessageImpl sendMessageImpl;
    @Autowired
    TestConsumerImpl testConsumerImpl;
    @Autowired
    private MongoTemplate mongoTemplate;
    /**
     * 居然有这么神奇的用法。写个接口就行。实现类都不用。
     * 查了下是spring data jpa，仅做了解吧。不过，对于这些若关系型的，用这种方式还是蛮不错的。
     */
    @Autowired
    private MongoRepositoryInter mongoRepositoryInter;
    /**
     * 和上面类似，也是springdata的写法
     */
    @Autowired
    private ElasticsearchRepositoryInter elasticsearchRepositoryInter;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    //策略模式生成订单
    @Autowired
    private SaveStrategyServiceInter testPlaceOrderStrategy;


    /**
     * 简单的测试
     * @param name
     * @return
     */
    @ApiOperation(value = "只是输出一个hello，最基本的测试")
    @GetMapping(value = "/hello")
    public String print(@RequestParam(value = "name",defaultValue = "Anonym",required = false) String name){
        logger.info(name + " Come on");
        return "Hi! " + name.toUpperCase() + ", Welcome to my world!";
    }
    /**
     * 集成mybatis+mysql
     * @return
     */
    @ApiOperation(value = "获取mysql的user表的所有数据")
    @GetMapping(value = "getAll")
    public  List<User>  getAll(){
        return userService.queryAll();
    }
    @ApiOperation(value = "新增一条记录，测试事务下的新增并发问题")
    @GetMapping(value = "/insertOne")
    public  String  insertOne(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                userService.insertOne();
            }
        };
        Thread thread = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        executorService.submit(thread);
        executorService.submit(thread2);
        return "success";
    }
    /**
     * 集成redis（使用了工具类redisUtil）
     */
    @ApiOperation(value = "获取redis中key为'name'的值是否存在")
    @GetMapping(value = "getByKey")
    public Object getByKey(@RequestParam(value = "key",defaultValue = "name",required = false) String key){
        return redisUtil.hasKey(key);
    }
    /**
     * 直接代码调用xxl-job,这样也可以直接调度，但是不推荐，因为调度器的日志并不能记录。这是当做一个正常方法去使用了
     */
    @ApiOperation(value = "代码调用xxl-job测试")
    @GetMapping(value = "runXxl")
    public Object runXxl()  throws Exception{
        return demoJobHandler.execute("1");
    }

    /**
     * 测试自定义的线程池是否需要调用shutdown()方法进行销毁
     */
    @ApiOperation(value = "测试自定义的线程池")
    @GetMapping(value = "testMulThread")
    public void testMulThread(@RequestParam(value = "num",defaultValue = "10",required = false) Integer num)  throws Exception{
        for(int i=0; i<num; i++){
            executorService.submit(new Runnable(){
                @Override
                public void run(){
                    try {
                        Thread.currentThread().sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    logger.info("线程启动" + Thread.currentThread().getName());
                }
            });
        }
    }
    /**
     *测试active的send方法。传递name和address参数。修改id为1的用户的名字。消息发送到第一个队列。消息会和队列绑定的？
     */
    @ApiOperation(value = "修改id为1的用户的名字")
    @GetMapping(value = "modifyNameByMQ")
    public void modifyNameByMQ(@RequestParam(value = "name",defaultValue = "ywq",required = false) String name,
                               @RequestParam(value = "address",defaultValue = "jx",required = false) String address
    ){
        User user = new User();
        user.setId(1);
        user.setName(name);
        user.setAddress(address);
        MessageDetail<User> messageDetail  =MessageCreateUtil.createUserDetail(user);
        String result = sendMessageImpl.sendMsg(messageDetail);
        if(!"true".equalsIgnoreCase(result)){
            logger.warn("mq消息发送失败");
        }
    }
    /**
     *测试dubbo消费者1
     */
    @ApiOperation(value = "测试dubbo消费者，获取名字和性别")
    @GetMapping(value = "dubboConsumerTest")
    public String dubboConsumerTest(){
        return "name:"+testConsumerImpl.getName()+",sex:"+testConsumerImpl.getSex();
    }
    /**
     *测试dubbo消费者2
     */
    @ApiOperation(value = "测试dubbo消费者，获取名字年龄")
    @GetMapping(value = "dubboConsumerTest1")
    public String dubboConsumerTest1(){
        return testConsumerImpl.getAge();
    }
    /**
     *测试dubbo消费者3
     */
    @ApiOperation(value = "测试dubbo消费者，获取梦想")
    @GetMapping(value = "dubboConsumerTest2")
    public String dubboConsumerTest2(){
        return testConsumerImpl.getDream();
    }
    /**
     *mongodb插入数据(增)
     */
    @ApiOperation(value = "mongodb插入数据(增)")
    @GetMapping(value = "mongodbInsertTest")
    public Object mongodbInsertTest(@RequestParam(value = "name",defaultValue = "俞武权",required = false) String name,
                                    @RequestParam(value = "collectionName",defaultValue = "userList",required = false) String collectionName){
        List<MongoUserObject> userList = new ArrayList<>();
        userList.add(new MongoUserObject(name,"earth:"+ DateUtil.get4yMdHm(new Date())));
        mongoTemplate.insert(userList, collectionName);
        return mongodbQueryTest("",collectionName);
    }
    /**
     *mongodb删除数据（删）
     */
    @ApiOperation(value = "mongodb删除数据（删）")
    @GetMapping(value = "mongodbDeleteTest")
    public Object mongodbDeleteTest(@RequestParam(value = "name",defaultValue = "俞武权",required = false) String name){
        Criteria criteria = new Criteria();
        criteria.and("name").is(name);
        Query query = new Query(criteria);
        mongoTemplate.remove(query,MongoUserObject.class,"userList");
        return mongodbQueryTest("","userList");
    }
    /**
     *mongodb更新数据（改）
     */
    @ApiOperation(value = "mongodb更新数据（改）")
    @GetMapping(value = "mongodbUpdateTest")
    public Object mongodbUpdateTest(@RequestParam(value = "originName",defaultValue = "俞武权",required = false) String originName,
                                    @RequestParam(value = "objectName",defaultValue = "ywq",required = false) String objectName){
        Criteria criteria = new Criteria();
        criteria.and("name").is(originName);
        Query query = new Query(criteria);
        Update update = new Update();
        update.set("name",objectName);
        mongoTemplate.updateMulti(query, update, MongoUserObject.class,"userList");
        return mongodbQueryTest("","userList");
    }
    /**
     *mongodb查询数据（查）
     */
    @ApiOperation(value = "mongodb精确查询数据（查）")
    @GetMapping(value = "mongodbQueryTest")
    public Object mongodbQueryTest(@RequestParam(value = "name",defaultValue = "",required = false) String name,
                                   @RequestParam(value = "collectionName",defaultValue = "userList",required = false) String collectionName){
        Criteria criteria = new Criteria();

        //条件方法一:
        // criteria.and("name").is("ywq");
        //条件方法二:
        criteria.orOperator(Criteria.where("name").regex("ywq"),//regex()里也可以传正则表达式
                Criteria.where("name").regex(name)
        );
        Query query = new Query(criteria);
        List<MongoUserObject> findList = mongoTemplate.find(query, MongoUserObject.class,collectionName);
        return findList;
    }
    /**
     * MongoRepository的使用
     */
    @ApiOperation(value = "MongoRepository的使用,居然不用写方法")
    @GetMapping(value = "MongoRepositoryTest")
    public Object MongoRepositoryTest(@RequestParam(value = "name",defaultValue = "",required = false) String name){
        return mongoRepositoryInter.findMongoUserObjectsByNameEquals(name);
    }
    /**
     * ElasticSearch的使用,查询数据
     */
    @ApiOperation(value = "ElasticSearch的查询(查)")
    @GetMapping(value = "ESQuery")
    public Object ESQuery(@RequestParam(value = "name",defaultValue = "",required = false) String name){
        return elasticsearchRepositoryInter.findAll();
    }
    /**
     * ElasticSearch的使用,查询数据
     */
    @ApiOperation(value = "ElasticSearch的增加(增)")
    @GetMapping(value = "ESSave")
    public Object ESSave(@RequestParam(value = "name",defaultValue = "",required = false) String name){
        ElasticsearchObject elasticsearchObject = new ElasticsearchObject("1l",name,"18","权的住址","俞家学校");
        return elasticsearchRepositoryInter.save(elasticsearchObject);
    }
    /**
     * ElasticSearch的使用,模糊查询数据
     */
    @ApiOperation(value = "ElasticSearch的查询(查)")
    @GetMapping(value = "ESQueryLike")
    public Object ESQueryLike(@RequestParam(value = "key",defaultValue = "",required = false) String name){
        return elasticsearchRepositoryInter.findByNameLike(name);
    }
    /**
     * ElasticSearch的使用,删除数据
     */
    @ApiOperation(value = "ElasticSearch的查询(删)")
    @GetMapping(value = "ESDelete")
    public Object ESDelete(@RequestParam(value = "key",defaultValue = "",required = false) String name){
        return elasticsearchRepositoryInter.deleteByNameEquals(name);
    }
    /**
     * 测试Special策略模式生成订单
     */
    @ApiOperation(value = "测试策略模式生成订单")
    @GetMapping(value = "strategyTest")
    public Object strategyTest(){
        Order order = new Order();
        testPlaceOrderStrategy.save(order);
        return order;
    }
    /**
     * 测试订单扣库存操作
     */
    @ApiOperation(value = "测试订单扣库存操作")
    @GetMapping(value = "delNum")
    public Object delNum(){
        return goodsService.updateGoodsInventory(1);
    }

    @ApiOperation(value = "获取二维码")
    @GetMapping(value="/createQRImage")
    public ResponseEntity<byte[]> getQRImage() {

        String text = "http://note.youdao.com/noteshare?id=8f72113e99d0c1a5b2e62395c10e22fb&sub=EB55DD4222D84A5ABE4526C1AE028A75";//二维码内的信息（扫描后能看见）

        byte[] qrcode = null;
        try {
            qrcode = getQRCode(text, 400, 400);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(qrcode, headers, HttpStatus.CREATED);//返回图片
    }

    public static byte[] getQRCode(String text, int width, int height) throws Exception {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height,hints);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }

}
