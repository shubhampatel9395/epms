package com.epms.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.epms.dao.IEnuEventTypeDAO;
import com.epms.dto.EnuEventTypeDTO;

public class EnuEventTypeDAO implements IEnuEventTypeDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<EnuEventTypeDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from enueventtype");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EnuEventTypeDTO>(EnuEventTypeDTO.class));
	}

	@Override
	public List<EnuEventTypeDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from enueventtype where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters,
				new BeanPropertyRowMapper<EnuEventTypeDTO>(EnuEventTypeDTO.class));
	}

	@Override
	public List<EnuEventTypeDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from enueventtype where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<EnuEventTypeDTO>(EnuEventTypeDTO.class));
	}

	@Override
	public EnuEventTypeDTO findById(Long id) {
		String sql = "select * from enueventtype where eventTypeId = :eventTypeId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("eventTypeId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EnuEventTypeDTO>(EnuEventTypeDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public EnuEventTypeDTO insert(EnuEventTypeDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnuEventTypeDTO update(EnuEventTypeDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
