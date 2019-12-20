package com.yuwuquan.demo.orm.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
@ApiModel
public class ReceivedTask extends BaseTable implements Serializable {
    private static final long serialVersionUID = 668543345682101751L;
    @ApiModelProperty(value = "主键id", example = "1", dataType = "Integer")
    Long id;
    @ApiModelProperty(value = "外键，关联的任务", example = "1", dataType = "Integer")
    Long fk_sys_task;
    @ApiModelProperty(value = "接收的任务外键", example = "1", dataType = "Integer")
    Long fk_publish_task;
    @ApiModelProperty(value = "接收的用户", example = "1", dataType = "Integer")
    Long fk_sys_user;
    @ApiModelProperty(value = "提交的答案凭证", example = "ywq")
    String answer;
    @ApiModelProperty(value = "任务状态。0未完成，1已完成，2无法完成", example = "0", dataType = "Integer")
    Integer answerStatus;
    @ApiModelProperty(value = "答案审核结果。0未审核，1审核通过，2审核不通过", example = "10", dataType = "Integer")
    Integer reviewStatus;
    @ApiModelProperty(value = "是否接收了该任务。0不接受，1接受，2刚分配， 3过期自动放弃", example = "10", dataType = "Integer")
    Integer isReceived;
}