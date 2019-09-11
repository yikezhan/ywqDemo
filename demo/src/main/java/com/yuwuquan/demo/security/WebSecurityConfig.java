package com.yuwuquan.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private JwtUserConfig userConfig;
    
    @Autowired(required=false)
    private JwtAuthenticationTokenFilter filter;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
        // 设置UserDetailsService
        .userDetailsService(this.userDetailsService)
        // 使用BCrypt进行密码的hash
        .passwordEncoder(passwordEncoder());
    }

    /**
     * 装载BCrypt密码编码器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry r = httpSecurity
    	// 由于使用的是JWT，我们这里不需要csrf
    	.csrf().disable()
    	// 基于token，所以不需要session
    	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
    	.authorizeRequests();
    	
    	List<String> urls = userConfig.getUrls();
    	if(!CollectionUtils.isEmpty(urls)){
    		r.anyRequest().permitAll();
    		for (String url : urls) {
    			log.info("add jwt for :"+url);
    			r =  r.antMatchers(url).authenticated();
    		}
    	}else{
    		r.anyRequest().permitAll();
    	}
    	
        // 禁用缓存
        httpSecurity.headers().cacheControl();
        if(filter!=null){
        	log.info("加载拦截器"+filter.getClass().getName());
        	httpSecurity.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        }
        
    }
    
//    @Bean
//    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
//        return new JwtAuthenticationTokenFilter();
//    }
}
