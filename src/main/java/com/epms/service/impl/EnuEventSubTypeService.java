package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IEnuEventSubTypeDAO;
import com.epms.dto.EnuEventSubTypeDTO;
import com.epms.service.IEnuEventSubTypeService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class EnuEventSubTypeService implements IEnuEventSubTypeService {
	@Autowired
	private IEnuEventSubTypeDAO enuEventSubTypeDAO;

	@Override
	public List<EnuEventSubTypeDTO> findAll() {
		return enuEventSubTypeDAO.findAll();
	}

	@Override
	public EnuEventSubTypeDTO findById(Long id) {
		return enuEventSubTypeDAO.findById(id);
	}

	@Override
	public List<EnuEventSubTypeDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return enuEventSubTypeDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<EnuEventSubTypeDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return enuEventSubTypeDAO.findByNamedParameters(paramSource);
	}

	@Override
	public EnuEventSubTypeDTO insert(EnuEventSubTypeDTO EnuEventSubTypeDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnuEventSubTypeDTO update(EnuEventSubTypeDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
