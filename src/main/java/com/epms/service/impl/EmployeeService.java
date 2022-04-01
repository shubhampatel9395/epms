package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IEmployeeDAO;
import com.epms.dto.AllServiceProvidersPackageDTO;
import com.epms.dto.EmployeeDTO;
import com.epms.dto.EmployeeEventWorkDTO;
import com.epms.dto.EventOrganizerEventWorkDTO;
import com.epms.dto.UpcomingWeekEventDTO;
import com.epms.service.IEmployeeService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class EmployeeService implements IEmployeeService {
	@Autowired
	private IEmployeeDAO employeeDAO;
	
	@Override
	public List<EmployeeDTO> findAll() {
		return employeeDAO.findAll();
	}

	@Override
	public EmployeeDTO findById(Long id) {
		return employeeDAO.findById(id);
	}

	@Override
	public List<EmployeeDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return employeeDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<EmployeeDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return employeeDAO.findByNamedParameters(paramSource);
	}

	@Override
	public EmployeeDTO insert(EmployeeDTO employeeDTO) {
		return employeeDAO.insert(employeeDTO);
	}

	@Override
	public void delete(Long id) {
		employeeDAO.delete(id);
	}

	@Override
	public EmployeeDTO update(EmployeeDTO entity) {
		return employeeDAO.update(entity);
	}

	@Override
	public void activate(Long id) {
		employeeDAO.activate(id);
	}

	@Override
	public Integer getEventOrganizerCompletedEventCount(Long eventOrganizerId) {
		return employeeDAO.getEventOrganizerCompletedEventCount(eventOrganizerId);
	}

	@Override
	public Integer getEventOrganizerUpcomingEventCount(Long eventOrganizerId) {
		return employeeDAO.getEventOrganizerUpcomingEventCount(eventOrganizerId);
	}

	@Override
	public Integer getEmployeeCompletedEventCount(Long employeeId) {
		return employeeDAO.getEmployeeCompletedEventCount(employeeId);
	}

	@Override
	public Integer getEmployeeUpcomingEventCount(Long employeeId) {
		return employeeDAO.getEmployeeUpcomingEventCount(employeeId);
	}

	@Override
	public List<EventOrganizerEventWorkDTO> getEventOrganizerCompletedEventDetails(Long eventOrganizerId) {
		return employeeDAO.getEventOrganizerCompletedEventDetails(eventOrganizerId);
	}

	@Override
	public List<EventOrganizerEventWorkDTO> getEventOrganizerUpcomingEventDetails(Long eventOrganizerId) {
		return employeeDAO.getEventOrganizerUpcomingEventDetails(eventOrganizerId);
	}

	@Override
	public List<EmployeeEventWorkDTO> getEmployeeCompletedEventDetails(Long employeeId) {
		return employeeDAO.getEmployeeCompletedEventDetails(employeeId);
	}

	@Override
	public List<EmployeeEventWorkDTO> getEmployeeUpcomingEventDetails(Long employeeId) {
		return employeeDAO.getEmployeeUpcomingEventDetails(employeeId);
	}

	@Override
	public List<AllServiceProvidersPackageDTO> getAllServiceProviderOnPackage(Long eventOrganizerId) {
		return employeeDAO.getAllServiceProviderOnPackage(eventOrganizerId);
	}

	@Override
	public EmployeeEventWorkDTO getEmployeeEventsDetails(long eventId, long employeeId) {
		return employeeDAO.getEmployeeEventsDetails(eventId,employeeId);
	}

	@Override
	public EventOrganizerEventWorkDTO getEventOrganizerEventDetails(long eventId, long eventOrganizerId) {
		return employeeDAO.getEventOrganizerEventDetails(eventId,eventOrganizerId);
	}

	@Override
	public List<UpcomingWeekEventDTO> getUpcomingWeekEventsEmployees(Long employeeId) {
		return employeeDAO.getUpcomingWeekEventsEmployees(employeeId);
	}

	@Override
	public List<UpcomingWeekEventDTO> getUpcomingWeekEventsEventOrganizer(Long eventOrganizerId) {
		return employeeDAO.getUpcomingWeekEventsEventOrganizer(eventOrganizerId);
	}
}