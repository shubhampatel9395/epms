package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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

	@Override
	public List<ServiceProviderDTO> findAll() {
		// TODO Auto-generated method stub
		return serviceProviderDAO.findAll();
	}
	

	@Override
	public List<ServiceProviderDTO> findAllActive() {
		// TODO Auto-generated method stub
		return serviceProviderDAO.findAllActive();
	}


	@Override
	public List<ServiceProviderDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServiceProviderDTO update(ServiceProviderDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ServiceProviderDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return serviceProviderDAO.findByFieldValue(fieldName, fieldValue);
	}
}
