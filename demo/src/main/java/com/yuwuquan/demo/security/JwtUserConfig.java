package com.yuwuquan.demo.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix="jwt")
public class JwtUserConfig {

	private List<User> users;
	
	private List<String> urls;
	
	private List<String> registerIps;
	
}
