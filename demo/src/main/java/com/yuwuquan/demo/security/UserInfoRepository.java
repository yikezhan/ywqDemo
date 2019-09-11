package com.yuwuquan.demo.security;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserInfoRepository {
	
	@Autowired
	private JwtUserConfig config;
	
	public JwtUser findUserInfoByName(String userName) {
		JwtUser user = null;
		List<User> users = config.getUsers();
		if(!CollectionUtils.isEmpty(users)){
			for (User userInfo : users) {
				if(userInfo.getUserName().equals(userName)){
					user = JwtUserFactory.create(userInfo);
				}
			}
		}
		return user;
	}

}
