package com.epms.dao;

import java.util.List;

import com.epms.core.ICRUDRepository;
import com.epms.dto.AllServiceProvidersPackageDTO;
import com.epms.dto.EmployeeDTO;
import com.epms.dto.EmployeeEventWorkDTO;
import com.epms.dto.EventOrganizerEventWorkDTO;

public interface IEmployeeDAO extends ICRUDRepository<EmployeeDTO, Long> {
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
}