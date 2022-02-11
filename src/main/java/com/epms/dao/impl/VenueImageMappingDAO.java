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

import com.epms.dao.IVenueImageMappingDAO;
import com.epms.dto.VenueImageMappingDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class VenueImageMappingDAO implements IVenueImageMappingDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<VenueImageMappingDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from venueimagemapping");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<VenueImageMappingDTO>(VenueImageMappingDTO.class));
	}

	@Override
	public List<VenueImageMappingDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from venueimagemapping where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters,
				new BeanPropertyRowMapper<VenueImageMappingDTO>(VenueImageMappingDTO.class));
	}

	@Override
	public List<VenueImageMappingDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from venueimagemapping where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource,
				new BeanPropertyRowMapper<VenueImageMappingDTO>(VenueImageMappingDTO.class));
	}

	@Override
	public VenueImageMappingDTO findById(Long id) {
		String sql = "select * from venueimagemapping where venueImageMappingId = :venueImageMappingId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("venueImageMappingId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<VenueImageMappingDTO>(VenueImageMappingDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public VenueImageMappingDTO insert(VenueImageMappingDTO entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		MapSqlParameterSource sc = new MapSqlParameterSource();
		sc.addValue("venueId", entity.getVenueId());
		sc.addValue("image", entity.getImage());

		int i = jdbcTemplate.update("insert into venueimagemapping(venueId,image) values(:venueId,:image)", sc,
				keyHolder, new String[] { "venueImageMappingId" });

		return findById(keyHolder.getKey().longValue());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public VenueImageMappingDTO update(VenueImageMappingDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
