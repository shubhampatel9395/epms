package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IAttendeeDAO;
import com.epms.dto.AttendeeDTO;
import com.epms.service.IAttendeeService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AttendeeService implements IAttendeeService {
	@Autowired
	private IAttendeeDAO attendeeDAO;

	@Override
	public List<AttendeeDTO> findAll() {
		return attendeeDAO.findAll();
	}

	@Override
	public AttendeeDTO findById(Long id) {
		return attendeeDAO.findById(id);
	}

	@Override
	public List<AttendeeDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return attendeeDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<AttendeeDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return attendeeDAO.findByNamedParameters(paramSource);
	}

	@Override
	public AttendeeDTO insert(AttendeeDTO AttendeeDTO) {
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public AttendeeDTO update(AttendeeDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
