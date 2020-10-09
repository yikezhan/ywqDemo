package com.yuwuquan.demo.orm.model;

import com.yuwuquan.demo.sysenum.PublishTaskEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 * 任务一经发布，不可再变。未完成部分的金额会在超过过期时间后自动退回。
 */
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
    @ApiModelProperty(value = "需要的人数。初始化后即不可再变", example = "10", dataType = "Integer")
    Integer needAmount;
    @ApiModelProperty(value = "已完成的任务次数", example = "0", dataType = "Integer")
    Integer finishAmount;
    @ApiModelProperty(value = "任务状态。包含支付状态，审核状态，发布状态等.14=8+4+2", example = "12", dataType = "Integer")
    Integer taskStatus;
    @ApiModelProperty(value = "总金额，\"分\"为单位", example = "0", dataType = "Integer")
    Integer totalPrice;
    public boolean checkAduitStatus(){
        if(taskStatus == null) return false;
        if((taskStatus.intValue() & PublishTaskEnum.AduitPass.getCode()) == 0){
            return false;
        }else{
            return true;
        }
    }
    public boolean checkPaymentStatus(){
        if(taskStatus == null) return false;
        if((taskStatus.intValue() & PublishTaskEnum.PaymentSuccess.getCode()) == 0){
            return false;
        }else{
            return true;
        }
    }
    public boolean checkPublishStatus(){
        if(taskStatus == null) return false;
        if((taskStatus.intValue() & PublishTaskEnum.PublishSuccess.getCode()) == 0){
            return false;
        }else{
            return true;
        }
    }
}