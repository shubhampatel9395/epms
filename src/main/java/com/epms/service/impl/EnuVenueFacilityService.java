package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dao.IEnuVenueFacilityDAO;
import com.epms.dto.EnuVenueFacilityDTO;
import com.epms.service.IEnuVenueFacilityService;

public class EnuVenueFacilityService implements IEnuVenueFacilityService {
	@Autowired
	private IEnuVenueFacilityDAO enuVenueFacilityDAO;

	@Override
	public List<EnuVenueFacilityDTO> findAll() {
		return enuVenueFacilityDAO.findAll();
	}

	@Override
	public EnuVenueFacilityDTO findById(Long id) {
		return enuVenueFacilityDAO.findById(id);
	}

	@Override
	public List<EnuVenueFacilityDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return enuVenueFacilityDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<EnuVenueFacilityDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return enuVenueFacilityDAO.findByNamedParameters(paramSource);
	}

	@Override
	public EnuVenueFacilityDTO insert(EnuVenueFacilityDTO EnuVenueFacilityDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnuVenueFacilityDTO update(EnuVenueFacilityDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
