package com.epms.service;

import com.epms.dto.UserDetailsDTO;

public interface IUserDetailsService {

	public UserDetailsDTO insert(UserDetailsDTO userDetailsDTO);

	public UserDetailsDTO findById(Long id);
}
