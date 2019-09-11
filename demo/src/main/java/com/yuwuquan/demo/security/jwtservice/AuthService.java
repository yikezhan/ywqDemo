package com.yuwuquan.demo.security.jwtservice;

import com.yuwuquan.demo.security.User;

public interface AuthService {
	
    String login(String username, String password);
    
    String refresh(String oldToken);
    
    User register(User userToAdd);
}
