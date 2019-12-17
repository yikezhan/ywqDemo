package com.yuwuquan.demo.service;

import com.yuwuquan.demo.orm.model.SysUserInfo;

public interface UserService {
    void insertUser(SysUserInfo user);
//    void updateUser(SysUserInfo user);//暂不支持改用户信息操作。
    void updatePasswordByPhone(SysUserInfo user);
    SysUserInfo queryByUser(SysUserInfo user);
}
