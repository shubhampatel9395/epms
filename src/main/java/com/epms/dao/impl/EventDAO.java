package com.epms.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import javax.validation.Valid;

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
import com.epms.dto.AssignedEmployeesInEventDTO;
import com.epms.dto.EventDTO;
import com.epms.dto.PackageDetailsDTO;
import com.epms.dto.UpcomingWeekEventDTO;

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
	public Integer getCountByEventType(String eventType) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("eventType", eventType);
		namedParams.addValue("isActive", true);
		namedParams.addValue("statusRegistered", "Registered");
		namedParams.addValue("statusVerified", "Verified");
		namedParams.addValue("statusCompleted", "Completed");
		int count = jdbcTemplate.queryForObject(
				"select count(1) from event e join enueventtype et on e.eventTypeId = et.eventTypeId join enueventstatus ees on e.eventStatusId = ees.statusId where et.eventType=:eventType and (ees.status=:statusRegistered or ees.status=:statusVerified or ees.status=:statusCompleted) and e.isActive=:isActive",
				namedParams, Integer.class);
		return count;
	}

	@Override
	public Integer getCount() {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("isActive", true);
		namedParams.addValue("statusRegistered", "Registered");
		namedParams.addValue("statusVerified", "Verified");
		namedParams.addValue("statusCompleted", "Completed");
		int count = jdbcTemplate.queryForObject(
				"select count(1) from event e join enueventstatus ees on e.eventStatusId = ees.statusId where (ees.status=:statusRegistered or ees.status=:statusVerified or ees.status=:statusCompleted) and e.isActive=:isActive",
				namedParams, Integer.class);
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

		if (eventDTO.getIsFree() != true) {
			namedParams.addValue("registrationFee", eventDTO.getRegistrationFee());
			namedParams.addValue("registrationAvailable", eventDTO.getRegistrationAvailable());
		} else {
			namedParams.addValue("registrationFee", 0);
			namedParams.addValue("registrationAvailable", 0);
		}

		jdbcTemplate.update(
				"insert into event(eventTitle,objective,eventTypeId,userDetailsId,packageId,eventOrganizerId,isPublic,isFree,startDate,startTime,endDate,endTime,estimatedGuest,registrationFee,registrationAvailable,totalCost,eventStatusId) values(:eventTitle,:objective,:eventTypeId,:userDetailsId,:packageId,:eventOrganizerId,:isPublic,:isFree,:startDate,:startTime,:endDate,:endTime,:estimatedGuest,:registrationFee,:registrationAvailable,:totalCost,:eventStatusId)",
				namedParams, keyHolder, new String[] { "eventId" });

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
		namedParams.addValue("totalCost", eventDTO.getTotalCost());

		jdbcTemplate.update(
				"update event e, packagedetails p set e.totalCost=:totalCost,e.eventOrganizerId=:eventOrganizerId,e.eventStatusId=:eventStatusId,p.venueId=:venueId where e.packageId=p.packageDetailsId AND e.eventId=:eventId;",
				namedParams);

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
		jdbcTemplate.update(
				"update event set eventStatusId=(SELECT statusId from enueventstatus where status='Completed') where eventId=:eventId",
				namedParams);
	}

	@Override
	public EventDTO updateByAdmin(EventDTO eventDTO) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("eventId", eventDTO.getEventId());
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
		namedParams.addValue("registrationFee", eventDTO.getRegistrationFee());
		namedParams.addValue("registrationAvailable", eventDTO.getRegistrationAvailable());

		jdbcTemplate.update(
				"update event set eventTitle=:eventTitle,objective=:objective,eventTypeId=:eventTypeId,userDetailsId=:userDetailsId,"
						+ "packageId=:packageId,eventOrganizerId=:eventOrganizerId,isPublic=:isPublic,isFree=:isFree,startDate=:startDate,startTime=:startTime,"
						+ "endDate=:endDate,endTime=:endTime,estimatedGuest=:estimatedGuest,totalCost=:totalCost,eventStatusId=:eventStatusId,registrationFee=:registrationFee,registrationAvailable=:registrationAvailable where eventId=:eventId",
				namedParams);
		return eventDTO;
	}

	@Override
	public EventDTO insertByCustomer(@Valid EventDTO eventDTO) {
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

		if (eventDTO.getIsFree() != true) {
			namedParams.addValue("registrationFee", eventDTO.getRegistrationFee());
			namedParams.addValue("registrationAvailable", eventDTO.getRegistrationAvailable());
		} else {
			namedParams.addValue("registrationFee", 0);
			namedParams.addValue("registrationAvailable", 0);
		}

		jdbcTemplate.update(
				"insert into event(eventTitle,objective,eventTypeId,userDetailsId,packageId,eventOrganizerId,isPublic,isFree,startDate,startTime,endDate,endTime,estimatedGuest,registrationFee,registrationAvailable,totalCost,eventStatusId) values(:eventTitle,:objective,:eventTypeId,:userDetailsId,:packageId,:eventOrganizerId,:isPublic,:isFree,:startDate,:startTime,:endDate,:endTime,:estimatedGuest,:registrationFee,:registrationAvailable,:totalCost,:eventStatusId)",
				namedParams, keyHolder, new String[] { "eventId" });

		return findById(keyHolder.getKey().longValue());
	}

	@Override
	public List<AssignedEmployeesInEventDTO> getAllAssignedEmployees(Long eventId) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("eventId", eventId);
		return jdbcTemplate.query("SELECT em.eventEmployeemappingId,concat(u.firstName,' ',u.lastName) as employeeName,er.role as employeeRole,u.email,u.mobileNumber,em.workDescription,emws.status as workingStatus from event e\r\n"
				+ "join eventemployeemapping em on em.eventId=e.eventId\r\n"
				+ "join enuemployeeworkingstatus emws on emws.statusId=em.statusId\r\n"
				+ "join employee emp on emp.employeeId=em.employeeId\r\n"
				+ "join enuemployeerole er on emp.employeeRoleId=er.employeeRoleId\r\n"
				+ "join userdetails u on emp.userDetailsId=u.userDetailsId\r\n"
				+ "where e.isActive=true and e.eventId=:eventId and em.isActive=true", namedParams, new BeanPropertyRowMapper<AssignedEmployeesInEventDTO>(AssignedEmployeesInEventDTO.class));
	}
	
	@Override
	public List<UpcomingWeekEventDTO> getUpcomingWeekEvents() {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		return jdbcTemplate.query("SELECT e.eventId,e.eventTitle,e.startDate,e.startTime,concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber\r\n"
				+ "from event e\r\n"
				+ "join userdetails u on u.userDetailsId= e.userDetailsId\r\n"
				+ "where e.isActive=true and e.eventStatusId=(SELECT statusId from enueventstatus where status='Verified')\r\n"
				+ "and e.startDate BETWEEN DATE(NOW()) AND DATE(NOW()) + INTERVAL 6 DAY and IF(e.startDate <=> e.endDate <=> DATE(NOW()), e.startTime >= TIME(NOW()), true) ORDER BY e.startDate", namedParams, new BeanPropertyRowMapper<UpcomingWeekEventDTO>(UpcomingWeekEventDTO.class));
	}
}
