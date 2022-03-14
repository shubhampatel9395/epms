package com.epms.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.PackageDetailsDTO;

public interface IPackageDetailsService {
	public List<PackageDetailsDTO> findAll();
	public PackageDetailsDTO findById(Long id);	
	public List<PackageDetailsDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<PackageDetailsDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public PackageDetailsDTO insert(PackageDetailsDTO PackageDetailsDTO);
	public void delete(Long id);
	public PackageDetailsDTO update(PackageDetailsDTO entity);
	public void activate(long packageDetailsId);
	public PackageDetailsDTO insertByCustomer(@Valid PackageDetailsDTO packageDetailsDTO);
}