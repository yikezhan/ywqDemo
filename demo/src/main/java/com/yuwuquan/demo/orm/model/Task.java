package com.yuwuquan.demo.orm.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
@ApiModel
public class Task extends BaseTable implements Serializable {
    public Task() {
    }
    private static final long serialVersionUID = 668543345682101751L;
    private Long id;
    private String taskName;
    @NonNull
    @ApiModelProperty(value = "手机号，登录用户名", example = "13127951111")
    private String question;

    @ApiModelProperty(value = "用户名，可空。用手机号代替", example = "ywq")
    private String answer;

    @ApiModelProperty(value = "支付总金额", example = "19931111")
    private long totalPrice ;

    @ApiModelProperty(value = "真实姓名", example = "俞武权")
    private String name;

    @ApiModelProperty(value = "性别。0男，1女", example = "1", dataType = "Integer")
    private int sex;
}