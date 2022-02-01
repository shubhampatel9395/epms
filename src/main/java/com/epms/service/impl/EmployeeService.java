package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IEmployeeDAO;
import com.epms.dto.EmployeeDTO;
import com.epms.service.IEmployeeService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class EmployeeService implements IEmployeeService {
	@Autowired
	private IEmployeeDAO employeeDAO;
	
	@Override
	public List<EmployeeDTO> findAll() {
		return employeeDAO.findAll();
	}

	@Override
	public EmployeeDTO findById(Long id) {
		return employeeDAO.findById(id);
	}

	@Override
	public List<EmployeeDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return employeeDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<EmployeeDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return employeeDAO.findByNamedParameters(paramSource);
	}

	@Override
	public EmployeeDTO insert(EmployeeDTO EmployeeDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EmployeeDTO update(EmployeeDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
