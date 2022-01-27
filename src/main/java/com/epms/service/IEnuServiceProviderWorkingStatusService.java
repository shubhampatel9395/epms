package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.EnuServiceProviderWorkingStatusDTO;

public interface IEnuServiceProviderWorkingStatusService {
	public List<EnuServiceProviderWorkingStatusDTO> findAll();
	public EnuServiceProviderWorkingStatusDTO findById(Long id);	
	public List<EnuServiceProviderWorkingStatusDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<EnuServiceProviderWorkingStatusDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public EnuServiceProviderWorkingStatusDTO insert(EnuServiceProviderWorkingStatusDTO EnuServiceProviderWorkingStatusDTO);
	public void delete(Long id);
	public EnuServiceProviderWorkingStatusDTO update(EnuServiceProviderWorkingStatusDTO entity);
}
