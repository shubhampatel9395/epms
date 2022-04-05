package com.epms.dao;

import com.epms.core.ICRUDRepository;
import com.epms.dto.PaymentDTO;

public interface IPaymentDAO extends ICRUDRepository<PaymentDTO, Long>{

	public PaymentDTO findById(String id);

	public void addEventId(String payment_id, Long eventId);

	public void refund(PaymentDTO paymentDTO);

}