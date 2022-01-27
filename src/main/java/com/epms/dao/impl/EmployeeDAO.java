package com.epms.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.epms.dao.IEmployeeDAO;
import com.epms.dto.EmployeeDTO;

public class EmployeeDAO implements IEmployeeDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<EmployeeDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from employee");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EmployeeDTO>(EmployeeDTO.class));
	}

	@Override
	public List<EmployeeDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from employee where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<EmployeeDTO>(EmployeeDTO.class));
	}

	@Override
	public List<EmployeeDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from employee where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<EmployeeDTO>(EmployeeDTO.class));
	}

	@Override
	public EmployeeDTO findById(Long id) {
		String sql = "select * from employee where employeeId = :employeeId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("employeeId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EmployeeDTO>(EmployeeDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public EmployeeDTO insert(EmployeeDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EmployeeDTO update(EmployeeDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
