package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.UserDetailsDTO;

public interface IUserDetailsService {

	public UserDetailsDTO insert(UserDetailsDTO userDetailsDTO);

	public UserDetailsDTO findById(Long id);
	
	public List<UserDetailsDTO> findByNamedParameters(MapSqlParameterSource paramSource);
}
