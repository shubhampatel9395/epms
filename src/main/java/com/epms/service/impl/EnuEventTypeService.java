package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IEnuEventTypeDAO;
import com.epms.dto.EnuEventTypeDTO;
import com.epms.service.IEnuEventTypeService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class EnuEventTypeService implements IEnuEventTypeService {
	@Autowired
	private IEnuEventTypeDAO enuEventTypeDAO;

	@Override
	public List<EnuEventTypeDTO> findAll() {
		return enuEventTypeDAO.findAll();
	}

	@Override
	public EnuEventTypeDTO findById(Long id) {
		return enuEventTypeDAO.findById(id);
	}

	@Override
	public List<EnuEventTypeDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return enuEventTypeDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<EnuEventTypeDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return enuEventTypeDAO.findByNamedParameters(paramSource);
	}

	@Override
	public EnuEventTypeDTO insert(EnuEventTypeDTO EnuEventTypeDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnuEventTypeDTO update(EnuEventTypeDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
