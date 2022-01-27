package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.VenueFacilityMappingDTO;

public interface IVenueFacilityMappingService {
	public List<VenueFacilityMappingDTO> findAll();
	public VenueFacilityMappingDTO findById(Long id);	
	public List<VenueFacilityMappingDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<VenueFacilityMappingDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public VenueFacilityMappingDTO insert(VenueFacilityMappingDTO VenueFacilityMappingDTO);
	public void delete(Long id);
	public VenueFacilityMappingDTO update(VenueFacilityMappingDTO entity);
}
