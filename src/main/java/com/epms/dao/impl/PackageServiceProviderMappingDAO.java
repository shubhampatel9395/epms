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

	// Using packageDetailsId
	@Override
	public void delete(Long id) {
		MapSqlParameterSource sc = new MapSqlParameterSource();
		sc.addValue("id", id);
		jdbcTemplate.update("update packageserviceprovidermapping set isActive=false where packageId=:id", sc);
	}

	@Override
	public PackageServiceProviderMappingDTO update(PackageServiceProviderMappingDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Long packageId, List<String> serviceProviderList) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("packageId", packageId);
		for (String serviceProviderId : serviceProviderList) {
			namedParameters.addValue("serviceProviderId", serviceProviderId);
			jdbcTemplate.update(
					"insert into packageserviceprovidermapping(packageId,serviceProviderId,serviceTypeId) values(:packageId,:serviceProviderId,(SELECT serviceTypeId FROM serviceprovider s WHERE :serviceProviderId = s.serviceProviderId))",
					namedParameters);
		}
	}

	// Using packageDetailsId
	@Override
	public void activate(long packageDetailsId) {
		MapSqlParameterSource sc = new MapSqlParameterSource();
		sc.addValue("id", packageDetailsId);
		jdbcTemplate.update("update packageserviceprovidermapping set isActive=true where packageId=:id", sc);
	}

	@Override
	public void deleteByPackageId(long packageId) {
		MapSqlParameterSource sc = new MapSqlParameterSource();
		sc.addValue("packageId", packageId);
		jdbcTemplate.update("delete from packageserviceprovidermapping where packageId=:packageId", sc);
	}
	
	@Override
	public void removedFromEvent(long packageId,long statusId) {
		MapSqlParameterSource sc = new MapSqlParameterSource();
		sc.addValue("packageId", packageId);
		sc.addValue("statusId", statusId);
		jdbcTemplate.update("update packageserviceprovidermapping set isActive=false,statusId=:statusId where packageId=:packageId", sc);
	}

	@Override
	public void assign(long packageId, List<String> serviceProviderIdList, long statusId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("packageId", packageId);
		namedParameters.addValue("statusId", statusId);
		for (String serviceProviderId : serviceProviderIdList) {
			namedParameters.addValue("serviceProviderId", serviceProviderId);
			jdbcTemplate.update(
					"insert into packageserviceprovidermapping(packageId,serviceProviderId,serviceTypeId,statusId) values(:packageId,:serviceProviderId,(SELECT serviceTypeId FROM serviceprovider s WHERE :serviceProviderId = s.serviceProviderId),:statusId)",
					namedParameters);
		}
	}

	@Override
	public void insertByCustomer(long packageId, List<String> serviceTypeIds) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("packageId", packageId);
		for (String serviceProviderType : serviceTypeIds) {
			namedParameters.addValue("serviceTypeId", Integer.parseInt(serviceProviderType));
			jdbcTemplate.update(
					"insert into packageserviceprovidermapping(packageId,serviceTypeId) values(:packageId,:serviceTypeId)",
					namedParameters);
		}
	}
}