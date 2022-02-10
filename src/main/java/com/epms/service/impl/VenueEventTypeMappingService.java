package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IVenueEventTypeMappingDAO;
import com.epms.dto.VenueEventTypeMappingDTO;
import com.epms.service.IVenueEventTypeMappingService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class VenueEventTypeMappingService implements IVenueEventTypeMappingService {
	@Autowired
	private IVenueEventTypeMappingDAO venueEventTypeMappingDAO;

	@Override
	public List<VenueEventTypeMappingDTO> findAll() {
		return venueEventTypeMappingDAO.findAll();
	}

	@Override
	public VenueEventTypeMappingDTO findById(Long id) {
		return venueEventTypeMappingDAO.findById(id);
	}

	@Override
	public List<VenueEventTypeMappingDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return venueEventTypeMappingDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<VenueEventTypeMappingDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return venueEventTypeMappingDAO.findByNamedParameters(paramSource);
	}

	@Override
	public VenueEventTypeMappingDTO insert(VenueEventTypeMappingDTO VenueEventTypeMappingDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public VenueEventTypeMappingDTO update(VenueEventTypeMappingDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void insert(Long venueId, List<String> list)
	{
		venueEventTypeMappingDAO.insert(venueId, list);
	}

}
