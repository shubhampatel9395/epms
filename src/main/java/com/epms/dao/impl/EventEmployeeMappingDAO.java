package com.epms.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.epms.dao.IEventEmployeeMappingDAO;
import com.epms.dto.EventEmployeeMappingDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class EventEmployeeMappingDAO implements IEventEmployeeMappingDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<EventEmployeeMappingDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from eventemployeemapping");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EventEmployeeMappingDTO>(EventEmployeeMappingDTO.class));
	}

	@Override
	public List<EventEmployeeMappingDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from eventemployeemapping where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters,
				new BeanPropertyRowMapper<EventEmployeeMappingDTO>(EventEmployeeMappingDTO.class));
	}

	@Override
	public List<EventEmployeeMappingDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from eventemployeemapping where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource,
				new BeanPropertyRowMapper<EventEmployeeMappingDTO>(EventEmployeeMappingDTO.class));
	}

	@Override
	public EventEmployeeMappingDTO findById(Long id) {
		String sql = "select * from eventemployeemapping where eventEmployeeMappingId = :eventEmployeeMappingId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("eventEmployeeMappingId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EventEmployeeMappingDTO>(EventEmployeeMappingDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public EventEmployeeMappingDTO insert(EventEmployeeMappingDTO entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("eventId", entity.getEventId());
		namedParams.addValue("employeeTypeId", entity.getEmployeeTypeId());
		namedParams.addValue("employeeId", entity.getEmployeeId());
		namedParams.addValue("workDescription", entity.getWorkDescription());

		jdbcTemplate.update(
				"insert into eventemployeemapping(eventId,employeeTypeId,employeeId,workDescription) values(:eventId,:employeeTypeId,:employeeId,:workDescription)",
				namedParams, keyHolder, new String[] { "eventEmployeemappingId" });

		return findById(keyHolder.getKey().longValue());
	}

	@Override
	public void delete(Long id) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("eventEmployeemappingId", id);
		jdbcTemplate.update("update eventemployeemapping set isActive=false where eventEmployeemappingId=:eventEmployeemappingId", namedParams);
	}

	@Override
	public EventEmployeeMappingDTO update(EventEmployeeMappingDTO entity) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("eventEmployeemappingId", entity.getEventEmployeeMappingId());
		namedParams.addValue("workDescription", entity.getWorkDescription());
		namedParams.addValue("statusId", entity.getStatusId());
		jdbcTemplate.update("update eventemployeemapping set workDescription=:workDescription,statusId=:statusId where eventEmployeemappingId=:eventEmployeemappingId", namedParams);
		return entity;
	}

}
