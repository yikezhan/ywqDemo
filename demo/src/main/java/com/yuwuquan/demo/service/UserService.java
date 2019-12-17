package com.yuwuquan.demo.service;

import com.yuwuquan.demo.orm.model.SysUserInfo;

public interface UserService {
    void insertUser(SysUserInfo user);
    SysUserInfo queryByUser(SysUserInfo user);
}
