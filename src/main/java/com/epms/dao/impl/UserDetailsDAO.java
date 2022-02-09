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

import com.epms.dao.IUserDetailsDAO;
import com.epms.dto.UserDetailsDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class UserDetailsDAO implements IUserDetailsDAO {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<UserDetailsDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from userdetails");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<UserDetailsDTO>(UserDetailsDTO.class));
	}

	@Override
	public List<UserDetailsDTO> findAllActive() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from userdetails where isActive=true");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<UserDetailsDTO>(UserDetailsDTO.class));
	}

	@Override
	public List<UserDetailsDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from userdetails where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters,
				new BeanPropertyRowMapper<UserDetailsDTO>(UserDetailsDTO.class));
	}

	@Override
	public List<UserDetailsDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from userdetails where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<UserDetailsDTO>(UserDetailsDTO.class));
	}

	@Override
	public UserDetailsDTO findById(Long id) {
		String sql = "select * from userdetails where userDetailsId = :userDetailsId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("userDetailsId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<UserDetailsDTO>(UserDetailsDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public UserDetailsDTO insert(UserDetailsDTO entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		MapSqlParameterSource sc = new MapSqlParameterSource();
		sc.addValue("firstName", entity.getFirstName());
		sc.addValue("lastName", entity.getLastName());
		sc.addValue("addressId", entity.getAddressId());
		sc.addValue("email", entity.getEmail());
		sc.addValue("password", entity.getPassword());
		sc.addValue("mobileNumber", entity.getMobileNumber());
		sc.addValue("isAuth", true);

		int i = jdbcTemplate.update(
				"insert into userDetails(firstName,lastName,addressId,email,password,mobileNumber,isAuth) values(:firstName,:lastName,:addressId,:email,:password,:mobileNumber,:isAuth)",
				sc, keyHolder, new String[] { "userDetailsId" });

		return findById(keyHolder.getKey().longValue());
	}

	@Override
	public UserDetailsDTO update(UserDetailsDTO entity) {
		/*
		MapSqlParameterSource sc = new MapSqlParameterSource();
		if (entity.getServiceProviderName() == null) {
			sc.addValue("userDetailsId", entity.getUserDetailsId());
			sc.addValue("firstName", entity.getFirstName());
			sc.addValue("lastName", entity.getLastName());
			sc.addValue("email", entity.getEmail());
			sc.addValue("mobileNumber", entity.getMobileNumber());
			jdbcTemplate.update(
					"update userDetails set firstName=:firstName,lastName=:lastName,email=:email,mobileNumber=:mobileNumber where userDetailsId=:userDetailsId",
					sc);
		} else {
			sc.addValue("userDetailsId", entity.getUserDetailsId());
			sc.addValue("serviceProviderName", entity.getServiceProviderName());
			sc.addValue("email", entity.getEmail());
			sc.addValue("mobileNumber", entity.getMobileNumber());
			jdbcTemplate.update(
					"update userDetails set serviceProviderName=:serviceProviderName,email=:email,mobileNumber=:mobileNumber where userDetailsId=:userDetailsId",
					sc);
		}
		*/
		MapSqlParameterSource sc = new MapSqlParameterSource();
		sc.addValue("userDetailsId", entity.getUserDetailsId());
		sc.addValue("firstName", entity.getFirstName());
		sc.addValue("lastName", entity.getLastName());
		sc.addValue("email", entity.getEmail());
		sc.addValue("mobileNumber", entity.getMobileNumber());
		jdbcTemplate.update(
				"update userDetails set firstName=:firstName,lastName=:lastName,email=:email,mobileNumber=:mobileNumber where userDetailsId=:userDetailsId",
				sc);
		return entity;
	}

	@Override
	public List<UserDetailsDTO> isUniqueEmail(String email) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("email", email);
		
		List<UserDetailsDTO> userDetailsDTOs = findByNamedParameters(parameterSource);
		return userDetailsDTOs;
	}

	@Override
	public List<UserDetailsDTO> isUniqueMobileNumber(String mobileNumber) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("mobileNumber", mobileNumber);
		
		List<UserDetailsDTO> userDetailsDTOs = findByNamedParameters(parameterSource);
		return userDetailsDTOs;
	}

	@Override
	public void delete(Long id) {
		MapSqlParameterSource sc = new MapSqlParameterSource();
		sc.addValue("id", id);
		jdbcTemplate.update("update userDetails set isActive=false,isAuth=false where userDetailsId=:id", sc);
	}

	@Override
	public void activate(Long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("userDetailsId", id);
		jdbcTemplate.update("update userDetails set isActive=true,isAuth=true where userDetailsId=:userDetailsId", namedParameters);
	}
}
