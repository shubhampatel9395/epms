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

import com.epms.dao.IEnquiryDAO;
import com.epms.dto.EnquiryDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class EnquiryDAO implements IEnquiryDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<EnquiryDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from enquiry");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EnquiryDTO>(EnquiryDTO.class));
	}

	@Override
	public List<EnquiryDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from enquiry where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<EnquiryDTO>(EnquiryDTO.class));
	}

	@Override
	public List<EnquiryDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from enquiry where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<EnquiryDTO>(EnquiryDTO.class));
	}

	@Override
	public EnquiryDTO findById(Long id) {
		String sql = "select * from enquiry where enquiryId = :enquiryId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("enquiryId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EnquiryDTO>(EnquiryDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public EnquiryDTO insert(EnquiryDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnquiryDTO update(EnquiryDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
