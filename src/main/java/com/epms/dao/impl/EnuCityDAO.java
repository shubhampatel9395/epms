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

import com.epms.dao.IEnuCityDAO;
import com.epms.dto.EnuCityDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class EnuCityDAO implements IEnuCityDAO {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<EnuCityDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select cityId, city, stateId from enucity");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EnuCityDTO>(EnuCityDTO.class));
	}

	@Override
	public List<EnuCityDTO> findByFieldValue(String fieldName, Object fieldValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnuCityDTO findById(Long id) {
		String sql = "select  cityId, city, stateId from enucity where cityId = :cityId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cityId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EnuCityDTO>(EnuCityDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<EnuCityDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select cityId, city, stateId from enucity where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<EnuCityDTO>(EnuCityDTO.class));
	}

	@Override
	public EnuCityDTO insert(EnuCityDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnuCityDTO update(EnuCityDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
