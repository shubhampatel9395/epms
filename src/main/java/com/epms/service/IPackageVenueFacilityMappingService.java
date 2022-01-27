package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.PackageVenueFacilityMappingDTO;

public interface IPackageVenueFacilityMappingService {
	public List<PackageVenueFacilityMappingDTO> findAll();
	public PackageVenueFacilityMappingDTO findById(Long id);	
	public List<PackageVenueFacilityMappingDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<PackageVenueFacilityMappingDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public PackageVenueFacilityMappingDTO insert(PackageVenueFacilityMappingDTO PackageVenueFacilityMappingDTO);
	public void delete(Long id);
	public PackageVenueFacilityMappingDTO update(PackageVenueFacilityMappingDTO entity);
}
