package com.epms.dao;

import java.util.List;

import com.epms.core.ICRUDRepository;
import com.epms.dto.UserDetailsDTO;

public interface IUserDetailsDAO extends ICRUDRepository<UserDetailsDTO, Long> {
	public List<UserDetailsDTO> findAllActive();
	public List<UserDetailsDTO> isUniqueEmail(String email);
	public List<UserDetailsDTO> isUniqueMobileNumber(String mobileNumber);
}