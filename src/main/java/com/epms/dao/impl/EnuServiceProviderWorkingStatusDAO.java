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

import com.epms.dao.IEnuServiceProviderWorkingStatusDAO;
import com.epms.dto.EnuServiceProviderWorkingStatusDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class EnuServiceProviderWorkingStatusDAO implements IEnuServiceProviderWorkingStatusDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<EnuServiceProviderWorkingStatusDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from enuserviceproviderworkingstatus");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EnuServiceProviderWorkingStatusDTO>(
						EnuServiceProviderWorkingStatusDTO.class));
	}

	@Override
	public List<EnuServiceProviderWorkingStatusDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from enuserviceproviderworkingstatus where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<EnuServiceProviderWorkingStatusDTO>(
				EnuServiceProviderWorkingStatusDTO.class));
	}

	@Override
	public List<EnuServiceProviderWorkingStatusDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from enuserviceproviderworkingstatus where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<EnuServiceProviderWorkingStatusDTO>(
				EnuServiceProviderWorkingStatusDTO.class));
	}

	@Override
	public EnuServiceProviderWorkingStatusDTO findById(Long id) {
		String sql = "select * from enuserviceproviderworkingstatus where statusId = :statusId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("statusId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EnuServiceProviderWorkingStatusDTO>(
							EnuServiceProviderWorkingStatusDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public EnuServiceProviderWorkingStatusDTO insert(EnuServiceProviderWorkingStatusDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnuServiceProviderWorkingStatusDTO update(EnuServiceProviderWorkingStatusDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
