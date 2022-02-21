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

import com.epms.dao.IEnquiryDAO;
import com.epms.dto.EnquiryDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class EnquiryDAO implements IEnquiryDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<EnquiryDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from enquiry");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EnquiryDTO>(EnquiryDTO.class));
	}

	@Override
	public List<EnquiryDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from enquiry where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<EnquiryDTO>(EnquiryDTO.class));
	}

	@Override
	public List<EnquiryDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from enquiry where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<EnquiryDTO>(EnquiryDTO.class));
	}

	@Override
	public EnquiryDTO findById(Long id) {
		String sql = "select * from enquiry where enquiryId = :enquiryId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("enquiryId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EnquiryDTO>(EnquiryDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public EnquiryDTO insert(EnquiryDTO entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource sc = new MapSqlParameterSource();
		sc.addValue("personName", entity.getPersonName());
		sc.addValue("mobileNumber", entity.getMobileNumber());
		sc.addValue("email", entity.getEmail());
		sc.addValue("eventTypeId", entity.getEventTypeId());
		sc.addValue("startDate", entity.getStartDate());
		sc.addValue("startTime", entity.getStartTime());
		sc.addValue("endDate", entity.getEndDate());
		sc.addValue("endTime", entity.getEndTime());
		sc.addValue("description", entity.getDescription());
		sc.addValue("isPublic", entity.getIsPublic());
		sc.addValue("isFree", entity.getIsFree());
		sc.addValue("estimatedGuest", entity.getEstimatedGuest());
		sc.addValue("minBudget", entity.getMinBudget());
		sc.addValue("maxBudget", entity.getMaxBudget());

		int i = jdbcTemplate.update(
				"insert into enquiry(personName,mobileNumber,email,eventTypeId,startDate,startTime,endDate,endTime,description,isPublic,isFree,estimatedGuest,minBudget,maxBudget) "
				+ "values(:personName,:mobileNumber,:email,:eventTypeId,:startDate,:startTime,:endDate,:endTime,:description,:isPublic,:isFree,:estimatedGuest,:minBudget,:maxBudget)",
				sc, keyHolder, new String[] { "enquiryId" });

		return findById(keyHolder.getKey().longValue());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnquiryDTO update(EnquiryDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<EnquiryDTO> findAllEnquiryWithStatus() {
		StringBuilder sql = new StringBuilder();
		sql.append("select e.*,es.status as enquiryStatus,et.eventType as eventType from enquiry e join enuenquirystatus es on e.enquiryStatusId = es.statusId left join enueventtype et on e.eventTypeId = et.eventTypeId ");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EnquiryDTO>(EnquiryDTO.class));
	}

	@Override
	public EnquiryDTO findEnquiryById(Long id) {
		String sql = "select e.*,es.status as enquiryStatus,et.eventType as eventType from enquiry e join enuenquirystatus es on e.enquiryStatusId = es.statusId left join enueventtype et on e.eventTypeId = et.eventTypeId where e.enquiryId = :enquiryId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("enquiryId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EnquiryDTO>(EnquiryDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void updateResponse(EnquiryDTO inquiry) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("response", inquiry.getResponse());
		namedParameters.addValue("enquiryStatusId", inquiry.getEnquiryStatusId());
		namedParameters.addValue("enquiryId", inquiry.getEnquiryId());
		
		jdbcTemplate.update(
				"update enquiry set response=:response,enquiryStatusId=:enquiryStatusId,responseDate=CURRENT_DATE(),responseTime=CURRENT_TIME() where enquiryId=:enquiryId",
				namedParameters);
	}

	@Override
	public void updateInreviewStatus(long enquiryId, Integer statusId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("enquiryStatusId", statusId);
		namedParameters.addValue("enquiryId", enquiryId);
		
		jdbcTemplate.update(
				"update enquiry set enquiryStatusId=:enquiryStatusId where enquiryId=:enquiryId",
				namedParameters);
		
	}
}
