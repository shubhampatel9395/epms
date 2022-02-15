package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.PackageServiceProviderMappingDTO;

public interface IPackageServiceProviderMappingService {
	public List<PackageServiceProviderMappingDTO> findAll();
	public PackageServiceProviderMappingDTO findById(Long id);	
	public List<PackageServiceProviderMappingDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<PackageServiceProviderMappingDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public PackageServiceProviderMappingDTO insert(PackageServiceProviderMappingDTO PackageServiceProviderMappingDTO);
	public void delete(Long id);
	public PackageServiceProviderMappingDTO update(PackageServiceProviderMappingDTO entity);
	public void insert(Long packageId,List<String> serviceProviderList);
	public void activate(long packageDetailsId);
	public void deleteByPackageId(long packageId);
}
