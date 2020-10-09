package com.yuwuquan.demo.orm.dao;


import com.yuwuquan.demo.orm.model.ReceivedTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReceivedTaskMapper {
    void insertTask(ReceivedTask receivedTask);
    void updateBySelect(ReceivedTask receivedTask);
    List<ReceivedTask> queryTask(ReceivedTask receivedTask);
}