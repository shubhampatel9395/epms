package com.epms.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.epms.dao.IEnuEmployeeWorkingStatusDAO;
import com.epms.dto.EnuEmployeeWorkingStatusDTO;

public class EnuEmployeeWorkingStatusDAO implements IEnuEmployeeWorkingStatusDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<EnuEmployeeWorkingStatusDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from enuemployeeworkingstatus");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EnuEmployeeWorkingStatusDTO>(EnuEmployeeWorkingStatusDTO.class));
	}

	@Override
	public List<EnuEmployeeWorkingStatusDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from enuemployeeworkingstatus where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters,
				new BeanPropertyRowMapper<EnuEmployeeWorkingStatusDTO>(EnuEmployeeWorkingStatusDTO.class));
	}

	@Override
	public List<EnuEmployeeWorkingStatusDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from enuemployeeworkingstatus where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource,
				new BeanPropertyRowMapper<EnuEmployeeWorkingStatusDTO>(EnuEmployeeWorkingStatusDTO.class));
	}

	@Override
	public EnuEmployeeWorkingStatusDTO findById(Long id) {
		String sql = "select * from enuemployeeworkingstatus where statusId = :statusId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("statusId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EnuEmployeeWorkingStatusDTO>(EnuEmployeeWorkingStatusDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public EnuEmployeeWorkingStatusDTO insert(EnuEmployeeWorkingStatusDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnuEmployeeWorkingStatusDTO update(EnuEmployeeWorkingStatusDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
