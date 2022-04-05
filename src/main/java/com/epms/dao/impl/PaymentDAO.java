package com.epms.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.epms.dao.IPaymentDAO;
import com.epms.dto.PaymentDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class PaymentDAO implements IPaymentDAO {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<PaymentDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from payment");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<PaymentDTO>(PaymentDTO.class));
	}

	@Override
	public List<PaymentDTO> findByFieldValue(String fieldName, Object fieldValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PaymentDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from payment where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<PaymentDTO>(PaymentDTO.class));
	}

	@Override
	public PaymentDTO findById(String paymentId) {
		String sql = "select * from payment where paymentId = :paymentId";

		MapSqlParameterSource namedParameters = new MapSqlParameterSource().addValue("paymentId", paymentId);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<PaymentDTO>(PaymentDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public PaymentDTO insert(PaymentDTO entity) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("paymentId", entity.getPaymentId());
		parameterSource.addValue("orderId", entity.getOrderId());
		parameterSource.addValue("userDetailsId", entity.getUserDetailsId());
		parameterSource.addValue("eventId", entity.getEventId());
		parameterSource.addValue("method", entity.getMethod());
		parameterSource.addValue("amount", entity.getAmount());
		parameterSource.addValue("description", entity.getDescription());
		parameterSource.addValue("status", entity.getStatus());
		parameterSource.addValue("refundStatus", entity.getRefundStatus());
		parameterSource.addValue("createdAt", entity.getCreatedAt());

		jdbcTemplate.update(
				"insert into payment(paymentId,orderId,userDetailsId,eventId,method,amount,description,status,refundStatus,createdAt) "
						+ "values(:paymentId,:orderId,:userDetailsId,:eventId,:method,:amount,:description,:status,:refundStatus,:createdAt)",
				parameterSource);

		return findById(entity.getPaymentId());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public PaymentDTO update(PaymentDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaymentDTO findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addEventId(String payment_id, Long eventId) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("paymentId", payment_id);
		parameterSource.addValue("eventId", eventId);
		jdbcTemplate.update("update payment set eventId=:eventId where paymentId=:paymentId", parameterSource);
	}

	@Override
	public void refund(PaymentDTO paymentDTO) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("paymentId", paymentDTO.getPaymentId());
		parameterSource.addValue("description", paymentDTO.getDescription());
		parameterSource.addValue("status", paymentDTO.getStatus());
		parameterSource.addValue("refundStatus", paymentDTO.getRefundStatus());
		parameterSource.addValue("createdAt", paymentDTO.getCreatedAt());

		jdbcTemplate.update(
				"update payment set description=:description,status=:status,refundStatus=:refundStatus,createdAt=:createdAt where paymentId=:paymentId",
				parameterSource);
	}
}
