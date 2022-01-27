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

import com.epms.dao.IEnuStateDAO;
import com.epms.dto.EnuStateDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class EnuStateDAO implements IEnuStateDAO {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<EnuStateDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select stateId, state, countryId from enustate");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EnuStateDTO>(EnuStateDTO.class));
	}

	@Override
	public List<EnuStateDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from userdetails where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<EnuStateDTO>(EnuStateDTO.class));
	}

	@Override
	public EnuStateDTO findById(Long id) {
		String sql = "select  stateId, state, countryId from enustate where stateId = :stateId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("stateId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EnuStateDTO>(EnuStateDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<EnuStateDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select stateId, state, countryId from enustate where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<EnuStateDTO>(EnuStateDTO.class));
	}
	
	@Override
	public EnuStateDTO insert(EnuStateDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnuStateDTO update(EnuStateDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
