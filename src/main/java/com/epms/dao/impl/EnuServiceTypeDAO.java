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

import com.epms.dao.IEnuServiceTypeDAO;
import com.epms.dto.EnuCountryDTO;
import com.epms.dto.EnuServiceTypeDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class EnuServiceTypeDAO implements IEnuServiceTypeDAO {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<EnuServiceTypeDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select serviceTypeId,service,description,isActive from enuservicetype");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EnuServiceTypeDTO>(EnuServiceTypeDTO.class));
	}

	@Override
	public List<EnuServiceTypeDTO> findAllActive() {
		StringBuilder sql = new StringBuilder();
		sql.append("select serviceTypeId,service,description,isActive from enuservicetype where isActive=1");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EnuServiceTypeDTO>(EnuServiceTypeDTO.class));
	}

	@Override
	public List<EnuServiceTypeDTO> findByFieldValue(String fieldName, Object fieldValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnuServiceTypeDTO findById(Long id) {
		String sql = "select serviceTypeId,service,description,isActive from enuservicetype where serviceTypeId = :serviceTypeId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("serviceTypeId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EnuServiceTypeDTO>(EnuServiceTypeDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<EnuServiceTypeDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select serviceTypeId,service,description,isActive from enuservicetype where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<EnuServiceTypeDTO>(EnuServiceTypeDTO.class));
	}
	
	@Override
	public EnuServiceTypeDTO insert(EnuServiceTypeDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnuServiceTypeDTO update(EnuServiceTypeDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}
}
