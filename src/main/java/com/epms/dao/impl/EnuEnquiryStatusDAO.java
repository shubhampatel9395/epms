package com.epms.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.epms.dao.IEnuEnquiryStatusDAO;
import com.epms.dto.EnuEnquiryStatusDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class EnuEnquiryStatusDAO implements IEnuEnquiryStatusDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<EnuEnquiryStatusDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from enuenquirystatus");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EnuEnquiryStatusDTO>(EnuEnquiryStatusDTO.class));
	}

	@Override
	public List<EnuEnquiryStatusDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from enuenquirystatus where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters,
				new BeanPropertyRowMapper<EnuEnquiryStatusDTO>(EnuEnquiryStatusDTO.class));
	}

	@Override
	public List<EnuEnquiryStatusDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from enuenquirystatus where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource,
				new BeanPropertyRowMapper<EnuEnquiryStatusDTO>(EnuEnquiryStatusDTO.class));
	}

	@Override
	public EnuEnquiryStatusDTO findById(Long id) {
		String sql = "select * from enuenquirystatus where statusId = :statusId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("statusId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EnuEnquiryStatusDTO>(EnuEnquiryStatusDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public EnuEnquiryStatusDTO insert(EnuEnquiryStatusDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnuEnquiryStatusDTO update(EnuEnquiryStatusDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
