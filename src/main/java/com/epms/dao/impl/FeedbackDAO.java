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

import com.epms.dao.IFeedbackDAO;
import com.epms.dto.FeedbackDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class FeedbackDAO implements IFeedbackDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<FeedbackDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from feedback");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<FeedbackDTO>(FeedbackDTO.class));
	}

	@Override
	public List<FeedbackDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from feedback where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<FeedbackDTO>(FeedbackDTO.class));
	}

	@Override
	public List<FeedbackDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from feedback where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<FeedbackDTO>(FeedbackDTO.class));
	}

	@Override
	public FeedbackDTO findById(Long id) {
		String sql = "select * from feedback where feedbackId = :feedbackId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("feedbackId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<FeedbackDTO>(FeedbackDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public FeedbackDTO insert(FeedbackDTO entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("eventId", entity.getEventId());
		namedParameters.addValue("eventRating", entity.getEventRating());
		namedParameters.addValue("eventDescription", entity.getEventDescription());
		namedParameters.addValue("serviceProviderId", entity.getServiceProviderId());
		namedParameters.addValue("serviceProviderRating", entity.getServiceProviderRating());
		namedParameters.addValue("serviceProviderDescription", entity.getServiceProviderDescription());
		namedParameters.addValue("packageId", entity.getPackageId());
		namedParameters.addValue("packageRating", entity.getPackageRating());
		namedParameters.addValue("packageDescription", entity.getPackageDescription());
		jdbcTemplate.update(
				"insert into feedback(eventId,eventRating,eventDescription,serviceProviderId,serviceProviderRating,serviceProviderDescription,packageId,packageRating,packageDescription) values(:eventId,:eventRating,:eventDescription,:serviceProviderId,:serviceProviderRating,:serviceProviderDescription,:packageId,:packageRating,:packageDescription)",
				namedParameters, keyHolder, new String[] { "feedbackId" });
		return findById(keyHolder.getKey().longValue());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
	}

	@Override
	public FeedbackDTO update(FeedbackDTO entity) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("feedbackId", entity.getFeedbackId());
		namedParameters.addValue("eventId", entity.getEventId());
		namedParameters.addValue("eventRating", entity.getEventRating());
		namedParameters.addValue("eventDescription", entity.getEventDescription());
		namedParameters.addValue("serviceProviderId", entity.getServiceProviderId());
		namedParameters.addValue("serviceProviderRating", entity.getServiceProviderRating());
		namedParameters.addValue("serviceProviderDescription", entity.getServiceProviderDescription());
		namedParameters.addValue("packageId", entity.getPackageId());
		namedParameters.addValue("packageRating", entity.getPackageRating());
		namedParameters.addValue("packageDescription", entity.getPackageDescription());

		jdbcTemplate.update(
				"update feedback set eventId=:eventId,eventRating=:eventRating,eventDescription=:eventDescription,serviceProviderId=:serviceProviderId,serviceProviderRating=:serviceProviderRating,serviceProviderDescription=:serviceProviderDescription,packageId=:packageId,packageRating=:packageRating,packageDescription=:packageDescription where feedbackId=:feedbackId",
				namedParameters);
		return entity;
	}

}
