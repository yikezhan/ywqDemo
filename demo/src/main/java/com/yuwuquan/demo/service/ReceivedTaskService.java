package com.yuwuquan.demo.service;

import com.yuwuquan.demo.common.PaginationDTO;
import com.yuwuquan.demo.orm.model.ReceivedTask;

import java.util.List;


public interface ReceivedTaskService {
    void receivedTask(ReceivedTask receivedTask);
    boolean commitAnswer(Long id, String answer, boolean isGiveUp);
    List<ReceivedTask> queryTask(ReceivedTask receivedTask, PaginationDTO paginationDTO);
}
