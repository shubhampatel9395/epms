package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IUserDetailsDAO;
import com.epms.dto.UserDetailsDTO;
import com.epms.service.IUserDetailsService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UserDetailsService implements IUserDetailsService {

	@Autowired
	private IUserDetailsDAO userDetailsDAO;
	
	@Override
	public UserDetailsDTO insert(UserDetailsDTO userDetailsDTO) {
		return userDetailsDAO.insert(userDetailsDTO);
	}
	
	@Override
	public UserDetailsDTO findById(Long id)
	{
		return userDetailsDAO.findById(id);
	}

	@Override
	public List<UserDetailsDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return userDetailsDAO.findByNamedParameters(paramSource);
	}

	@Override
	public List<UserDetailsDTO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserDetailsDTO update(UserDetailsDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}
}
