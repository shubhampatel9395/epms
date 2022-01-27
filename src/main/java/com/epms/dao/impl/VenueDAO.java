package com.epms.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.epms.dao.IVenueDAO;
import com.epms.dto.VenueDTO;

public class VenueDAO implements IVenueDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<VenueDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from venue");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<VenueDTO>(VenueDTO.class));
	}

	@Override
	public List<VenueDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from venue where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<VenueDTO>(VenueDTO.class));
	}

	@Override
	public List<VenueDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from venue where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<VenueDTO>(VenueDTO.class));
	}

	@Override
	public VenueDTO findById(Long id) {
		String sql = "select * from venue where venueId = :venueId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("venueId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<VenueDTO>(VenueDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public VenueDTO insert(VenueDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public VenueDTO update(VenueDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
