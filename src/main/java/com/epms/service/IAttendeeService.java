package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.AttendeeDTO;

public interface IAttendeeService {
	public List<AttendeeDTO> findAll();
	public AttendeeDTO findById(Long id);	
	public List<AttendeeDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<AttendeeDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public AttendeeDTO insert(AttendeeDTO AttendeeDTO);
	public void delete(Long id);
	public AttendeeDTO update(AttendeeDTO entity);
}
