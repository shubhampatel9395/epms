package com.epms.service;

import com.epms.dto.EventVenueDetailsDTO;

public interface IInvoiceService {
	public EventVenueDetailsDTO getEventVenueDetails(long eventId);

}
