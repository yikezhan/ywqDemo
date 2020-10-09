package com.yuwuquan.demo.service;

import com.yuwuquan.demo.common.PaginationDTO;
import com.yuwuquan.demo.orm.model.PublishTask;

import java.util.List;

public interface PublishTaskService {
    void createTask(PublishTask publishTask) throws Exception;
    List<PublishTask> queryTask(PublishTask publishTask, PaginationDTO paginationDTO);
    List<PublishTask> publishTask(Long id);
}
