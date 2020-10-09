package com.yuwuquan.demo.activemq.message;

import com.yuwuquan.demo.activemq.constant.MqQueueEnum;
import com.yuwuquan.demo.activemq.message.messagedetail.FirstKindMessageDetail;
import com.yuwuquan.demo.activemq.message.messagedetail.SecondKindMessageDetail;
import com.yuwuquan.demo.activemq.message.template.MessageDetail;
import com.yuwuquan.demo.orm.model.SysUserInfo;
import com.yuwuquan.demo.hatch_seckillsys.dto.SecKillGoodsDTO;

/**
 * 简单工厂，从这里可以创建出不同类型的消息。该消息属于哪个队列也在这里进行设定。疑问：消息怎么和队列绑定的？如果一种消息可以发送到多个队列要手动逻辑控制？
 */
public class MessageCreateUtil {
    //FirstKindMessageDetail类型的消息可以放在第一个队列
    public static MessageDetail<FirstKindMessageDetail> createFirstKindMessageDetail1(FirstKindMessageDetail firstKindMessageDetail) {
        MessageDetail<FirstKindMessageDetail> messageDetail = new MessageDetail<>();
        messageDetail.setT(firstKindMessageDetail);
        messageDetail.setTType(firstKindMessageDetail.getClass());
        messageDetail.setProcessType(MqQueueEnum.FIRSR_QUEUE.getCode());
        messageDetail.setQueueName(MqQueueEnum.FIRSR_QUEUE.getName());
        return messageDetail;
    }
    //FirstKindMessageDetail类型的消息也可以放在第二个队列
    public static MessageDetail<FirstKindMessageDetail> createFirstKindMessageDetail2(FirstKindMessageDetail firstKindMessageDetail) {
        MessageDetail<FirstKindMessageDetail> messageDetail = new MessageDetail<>();
        messageDetail.setT(firstKindMessageDetail);
        messageDetail.setTType(firstKindMessageDetail.getClass());
        messageDetail.setProcessType(MqQueueEnum.FIRSR_QUEUE.getCode());
        messageDetail.setQueueName(MqQueueEnum.SECOND_QUEUE.getName());
        return messageDetail;
    }
    //SecondKindMessageDetail类型的消息可以放在第三个队列
    public static MessageDetail<SecondKindMessageDetail> createSecondKindMessageDetail(SecondKindMessageDetail secondKindMessageDetail) {
        MessageDetail<SecondKindMessageDetail> messageDetail = new MessageDetail<>();
        messageDetail.setT(secondKindMessageDetail);
        messageDetail.setTType(secondKindMessageDetail.getClass());
        messageDetail.setProcessType(MqQueueEnum.THIRD_QUEUE.getCode());
        messageDetail.setQueueName(MqQueueEnum.THIRD_QUEUE.getName());
        return messageDetail;
    }

    //创建user类型的消息，包含name，address，测试接收端转为FirstKindMessageDetail
    public static MessageDetail<SysUserInfo> createUserDetail(SysUserInfo user) {
        MessageDetail<SysUserInfo> messageDetail = new MessageDetail<>();
        messageDetail.setT(user);
        messageDetail.setTType(user.getClass());
        messageDetail.setProcessType(MqQueueEnum.FORTH_QUEUE.getCode());
        messageDetail.setQueueName(MqQueueEnum.FORTH_QUEUE.getName());
        return messageDetail;
    }
    //创建秒杀实体类信息
    public static MessageDetail<SecKillGoodsDTO> createSecKillInfo(SecKillGoodsDTO secKillGoodsDTO) {
        MessageDetail<SecKillGoodsDTO> messageDetail = new MessageDetail<>();
        messageDetail.setT(secKillGoodsDTO);
        messageDetail.setTType(secKillGoodsDTO.getClass());
        messageDetail.setProcessType(MqQueueEnum.SEC_KILL_QUEUE.getCode());
        messageDetail.setQueueName(MqQueueEnum.SEC_KILL_QUEUE.getName());
        return messageDetail;
    }

}
