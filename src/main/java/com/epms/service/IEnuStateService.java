package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.EnuStateDTO;

public interface IEnuStateService {
	public List<EnuStateDTO> findAll();

	public EnuStateDTO findById(Long stateId);

	public List<EnuStateDTO> findByNamedParameters(MapSqlParameterSource paramSource);

}
