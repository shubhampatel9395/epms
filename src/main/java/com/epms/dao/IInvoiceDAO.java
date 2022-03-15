package com.epms.dao;

import com.epms.dto.EventVenueDetailsDTO;

public interface IInvoiceDAO {
	public EventVenueDetailsDTO getEventVenueDetails(long eventId);
}
