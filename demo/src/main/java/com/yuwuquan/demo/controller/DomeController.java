package com.yuwuquan.demo.controller;

import com.yuwuquan.demo.orm.model.User;
import com.yuwuquan.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ResponseBody
@Controller
public class DomeController {
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(DomeController.class);
    @GetMapping(value = "/hello")
    public String print(@RequestParam(value = "name",defaultValue = "Anonym",required = false) String name){
        logger.info(name + " Come on");
        return "Hi! " + name.toUpperCase() + ", Welcome to my world!";
    }

    @GetMapping(value = "getAll")
    public  List<User>  getAll(){
        QueryCondition queryCondition = new
        return userService.queryAll();
    }
}
