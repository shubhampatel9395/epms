package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.EnuEmployeeRoleDTO;

public interface IEnuEmployeeRoleService {
	public List<EnuEmployeeRoleDTO> findAll();
	public EnuEmployeeRoleDTO findById(Long id);	
	public List<EnuEmployeeRoleDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<EnuEmployeeRoleDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public EnuEmployeeRoleDTO insert(EnuEmployeeRoleDTO EnuEmployeeRoleDTO);
	public void delete(Long id);
	public EnuEmployeeRoleDTO update(EnuEmployeeRoleDTO entity);
}
