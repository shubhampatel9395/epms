package com.epms.dao;

import java.util.List;

import com.epms.core.ICRUDRepository;
import com.epms.dto.PackageServiceProviderMappingDTO;

public interface IPackageServiceProviderMappingDAO extends ICRUDRepository<PackageServiceProviderMappingDTO, Long> {

	public void insert(Long packageId,List<String> serviceProviderList);

	public void activate(long packageDetailsId);

}
