package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IEnuEmployeeWorkingStatusDAO;
import com.epms.dto.EnuEmployeeWorkingStatusDTO;
import com.epms.service.IEnuEmployeeWorkingStatusService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class EnuEmployeeWorkingStatusService implements IEnuEmployeeWorkingStatusService {
	@Autowired
	private IEnuEmployeeWorkingStatusDAO enuEmployeeWorkingStatusDAO;

	@Override
	public List<EnuEmployeeWorkingStatusDTO> findAll() {
		return enuEmployeeWorkingStatusDAO.findAll();
	}

	@Override
	public EnuEmployeeWorkingStatusDTO findById(Long id) {
		return enuEmployeeWorkingStatusDAO.findById(id);
	}

	@Override
	public List<EnuEmployeeWorkingStatusDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return enuEmployeeWorkingStatusDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<EnuEmployeeWorkingStatusDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return enuEmployeeWorkingStatusDAO.findByNamedParameters(paramSource);
	}

	@Override
	public EnuEmployeeWorkingStatusDTO insert(EnuEmployeeWorkingStatusDTO EnuEmployeeWorkingStatusDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnuEmployeeWorkingStatusDTO update(EnuEmployeeWorkingStatusDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
