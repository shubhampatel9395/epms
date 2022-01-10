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

import com.epms.dao.IEnuCountryDAO;
import com.epms.dto.EnuCountryDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class EnuCountryDAO implements IEnuCountryDAO {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<EnuCountryDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append(" select countryId, country from enucountry");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EnuCountryDTO>(EnuCountryDTO.class));
	}

	@Override
	public List<EnuCountryDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return null;
	}

	@Override
	public EnuCountryDTO findById(Long id) {
		String sql = "select  countryId, country from enucountry where countryId = :countryId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("countryId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EnuCountryDTO>(EnuCountryDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<EnuCountryDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select countryId, country from enucountry where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<EnuCountryDTO>(EnuCountryDTO.class));
	}

	@Override
	public EnuCountryDTO insert(EnuCountryDTO entity) {
		return null;
	}

	@Override
	public void delete(Long id) {

	}

	@Override
	public EnuCountryDTO update(EnuCountryDTO entity) {
		return null;
	}
}
