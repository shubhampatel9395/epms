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

import com.epms.dao.IPackageDetailsDAO;
import com.epms.dto.PackageDetailsDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
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
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("title", entity.getTitle());
		namedParameters.addValue("description", entity.getDescription());
		namedParameters.addValue("guestAmount", entity.getGuestAmount());
		namedParameters.addValue("totalCost", entity.getTotalCost());
		namedParameters.addValue("eventTypeId", entity.getEventTypeId());
		namedParameters.addValue("venueId", entity.getVenueId());
		namedParameters.addValue("isStatic", entity.getIsStatic());
		jdbcTemplate.update(
				"insert into packagedetails(title,description,guestAmount,totalCost,eventTypeId,venueId,venueTypeId,isStatic) values(:title,:description,:guestAmount,:totalCost,:eventTypeId,:venueId, (SELECT venueTypeId FROM venue v WHERE :venueId = v.venueId) ,:isStatic)",
				namedParameters, keyHolder, new String[] { "packageDetailsId" });
		return findById(keyHolder.getKey().longValue());
	}

	@Override
	public void delete(Long id) {
		MapSqlParameterSource sc = new MapSqlParameterSource();
		sc.addValue("id", id);
		jdbcTemplate.update("update packagedetails set isActive=false where packageDetailsId=:id", sc);
	}

	@Override
	public PackageDetailsDTO update(PackageDetailsDTO entity) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("packageDetailsId", entity.getPackageDetailsId());
		namedParameters.addValue("title", entity.getTitle());
		namedParameters.addValue("description", entity.getDescription());
		namedParameters.addValue("guestAmount", entity.getGuestAmount());
		namedParameters.addValue("totalCost", entity.getTotalCost());
		namedParameters.addValue("eventTypeId", entity.getEventTypeId());
		namedParameters.addValue("venueId", entity.getVenueId());

		jdbcTemplate.update(
				"update packagedetails set title=:title,description=:description,guestAmount=:guestAmount,totalCost=:totalCost,eventTypeId=:eventTypeId,venueId=:venueId,venueTypeId=(SELECT venueTypeId FROM venue v WHERE :venueId = v.venueId) where packageDetailsId=:packageDetailsId",
				namedParameters);
		return entity;
	}

	@Override
	public void activate(long packageDetailsId) {
		MapSqlParameterSource sc = new MapSqlParameterSource();
		sc.addValue("id", packageDetailsId);
		jdbcTemplate.update("update packagedetails set isActive=true where packageDetailsId=:id", sc);
	}

}
