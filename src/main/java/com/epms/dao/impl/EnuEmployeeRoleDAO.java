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

import com.epms.dao.IEnuEmployeeRoleDAO;
import com.epms.dto.EnuEmployeeRoleDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class EnuEmployeeRoleDAO implements IEnuEmployeeRoleDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<EnuEmployeeRoleDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from enuemployeerole");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EnuEmployeeRoleDTO>(EnuEmployeeRoleDTO.class));
	}

	@Override
	public List<EnuEmployeeRoleDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from enuemployeerole where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters,
				new BeanPropertyRowMapper<EnuEmployeeRoleDTO>(EnuEmployeeRoleDTO.class));
	}

	@Override
	public List<EnuEmployeeRoleDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from enuemployeerole where 1=1";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource,
				new BeanPropertyRowMapper<EnuEmployeeRoleDTO>(EnuEmployeeRoleDTO.class));
	}

	@Override
	public EnuEmployeeRoleDTO findById(Long id) {
		String sql = "select * from enuemployeerole where employeeRoleId = :employeeRoleId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("employeeRoleId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EnuEmployeeRoleDTO>(EnuEmployeeRoleDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public EnuEmployeeRoleDTO insert(EnuEmployeeRoleDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnuEmployeeRoleDTO update(EnuEmployeeRoleDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
