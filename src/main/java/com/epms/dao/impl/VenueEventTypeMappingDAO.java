package com.epms.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.epms.dao.IVenueEventTypeMappingDAO;
import com.epms.dto.VenueEventTypeMappingDTO;

public class VenueEventTypeMappingDAO implements IVenueEventTypeMappingDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<VenueEventTypeMappingDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from venueeventtypemapping");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<VenueEventTypeMappingDTO>(VenueEventTypeMappingDTO.class));
	}

	@Override
	public List<VenueEventTypeMappingDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from venueeventtypemapping where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters,
				new BeanPropertyRowMapper<VenueEventTypeMappingDTO>(VenueEventTypeMappingDTO.class));
	}

	@Override
	public List<VenueEventTypeMappingDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from venueeventtypemapping where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource,
				new BeanPropertyRowMapper<VenueEventTypeMappingDTO>(VenueEventTypeMappingDTO.class));
	}

	@Override
	public VenueEventTypeMappingDTO findById(Long id) {
		String sql = "select * from venueeventtypemapping where venueEventTypeMappingId = :venueEventTypeMappingId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("venueEventTypeMappingId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<VenueEventTypeMappingDTO>(VenueEventTypeMappingDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public VenueEventTypeMappingDTO insert(VenueEventTypeMappingDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public VenueEventTypeMappingDTO update(VenueEventTypeMappingDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
