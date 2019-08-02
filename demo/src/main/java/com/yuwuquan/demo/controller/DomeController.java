package com.yuwuquan.demo.controller;

import com.yuwuquan.demo.activemq.message.messagedetail.FirstKindMessageDetail;
import com.yuwuquan.demo.activemq.message.messagedetail.SecondKindMessageDetail;
import com.yuwuquan.demo.dubbo.consumer.TestConsumer;
import com.yuwuquan.demo.dubbo.consumer.impl.TestConsumerImpl;
import com.yuwuquan.demo.job.DemoJobHandler;
import com.yuwuquan.demo.activemq.message.MessageCreateUtil;
import com.yuwuquan.demo.activemq.message.template.MessageDetail;
import com.yuwuquan.demo.activemq.send.impl.SendMessageImpl;
import com.yuwuquan.demo.orm.dto.MongoUserObject;
import com.yuwuquan.demo.orm.model.User;
import com.yuwuquan.demo.service.MongoRepositoryInter;
import com.yuwuquan.demo.service.UserService;
import com.yuwuquan.demo.util.RedisUtil;
import com.yuwuquan.demo.util.common.DateUtil;
import com.yuwuquan.demo.util.common.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/demo")
@Api(tags="测试用的Dome类")
public class DomeController{
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(DomeController.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private DemoJobHandler demoJobHandler;//测试代码中调度用，不推荐
    @Resource(name= "MqExecutorService")//该线程池是mq用的，这里使用是为了测试
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


}
