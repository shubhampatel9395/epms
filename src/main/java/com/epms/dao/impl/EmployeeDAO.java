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

import com.epms.dao.IEmployeeDAO;
import com.epms.dto.EmployeeDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class EmployeeDAO implements IEmployeeDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<EmployeeDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from employee");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<EmployeeDTO>(EmployeeDTO.class));
	}

	@Override
	public List<EmployeeDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from employee where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<EmployeeDTO>(EmployeeDTO.class));
	}

	@Override
	public List<EmployeeDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from employee where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<EmployeeDTO>(EmployeeDTO.class));
	}

	@Override
	public EmployeeDTO findById(Long id) {
		String sql = "select * from employee where employeeId = :employeeId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("employeeId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EmployeeDTO>(EmployeeDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public EmployeeDTO insert(EmployeeDTO entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("firstName", entity.getFirstName());
		namedParameters.addValue("lastName", entity.getLastName());
		namedParameters.addValue("addressId", entity.getAddressId());
		namedParameters.addValue("email", entity.getEmail());
		namedParameters.addValue("password", entity.getPassword());
		namedParameters.addValue("mobileNumber", entity.getMobileNumber());
		namedParameters.addValue("roleName", "ROLE_EMPLOYEE");
		namedParameters.addValue("isCustomer", false);
		namedParameters.addValue("isEmployee", true);
		namedParameters.addValue("isAuth", true);

		int i = jdbcTemplate.update(
				"insert into userDetails(firstName,lastName,addressId,email,password,mobileNumber,roleName,isCustomer,isEmployee,isAuth) values(:firstName,:lastName,:addressId,:email,:password,:mobileNumber,:roleName,:isCustomer,:isEmployee,:isAuth)",
				namedParameters, keyHolder, new String[] { "userDetailsId" });

		namedParameters.addValue("userDetailsId", keyHolder.getKey().longValue());
		namedParameters.addValue("employeeRoleId", entity.getEmployeeRoleId());
		namedParameters.addValue("gender", entity.getGender());
		namedParameters.addValue("DOB", entity.getDOB());
		namedParameters.addValue("hiringDate", entity.getHiringDate());
		namedParameters.addValue("salary", entity.getSalary());

		i = jdbcTemplate.update(
				"insert into employee(userDetailsId,employeeRoleId,gender,DOB,hiringDate,salary) values(:userDetailsId,:employeeRoleId,:gender,:DOB,:hiringDate,:salary)",
				namedParameters, keyHolder, new String[] { "employeeId" });

		return findById(keyHolder.getKey().longValue());
	}

	@Override
	public void delete(Long id) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("employeeId", id);
		jdbcTemplate.update("update employee set isActive=false where employeeId=:employeeId", parameterSource);
	}

	@Override
	public EmployeeDTO update(EmployeeDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void activate(Long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("employeeId", id);
		jdbcTemplate.update("update employee set isActive=true where employeeId=:employeeId", namedParameters);
	}
}
