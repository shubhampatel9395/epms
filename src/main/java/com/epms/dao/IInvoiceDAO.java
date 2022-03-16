package com.epms.dao;

import java.util.List;

import com.epms.dto.EventVenueDetailsDTO;
import com.epms.dto.ServiceProviderDTO;

public interface IInvoiceDAO {
	public EventVenueDetailsDTO getEventVenueDetails(long eventId);

	public List<ServiceProviderDTO> getServiceProviderDetails(long eventId);
}
