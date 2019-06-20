package com.yuwuquan.demo.controller;

import com.yuwuquan.demo.job.DemoJobHandler;
import com.yuwuquan.demo.orm.model.User;
import com.yuwuquan.demo.service.UserService;
import com.yuwuquan.demo.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Api(tags="测试用的Dome类")
@ResponseBody
@Controller
public class DomeController {
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(DomeController.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private DemoJobHandler demoJobHandler;


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
    public Object runXxl(@RequestParam(value = "key",defaultValue = "name",required = false) String key)  throws Exception{
        return demoJobHandler.execute("1");
    }
}
