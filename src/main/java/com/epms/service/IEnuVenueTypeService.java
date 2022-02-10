package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.EnuVenueTypeDTO;

public interface IEnuVenueTypeService {
	public List<EnuVenueTypeDTO> findAll();
	public EnuVenueTypeDTO findById(Long id);	
	public List<EnuVenueTypeDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<EnuVenueTypeDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public EnuVenueTypeDTO insert(EnuVenueTypeDTO EnuEventTypeDTO);
	public void delete(Long id);
	public EnuVenueTypeDTO update(EnuVenueTypeDTO entity);
}