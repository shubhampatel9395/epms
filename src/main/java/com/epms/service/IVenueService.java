package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.VenueDTO;

public interface IVenueService {
	public List<VenueDTO> findAll();
	public VenueDTO findById(Long id);	
	public List<VenueDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<VenueDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public VenueDTO insert(VenueDTO VenueDTO);
	public void delete(Long id);
	public VenueDTO update(VenueDTO entity);
	public void activate(Long id);
	public Integer getCount();
}
