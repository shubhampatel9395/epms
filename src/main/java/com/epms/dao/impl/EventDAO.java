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

import com.epms.dao.IEventDAO;
import com.epms.dto.EventDTO;
import com.epms.dto.PackageDetailsDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class EventDAO implements IEventDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<EventDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from event");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EventDTO>(EventDTO.class));
	}

	@Override
	public List<EventDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from event where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<EventDTO>(EventDTO.class));
	}

	@Override
	public List<EventDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from event where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<EventDTO>(EventDTO.class));
	}

	@Override
	public EventDTO findById(Long id) {
		String sql = "select * from event where eventId = :eventId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("eventId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EventDTO>(EventDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public EventDTO insert(EventDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("eventId", id);
		jdbcTemplate.update("update event set isActive=false where eventId=:eventId", namedParams);
	}

	@Override
	public EventDTO update(EventDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Integer getCountByEventType(String eventType)
	{
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("eventType", eventType);
		namedParams.addValue("isActive", true);
		namedParams.addValue("statusRegistered", "Registered");
		namedParams.addValue("statusVerified", "Verified");
		int count = jdbcTemplate.queryForObject("select count(1) from event e join enueventtype et on e.eventTypeId = et.eventTypeId join enueventstatus ees on e.eventStatusId = ees.statusId where et.eventType=:eventType and (ees.status=:statusRegistered or ees.status=:statusVerified) and e.isActive=:isActive",namedParams,Integer.class);
		return count;
	}
	
	@Override
	public Integer getCount()
	{
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("isActive", true);
		namedParams.addValue("statusRegistered", "Registered");
		namedParams.addValue("statusVerified", "Verified");
		int count = jdbcTemplate.queryForObject("select count(1) from event e join enueventstatus ees on e.eventStatusId = ees.statusId where (ees.status=:statusRegistered or ees.status=:statusVerified) and e.isActive=:isActive",namedParams,Integer.class);
		return count;
	}

	@Override
	public List<EventDTO> getLastDayData() {
		String sql = "select * from event where 1=1 and createdAt >= DATE(NOW()) - INTERVAL 1 DAY";
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EventDTO>(EventDTO.class));
	}

	@Override
	public EventDTO insertByAdmin(EventDTO eventDTO) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("eventTitle", eventDTO.getEventTitle());
		namedParams.addValue("objective", eventDTO.getObjective());
		namedParams.addValue("eventTypeId", eventDTO.getEventTypeId());
		namedParams.addValue("userDetailsId", eventDTO.getUserDetailsId());
		namedParams.addValue("packageId", eventDTO.getPackageId());
		namedParams.addValue("eventOrganizerId", eventDTO.getEventOrganizerId());
		namedParams.addValue("isPublic", eventDTO.getIsPublic());
		namedParams.addValue("isFree", eventDTO.getIsFree());
		namedParams.addValue("startDate", eventDTO.getStartDate());
		namedParams.addValue("startTime", eventDTO.getStartTime());
		namedParams.addValue("endDate", eventDTO.getEndDate());
		namedParams.addValue("endTime", eventDTO.getEndTime());
		namedParams.addValue("estimatedGuest", eventDTO.getEstimatedGuest());
		namedParams.addValue("totalCost", eventDTO.getTotalCost());
		namedParams.addValue("eventStatusId", eventDTO.getEventStatusId());
		
		if(eventDTO.getIsFree() != true)
		{
			namedParams.addValue("registrationFee", eventDTO.getRegistrationFee());
			namedParams.addValue("registrationAvailable", eventDTO.getRegistrationAvailable());
		} else {
			namedParams.addValue("registrationFee", 0);
			namedParams.addValue("registrationAvailable", 0);
		}
		
		jdbcTemplate.update("insert into event(eventTitle,objective,eventTypeId,userDetailsId,packageId,eventOrganizerId,isPublic,isFree,startDate,startTime,endDate,endTime,estimatedGuest,registrationFee,registrationAvailable,totalCost,eventStatusId) values(:eventTitle,:objective,:eventTypeId,:userDetailsId,:packageId,:eventOrganizerId,:isPublic,:isFree,:startDate,:startTime,:endDate,:endTime,:estimatedGuest,:registrationFee,:registrationAvailable,:totalCost,:eventStatusId)",namedParams,keyHolder,new String[] { "eventId" });
		
		return findById(keyHolder.getKey().longValue());
	}

	@Override
	public EventDTO verifyEvent(EventDTO eventDTO, PackageDetailsDTO packageDetailsDTO) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("eventId", eventDTO.getEventId());
		namedParams.addValue("eventStatusId", eventDTO.getEventStatusId());
		namedParams.addValue("eventOrganizerId", eventDTO.getEventOrganizerId());
		namedParams.addValue("packageDetailsId", packageDetailsDTO.getPackageDetailsId());
		namedParams.addValue("venueId", packageDetailsDTO.getVenueId());
		
		jdbcTemplate.update("update event e, packagedetails p set e.eventOrganizerId=:eventOrganizerId,e.eventStatusId=:eventStatusId,p.venueId=:venueId where e.packageId=p.packageDetailsId AND e.eventId=:eventId;", namedParams);
		
		return findById(eventDTO.getEventId().longValue());
	}

	@Override
	public void unVerifyEvent(long eventId) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("eventId", eventId);
		jdbcTemplate.update("update event set isActive=false where eventId=:eventId", namedParams);
	}

	@Override
	public void complete(long eventId) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("eventId", eventId);
		jdbcTemplate.update("update event set eventStatusId=(SELECT statusId from enueventstatus where status='Completed') where eventId=:eventId", namedParams);
	}
}

