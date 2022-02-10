package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IEnuVenueFacilityDAO;
import com.epms.dto.EnuVenueFacilityDTO;
import com.epms.service.IEnuVenueFacilityService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
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
		return null;
	}

	@Override
	public void delete(Long id) {

	}

	@Override
	public EnuVenueFacilityDTO update(EnuVenueFacilityDTO entity) {
		return null;
	}

}
