package com.epms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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
	IUserDetailsDAO userDetailsDAO;
	
	@Override
	public UserDetailsDTO insert(UserDetailsDTO userDetailsDTO) {
		return userDetailsDAO.insert(userDetailsDTO);
	}

}
