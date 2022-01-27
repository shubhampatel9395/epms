package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.EnuEmployeeWorkingStatusDTO;

public interface IEnuEmployeeWorkingStatusService {
	public List<EnuEmployeeWorkingStatusDTO> findAll();
	public EnuEmployeeWorkingStatusDTO findById(Long id);	
	public List<EnuEmployeeWorkingStatusDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<EnuEmployeeWorkingStatusDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public EnuEmployeeWorkingStatusDTO insert(EnuEmployeeWorkingStatusDTO EnuEmployeeWorkingStatusDTO);
	public void delete(Long id);
	public EnuEmployeeWorkingStatusDTO update(EnuEmployeeWorkingStatusDTO entity);
}
