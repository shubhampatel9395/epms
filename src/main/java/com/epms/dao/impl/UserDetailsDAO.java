package com.epms.dao.impl;

import java.util.List;

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
		return null;
	}

	@Override
	public List<UserDetailsDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return null;
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
		sc.addValue("firstName",entity.getFirstName());
		sc.addValue("lastName",entity.getLastName());
		sc.addValue("addressId", entity.getAddressId());
		sc.addValue("email",entity.getEmail());
		sc.addValue("password",entity.getPassword());
		sc.addValue("mobileNumber",entity.getMobileNumber());
		sc.addValue("isAuth",true);
		
		int i  = jdbcTemplate.update("insert into userDetails(firstName,lastName,addressId,email,password,mobileNumber,isAuth) values(:firstName,:lastName,:addressId,:email,:password,:mobileNumber,:isAuth)", 
					sc, keyHolder,new String[] { "userDetailsId" });
		
		return findById(keyHolder.getKey().longValue());
	}

	@Override
	public void delete(Long id) {
		
	}

	@Override
	public UserDetailsDTO update(UserDetailsDTO entity) {
		return null;
	}

}
