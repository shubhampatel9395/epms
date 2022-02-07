package com.epms.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.epms.dao.IAddressDAO;
import com.epms.dto.AddressDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class AddressDAO implements IAddressDAO {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<AddressDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from address");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<AddressDTO>(AddressDTO.class));
	}

	@Override
	public List<AddressDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from userdetails where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<AddressDTO>(AddressDTO.class));
	}
	
	@Override
	public List<AddressDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from address where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<AddressDTO>(AddressDTO.class));
	}

	@Override
	public AddressDTO findById(Long addressId) {
		String sql = "select * from address where addressId = :addressId";

		MapSqlParameterSource namedParameters = new MapSqlParameterSource().addValue("addressId", addressId);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<AddressDTO>(AddressDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public AddressDTO insert(AddressDTO entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("address1", entity.getAddress1());
		parameterSource.addValue("address2", entity.getAddress2());
		parameterSource.addValue("cityId", entity.getCityId());
		parameterSource.addValue("stateId", entity.getStateId());
		parameterSource.addValue("countryId", entity.getCountryId());
		parameterSource.addValue("postalCode", entity.getPostalCode());

		int i = jdbcTemplate.update(
				"insert into address(address1,address2,cityId,stateId,countryId,postalCode) "
						+ "values(:address1,:address2,:cityId,:stateId,:countryId,:postalCode)",
				parameterSource, keyHolder,new String[] { "addressId" });

		return findById(keyHolder.getKey().longValue());
	}

	@Override
	public void delete(Long id) {
		MapSqlParameterSource sc = new MapSqlParameterSource();
		sc.addValue("id", id);
		jdbcTemplate.update("update address set isActive=false where addressId=:id", sc);
	}

	@Override
	public AddressDTO update(AddressDTO entity) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("addressId", entity.getAddressId());
		parameterSource.addValue("address1", entity.getAddress1());
		parameterSource.addValue("address2", entity.getAddress2());
		parameterSource.addValue("cityId", entity.getCityId());
		parameterSource.addValue("stateId", entity.getStateId());
		parameterSource.addValue("countryId", entity.getCountryId());
		parameterSource.addValue("postalCode", entity.getPostalCode());
		jdbcTemplate.update("update address set address1=:address1,address2=:address2,cityId=:cityId,stateId=:stateId,countryId=:countryId,postalCode=:postalCode where addressId=:addressId", parameterSource);
		return entity;
	}
}
