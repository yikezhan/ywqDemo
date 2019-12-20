package com.yuwuquan.demo.service;


import com.yuwuquan.demo.orm.model.SysTaskInfo;

import java.util.List;

public interface TaskService {
    List<SysTaskInfo> queryAllTask(SysTaskInfo sysTaskInfo);
}
