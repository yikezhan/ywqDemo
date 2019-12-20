package com.yuwuquan.demo.orm.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class UserReceivedTask extends BaseTable implements Serializable {
    private static final long serialVersionUID = 668543345682101751L;
    @ApiModelProperty(value = "主键id", example = "1", dataType = "Integer")
    Long id;
    @ApiModelProperty(value = "外键，关联的任务", example = "1", dataType = "Integer")
    Long fk_sys_task;
    @ApiModelProperty(value = "接收的用户", example = "1", dataType = "Integer")
    Long fk_sys_user;
    @ApiModelProperty(value = "当前能接收的任务数", example = "0", dataType = "Integer")
    Integer received_amount;
}