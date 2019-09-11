package com.yuwuquan.demo.login.service.impl;

import com.yuwuquan.demo.login.account.User;
import com.yuwuquan.demo.login.service.AuthenticationService;
import com.yuwuquan.demo.login.service.common.BaseService;
import com.yuwuquan.demo.orm.dao.UserMapper;
import com.yuwuquan.demo.util.encryption.PwdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;


@Service
@CacheConfig(cacheNames="AuthenticationService")
public class AuthenticationServiceImpl extends BaseService implements AuthenticationService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public boolean isUserVailid(User user) {
		return true;
	}

	@Override
	public User queryUser(@NotNull String userName, @NotNull String password) {
		
		User user = new User();
		user.setUsername(userName);
		user.setPassword(PwdUtils.encryptPassword(userName,password));
		user = getDozerMapper().map(userMapper.selectByPrimaryKey(1),User.class);
		return user;
	}

}
