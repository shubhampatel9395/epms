package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IVenueDAO;
import com.epms.dto.VenueDTO;
import com.epms.service.IVenueService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class VenueService implements IVenueService {
	@Autowired
	private IVenueDAO venueDAO;

	@Override
	public List<VenueDTO> findAll() {
		return venueDAO.findAll();
	}

	@Override
	public VenueDTO findById(Long id) {
		return venueDAO.findById(id);
	}

	@Override
	public List<VenueDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return venueDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<VenueDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return venueDAO.findByNamedParameters(paramSource);
	}

	@Override
	public VenueDTO insert(VenueDTO VenueDTO) {
		return venueDAO.insert(VenueDTO);
	}

	@Override
	public void delete(Long id) {
		venueDAO.delete(id);
	}

	@Override
	public VenueDTO update(VenueDTO entity) {
		return venueDAO.update(entity);
	}

	@Override
	public void activate(Long id) {
		venueDAO.activate(id);
	}

}
