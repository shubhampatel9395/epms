package com.epms.dao;

import java.util.List;

import com.epms.core.ICRUDRepository;
import com.epms.dto.VenueEventTypeMappingDTO;

public interface IVenueEventTypeMappingDAO extends ICRUDRepository<VenueEventTypeMappingDTO, Long> {
	public void update(Long venueId, List<String> list);
	public void insert(Long venueId,List<String> list);
	public void activate(Long id);
}
