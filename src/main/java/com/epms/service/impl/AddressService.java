package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IAddressDAO;
import com.epms.dto.AddressDTO;
import com.epms.service.IAddressService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AddressService implements IAddressService {

	@Autowired
	IAddressDAO addressDAO;
	
	@Override
	public AddressDTO insert(AddressDTO addressDTO) {
		return addressDAO.insert(addressDTO);
	}
	
	@Override
	public List<AddressDTO> findAll() {
		return addressDAO.findAll();
	}
	
	@Override
	public AddressDTO findById(Long id) {
		return addressDAO.findById(id);
	}

	@Override
	public List<AddressDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return addressDAO.findByNamedParameters(paramSource);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
	}

	@Override
	public AddressDTO update(AddressDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
