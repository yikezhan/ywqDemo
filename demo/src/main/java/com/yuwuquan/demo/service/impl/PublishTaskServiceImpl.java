package com.yuwuquan.demo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yuwuquan.demo.common.PaginationDTO;
import com.yuwuquan.demo.exception.ApplicationException;
import com.yuwuquan.demo.orm.dao.PublishTaskMapper;
import com.yuwuquan.demo.orm.model.PublishTask;
import com.yuwuquan.demo.orm.model.SysUserInfo;
import com.yuwuquan.demo.service.PublishTaskService;
import com.yuwuquan.demo.session.SysContent;
import com.yuwuquan.demo.sysenum.PublishTaskEnum;
import com.yuwuquan.demo.util.common.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
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

    /**使用RocketMq的生产者*/
    @Autowired
    private DefaultMQProducer defaultMQProducer;

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

    private void publishTaskCheck(Long id) {
        if(id == null) throw new ApplicationException("发布任务id为空", -1);
    }
    @Override
    public void publishTask(Long id) throws Exception{
        SysUserInfo s = new SysUserInfo("123");
        s.setId(1l);
        SysContent.setOperator(s);

        publishTaskCheck(id);
        PublishTask publishTask = new PublishTask();
        publishTask.setFk_sys_user(SysContent.getCurrentOperator().getId());
        publishTask.setId(id);
        List<PublishTask> publishTasks = publishTaskMapper.queryTask(publishTask);
        if(CollectionUtils.isEmpty(publishTasks)) throw new ApplicationException("发布任务不存在",-1);
        publishTask = publishTasks.get(0);
        if(!publishTask.checkAduitStatus()) throw new ApplicationException("尚未审核通过",-1);
        if(!publishTask.checkPaymentStatus()) throw new ApplicationException("尚未付款",-1);
        if(publishTask.checkPublishStatus()) throw new ApplicationException("已在发布中",-1);

        PublishTask tmp = new PublishTask();
        tmp.setId(publishTask.getId());
        tmp.setTaskStatus(publishTask.getTaskStatus() | PublishTaskEnum.PublishSuccess.getCode());
        publishTaskMapper.updateTask(tmp);

        //发布到mq中
        String msg = "demo msg test by ywq";
        logger.info("开始发送消息："+msg);
        Message sendMsg = new Message("demoTopic","demoTag",msg.getBytes());
        //默认3秒超时
        SendResult sendResult = defaultMQProducer.send(sendMsg);
        logger.info("消息发送响应信息："+sendResult.toString());
    }

}
