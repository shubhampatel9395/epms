package com.epms.dao.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.epms.dao.IEmployeeDAO;
import com.epms.dto.AllServiceProvidersPackageDTO;
import com.epms.dto.EmployeeDTO;
import com.epms.dto.EmployeeEventWorkDTO;
import com.epms.dto.EventOrganizerEventWorkDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class EmployeeDAO implements IEmployeeDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<EmployeeDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from employee");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EmployeeDTO>(EmployeeDTO.class));
	}

	@Override
	public List<EmployeeDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from employee where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<EmployeeDTO>(EmployeeDTO.class));
	}

	@Override
	public List<EmployeeDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from employee where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<EmployeeDTO>(EmployeeDTO.class));
	}

	@Override
	public EmployeeDTO findById(Long id) {
		String sql = "select * from employee where employeeId = :employeeId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("employeeId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EmployeeDTO>(EmployeeDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public EmployeeDTO insert(EmployeeDTO entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("firstName", entity.getFirstName());
		namedParameters.addValue("lastName", entity.getLastName());
		namedParameters.addValue("addressId", entity.getAddressId());
		namedParameters.addValue("email", entity.getEmail());
		namedParameters.addValue("password", entity.getPassword());
		namedParameters.addValue("mobileNumber", entity.getMobileNumber());
		if (entity.getEmployeeRoleId() == 2) {
			namedParameters.addValue("roleName", "ROLE_EVENTORGANIZER");
		} else {
			namedParameters.addValue("roleName", "ROLE_EMPLOYEE");
		}
		namedParameters.addValue("isCustomer", false);
		namedParameters.addValue("isEmployee", true);
		namedParameters.addValue("isAuth", true);

		int i = jdbcTemplate.update(
				"insert into userDetails(firstName,lastName,addressId,email,password,mobileNumber,roleName,isCustomer,isEmployee,isAuth) values(:firstName,:lastName,:addressId,:email,:password,:mobileNumber,:roleName,:isCustomer,:isEmployee,:isAuth)",
				namedParameters, keyHolder, new String[] { "userDetailsId" });

		namedParameters.addValue("userDetailsId", keyHolder.getKey().longValue());
		namedParameters.addValue("employeeRoleId", entity.getEmployeeRoleId());
		namedParameters.addValue("gender", entity.getGender());
		namedParameters.addValue("DOB", entity.getDOB());
		namedParameters.addValue("hiringDate", entity.getHiringDate());
		namedParameters.addValue("salary", entity.getSalary());

		i = jdbcTemplate.update(
				"insert into employee(userDetailsId,employeeRoleId,gender,DOB,hiringDate,salary) values(:userDetailsId,:employeeRoleId,:gender,:DOB,:hiringDate,:salary)",
				namedParameters, keyHolder, new String[] { "employeeId" });

		return findById(keyHolder.getKey().longValue());
	}

	@Override
	public void delete(Long id) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("employeeId", id);
		parameterSource.addValue("leavingDate", LocalDate.now());
		jdbcTemplate.update("update employee set isActive=false,leavingDate=:leavingDate where employeeId=:employeeId",
				parameterSource);
	}

	@Override
	public EmployeeDTO update(EmployeeDTO entity) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		// Update UserDetails
		namedParameters.addValue("userDetailsId", entity.getUserDetailsId());
		namedParameters.addValue("firstName", entity.getFirstName());
		namedParameters.addValue("lastName", entity.getLastName());
		namedParameters.addValue("email", entity.getEmail());
		namedParameters.addValue("mobileNumber", entity.getMobileNumber());
		jdbcTemplate.update(
				"update userDetails set firstName=:firstName,lastName=:lastName,email=:email,mobileNumber=:mobileNumber where userDetailsId=:userDetailsId",
				namedParameters);

		// Update Employee
		namedParameters.addValue("employeeId", entity.getEmployeeId());
		namedParameters.addValue("employeeRoleId", entity.getEmployeeRoleId());
		namedParameters.addValue("gender", entity.getGender());
		namedParameters.addValue("DOB", entity.getDOB());
		namedParameters.addValue("hiringDate", entity.getHiringDate());
		namedParameters.addValue("salary", entity.getSalary());

		jdbcTemplate.update(
				"update employee set employeeRoleId=:employeeRoleId,gender=:gender,DOB=:DOB,hiringDate=:hiringDate,salary=:salary where employeeId=:employeeId",
				namedParameters);

		return entity;
	}

	@Override
	public void activate(Long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("employeeId", id);
		namedParameters.addValue("leavingDate", null);
		jdbcTemplate.update("update employee set isActive=true,leavingDate=:leavingDate where employeeId=:employeeId",
				namedParameters);
	}

	@Override
	public Integer getEventOrganizerCompletedEventCount(Long eventOrganizerId) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("eventOrganizerId", eventOrganizerId);
		return jdbcTemplate.queryForObject("SELECT count(1) FROM event e\r\n"
				+ "where e.eventStatusId=(SELECT statusId from enueventstatus where status='Completed') and e.isActive=true and e.eventOrganizerId=:eventOrganizerId",namedParams, Integer.class);
	}

	@Override
	public Integer getEventOrganizerUpcomingEventCount(Long eventOrganizerId) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("eventOrganizerId", eventOrganizerId);
		return jdbcTemplate.queryForObject("SELECT count(1) FROM event e\r\n"
				+ "where e.eventStatusId=(SELECT statusId from enueventstatus where status='Verified') and e.isActive=true and e.eventOrganizerId=:eventOrganizerId",namedParams, Integer.class);
	}

	@Override
	public Integer getEmployeeCompletedEventCount(Long employeeId) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("employeeId", employeeId);
		return jdbcTemplate.queryForObject("SELECT count(1) from event e\r\n"
				+ "join eventemployeemapping em on em.eventId=e.eventId\r\n"
				+ "where em.employeeId=:employeeId and em.isActive=true and e.eventStatusId=(SELECT statusId from enueventstatus where status='Verified');",namedParams, Integer.class);
	}

	@Override
	public Integer getEmployeeUpcomingEventCount(Long employeeId) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("employeeId", employeeId);
		return jdbcTemplate.queryForObject("SELECT count(1) from event e\r\n"
				+ "join eventemployeemapping em on em.eventId=e.eventId\r\n"
				+ "where em.employeeId=:employeeId and em.isActive=true and e.eventStatusId=(SELECT statusId from enueventstatus where status='Completed');",namedParams, Integer.class);
	}

	@Override
	public List<EventOrganizerEventWorkDTO> getEventOrganizerCompletedEventDetails(Long eventOrganizerId) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("eventOrganizerId", eventOrganizerId);
		return jdbcTemplate.query("SELECT e.eventId,e.eventTitle,e.objective,(SELECT bannerId from eventbanner where e.eventId=eventId and isActive=true) as bannerId,et.eventType,e.startDate,e.startTime,e.endDate,e.endTime,e.isPublic,e.updatedAt,\r\n"
				+ "concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber,\r\n"
				+ "(SELECT concat(firstName,' ',lastName) from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerName,\r\n"
				+ "(SELECT email from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerEmail,\r\n"
				+ "(SELECT mobileNumber from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerMobileNumber,\r\n"
				+ "v.venueName,v.addressId,v.email as venueEmail,v.contactNumber,e.isFree,e.packageId\r\n"
				+ "from event e\r\n"
				+ "join packagedetails p on e.packageId=p.packageDetailsId\r\n"
				+ "join venue v on p.venueId = v.venueId\r\n"
				+ "join enueventtype et on et.eventTypeId = e.eventTypeId\r\n"
				+ "join userdetails u on u.userDetailsId= e.userDetailsId\r\n"
				+ "join employee emp on emp.employeeId=e.eventOrganizerId\r\n"
				+ "where e.eventStatusId=(SELECT statusId from enueventstatus where status='Completed') and e.isActive=true and e.eventOrganizerId=:eventOrganizerId ORDER BY e.endDate DESC",namedParams,new BeanPropertyRowMapper<EventOrganizerEventWorkDTO>(EventOrganizerEventWorkDTO.class));
	}

	@Override
	public List<EventOrganizerEventWorkDTO> getEventOrganizerUpcomingEventDetails(Long eventOrganizerId) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("eventOrganizerId", eventOrganizerId);
		return jdbcTemplate.query("SELECT e.eventId,e.eventTitle,e.objective,(SELECT bannerId from eventbanner where e.eventId=eventId and isActive=true) as bannerId,et.eventType,e.startDate,e.startTime,e.endDate,e.endTime,e.isPublic,e.updatedAt,\r\n"
				+ "concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber,\r\n"
				+ "(SELECT concat(firstName,' ',lastName) from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerName,\r\n"
				+ "(SELECT email from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerEmail,\r\n"
				+ "(SELECT mobileNumber from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerMobileNumber,\r\n"
				+ "v.venueName,v.addressId,v.email as venueEmail,v.contactNumber,e.isFree,e.packageId\r\n"
				+ "from event e\r\n"
				+ "join packagedetails p on e.packageId=p.packageDetailsId\r\n"
				+ "join venue v on p.venueId = v.venueId\r\n"
				+ "join enueventtype et on et.eventTypeId = e.eventTypeId\r\n"
				+ "join userdetails u on u.userDetailsId= e.userDetailsId\r\n"
				+ "join employee emp on emp.employeeId=e.eventOrganizerId\r\n"
				+ "where e.eventStatusId=(SELECT statusId from enueventstatus where status='Verified') and e.isActive=true and e.eventOrganizerId=:eventOrganizerId ORDER BY e.startDate\r\n"
				+ "",namedParams,new BeanPropertyRowMapper<EventOrganizerEventWorkDTO>(EventOrganizerEventWorkDTO.class));
	}

	@Override
	public List<EmployeeEventWorkDTO> getEmployeeCompletedEventDetails(Long employeeId) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("employeeId", employeeId);
		return jdbcTemplate.query("SELECT e.eventId,e.eventTitle,e.objective,(SELECT bannerId from eventbanner where e.eventId=eventId and isActive=true) as bannerId,et.eventType,e.startDate,e.startTime,e.endDate,e.endTime,e.isPublic,e.isFree,e.updatedAt,\r\n"
				+ "concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber,\r\n"
				+ "(SELECT concat(firstName,' ',lastName) from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerName,\r\n"
				+ "(SELECT email from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerEmail,\r\n"
				+ "(SELECT mobileNumber from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerMobileNumber,\r\n"
				+ "v.venueName,v.addressId,v.email as venueEmail,v.contactNumber,em.workDescription,emws.status\r\n"
				+ "from event e\r\n"
				+ "join packagedetails p on e.packageId=p.packageDetailsId\r\n"
				+ "join eventemployeemapping em on em.eventId=e.eventId\r\n"
				+ "join enuemployeeworkingstatus emws on emws.statusId=em.statusId\r\n"
				+ "join venue v on p.venueId = v.venueId\r\n"
				+ "join enueventtype et on et.eventTypeId = e.eventTypeId\r\n"
				+ "join userdetails u on u.userDetailsId= e.userDetailsId\r\n"
				+ "join employee emp on emp.employeeId=e.eventOrganizerId\r\n"
				+ "where e.eventStatusId=(SELECT statusId from enueventstatus where status='Completed') and e.isActive=true and em.employeeId=:employeeId and em.isActive=true ORDER BY e.endDate DESC",namedParams,new BeanPropertyRowMapper<EmployeeEventWorkDTO>(EmployeeEventWorkDTO.class));
	}

	@Override
	public List<EmployeeEventWorkDTO> getEmployeeUpcomingEventDetails(Long employeeId) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("employeeId", employeeId);
		return jdbcTemplate.query("SELECT e.eventId,e.eventTitle,e.objective,(SELECT bannerId from eventbanner where e.eventId=eventId and isActive=true) as bannerId,et.eventType,e.startDate,e.startTime,e.endDate,e.endTime,e.isPublic,e.isFree,e.updatedAt,\r\n"
				+ "concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber,\r\n"
				+ "(SELECT concat(firstName,' ',lastName) from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerName,\r\n"
				+ "(SELECT email from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerEmail,\r\n"
				+ "(SELECT mobileNumber from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerMobileNumber,\r\n"
				+ "v.venueName,v.addressId,v.email as venueEmail,v.contactNumber,em.workDescription,emws.status\r\n"
				+ "from event e\r\n"
				+ "join packagedetails p on e.packageId=p.packageDetailsId\r\n"
				+ "join eventemployeemapping em on em.eventId=e.eventId\r\n"
				+ "join enuemployeeworkingstatus emws on emws.statusId=em.statusId\r\n"
				+ "join venue v on p.venueId = v.venueId\r\n"
				+ "join enueventtype et on et.eventTypeId = e.eventTypeId\r\n"
				+ "join userdetails u on u.userDetailsId= e.userDetailsId\r\n"
				+ "join employee emp on emp.employeeId=e.eventOrganizerId\r\n"
				+ "where e.eventStatusId=(SELECT statusId from enueventstatus where status='Verified') and e.isActive=true and em.isActive=true and em.employeeId=:employeeId ORDER BY e.startDate",namedParams,new BeanPropertyRowMapper<EmployeeEventWorkDTO>(EmployeeEventWorkDTO.class));
	}

	@Override
	public List<AllServiceProvidersPackageDTO> getAllServiceProviderOnPackage(Long eventOrganizerId) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("eventOrganizerId", eventOrganizerId);
		return jdbcTemplate.query("SELECT p.packageDetailsId,\r\n"
				+ "u.serviceProviderName,u.email,est.service\r\n"
				+ "from packagedetails p\r\n"
				+ "join packageserviceprovidermapping m on p.packageDetailsId=m.packageId\r\n"
				+ "join serviceprovider sp on m.serviceProviderId=sp.serviceProviderId\r\n"
				+ "join userdetails u on sp.userDetailsId=u.userDetailsId\r\n"
				+ "join enuservicetype est on sp.serviceTypeId=est.serviceTypeId\r\n"
				+ "where p.packageDetailsId IN (SELECT e.packageId\r\n"
				+ "from event e\r\n"
				+ "join packagedetails p on e.packageId=p.packageDetailsId\r\n"
				+ "where e.eventOrganizerId=:eventOrganizerId)",namedParams,new BeanPropertyRowMapper<AllServiceProvidersPackageDTO>(AllServiceProvidersPackageDTO.class));
	}

	@Override
	public EmployeeEventWorkDTO getEmployeeEventsDetails(long eventId, long employeeId) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("eventId", eventId);
		namedParams.addValue("employeeId", employeeId);
		return DataAccessUtils.singleResult(jdbcTemplate.query("SELECT e.eventId,e.eventTitle,e.objective,(SELECT bannerId from eventbanner where e.eventId=eventId and isActive=true) as bannerId,et.eventType,e.startDate,e.startTime,e.endDate,e.endTime,e.isPublic,e.isFree,e.updatedAt,\r\n"
				+ "concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber,\r\n"
				+ "(SELECT concat(firstName,' ',lastName) from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerName,\r\n"
				+ "(SELECT email from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerEmail,\r\n"
				+ "(SELECT mobileNumber from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerMobileNumber,\r\n"
				+ "v.venueName,v.addressId,v.email as venueEmail,v.contactNumber,em.workDescription,emws.status\r\n"
				+ "from event e\r\n"
				+ "join packagedetails p on e.packageId=p.packageDetailsId\r\n"
				+ "join eventemployeemapping em on em.eventId=e.eventId\r\n"
				+ "join enuemployeeworkingstatus emws on emws.statusId=em.statusId\r\n"
				+ "join venue v on p.venueId = v.venueId\r\n"
				+ "join enueventtype et on et.eventTypeId = e.eventTypeId\r\n"
				+ "join userdetails u on u.userDetailsId= e.userDetailsId\r\n"
				+ "join employee emp on emp.employeeId=e.eventOrganizerId\r\n"
				+ "where e.isActive=true and em.employeeId=:employeeId and e.eventId=:eventId and em.isActive=true", namedParams, new BeanPropertyRowMapper<EmployeeEventWorkDTO>(EmployeeEventWorkDTO.class)));
	}

	@Override
	public EventOrganizerEventWorkDTO getEventOrganizerEventDetails(long eventId, long eventOrganizerId) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("eventId", eventId);
		namedParams.addValue("eventOrganizerId", eventOrganizerId);
		return DataAccessUtils.singleResult(jdbcTemplate.query("SELECT e.eventId,e.eventTitle,e.objective,(SELECT bannerId from eventbanner where e.eventId=eventId and isActive=true) as bannerId,et.eventType,e.startDate,e.startTime,e.endDate,e.endTime,e.isPublic,e.updatedAt,\r\n"
				+ "concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber,\r\n"
				+ "(SELECT concat(firstName,' ',lastName) from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerName,\r\n"
				+ "(SELECT email from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerEmail,\r\n"
				+ "(SELECT mobileNumber from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerMobileNumber,\r\n"
				+ "v.venueName,v.addressId,v.email as venueEmail,v.contactNumber,e.isFree,e.packageId\r\n"
				+ "from event e\r\n"
				+ "join packagedetails p on e.packageId=p.packageDetailsId\r\n"
				+ "join venue v on p.venueId = v.venueId\r\n"
				+ "join enueventtype et on et.eventTypeId = e.eventTypeId\r\n"
				+ "join userdetails u on u.userDetailsId= e.userDetailsId\r\n"
				+ "join employee emp on emp.employeeId=e.eventOrganizerId\r\n"
				+ "where e.isActive=true and e.eventOrganizerId=:eventOrganizerId and e.eventId=:eventId", namedParams, new BeanPropertyRowMapper<EventOrganizerEventWorkDTO>(EventOrganizerEventWorkDTO.class)));
	}
}
