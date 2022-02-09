package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.EmployeeDTO;

public interface IEmployeeService {
	public List<EmployeeDTO> findAll();
	public EmployeeDTO findById(Long id);	
	public List<EmployeeDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<EmployeeDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public EmployeeDTO insert(EmployeeDTO EmployeeDTO);
	public void delete(Long id);
	public EmployeeDTO update(EmployeeDTO entity);
	public void activate(Long id);
}
