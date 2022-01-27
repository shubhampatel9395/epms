package com.epms.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.epms.dao.IPackageDetailsDAO;
import com.epms.dto.PackageDetailsDTO;

public class PackageDetailsDAO implements IPackageDetailsDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<PackageDetailsDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from packagedetails");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<PackageDetailsDTO>(PackageDetailsDTO.class));
	}

	@Override
	public List<PackageDetailsDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from packagedetails where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters,
				new BeanPropertyRowMapper<PackageDetailsDTO>(PackageDetailsDTO.class));
	}

	@Override
	public List<PackageDetailsDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from packagedetails where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource,
				new BeanPropertyRowMapper<PackageDetailsDTO>(PackageDetailsDTO.class));
	}

	@Override
	public PackageDetailsDTO findById(Long id) {
		String sql = "select * from packagedetails where packageDetailsId = :packageDetailsId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("packageDetailsId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<PackageDetailsDTO>(PackageDetailsDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public PackageDetailsDTO insert(PackageDetailsDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public PackageDetailsDTO update(PackageDetailsDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
