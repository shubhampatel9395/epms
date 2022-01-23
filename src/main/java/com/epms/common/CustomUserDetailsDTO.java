package com.epms.common;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.epms.dto.UserDetailsDTO;

public class CustomUserDetailsDTO implements UserDetails {

	private UserDetailsDTO userDetailsDTO;
	
	
	public CustomUserDetailsDTO(UserDetailsDTO userDetailsDTO) {
		this.userDetailsDTO = userDetailsDTO;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return userDetailsDTO.getPassword();
	}

	@Override
	public String getUsername() {
		return userDetailsDTO.getEmail();
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
		return userDetailsDTO.getFirstName() +" " + userDetailsDTO.getLastName();
	}
}
