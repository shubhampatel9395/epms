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
		StringBuilder sql = new StringBuilder();
		sql.append("select * from serviceprovider");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<ServiceProviderDTO>(ServiceProviderDTO.class));
	}

	@Override
	public List<ServiceProviderDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from userdetails where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<ServiceProviderDTO>(ServiceProviderDTO.class));
	}
	
	@Override
	public List<ServiceProviderDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from serviceprovider where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<ServiceProviderDTO>(ServiceProviderDTO.class));
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
				"insert into userDetails(serviceProviderName,addressId,email,password,mobileNumber,isAuth,isServiceProvider) values(:serviceProviderName,:addressId,:email,:password,:mobileNumber,:isAuth,:isServiceProvider)",
				sc, keyHolder, new String[] { "userDetailsId" });
		
		sc.addValue("userDetailsId", keyHolder.getKey().longValue());
		sc.addValue("serviceTypeId", entity.getServiceTypeId());
		sc.addValue("cost", entity.getCost());
		
		i = jdbcTemplate.update(
				"insert into serviceprovider(userDetailsId,serviceTypeId,cost) values(:userDetailsId,:serviceTypeId,:cost)",
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
