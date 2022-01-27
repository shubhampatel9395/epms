package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.EnuServiceTypeDTO;
import com.epms.dto.UserDetailsDTO;

public interface IEnuServiceTypeService {
	public List<EnuServiceTypeDTO> findAll();
	public List<EnuServiceTypeDTO> findAllActive();
	public EnuServiceTypeDTO findById(Long id);
	public List<EnuServiceTypeDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<EnuServiceTypeDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public EnuServiceTypeDTO insert(EnuServiceTypeDTO entity);
	public void delete(Long id);
	public EnuServiceTypeDTO update(EnuServiceTypeDTO entity);
}
