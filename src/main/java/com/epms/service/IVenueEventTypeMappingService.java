package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.VenueEventTypeMappingDTO;

public interface IVenueEventTypeMappingService {
	public List<VenueEventTypeMappingDTO> findAll();
	public VenueEventTypeMappingDTO findById(Long id);	
	public List<VenueEventTypeMappingDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<VenueEventTypeMappingDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public VenueEventTypeMappingDTO insert(VenueEventTypeMappingDTO VenueEventTypeMappingDTO);
	public void delete(Long id);
	public VenueEventTypeMappingDTO update(VenueEventTypeMappingDTO entity);
}
