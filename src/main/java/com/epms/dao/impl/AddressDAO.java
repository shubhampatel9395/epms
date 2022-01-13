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

import com.epms.dao.IAddressDAO;
import com.epms.dto.AddressDTO;
import com.epms.dto.EnuCityDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class AddressDAO implements IAddressDAO {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<AddressDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select addressId,address1,address2,city,stateId,countryId,postalCode from address");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<AddressDTO>(AddressDTO.class));
	}

	@Override
	public List<AddressDTO> findByFieldValue(String fieldName, Object fieldValue) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<AddressDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select addressId,address1,address2,city,stateId,countryId,postalCode from address where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<AddressDTO>(AddressDTO.class));
	}

	@Override
	public AddressDTO findById(Long addressId) {
		String sql = "select  addressId,address1,address2,city,stateId,countryId,postalCode"
				+ "from address where addressId = :addressId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("addressId", addressId);

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
		parameterSource.addValue("city", entity.getCity());
		parameterSource.addValue("stateId", entity.getStateId());
		parameterSource.addValue("countryId", entity.getCountryId());
		parameterSource.addValue("postalCode", entity.getPostalCode());

		int i = jdbcTemplate.update(
				"insert into address(address1,address2,city,stateId,countryId,postalCode) "
						+ "values(:address1,:address2,:city,:stateId,:countryId,:postalCode)",
				parameterSource, keyHolder);

		return findById(keyHolder.getKey().longValue());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public AddressDTO update(AddressDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
