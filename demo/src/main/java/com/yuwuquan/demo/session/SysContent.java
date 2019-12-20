package com.yuwuquan.demo.session;

import com.yuwuquan.demo.orm.model.SysUserInfo;

public class SysContent {
    private static ThreadLocal<SysUserInfo> USER_LOCAL = new ThreadLocal<>();
    public static void setOperator(SysUserInfo sysUserInfo) {
        USER_LOCAL.set(sysUserInfo);
    }
    public static SysUserInfo getCurrentOperator() {
        SysUserInfo sysUserInfo = USER_LOCAL.get();
        return sysUserInfo;
    }
}
