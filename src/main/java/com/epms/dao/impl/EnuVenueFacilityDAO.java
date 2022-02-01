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

import com.epms.dao.IEnuVenueFacilityDAO;
import com.epms.dto.EnuVenueFacilityDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class EnuVenueFacilityDAO implements IEnuVenueFacilityDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<EnuVenueFacilityDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from enuvenuefacility");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EnuVenueFacilityDTO>(EnuVenueFacilityDTO.class));
	}

	@Override
	public List<EnuVenueFacilityDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from enuvenuefacility where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters,
				new BeanPropertyRowMapper<EnuVenueFacilityDTO>(EnuVenueFacilityDTO.class));
	}

	@Override
	public List<EnuVenueFacilityDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from enuvenuefacility where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource,
				new BeanPropertyRowMapper<EnuVenueFacilityDTO>(EnuVenueFacilityDTO.class));
	}

	@Override
	public EnuVenueFacilityDTO findById(Long id) {
		String sql = "select * from enuvenuefacility where venueFacilityId = :venueFacilityId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("venueFacilityId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EnuVenueFacilityDTO>(EnuVenueFacilityDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public EnuVenueFacilityDTO insert(EnuVenueFacilityDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnuVenueFacilityDTO update(EnuVenueFacilityDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
