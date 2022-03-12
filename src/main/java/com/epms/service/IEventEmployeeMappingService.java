package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.EventEmployeeMappingDTO;

public interface IEventEmployeeMappingService {
	public List<EventEmployeeMappingDTO> findAll();
	public EventEmployeeMappingDTO findById(Long id);	
	public List<EventEmployeeMappingDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<EventEmployeeMappingDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public EventEmployeeMappingDTO insert(EventEmployeeMappingDTO EventEmployeeMappingDTO);
	public void delete(Long id);
	public EventEmployeeMappingDTO update(EventEmployeeMappingDTO entity);
	public void removedFromEvent(long eventId);
}
