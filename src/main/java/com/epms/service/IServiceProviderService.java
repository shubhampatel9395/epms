package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.ServiceProviderDTO;

public interface IServiceProviderService {
	public List<ServiceProviderDTO> findAll();
	public ServiceProviderDTO findById(Long id);
	public List<ServiceProviderDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public ServiceProviderDTO insert(ServiceProviderDTO serviceProviderDTO);
	public void delete(Long id);
	public ServiceProviderDTO update(ServiceProviderDTO entity);
}
