package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.AllServiceProvidersPackageDTO;
import com.epms.dto.ServiceProviderDTO;
import com.epms.dto.ServiceProviderEventWorkDTO;
import com.epms.dto.ServiceProviderPackageDTO;
import com.epms.dto.ShowFeedbackDTO;
import com.epms.dto.UpcomingWeekEventDTO;

public interface IServiceProviderService {
	public List<ServiceProviderDTO> findAll();
	public List<ServiceProviderDTO> findAllActive();
	public ServiceProviderDTO findById(Long id);
	public List<ServiceProviderDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public List<ServiceProviderDTO> findByFieldValue(String fieldName, Object fieldValue);
	public ServiceProviderDTO insert(ServiceProviderDTO serviceProviderDTO);
	public void delete(Long id);
	public ServiceProviderDTO update(ServiceProviderDTO entity);
	public void authenticate(Long id);
	public int getTotalParticipatedPackages(Long id);
	public int getCompletedEvents(Long id);
	public int getOngoingEvents(Long id);
	public double getAverageRatings(Long id);
	public List<ServiceProviderEventWorkDTO> getCompletedEventsDetails(Long id);
	public List<ServiceProviderEventWorkDTO> getOngoingEventsDetails(Long id);
	public ServiceProviderEventWorkDTO getEventsDetails(Long eventId, Long serviceProviderId);
	public List<ShowFeedbackDTO> getFeedbackDetails(Long serviceProviderId);
	public List<ServiceProviderPackageDTO> getPackageDetails(Long serviceProviderId);
	public List<AllServiceProvidersPackageDTO> getAllServiceProvidersPackageDetails(Long serviceProviderId);
	public List<UpcomingWeekEventDTO> getUpcomingWeekEvents(Long serviceProviderId);
}