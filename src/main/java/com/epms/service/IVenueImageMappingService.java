package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.VenueImageMappingDTO;

public interface IVenueImageMappingService {
	public List<VenueImageMappingDTO> findAll();
	public VenueImageMappingDTO findById(Long id);	
	public List<VenueImageMappingDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<VenueImageMappingDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public VenueImageMappingDTO insert(VenueImageMappingDTO VenueImageMappingDTO);
	public void delete(Long id);
	public VenueImageMappingDTO update(VenueImageMappingDTO entity);
}
