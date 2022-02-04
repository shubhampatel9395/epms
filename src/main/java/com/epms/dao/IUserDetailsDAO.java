package com.epms.dao;

import java.util.List;

import com.epms.core.ICRUDRepository;
import com.epms.dto.UserDetailsDTO;

public interface IUserDetailsDAO extends ICRUDRepository<UserDetailsDTO, Long> {
	public List<UserDetailsDTO> findAllActive();
}
