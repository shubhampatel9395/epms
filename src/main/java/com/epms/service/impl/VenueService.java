package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dao.IVenueDAO;
import com.epms.dto.VenueDTO;
import com.epms.service.IVenueService;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public VenueDTO update(VenueDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
