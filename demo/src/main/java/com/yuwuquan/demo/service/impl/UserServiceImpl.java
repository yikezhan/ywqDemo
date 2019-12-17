package com.yuwuquan.demo.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yuwuquan.demo.exception.ApplicationException;
import com.yuwuquan.demo.orm.dao.UserMapper;
import com.yuwuquan.demo.orm.model.SysUserInfo;
import com.yuwuquan.demo.service.UserService;
import com.yuwuquan.demo.util.common.StringUtil;
import com.yuwuquan.demo.util.common.UniqueIdentificationUtil;
import com.yuwuquan.demo.util.encryption.PwdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public void insertUser(SysUserInfo user) throws ApplicationException {
        if(user.getPhone() != null && user.getPassword() != null){
            user.setPassword(PwdUtils.encryptPassword(user.getPhone(),user.getPassword()));
            user.setUnique_id(UniqueIdentificationUtil.getNewUserUnique());
            userMapper.insertUser(user);
        }else{
            throw new ApplicationException("参数校验失败" , -1);
        }
    }

    @Override
    public SysUserInfo queryByUser(SysUserInfo user) {
        if(user != null && StringUtil.isNotBlank(user.getPhone())){
            List<SysUserInfo> sysUserInfos = userMapper.queryByUser(user);
            if(CollectionUtils.isNotEmpty(sysUserInfos) && sysUserInfos.size()==1){
                return sysUserInfos.get(0);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
}
