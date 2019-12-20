package com.yuwuquan.demo.service.impl;

import com.yuwuquan.demo.common.ResponseDTO;
import com.yuwuquan.demo.orm.dao.TaskMapper;
import com.yuwuquan.demo.orm.model.PublishTask;
import com.yuwuquan.demo.orm.model.SysTaskInfo;
import com.yuwuquan.demo.service.PublishTaskService;
import com.yuwuquan.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PublishTaskServiceImpl implements PublishTaskService {

    @Override
    public ResponseDTO addPublishTask(PublishTask publishTask) {

        return null;
    }

    @Override
    public ResponseDTO startPublishTask(PublishTask publishTask) {
        return null;
    }

    @Override
    public ResponseDTO pausePublishTask(PublishTask publishTask) {
        return null;
    }

    @Override
    public ResponseDTO cancelPublishTask(PublishTask publishTask) {
        return null;
    }

    @Override
    public List<PublishTask> queryPublishTask(PublishTask publishTask) {
        return null;
    }
}
