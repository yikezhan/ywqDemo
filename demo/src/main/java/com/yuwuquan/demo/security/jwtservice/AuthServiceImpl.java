package com.yuwuquan.demo.security.jwtservice;

import com.google.common.collect.Lists;
import com.yuwuquan.demo.security.JwtTokenUtil;
import com.yuwuquan.demo.security.JwtUser;
import com.yuwuquan.demo.security.User;
import com.yuwuquan.demo.security.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnExpression("${jwt.enabled:true}")
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserInfoRepository userRepository;
	
	@Value("${jwt.tokenHead}")
    private String tokenHead;
	
	 @Override
	public User register(User userToAdd) {
		final String username = userToAdd.getUserName();
		if (userRepository.findUserInfoByName(username) != null) {
			return null;
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		final String rawPassword = userToAdd.getPassword();
		userToAdd.setPassword(encoder.encode(rawPassword));
		userToAdd.setRoles(Lists.newArrayList("ROLE_USER"));
		return userToAdd;
	}

	@Override
	public String login(String username, String password) {
		UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
	}

	@Override
	public String refresh(String oldToken) {
		 final String token = oldToken.substring(tokenHead.length());
	        String username = jwtTokenUtil.getUsernameFromToken(token);
	        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
	        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
	            return jwtTokenUtil.refreshToken(token);
	        }
	        return null;
	}

}
