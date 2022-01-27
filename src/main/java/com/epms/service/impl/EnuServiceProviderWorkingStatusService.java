package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dao.IEnuServiceProviderWorkingStatusDAO;
import com.epms.dto.EnuServiceProviderWorkingStatusDTO;
import com.epms.service.IEnuServiceProviderWorkingStatusService;

public class EnuServiceProviderWorkingStatusService implements IEnuServiceProviderWorkingStatusService {
	@Autowired
	private IEnuServiceProviderWorkingStatusDAO enuServiceProviderWorkingStatusDAO;

	@Override
	public List<EnuServiceProviderWorkingStatusDTO> findAll() {
		return enuServiceProviderWorkingStatusDAO.findAll();
	}

	@Override
	public EnuServiceProviderWorkingStatusDTO findById(Long id) {
		return enuServiceProviderWorkingStatusDAO.findById(id);
	}

	@Override
	public List<EnuServiceProviderWorkingStatusDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return enuServiceProviderWorkingStatusDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<EnuServiceProviderWorkingStatusDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return enuServiceProviderWorkingStatusDAO.findByNamedParameters(paramSource);
	}

	@Override
	public EnuServiceProviderWorkingStatusDTO insert(
			EnuServiceProviderWorkingStatusDTO EnuServiceProviderWorkingStatusDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnuServiceProviderWorkingStatusDTO update(EnuServiceProviderWorkingStatusDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
