package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IEnuEventStatusDAO;
import com.epms.dto.EnuEventStatusDTO;
import com.epms.service.IEnuEventStatusService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class EnuEventStatusService implements IEnuEventStatusService {
	@Autowired
	IEnuEventStatusDAO enuEventStatusDAO;
	
	@Override
	public List<EnuEventStatusDTO> findAll() {
		return enuEventStatusDAO.findAll();
	}

	@Override
	public EnuEventStatusDTO findById(Long id) {
		return enuEventStatusDAO.findById(id);
	}

	@Override
	public List<EnuEventStatusDTO> findByFieldValue(String fieldName, Object fieldValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EnuEventStatusDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return enuEventStatusDAO.findByNamedParameters(paramSource);
	}

	@Override
	public EnuEventStatusDTO insert(EnuEventStatusDTO EnuEmployeeWorkingStatusDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnuEventStatusDTO update(EnuEventStatusDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
