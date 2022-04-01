package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.AllServiceProvidersPackageDTO;
import com.epms.dto.EmployeeDTO;
import com.epms.dto.EmployeeEventWorkDTO;
import com.epms.dto.EventOrganizerEventWorkDTO;
import com.epms.dto.UpcomingWeekEventDTO;

public interface IEmployeeService {
	public List<EmployeeDTO> findAll();
	public EmployeeDTO findById(Long id);	
	public List<EmployeeDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<EmployeeDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public EmployeeDTO insert(EmployeeDTO EmployeeDTO);
	public void delete(Long id);
	public EmployeeDTO update(EmployeeDTO entity);
	public void activate(Long id);
	public Integer getEventOrganizerCompletedEventCount(Long eventOrganizerId);
	public Integer getEventOrganizerUpcomingEventCount(Long eventOrganizerId);
	public Integer getEmployeeCompletedEventCount(Long employeeId);
	public Integer getEmployeeUpcomingEventCount(Long employeeId);
	public List<EventOrganizerEventWorkDTO> getEventOrganizerCompletedEventDetails(Long eventOrganizerId);
	public List<EventOrganizerEventWorkDTO> getEventOrganizerUpcomingEventDetails(Long eventOrganizerId);
	public List<EmployeeEventWorkDTO> getEmployeeCompletedEventDetails(Long employeeId);
	public List<EmployeeEventWorkDTO> getEmployeeUpcomingEventDetails(Long employeeId);
	public List<AllServiceProvidersPackageDTO> getAllServiceProviderOnPackage(Long eventOrganizerId);
	public EmployeeEventWorkDTO getEmployeeEventsDetails(long eventId, long employeeId);
	public EventOrganizerEventWorkDTO getEventOrganizerEventDetails(long eventId, long eventOrganizerId);
	public List<UpcomingWeekEventDTO> getUpcomingWeekEventsEmployees(Long employeeId);
	public List<UpcomingWeekEventDTO> getUpcomingWeekEventsEventOrganizer(Long eventOrganizerId);
}