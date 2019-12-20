package com.yuwuquan.demo.orm.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
@ApiModel
public class PublishTask extends BaseTable implements Serializable {
    private static final long serialVersionUID = 668543345682101751L;
    @ApiModelProperty(value = "主键id", example = "1", dataType = "Integer")
    Long id;
    @ApiModelProperty(value = "外键，关联的任务", example = "1", dataType = "Integer")
    Long fk_sys_task;
    @ApiModelProperty(value = "发布的用户", example = "1", dataType = "Integer")
    Long fk_sys_user;
    @ApiModelProperty(value = "标题", example = "需要20层以上，满足的接。答案为点击后显示的用户名")
    String title;
    @ApiModelProperty(value = "问题描述/任务", example = "www.baidu.com")
    String question;
    @ApiModelProperty(value = "问题对应的答案", example = "ywq")
    String answer;
    @ApiModelProperty(value = "任务过期时间", example = "2020-11-11", dataType = "Date")
    Date remainTime;
    @ApiModelProperty(value = "确认方式，0手动确认，1自动确认", example = "0", dataType = "Integer")
    Integer confirmType;
    @ApiModelProperty(value = "需要的人数。初始化后即不可再变", example = "10", dataType = "Integer")
    Integer needAmount;
    @ApiModelProperty(value = "等待发布(入队)的人数。初始化时=need_amount,每回归一个未完成的，则值+1", example = "10", dataType = "Integer")
    Integer waitAmount;
    @ApiModelProperty(value = "需要的用户类型，0普通用户，1高质量用户", example = "0", dataType = "Integer")
    Integer needUserType;
    @ApiModelProperty(value = "支付状态，已付款才可入队列。0未支付，1已支付", example = "0", dataType = "Integer")
    Integer paymentStatus;
    @ApiModelProperty(value = "发布状态，已发布才可入队列。0未发布，1已发布", example = "0", dataType = "Integer")
    Integer publishStatus;
    @ApiModelProperty(value = "审核状态,已审核才可入队列。0未审核，1已审核通过，2审核不通过", example = "0", dataType = "Integer")
    Integer reviewStatus;
    @ApiModelProperty(value = "总金额，\"分\"为单位", example = "0", dataType = "Integer")
    Integer totalPrice;
    @ApiModelProperty(value = "任务被拒绝的次数", example = "0", dataType = "Integer")
    Integer refuseAmount;
    @ApiModelProperty(value = "已分发出去的任务次数", example = "0", dataType = "Integer")
    Integer allocationAmount;
    @ApiModelProperty(value = "已完成的任务次数", example = "0", dataType = "Integer")
    Integer finishAmount;
    @ApiModelProperty(value = "入队次数", example = "0", dataType = "Integer")
    Integer sendTimes;
    @ApiModelProperty(value = "最近一次发送时间", example = "2020-12-12", dataType = "Date")
    Integer lastSendTime;
}