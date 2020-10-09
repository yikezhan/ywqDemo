package com.yuwuquan.demo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yuwuquan.demo.common.PaginationDTO;
import com.yuwuquan.demo.exception.ApplicationException;
import com.yuwuquan.demo.orm.dao.PublishTaskMapper;
import com.yuwuquan.demo.orm.model.PublishTask;
import com.yuwuquan.demo.service.PublishTaskService;
import com.yuwuquan.demo.session.SysContent;
import com.yuwuquan.demo.util.common.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PublishTaskServiceImpl implements PublishTaskService {
    private static final Logger logger = LoggerFactory.getLogger(PublishTaskServiceImpl.class);
    @Autowired
    private  PublishTaskMapper publishTaskMapper;

    private void createTaskCheck(PublishTask publishTask) {
        if(publishTask.getRemainTime() == null || publishTask.getRemainTime().before(DateUtil.addDay(new Date(),1)))
            throw new ApplicationException("任务过期时间至少为1天" , -1);
        if(publishTask.getTotalPrice() == null || publishTask.getTotalPrice().intValue() <100)
            throw new ApplicationException("任务金额至少为1元" , -1);
    }
    @Override
    public void createTask(PublishTask publishTask) throws Exception{
        try {
            createTaskCheck(publishTask);
            Long userId = SysContent.getCurrentOperator().getId();
            publishTask.setFk_sys_user(userId);
            publishTask.setFkUserCreate(userId.intValue());
            publishTask.setFkUserModify(userId.intValue());
            publishTaskMapper.insertTask(publishTask);
        } catch (ApplicationException e){
            throw e;
        } catch (Exception e){
            logger.error("发布任务出错",e);
            throw e;
        }
    }

    @Override
    public List<PublishTask> queryTask(PublishTask publishTask, PaginationDTO paginationDTO) {
        publishTask.setFk_sys_user(SysContent.getCurrentOperator().getId());
        Page<PublishTask> publishTaskPage = PageHelper.startPage(paginationDTO.getPageNumber(),
                paginationDTO.getPageSize(), null);
        publishTaskMapper.queryTask(publishTask);
        return publishTaskPage.getResult();
    }

    private PublishTask publishTaskCheck(Long id) {
        if(id == null) throw new ApplicationException("发布任务id为空", -1);
    }
    @Override
    public List<PublishTask> publishTask(Long id) {
        publishTaskCheck(id);
        PublishTask publishTask = new PublishTask();
        publishTask.setFk_sys_user(SysContent.getCurrentOperator().getId());
        publishTask.setId(id);
        List<PublishTask> publishTasks = publishTaskMapper.queryTask(publishTask);
        if(CollectionUtils.isEmpty(publishTasks)) throw new ApplicationException("发布任务不存在",-1);

        PublishTask tmp = new PublishTask();
        tmp.setId(publishTasks.get(0).getId());
        tmp.setTaskStatus(publishTasks.get(0).);
        publishTaskMapper.updateTask(publishTasks.get(0));
        return null;
    }

}
