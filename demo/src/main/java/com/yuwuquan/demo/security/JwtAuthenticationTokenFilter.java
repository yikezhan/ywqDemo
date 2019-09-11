package com.yuwuquan.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@ConditionalOnExpression("${jwt.enabled:true}")
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
	
	public JwtAuthenticationTokenFilter() {
		log.info("创建 JwtAuthenticationTokenFilter ");
	}
	
	@Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private JwtUserConfig jwtUserConfig;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
			throws ServletException, IOException {
		String authHeader = request.getHeader(this.tokenHeader);
		if(jwtUserConfig !=null && !CollectionUtils.isEmpty(jwtUserConfig.getUrls())){
			if(request.getRequestURI().startsWith(jwtUserConfig.getUrls().get(0))
					&& !request.getRequestURI().startsWith("/demo/swagger")
					&& !request.getRequestURI().startsWith("/demo/usr/login")
			){
			//if(jwtUserConfig.getUrls().contains(request.getRequestURI())){
				if(authHeader == null || !authHeader.startsWith(tokenHead)){
					throw new ServletException("Missing or invalid Authorization header");
				}else{
		            final String authToken = authHeader.substring(tokenHead.length()); // The part after "Bearer "
		            String username = jwtTokenUtil.getUsernameFromToken(authToken);
		            log.info("checking authentication " + username);
		            if(username == null && SecurityContextHolder.getContext().getAuthentication() == null){
		            	throw new ServletException("Missing or invalid Authorization header");
		            }else{
		            	Authentication exits = SecurityContextHolder.getContext().getAuthentication();
		            	if (username != null && exits == null) {
			                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
			                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
			                            userDetails, null, userDetails.getAuthorities());
			                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
			                            request));
			                    log.info("authenticated user " + username + ", setting security context");
			                    SecurityContextHolder.getContext().setAuthentication(authentication);
			                }else{
			                	throw new ServletException("Missing or invalid Authorization header");
			                }
			            }else{
			            	if(exits == null){
			            		throw new ServletException("Missing or invalid Authorization header");
			            	} else{
			            		if(exits instanceof UsernamePasswordAuthenticationToken){
			            			UsernamePasswordAuthenticationToken p = (UsernamePasswordAuthenticationToken)exits;
			            			if(p.getDetails()==null){
			            				p.setDetails(new WebAuthenticationDetailsSource().buildDetails(
					                            request));
			            			}
			            		}
			            	}
			            }
		            }
		            
				}
			}
			
		}
        chain.doFilter(request, response);
		
	}

}
