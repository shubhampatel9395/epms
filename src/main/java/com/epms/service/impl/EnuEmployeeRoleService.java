package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IEnuEmployeeRoleDAO;
import com.epms.dto.EnuEmployeeRoleDTO;
import com.epms.service.IEnuEmployeeRoleService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class EnuEmployeeRoleService implements IEnuEmployeeRoleService {
	@Autowired
	private IEnuEmployeeRoleDAO  enuEmployeeRoleDAO;

	@Override
	public List<EnuEmployeeRoleDTO> findAll() {
		return enuEmployeeRoleDAO.findAll();
	}

	@Override
	public EnuEmployeeRoleDTO findById(Long id) {
		return enuEmployeeRoleDAO.findById(id);
	}

	@Override
	public List<EnuEmployeeRoleDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return enuEmployeeRoleDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<EnuEmployeeRoleDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return enuEmployeeRoleDAO.findByNamedParameters(paramSource);
	}

	@Override
	public EnuEmployeeRoleDTO insert(EnuEmployeeRoleDTO EnuEmployeeRoleDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnuEmployeeRoleDTO update(EnuEmployeeRoleDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
