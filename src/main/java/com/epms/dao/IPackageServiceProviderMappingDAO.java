package com.epms.dao;

import java.util.List;

import com.epms.core.ICRUDRepository;
import com.epms.dto.PackageServiceProviderMappingDTO;

public interface IPackageServiceProviderMappingDAO extends ICRUDRepository<PackageServiceProviderMappingDTO, Long> {

	public void insert(Long packageId,List<String> serviceProviderList);

	public void activate(long packageDetailsId);

	public void deleteByPackageId(long packageId);

	public void removedFromEvent(long packageId,long statusId);

	public void assign(long packageId, List<String> serviceProviderIdList,long statusId);

	public void insertByCustomer(long longValue, List<String> serviceTypeIds);
}
