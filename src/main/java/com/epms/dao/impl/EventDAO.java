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

import com.epms.dao.IEventDAO;
import com.epms.dto.EventDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class EventDAO implements IEventDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<EventDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from event");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EventDTO>(EventDTO.class));
	}

	@Override
	public List<EventDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from event where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<EventDTO>(EventDTO.class));
	}

	@Override
	public List<EventDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from event where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<EventDTO>(EventDTO.class));
	}

	@Override
	public EventDTO findById(Long id) {
		String sql = "select * from event where eventId = :eventId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("eventId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EventDTO>(EventDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public EventDTO insert(EventDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EventDTO update(EventDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
