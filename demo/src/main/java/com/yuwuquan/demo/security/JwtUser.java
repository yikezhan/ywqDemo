package com.yuwuquan.demo.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * @author lenovo
 *
 */
@Getter
@ToString
public class JwtUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private final String id;
	private final String password;
	private final String username;
	private final Collection<? extends GrantedAuthority> authorities;
	private final Date lastPasswordResetDate;
	
	public JwtUser(
            String id,
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            Date lastPasswordResetDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}
	
}
