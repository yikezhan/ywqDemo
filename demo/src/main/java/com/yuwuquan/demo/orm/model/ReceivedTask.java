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
    @ApiModelProperty(value = "提交状态，0未提交，1已提交，3放弃", example = "0", dataType = "Integer")
    Integer commitStatus;
    @ApiModelProperty(value = "答案结果。0错误，1正确，空则表示未作答", example = "0", dataType = "Integer")
    Integer answerStatus;
}