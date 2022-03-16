package com.epms.service;

import java.util.List;

import com.epms.dto.EventVenueDetailsDTO;
import com.epms.dto.ServiceProviderDTO;

public interface IInvoiceService {
	public EventVenueDetailsDTO getEventVenueDetails(long eventId);
	public List<ServiceProviderDTO> getServiceProviderDetails(long eventId);

}
