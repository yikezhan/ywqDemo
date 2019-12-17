package com.yuwuquan.demo.orm.dao;


import com.yuwuquan.demo.orm.model.SysUserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    void insertUser(SysUserInfo user);
    List<SysUserInfo> queryByUser(SysUserInfo user);
    void updatePasswordByPhone(SysUserInfo user);

}