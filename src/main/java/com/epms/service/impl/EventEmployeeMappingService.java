package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dao.IEventEmployeeMappingDAO;
import com.epms.dto.EventEmployeeMappingDTO;
import com.epms.service.IEventEmployeeMappingService;

public class EventEmployeeMappingService implements IEventEmployeeMappingService {
	@Autowired
	private IEventEmployeeMappingDAO eventEmployeeMappingDAO;

	@Override
	public List<EventEmployeeMappingDTO> findAll() {
		return eventEmployeeMappingDAO.findAll();
	}

	@Override
	public EventEmployeeMappingDTO findById(Long id) {
		return eventEmployeeMappingDAO.findById(id);
	}

	@Override
	public List<EventEmployeeMappingDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return eventEmployeeMappingDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<EventEmployeeMappingDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return eventEmployeeMappingDAO.findByNamedParameters(paramSource);
	}

	@Override
	public EventEmployeeMappingDTO insert(EventEmployeeMappingDTO EventEmployeeMappingDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EventEmployeeMappingDTO update(EventEmployeeMappingDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
