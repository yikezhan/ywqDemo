package com.yuwuquan.demo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yuwuquan.demo.common.PaginationDTO;
import com.yuwuquan.demo.orm.dao.PublishTaskMapper;
import com.yuwuquan.demo.orm.dao.ReceivedTaskMapper;
import com.yuwuquan.demo.orm.model.PublishTask;
import com.yuwuquan.demo.orm.model.ReceivedTask;
import com.yuwuquan.demo.orm.model.SysUserInfo;
import com.yuwuquan.demo.service.ReceivedTaskService;
import com.yuwuquan.demo.session.SysContent;
import com.yuwuquan.demo.sysenum.ReceivedTaskEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceivedTaskServiceImpl implements ReceivedTaskService {
    private static final Logger logger = LoggerFactory.getLogger(ReceivedTaskServiceImpl.class);
    @Autowired
    private ReceivedTaskMapper receivedTaskMapper;
    @Autowired
    private PublishTaskMapper publishTaskMapper;

    @Override
    public void receivedTask(ReceivedTask receivedTask) {//拉取，接受任务
        receivedTask.setFk_sys_user(SysContent.getCurrentOperator().getId());
        receivedTaskMapper.insertTask(receivedTask);
    }

    boolean MatchingAnswer(SysUserInfo sysUserInfo, String correctAnswer, String answer){
        logger.info("用户{}回答的答案:{};正确答案为：{}",sysUserInfo.getId(),answer,correctAnswer);
        return correctAnswer.trim().equals(answer.trim());
    }
    @Override
    public boolean commitAnswer(Long id, String answer, boolean isGiveUp) {//提交问题
        ReceivedTask receivedTask = new ReceivedTask();
        receivedTask.setId(id);
        receivedTask.setFk_sys_user(SysContent.getCurrentOperator().getId());
        if(isGiveUp){
            receivedTask.setCommitStatus(ReceivedTaskEnum.CommitEnum.GiveUpCommit.getCode());
            receivedTaskMapper.updateBySelect(receivedTask);
            return false;
        }

        List<ReceivedTask>  receivedTasks = receivedTaskMapper.queryTask(receivedTask);
        PublishTask publishTask = new PublishTask();
        publishTask.setId(receivedTasks.get(0).getFk_publish_task());
        List<PublishTask> publishTasks = publishTaskMapper.queryTask(publishTask);


        receivedTask.setAnswer(answer);
        receivedTask.setCommitStatus(ReceivedTaskEnum.CommitEnum.Commit.getCode());
        if(MatchingAnswer(SysContent.getCurrentOperator(),publishTasks.get(0).getAnswer(),answer)){//回答正确
            receivedTask.setAnswerStatus(ReceivedTaskEnum.AnswerEnum.TRUE.getCode());
            receivedTaskMapper.updateBySelect(receivedTask);
            return true;
        }else{//回答错误
            receivedTask.setAnswerStatus(ReceivedTaskEnum.AnswerEnum.FALSE.getCode());
            receivedTaskMapper.updateBySelect(receivedTask);
            return false;
        }
    }

    @Override
    public List<ReceivedTask> queryTask(ReceivedTask receivedTask, PaginationDTO paginationDTO) {//查询当前用户接受的任务列表
        receivedTask.setFk_sys_user(SysContent.getCurrentOperator().getId());
        Page<ReceivedTask> receivedTaskPage = PageHelper.startPage(paginationDTO.getPageNumber(),
                paginationDTO.getPageSize(), null);
        receivedTaskMapper.queryTask(receivedTask);
        return receivedTaskPage.getResult();
    }
}
