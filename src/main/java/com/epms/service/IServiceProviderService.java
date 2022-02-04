package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.ServiceProviderDTO;
import com.epms.dto.UserDetailsDTO;

public interface IServiceProviderService {
	public List<ServiceProviderDTO> findAll();
	public List<ServiceProviderDTO> findAllActive();
	public ServiceProviderDTO findById(Long id);
	public List<ServiceProviderDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public List<ServiceProviderDTO> findByFieldValue(String fieldName, Object fieldValue);
	public ServiceProviderDTO insert(ServiceProviderDTO serviceProviderDTO);
	public void delete(Long id);
	public ServiceProviderDTO update(ServiceProviderDTO entity);
}
