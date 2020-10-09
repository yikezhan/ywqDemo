package com.yuwuquan.demo.orm.dao;


import com.yuwuquan.demo.orm.model.PublishTask;
import com.yuwuquan.demo.orm.model.SysTaskInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PublishTaskMapper {
    void insertTask(PublishTask publishTask);
    List<PublishTask> queryTask(PublishTask publishTask);
}