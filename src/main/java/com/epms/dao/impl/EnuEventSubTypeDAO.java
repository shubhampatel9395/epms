package com.epms.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.epms.dao.IEnuEventSubTypeDAO;
import com.epms.dto.EnuEventSubTypeDTO;

public class EnuEventSubTypeDAO implements IEnuEventSubTypeDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<EnuEventSubTypeDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from enueventsubtype");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EnuEventSubTypeDTO>(EnuEventSubTypeDTO.class));
	}

	@Override
	public List<EnuEventSubTypeDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from enueventsubtype where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<EnuEventSubTypeDTO>(EnuEventSubTypeDTO.class));
	}
	
	@Override
	public List<EnuEventSubTypeDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from enueventsubtype where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<EnuEventSubTypeDTO>(EnuEventSubTypeDTO.class));
	}

	@Override
	public EnuEventSubTypeDTO findById(Long id) {
		String sql = "select * from enueventsubtype where eventSubTypeId = :eventSubTypeId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("eventSubTypeId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EnuEventSubTypeDTO>(EnuEventSubTypeDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public EnuEventSubTypeDTO insert(EnuEventSubTypeDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnuEventSubTypeDTO update(EnuEventSubTypeDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
