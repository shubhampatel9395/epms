package com.epms.service;

import com.epms.dto.ServiceProviderDTO;

public interface IServiceProviderService {
	public ServiceProviderDTO insert(ServiceProviderDTO serviceProviderDTO);

	public ServiceProviderDTO findById(Long id);
}
