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

import com.epms.dao.IPackageVenueFacilityMappingDAO;
import com.epms.dto.PackageVenueFacilityMappingDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class PackageVenueFacilityMappingDAO implements IPackageVenueFacilityMappingDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<PackageVenueFacilityMappingDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from packagevenuefacilitymapping");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<PackageVenueFacilityMappingDTO>(PackageVenueFacilityMappingDTO.class));
	}

	@Override
	public List<PackageVenueFacilityMappingDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from packagevenuefacilitymapping where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters,
				new BeanPropertyRowMapper<PackageVenueFacilityMappingDTO>(PackageVenueFacilityMappingDTO.class));
	}

	@Override
	public List<PackageVenueFacilityMappingDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from packagevenuefacilitymapping where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource,
				new BeanPropertyRowMapper<PackageVenueFacilityMappingDTO>(PackageVenueFacilityMappingDTO.class));
	}

	@Override
	public PackageVenueFacilityMappingDTO findById(Long id) {
		String sql = "select * from packagevenuefacilitymapping where packageVenueFacilityMappingId = :packageVenueFacilityMappingId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("packageVenueFacilityMappingId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<PackageVenueFacilityMappingDTO>(PackageVenueFacilityMappingDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public PackageVenueFacilityMappingDTO insert(PackageVenueFacilityMappingDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public PackageVenueFacilityMappingDTO update(PackageVenueFacilityMappingDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
