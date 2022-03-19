package com.epms.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.AssignedEmployeesInEventDTO;
import com.epms.dto.EventDTO;
import com.epms.dto.PackageDetailsDTO;

public interface IEventService {
	public List<EventDTO> findAll();
	public EventDTO findById(Long id);	
	public List<EventDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<EventDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public EventDTO insert(EventDTO EventDTO);
	public void delete(Long id);
	public EventDTO update(EventDTO entity);
	public Integer getCountByEventType(String eventType);
	public Integer getCount();
	public List<EventDTO> getLastDayData();
	public EventDTO insertByAdmin(EventDTO eventDTO);
	public EventDTO verifyEvent(EventDTO eventDTO, PackageDetailsDTO packageDetailsDTO);
	public void unVerifyEvent(long eventId);
	public void complete(long eventId);
	public EventDTO updateByAdmin(EventDTO entity);
	public EventDTO insertByCustomer(@Valid EventDTO eventDTO);
	public List<AssignedEmployeesInEventDTO> getAllAssignedEmployees(Long eventId);
}