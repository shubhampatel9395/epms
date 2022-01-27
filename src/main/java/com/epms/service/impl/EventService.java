package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dao.IEventDAO;
import com.epms.dto.EventDTO;
import com.epms.service.IEventService;

public class EventService implements IEventService {
	@Autowired
	private IEventDAO eventDAO;

	@Override
	public List<EventDTO> findAll() {
		return eventDAO.findAll();
	}

	@Override
	public EventDTO findById(Long id) {
		return eventDAO.findById(id);
	}

	@Override
	public List<EventDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return eventDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<EventDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return eventDAO.findByNamedParameters(paramSource);
	}

	@Override
	public EventDTO insert(EventDTO EventDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EventDTO update(EventDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
