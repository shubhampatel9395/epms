package com.epms.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.epms.dto.UserDetailsDTO;

public class CustomUserDetailsDTO implements UserDetails {

	private UserDetailsDTO userDetailsDTO;

	public CustomUserDetailsDTO(UserDetailsDTO userDetailsDTO) {
		this.userDetailsDTO = userDetailsDTO;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(userDetailsDTO.getRoleName()));
		return authorities;
	}

	@Override
	public String getPassword() {
		return userDetailsDTO.getPassword();
	}

	@Override
	public String getUsername() {
		return userDetailsDTO.getEmail();
	}

	public String getRoleName() {
		return userDetailsDTO.getRoleName();
	}

	public Integer getUserDetailsId() {
		return userDetailsDTO.getUserDetailsId();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getFullName() {
		return userDetailsDTO.getFirstName() + " " + userDetailsDTO.getLastName();
	}
}
