package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.EnuEventTypeDTO;

public interface IEnuEventTypeService {
	public List<EnuEventTypeDTO> findAll();
	public EnuEventTypeDTO findById(Long id);	
	public List<EnuEventTypeDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<EnuEventTypeDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public EnuEventTypeDTO insert(EnuEventTypeDTO EnuEventTypeDTO);
	public void delete(Long id);
	public EnuEventTypeDTO update(EnuEventTypeDTO entity);
}