package com.yuwuquan.demo.orm.dao;


import com.yuwuquan.demo.orm.model.SysTaskInfo;
import com.yuwuquan.demo.orm.model.SysUserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysTaskInfoMapper {
    List<SysTaskInfo> queryAllTask(SysTaskInfo sysTaskInfo);
}