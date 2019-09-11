package com.yuwuquan.demo.login.service;

import com.yuwuquan.demo.login.account.User;

public interface AuthenticationService {

	
	public boolean isUserVailid(User user);
	
	public User queryUser(String userName, String password);

}
