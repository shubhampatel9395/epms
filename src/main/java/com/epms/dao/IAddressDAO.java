package com.epms.dao;

import com.epms.core.ICRUDRepository;
import com.epms.dto.AddressDTO;

public interface IAddressDAO extends ICRUDRepository<AddressDTO, Long> {
	public void activate(Long id);
}