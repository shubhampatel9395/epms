package com.epms.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.epms.dao.IPackageServiceProviderMappingDAO;
import com.epms.dto.PackageServiceProviderMappingDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class PackageServiceProviderMappingDAO implements IPackageServiceProviderMappingDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<PackageServiceProviderMappingDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from packageserviceprovidermapping");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<PackageServiceProviderMappingDTO>(PackageServiceProviderMappingDTO.class));
	}

	@Override
	public List<PackageServiceProviderMappingDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from packageserviceprovidermapping where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters,
				new BeanPropertyRowMapper<PackageServiceProviderMappingDTO>(PackageServiceProviderMappingDTO.class));
	}

	@Override
	public List<PackageServiceProviderMappingDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from packageserviceprovidermapping where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource,
				new BeanPropertyRowMapper<PackageServiceProviderMappingDTO>(PackageServiceProviderMappingDTO.class));
	}

	@Override
	public PackageServiceProviderMappingDTO findById(Long id) {
		String sql = "select * from packageserviceprovidermapping where packageServiceProviderMappingId = :packageServiceProviderMappingId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("packageServiceProviderMappingId",
				id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<PackageServiceProviderMappingDTO>(
							PackageServiceProviderMappingDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public PackageServiceProviderMappingDTO insert(PackageServiceProviderMappingDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public PackageServiceProviderMappingDTO update(PackageServiceProviderMappingDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
