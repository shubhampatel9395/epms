package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IEventEmployeeMappingDAO;
import com.epms.dto.EventEmployeeMappingDTO;
import com.epms.service.IEventEmployeeMappingService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
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
		return eventEmployeeMappingDAO.insert(EventEmployeeMappingDTO);
	}

	@Override
	public void delete(Long id) {
		eventEmployeeMappingDAO.delete(id);
	}

	@Override
	public EventEmployeeMappingDTO update(EventEmployeeMappingDTO entity) {
		return eventEmployeeMappingDAO.update(entity);
	}
}