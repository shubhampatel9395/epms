package com.epms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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

}
