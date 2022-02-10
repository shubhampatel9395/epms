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

import com.epms.dao.IVenueFacilityMappingDAO;
import com.epms.dto.VenueFacilityMappingDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class VenueFacilityMappingDAO implements IVenueFacilityMappingDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<VenueFacilityMappingDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from venuefacilitymapping");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<VenueFacilityMappingDTO>(VenueFacilityMappingDTO.class));
	}

	@Override
	public List<VenueFacilityMappingDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from venuefacilitymapping where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters,
				new BeanPropertyRowMapper<VenueFacilityMappingDTO>(VenueFacilityMappingDTO.class));
	}

	@Override
	public List<VenueFacilityMappingDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from venuefacilitymapping where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource,
				new BeanPropertyRowMapper<VenueFacilityMappingDTO>(VenueFacilityMappingDTO.class));
	}

	@Override
	public VenueFacilityMappingDTO findById(Long id) {
		String sql = "select * from venuefacilitymapping where venueFacilityMappingId = :venueFacilityMappingId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("venueFacilityMappingId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<VenueFacilityMappingDTO>(VenueFacilityMappingDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public VenueFacilityMappingDTO insert(VenueFacilityMappingDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public VenueFacilityMappingDTO update(VenueFacilityMappingDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Long venueId,List<String> list) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("venueId", venueId);
		for (String item : list) {
			namedParameters.addValue("venueFacilityId", item);
			jdbcTemplate.update("insert into venuefacilitymapping(venueId,facilityId) values(:venueId,:venueFacilityId)",namedParameters);
		}
	}

}
