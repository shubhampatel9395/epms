package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.AddressDTO;

public interface IAddressService {
	public List<AddressDTO> findAll();
	public AddressDTO findById(Long id);
	public List<AddressDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public AddressDTO insert(AddressDTO entity);
	public void delete(Long id);
	public AddressDTO update(AddressDTO entity);
}