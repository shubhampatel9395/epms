package com.epms.dao.impl;

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

import com.epms.dao.IServiceProviderDAO;
import com.epms.dto.ServiceProviderDTO;
import com.epms.dto.ServiceProviderEventWorkDTO;
import com.epms.dto.ShowFeedbackDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class ServiceProviderDAO implements IServiceProviderDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<ServiceProviderDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from serviceprovider");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<ServiceProviderDTO>(ServiceProviderDTO.class));
	}

	@Override
	public List<ServiceProviderDTO> findAllActive() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from serviceprovider where isActive=true");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<ServiceProviderDTO>(ServiceProviderDTO.class));
	}

	@Override
	public List<ServiceProviderDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from userdetails where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters,
				new BeanPropertyRowMapper<ServiceProviderDTO>(ServiceProviderDTO.class));
	}

	@Override
	public List<ServiceProviderDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from serviceprovider where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource,
				new BeanPropertyRowMapper<ServiceProviderDTO>(ServiceProviderDTO.class));
	}

	@Override
	public ServiceProviderDTO findById(Long id) {
		String sql = "select * from serviceprovider where serviceProviderId = :serviceProviderId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("serviceProviderId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<ServiceProviderDTO>(ServiceProviderDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public ServiceProviderDTO insert(ServiceProviderDTO entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		MapSqlParameterSource sc = new MapSqlParameterSource();
		sc.addValue("serviceProviderName", entity.getServiceProviderName());
		sc.addValue("addressId", entity.getAddressId());
		sc.addValue("email", entity.getEmail());
		sc.addValue("password", entity.getPassword());
		sc.addValue("mobileNumber", entity.getMobileNumber());
		sc.addValue("isAuth", false);
		sc.addValue("isCustomer", false);
		sc.addValue("isServiceProvider", true);
		sc.addValue("isActive", false);
		sc.addValue("roleName", "ROLE_SERVICEPROVIDER");

		int i = jdbcTemplate.update(
				"insert into userDetails(serviceProviderName,addressId,email,password,mobileNumber,isAuth,isCustomer,isServiceProvider,roleName,isActive) values(:serviceProviderName,:addressId,:email,:password,:mobileNumber,:isAuth,:isCustomer,:isServiceProvider,:roleName,:isActive)",
				sc, keyHolder, new String[] { "userDetailsId" });

		sc.addValue("userDetailsId", keyHolder.getKey().longValue());
		sc.addValue("serviceTypeId", entity.getServiceTypeId());
		sc.addValue("cost", entity.getCost());
		sc.addValue("isActive", false);

		i = jdbcTemplate.update(
				"insert into serviceprovider(userDetailsId,serviceTypeId,cost,isActive) values(:userDetailsId,:serviceTypeId,:cost,:isActive)",
				sc, keyHolder, new String[] { "serviceProviderId" });

		return findById(keyHolder.getKey().longValue());
	}

	@Override
	public void delete(Long id) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("serviceProviderId", id);
//		jdbcTemplate.update(
//				"update userDetails set isAuth=false where userDetailsId=(select userDetailsId from serviceprovider where serviceProviderId=:serviceProviderId)",
//				parameterSource);
		jdbcTemplate.update("update serviceprovider set isActive=false where serviceProviderId=:serviceProviderId",
				parameterSource);
	}

	@Override
	public ServiceProviderDTO update(ServiceProviderDTO entity) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();

		// Update UserDetails
		parameterSource.addValue("userDetailsId", entity.getUserDetailsId());
		parameterSource.addValue("serviceProviderName", entity.getServiceProviderName());
		parameterSource.addValue("email", entity.getEmail());
		parameterSource.addValue("mobileNumber", entity.getMobileNumber());
		jdbcTemplate.update(
				"update userDetails set serviceProviderName=:serviceProviderName,email=:email,mobileNumber=:mobileNumber where userDetailsId=:userDetailsId",
				parameterSource);

		// Update Service Provider
		parameterSource.addValue("serviceProviderId", entity.getServiceProviderId());
		parameterSource.addValue("serviceTypeId", entity.getServiceTypeId());
		parameterSource.addValue("cost", entity.getCost());
		jdbcTemplate.update(
				"update serviceprovider set serviceTypeId=:serviceTypeId,cost=:cost where serviceProviderId=:serviceProviderId",
				parameterSource);

		return entity;
	}

	@Override
	public void authenticate(Long id) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("serviceProviderId", id);
		jdbcTemplate.update("update serviceprovider set isActive=true where serviceProviderId=:serviceProviderId",
				parameterSource);
	}

	@Override
	public int getTotalParticipatedPackages(Long id) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("serviceProviderId", id);
		return jdbcTemplate.queryForObject("SELECT count(1) from packagedetails p join packageserviceprovidermapping m on p.packageDetailsId=m.packageId where p.isStatic=1 and p.isActive=true and m.serviceProviderId=:serviceProviderId" ,namedParams , Integer.class);
	}

	@Override
	public int getCompletedEvents(Long id) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("serviceProviderId", id);
		return jdbcTemplate.queryForObject("SELECT count(1) from event e "
				+ "join packagedetails p on e.packageId=p.packageDetailsId "
				+ "join packageserviceprovidermapping m on p.packageDetailsId=m.packageId "
				+ "where e.eventStatusId=(SELECT statusId from enueventstatus where status='Completed') and e.isActive=true and p.isActive=true and m.serviceProviderId=:serviceProviderId" ,namedParams , Integer.class);
	}

	@Override
	public int getOngoingEvents(Long id) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("serviceProviderId", id);
		return jdbcTemplate.queryForObject("SELECT count(1) from event e "
				+ "join packagedetails p on e.packageId=p.packageDetailsId "
				+ "join packageserviceprovidermapping m on p.packageDetailsId=m.packageId "
				+ "where e.eventStatusId=(SELECT statusId from enueventstatus where status='Verified') and e.isActive=true and p.isActive=true and m.serviceProviderId=:serviceProviderId" ,namedParams , Integer.class);
	}

	@Override
	public double getAverageRatings(Long id) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("serviceProviderId", id);
		return jdbcTemplate.queryForObject("SELECT IFNULL(sum(serviceProviderRating) / 5, 0.0) from feedback f "
				+ "join serviceprovider s on f.serviceProviderId=s.serviceProviderId "
				+ "where f.isActive=true and s.serviceProviderId=:serviceProviderId" ,namedParams, Double.class);
	}

	@Override
	public List<ServiceProviderEventWorkDTO> getCompletedEventsDetails(Long id) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("serviceProviderId", id);
		return jdbcTemplate.query("SELECT e.eventId,e.eventTitle,e.objective,(SELECT bannerId from eventbanner where e.eventId=eventId and isActive=true) as bannerId,et.eventType,e.startDate,e.startTime,e.endDate,e.endTime,e.isPublic,e.updatedAt,\r\n"
				+ "concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber,\r\n"
				+ "(SELECT concat(firstName,' ',lastName) from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerName,\r\n"
				+ "(SELECT email from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerEmail,\r\n"
				+ "(SELECT mobileNumber from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerMobileNumber,\r\n"
				+ "v.venueName,v.addressId,v.email as venueEmail,v.contactNumber \r\n"
				+ "from event e\r\n"
				+ "join packagedetails p on e.packageId=p.packageDetailsId\r\n"
				+ "join packageserviceprovidermapping m on p.packageDetailsId=m.packageId\r\n"
				+ "join venue v on p.venueId = v.venueId\r\n"
				+ "join enueventtype et on et.eventTypeId = e.eventTypeId\r\n"
				+ "join userdetails u on u.userDetailsId= e.userDetailsId\r\n"
				+ "join employee emp on emp.employeeId=e.eventOrganizerId\r\n"
				+ "where e.eventStatusId=(SELECT statusId from enueventstatus where status='Completed') and e.isActive=true and p.isActive=true and m.serviceProviderId=:serviceProviderId", namedParams,
				new BeanPropertyRowMapper<ServiceProviderEventWorkDTO>(ServiceProviderEventWorkDTO.class));
	}

	@Override
	public List<ServiceProviderEventWorkDTO> getOngoingEventsDetails(Long id) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("serviceProviderId", id);
		return jdbcTemplate.query("SELECT e.eventId,e.eventTitle,e.objective,(SELECT bannerId from eventbanner where e.eventId=eventId and isActive=true) as bannerId,et.eventType,e.startDate,e.startTime,e.endDate,e.endTime,e.isPublic,e.updatedAt,\r\n"
				+ "concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber,\r\n"
				+ "(SELECT concat(firstName,' ',lastName) from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerName,\r\n"
				+ "(SELECT email from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerEmail,\r\n"
				+ "(SELECT mobileNumber from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerMobileNumber,\r\n"
				+ "v.venueName,v.addressId,v.email as venueEmail,v.contactNumber\r\n"
				+ "from event e\r\n"
				+ "join packagedetails p on e.packageId=p.packageDetailsId\r\n"
				+ "join packageserviceprovidermapping m on p.packageDetailsId=m.packageId\r\n"
				+ "join venue v on p.venueId = v.venueId\r\n"
				+ "join enueventtype et on et.eventTypeId = e.eventTypeId\r\n"
				+ "join userdetails u on u.userDetailsId= e.userDetailsId\r\n"
				+ "join employee emp on emp.employeeId=e.eventOrganizerId\r\n"
				+ "where e.eventStatusId=(SELECT statusId from enueventstatus where status='Verified') and e.isActive=true and p.isActive=true and m.serviceProviderId=:serviceProviderId", namedParams,
				new BeanPropertyRowMapper<ServiceProviderEventWorkDTO>(ServiceProviderEventWorkDTO.class));
	}
	
	@Override
	public ServiceProviderEventWorkDTO getEventsDetails(Long eventId,Long serviceProviderId) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("eventId", eventId);
		namedParams.addValue("serviceProviderId", serviceProviderId);
		return DataAccessUtils.singleResult(jdbcTemplate.query("SELECT e.eventId,e.eventTitle,e.objective,(SELECT bannerId from eventbanner where e.eventId=eventId and isActive=true) as bannerId,et.eventType,e.startDate,e.startTime,e.endDate,e.endTime,e.isPublic,e.updatedAt,\r\n"
				+ "concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber,\r\n"
				+ "(SELECT concat(firstName,' ',lastName) from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerName,\r\n"
				+ "(SELECT email from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerEmail,\r\n"
				+ "(SELECT mobileNumber from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerMobileNumber,\r\n"
				+ "v.venueName,v.addressId,v.email as venueEmail,v.contactNumber \r\n"
				+ "from event e\r\n"
				+ "join packagedetails p on e.packageId=p.packageDetailsId\r\n"
				+ "join packageserviceprovidermapping m on p.packageDetailsId=m.packageId\r\n"
				+ "join venue v on p.venueId = v.venueId\r\n"
				+ "join enueventtype et on et.eventTypeId = e.eventTypeId\r\n"
				+ "join userdetails u on u.userDetailsId= e.userDetailsId\r\n"
				+ "join employee emp on emp.employeeId=e.eventOrganizerId\r\n"
				+ "where e.eventId=:eventId and e.isActive=true and p.isActive=true and m.serviceProviderId=:serviceProviderId", namedParams,
				new BeanPropertyRowMapper<ServiceProviderEventWorkDTO>(ServiceProviderEventWorkDTO.class)));
	}
	
	@Override
	public List<ShowFeedbackDTO> getFeedbackDetails(Long serviceProviderId) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("serviceProviderId", serviceProviderId);
		return jdbcTemplate.query("SELECT e.eventId,e.eventTitle,e.objective,(SELECT bannerId from eventbanner where e.eventId=eventId and isActive=true) as bannerId,et.eventType,e.startDate,e.startTime,e.endDate,e.endTime,e.isPublic,e.updatedAt,\r\n"
				+ "concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber,\r\n"
				+ "(SELECT concat(firstName,' ',lastName) from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerName,\r\n"
				+ "(SELECT email from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerEmail,\r\n"
				+ "(SELECT mobileNumber from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerMobileNumber,\r\n"
				+ "v.venueName,v.addressId,v.email as venueEmail,v.contactNumber,f.feedbackId,f.eventId, f.eventRating, f.eventDescription, f.venueId,f.venueRating, f.venueDescription, f.employeeId, f.employeeRating, f.employeeDescription, f.serviceProviderId, f.serviceProviderRating, f.serviceProviderDescription, f.packageId, f.packageRating, f.packageDescription, f.createdAt, f.updatedAt, f.isActive, f.createdBy, f.updatedBy \r\n"
				+ "FROM feedback f\r\n"
				+ "join event e on e.eventId=f.eventId\r\n"
				+ "join serviceprovider sp on sp.serviceProviderId=f.serviceProviderId\r\n"
				+ "join packagedetails p on e.packageId=p.packageDetailsId\r\n"
				+ "join venue v on p.venueId = v.venueId\r\n"
				+ "join enueventtype et on et.eventTypeId = e.eventTypeId\r\n"
				+ "join userdetails u on u.userDetailsId= e.userDetailsId\r\n"
				+ "join employee emp on emp.employeeId=e.eventOrganizerId\r\n"
				+ "where sp.serviceProviderId=:serviceProviderId and e.eventId IN (SELECT e.eventId from event e\r\n"
				+ "join packagedetails p on e.packageId=p.packageDetailsId\r\n"
				+ "join packageserviceprovidermapping m on p.packageDetailsId=m.packageId\r\n"
				+ "where e.eventStatusId=(SELECT statusId from enueventstatus where status='Completed') and e.isActive=true and p.isActive=true and m.serviceProviderId=:serviceProviderId)", namedParams,
				new BeanPropertyRowMapper<ShowFeedbackDTO>(ShowFeedbackDTO.class));
	}
}