package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.EnuEventStatusDTO;

public interface IEnuEventStatusService {
	public List<EnuEventStatusDTO> findAll();
	public EnuEventStatusDTO findById(Long id);	
	public List<EnuEventStatusDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<EnuEventStatusDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public EnuEventStatusDTO insert(EnuEventStatusDTO EnuEmployeeWorkingStatusDTO);
	public void delete(Long id);
	public EnuEventStatusDTO update(EnuEventStatusDTO entity);
}
