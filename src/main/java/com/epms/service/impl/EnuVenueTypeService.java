package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IEnuVenueTypeDAO;
import com.epms.dto.EnuVenueTypeDTO;
import com.epms.service.IEnuVenueTypeService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class EnuVenueTypeService implements IEnuVenueTypeService {
	@Autowired
	private IEnuVenueTypeDAO enuVenueTypeDAO;

	@Override
	public List<EnuVenueTypeDTO> findAll() {
		return enuVenueTypeDAO.findAll();
	}

	@Override
	public EnuVenueTypeDTO findById(Long id) {
		return enuVenueTypeDAO.findById(id);
	}

	@Override
	public List<EnuVenueTypeDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return enuVenueTypeDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<EnuVenueTypeDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return enuVenueTypeDAO.findByNamedParameters(paramSource);
	}

	@Override
	public EnuVenueTypeDTO insert(EnuVenueTypeDTO EnuEventTypeDTO) {
		return null;
	}

	@Override
	public void delete(Long id) {

	}

	@Override
	public EnuVenueTypeDTO update(EnuVenueTypeDTO entity) {
		return null;
	}

}
