package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.EventDTO;

public interface IEventService {
	public List<EventDTO> findAll();
	public EventDTO findById(Long id);	
	public List<EventDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<EventDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public EventDTO insert(EventDTO EventDTO);
	public void delete(Long id);
	public EventDTO update(EventDTO entity);
}
