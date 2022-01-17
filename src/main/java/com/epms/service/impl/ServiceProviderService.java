package com.epms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IServiceProviderDAO;
import com.epms.dto.ServiceProviderDTO;
import com.epms.service.IServiceProviderService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ServiceProviderService implements IServiceProviderService {
	@Autowired
	private IServiceProviderDAO serviceProviderDAO;
	
	@Override
	public ServiceProviderDTO insert(ServiceProviderDTO serviceProviderDTO) {
		return serviceProviderDAO.insert(serviceProviderDTO);
	}

	@Override
	public ServiceProviderDTO findById(Long id) {
		return serviceProviderDAO.findById(id);
	}

}
