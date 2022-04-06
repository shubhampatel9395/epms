package com.epms.dao;

import java.util.List;

import com.epms.core.ICRUDRepository;
import com.epms.dto.PaymentDTO;
import com.epms.dto.PaymentDetailsDTO;

public interface IPaymentDAO extends ICRUDRepository<PaymentDTO, Long>{

	public PaymentDTO findById(String id);

	public void addEventId(String payment_id, Long eventId);

	public void refund(PaymentDTO paymentDTO);
	
	public List<PaymentDetailsDTO> getAllPaymentDetails();
	
	public PaymentDetailsDTO getPaymentDetails(String paymentId);

}