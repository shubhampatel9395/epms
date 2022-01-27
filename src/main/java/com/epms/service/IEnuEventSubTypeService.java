package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.EnuEventSubTypeDTO;

public interface IEnuEventSubTypeService {
	public List<EnuEventSubTypeDTO> findAll();
	public EnuEventSubTypeDTO findById(Long id);	
	public List<EnuEventSubTypeDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<EnuEventSubTypeDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public EnuEventSubTypeDTO insert(EnuEventSubTypeDTO EnuEventSubTypeDTO);
	public void delete(Long id);
	public EnuEventSubTypeDTO update(EnuEventSubTypeDTO entity);
}
