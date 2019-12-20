package com.yuwuquan.demo.service;


import com.yuwuquan.demo.common.ResponseDTO;
import com.yuwuquan.demo.orm.model.PublishTask;
import com.yuwuquan.demo.orm.model.SysTaskInfo;

import java.util.List;

public interface PublishTaskService {
    /**
     * 初始化发布任务
     * @param publishTask
     * @return
     */
    ResponseDTO addPublishTask(PublishTask publishTask);

    /**
     * 发布任务
     * @param publishTask
     * @return
     */
    ResponseDTO startPublishTask(PublishTask publishTask);

    /**
     * 暂停任务。设置publish_status=0
     * @param publishTask
     * @return
     */
    ResponseDTO pausePublishTask(PublishTask publishTask);

    /**
     * 取消任务。设置isvalid=0
     * @param publishTask
     * @return
     */
    ResponseDTO cancelPublishTask(PublishTask publishTask);

    /**
     * 查询当前任务情况。
     * @param publishTask
     * @return
     */
    List<PublishTask> queryPublishTask(PublishTask publishTask);

}
