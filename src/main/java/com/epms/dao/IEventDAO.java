package com.epms.dao;

import java.util.List;

import javax.validation.Valid;

import com.epms.core.ICRUDRepository;
import com.epms.dto.AssignedEmployeesInEventDTO;
import com.epms.dto.EventDTO;
import com.epms.dto.PackageDetailsDTO;
import com.epms.dto.UpcomingWeekEventDTO;

public interface IEventDAO extends ICRUDRepository<EventDTO, Long> {

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

	public List<UpcomingWeekEventDTO> getUpcomingWeekEvents();
}