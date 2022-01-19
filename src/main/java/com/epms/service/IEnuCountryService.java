package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.EnuCountryDTO;

public interface IEnuCountryService {
	public List<EnuCountryDTO> findAll();

	public EnuCountryDTO findById(Long countryId);

	public List<EnuCountryDTO> findByNamedParameters(MapSqlParameterSource paramSource);
}