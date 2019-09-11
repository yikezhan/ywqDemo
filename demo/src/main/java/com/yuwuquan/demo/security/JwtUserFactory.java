package com.yuwuquan.demo.security;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class JwtUserFactory {

	public static JwtUser create(User user) {
		return new JwtUser(user.getId(), user.getUserName(), user.getPassword(),
				mapToGrantedAuthorities(user.getRoles()), user.getLastPasswordResetDate());
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
		return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}
}
