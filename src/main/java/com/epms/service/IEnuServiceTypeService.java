package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.EnuServiceTypeDTO;

public interface IEnuServiceTypeService {
	public List<EnuServiceTypeDTO> findAll();
	
	public List<EnuServiceTypeDTO> findAllActive();

	public EnuServiceTypeDTO findById(Long id);

	public List<EnuServiceTypeDTO> findByNamedParameters(MapSqlParameterSource paramSource);
}
