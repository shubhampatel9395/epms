package com.epms.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.epms.dao.IUserDetailsDAO;
import com.epms.dto.UserDetailsDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class UserDetailsDAO implements IUserDetailsDAO {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<UserDetailsDTO> findAll() {
		return null;
	}

	@Override
	public List<UserDetailsDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return null;
	}

	@Override
	public UserDetailsDTO findById(Long id) {
		return null;
	}

	@Override
	public UserDetailsDTO insert(UserDetailsDTO entity) {
		// TODO: add insert code 
		return null;
	}

	@Override
	public void delete(Long id) {
		
	}

	@Override
	public UserDetailsDTO update(UserDetailsDTO entity) {
		return null;
	}

}
