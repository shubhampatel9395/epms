package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IInvoiceDAO;
import com.epms.dto.EventVenueDetailsDTO;
import com.epms.dto.InvoiceServiceProviderDTO;
import com.epms.service.IInvoiceService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class InvoiceService implements IInvoiceService{
	
	@Autowired
	IInvoiceDAO invoiceDAO;

	@Override
	public EventVenueDetailsDTO getEventVenueDetails(long eventId) {
		return invoiceDAO.getEventVenueDetails(eventId);
	}

	@Override
	public List<InvoiceServiceProviderDTO> getServiceProviderDetails(long eventId) {
		return invoiceDAO.getServiceProviderDetails(eventId);
	}

}
