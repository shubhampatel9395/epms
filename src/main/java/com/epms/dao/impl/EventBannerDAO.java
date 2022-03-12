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

import com.epms.dao.IEventBannerDAO;
import com.epms.dto.EventBannerDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class EventBannerDAO implements IEventBannerDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<EventBannerDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from eventbanner");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EventBannerDTO>(EventBannerDTO.class));
	}

	@Override
	public List<EventBannerDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from eventbanner where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters,
				new BeanPropertyRowMapper<EventBannerDTO>(EventBannerDTO.class));
	}

	@Override
	public List<EventBannerDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from eventbanner where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<EventBannerDTO>(EventBannerDTO.class));
	}

	@Override
	public EventBannerDTO findById(Long id) {
		String sql = "select * from eventbanner where bannerId = :bannerId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("bannerId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EventBannerDTO>(EventBannerDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public EventBannerDTO insert(EventBannerDTO entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		MapSqlParameterSource sc = new MapSqlParameterSource();
		sc.addValue("eventId", entity.getEventId());
		sc.addValue("banner", entity.getBanner());

		int i = jdbcTemplate.update("insert into eventbanner(eventId,banner) values(:eventId,:banner)", sc,
				keyHolder, new String[] { "bannerId" });

		return findById(keyHolder.getKey().longValue());
	}

	@Override
	public void delete(Long id) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("eventId", id);
		jdbcTemplate.update("update eventbanner set isActive=false where eventId=:eventId", namedParams);
	}

	@Override
	public EventBannerDTO update(EventBannerDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
