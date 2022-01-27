package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.EnuVenueFacilityDTO;

public interface IEnuVenueFacilityService {
	public List<EnuVenueFacilityDTO> findAll();
	public EnuVenueFacilityDTO findById(Long id);	
	public List<EnuVenueFacilityDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<EnuVenueFacilityDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public EnuVenueFacilityDTO insert(EnuVenueFacilityDTO EnuVenueFacilityDTO);
	public void delete(Long id);
	public EnuVenueFacilityDTO update(EnuVenueFacilityDTO entity);
}
