package com.yuwuquan.demo.service.impl;

import com.github.pagehelper.Page;
import com.yuwuquan.demo.orm.dao.TaskMapper;
import com.yuwuquan.demo.orm.model.SysTaskInfo;
import com.yuwuquan.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public List<SysTaskInfo> queryAllTask(SysTaskInfo sysTaskInfo) {
        return taskMapper.queryAllTask(sysTaskInfo);
    }
}
