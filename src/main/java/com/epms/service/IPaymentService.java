package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.PaymentDTO;
import com.epms.dto.PaymentDetailsDTO;

public interface IPaymentService {
	public List<PaymentDTO> findAll();
	public PaymentDTO findById(Long id);
	public PaymentDTO findById(String id);
	public List<PaymentDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<PaymentDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public PaymentDTO insert(PaymentDTO entity);
	public void delete(Long id);
	public PaymentDTO update(PaymentDTO entity);
	public void addEventId(String payment_id, Long eventId);
	public void refund(PaymentDTO paymentDTO);
	public List<PaymentDetailsDTO> getAllPaymentDetails();
	public PaymentDetailsDTO getPaymentDetails(String paymentId);
}