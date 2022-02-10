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

import com.epms.dao.IEnuVenueTypeDAO;
import com.epms.dto.EnuVenueTypeDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class EnuVenueTypeDAO implements IEnuVenueTypeDAO {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<EnuVenueTypeDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from enuvenuetype");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EnuVenueTypeDTO>(EnuVenueTypeDTO.class));
	}

	@Override
	public List<EnuVenueTypeDTO> findByFieldValue(String fieldName, Object fieldValue) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<EnuVenueTypeDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from enuvenuetype where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<EnuVenueTypeDTO>(EnuVenueTypeDTO.class));
	}

	@Override
	public EnuVenueTypeDTO findById(Long id) {
		String sql = "select * from enuvenuetype where enuVenueTypeId = :enuVenueTypeId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("enuVenueTypeId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EnuVenueTypeDTO>(EnuVenueTypeDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public EnuVenueTypeDTO insert(EnuVenueTypeDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EnuVenueTypeDTO update(EnuVenueTypeDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
