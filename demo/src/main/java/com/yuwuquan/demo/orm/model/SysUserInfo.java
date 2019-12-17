package com.yuwuquan.demo.orm.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
@ApiModel
public class SysUserInfo extends BaseTable implements Serializable {
    public SysUserInfo() {
    }
    public SysUserInfo(String phone) {
        this.phone = phone;
    }
    private static final long serialVersionUID = 668543345682101751L;
    private Long id;
    private String unique_id;
    @NonNull
    @ApiModelProperty(value = "手机号，登录用户名", example = "13127951111")
    private String phone;

    @ApiModelProperty(value = "用户名，可空。用手机号代替", example = "ywq")
    private String username;

    @ApiModelProperty(value = "密码", example = "19931111")
    private String password ;

    @ApiModelProperty(value = "真实姓名", example = "俞武权")
    private String name;

    @ApiModelProperty(value = "性别。0男，1女", example = "1", dataType = "Integer")
    private int sex;
}