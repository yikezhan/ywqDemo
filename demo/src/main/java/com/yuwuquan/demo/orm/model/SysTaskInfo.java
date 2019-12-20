package com.yuwuquan.demo.orm.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
@ApiModel
public class SysTaskInfo extends BaseTable implements Serializable {
    private static final long serialVersionUID = 668543345682101751L;
    @ApiModelProperty(value = "主键id", example = "1", dataType = "Integer")
    Integer id;
    @ApiModelProperty(value = "任务名", example = "天猫盖楼活动")
    String taskName;
    @ApiModelProperty(value = "任务描述", example = "互组盖楼，按楼层付费")
    String taskDesc;
}