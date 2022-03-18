package com.epms.dao;

import java.util.List;

import com.epms.core.ICRUDRepository;
import com.epms.dto.AllServiceProvidersPackageDTO;
import com.epms.dto.ServiceProviderDTO;
import com.epms.dto.ServiceProviderEventWorkDTO;
import com.epms.dto.ServiceProviderPackageDTO;
import com.epms.dto.ShowFeedbackDTO;

public interface IServiceProviderDAO extends ICRUDRepository<ServiceProviderDTO, Long> {
	public List<ServiceProviderDTO> findAllActive();
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
}