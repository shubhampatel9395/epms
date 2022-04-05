package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IAddressDAO;
import com.epms.dao.IPaymentDAO;
import com.epms.dto.PaymentDTO;
import com.epms.service.IPaymentService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class PaymentService implements IPaymentService {

	@Autowired
	IPaymentDAO paymentDAO;
	
	@Override
	public List<PaymentDTO> findAll() {
		return paymentDAO.findAll();
	}

	@Override
	public PaymentDTO findById(Long id) {
		return paymentDAO.findById(id);
	}

	@Override
	public PaymentDTO findById(String id) {
		return paymentDAO.findById(id);
	}

	@Override
	public List<PaymentDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return paymentDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<PaymentDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return paymentDAO.findByNamedParameters(paramSource);
	}

	@Override
	public PaymentDTO insert(PaymentDTO entity) {
		return paymentDAO.insert(entity);
	}

	@Override
	public void delete(Long id) {
		paymentDAO.delete(id);
	}

	@Override
	public PaymentDTO update(PaymentDTO entity) {
		return paymentDAO.update(entity);
	}

	@Override
	public void addEventId(String payment_id, Long eventId) {
		paymentDAO.addEventId(payment_id, eventId);
	}

	@Override
	public void refund(PaymentDTO paymentDTO) {
		paymentDAO.refund(paymentDTO);
	}

}
