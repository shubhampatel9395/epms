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

import com.epms.dao.IServiceProviderDAO;
import com.epms.dto.ServiceProviderDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class ServiceProviderDAO implements IServiceProviderDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<ServiceProviderDTO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ServiceProviderDTO> findByFieldValue(String fieldName, Object fieldValue) {
		// TODO Auto-generated method stub
		return null;
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
		sc.addValue("isServiceProvider", true);

		int i = jdbcTemplate.update(
				"insert into userDetails(serviceProviderName,addressId,email,password,mobileNumber,isAuth,isServiceProvider) values(:firstName,:lastName,:addressId,:email,:password,:mobileNumber,:isAuth,:isServiceProvider)",
				sc, keyHolder, new String[] { "userDetailsId" });
		
		sc.addValue("serviceTypeId", entity.getServiceTypeId());
		sc.addValue("cost", entity.getCost());
		
		i = jdbcTemplate.update(
				"insert into serviceprovider(serviceTypeId,cost) values(:serviceTypeId,:cost)",
				sc, keyHolder, new String[] { "serviceProviderId" });

		return findById(keyHolder.getKey().longValue());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public ServiceProviderDTO update(ServiceProviderDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
