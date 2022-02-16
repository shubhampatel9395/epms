package com.epms.dao;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.core.ICRUDRepository;
import com.epms.dto.UserDetailsDTO;

public interface IUserDetailsDAO extends ICRUDRepository<UserDetailsDTO, Long> {
	public List<UserDetailsDTO> findAllActive();
	public List<UserDetailsDTO> isUniqueEmail(String email);
	public List<UserDetailsDTO> isUniqueMobileNumber(String mobileNumber);
	public void activate(Long id);
	public void updateResetPasswordToken(String token, String email);
	void updateUserPassword(Integer userDetailsId, String password);
	public Integer getCustomerCount();
	public Integer getServiceproviderCount();
	public List<UserDetailsDTO> getLastDayData(MapSqlParameterSource parameterSource);
}