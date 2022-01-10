package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IEnuStateDAO;
import com.epms.dto.EnuStateDTO;
import com.epms.service.IEnuStateService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class EnuStateService implements IEnuStateService {

	@Autowired
	private IEnuStateDAO enuStateDAO;

	@Override
	public List<EnuStateDTO> findAll() {
		return enuStateDAO.findAll();
	}

	@Override
	public EnuStateDTO findById(Long stateId) {
		return enuStateDAO.findById(stateId);
	}

	@Override
	public List<EnuStateDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return enuStateDAO.findByNamedParameters(paramSource);
	}

}
