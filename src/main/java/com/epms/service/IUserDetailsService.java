package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.UserDetailsDTO;

public interface IUserDetailsService {
	public List<UserDetailsDTO> findAll();
	public List<UserDetailsDTO> findAllActive();
	public UserDetailsDTO findById(Long id);	
	public List<UserDetailsDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<UserDetailsDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public UserDetailsDTO insert(UserDetailsDTO userDetailsDTO);
	public void delete(Long id);
	public UserDetailsDTO update(UserDetailsDTO entity);
	public List<UserDetailsDTO> isUniqueEmail(String email);
	public List<UserDetailsDTO> isUniqueMobileNumber(String mobileNumber);
	public void activate(Long id);
}