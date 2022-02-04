package com.epms.dao;

import java.util.List;

import com.epms.core.ICRUDRepository;
import com.epms.dto.ServiceProviderDTO;

public interface IServiceProviderDAO extends ICRUDRepository<ServiceProviderDTO, Long> {
	public List<ServiceProviderDTO> findAllActive();
}