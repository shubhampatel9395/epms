package com.epms.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.epms.dao.IAttendeeDAO;
import com.epms.dto.AttendeeDTO;

public class AttendeeDAO implements IAttendeeDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<AttendeeDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from attendee");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<AttendeeDTO>(AttendeeDTO.class));
	}

	@Override
	public List<AttendeeDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from attendee where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<AttendeeDTO>(AttendeeDTO.class));
	}
	
	@Override
	public List<AttendeeDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from attendee where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<AttendeeDTO>(AttendeeDTO.class));
	}

	@Override
	public AttendeeDTO findById(Long id) {
		String sql = "select * from attendee where attendeeId = :attendeeId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("attendeeId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<AttendeeDTO>(AttendeeDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public AttendeeDTO insert(AttendeeDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public AttendeeDTO update(AttendeeDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
