package com.portiaxe.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7500247719054391906L;
	private User user;
	private static final boolean DEFAULT = true;
	public UserDetailsImpl(User user) {
		this.setUser(user);
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Set<GrantedAuthority> authorities = new HashSet<>();
		
		for(Role r: user.getRoles()) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(r.getRole());
			authorities.add(grantedAuthority);
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return DEFAULT;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return DEFAULT;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return DEFAULT;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return DEFAULT;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
