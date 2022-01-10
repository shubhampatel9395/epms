package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.EnuCityDTO;

public interface IEnuCityService {
	public List<EnuCityDTO> findAll();

	public EnuCityDTO findById(Long cityId);

	public List<EnuCityDTO> findByNamedParameters(MapSqlParameterSource paramSource);
}
