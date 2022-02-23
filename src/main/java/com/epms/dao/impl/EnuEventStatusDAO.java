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

import com.epms.dao.IEnuEventStatusDAO;
import com.epms.dto.EnuEventStatusDTO;

@Repository
public class EnuEventStatusDAO implements IEnuEventStatusDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<EnuEventStatusDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from enueventstatus");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EnuEventStatusDTO>(EnuEventStatusDTO.class));
	}

	@Override
	public List<EnuEventStatusDTO> findByFieldValue(String fieldName, Object fieldValue) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<EnuEventStatusDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from enueventstatus where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<EnuEventStatusDTO>(EnuEventStatusDTO.class));
	}

	@Override
	public EnuEventStatusDTO findById(Long id) {
		String sql = "select * from enueventstatus where statusId = :statusId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("statusId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EnuEventStatusDTO>(EnuEventStatusDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public EnuEventStatusDTO insert(EnuEventStatusDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnuEventStatusDTO update(EnuEventStatusDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
