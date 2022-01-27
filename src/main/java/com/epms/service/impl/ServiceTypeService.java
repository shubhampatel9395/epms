package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IEnuServiceTypeDAO;
import com.epms.dto.EnuServiceTypeDTO;
import com.epms.service.IEnuServiceTypeService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ServiceTypeService implements IEnuServiceTypeService {
	@Autowired
	private IEnuServiceTypeDAO enuServiceTypeDAO;

	@Override
	public List<EnuServiceTypeDTO> findAll() {
		return enuServiceTypeDAO.findAll();
	}

	@Override
	public EnuServiceTypeDTO findById(Long id) {
		return enuServiceTypeDAO.findById(id);
	}

	@Override
	public List<EnuServiceTypeDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return enuServiceTypeDAO.findByNamedParameters(paramSource);
	}

	@Override
	public List<EnuServiceTypeDTO> findAllActive() {
		return enuServiceTypeDAO.findAllActive();
	}

	@Override
	public EnuServiceTypeDTO insert(EnuServiceTypeDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EnuServiceTypeDTO update(EnuServiceTypeDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EnuServiceTypeDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return enuServiceTypeDAO.findByFieldValue(fieldName, fieldValue);
	}

}
