package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IPackageServiceProviderMappingDAO;
import com.epms.dto.PackageServiceProviderMappingDTO;
import com.epms.service.IPackageServiceProviderMappingService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class PackageServiceProviderMappingService implements IPackageServiceProviderMappingService {
	@Autowired
	private IPackageServiceProviderMappingDAO packageServiceProviderMappingDAO;

	@Override
	public List<PackageServiceProviderMappingDTO> findAll() {
		return packageServiceProviderMappingDAO.findAll();
	}

	@Override
	public PackageServiceProviderMappingDTO findById(Long id) {
		return packageServiceProviderMappingDAO.findById(id);
	}

	@Override
	public List<PackageServiceProviderMappingDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return packageServiceProviderMappingDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<PackageServiceProviderMappingDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return packageServiceProviderMappingDAO.findByNamedParameters(paramSource);
	}

	@Override
	public PackageServiceProviderMappingDTO insert(PackageServiceProviderMappingDTO PackageServiceProviderMappingDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		packageServiceProviderMappingDAO.delete(id);
	}

	@Override
	public PackageServiceProviderMappingDTO update(PackageServiceProviderMappingDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Long packageId,List<String> serviceProviderList) {
		packageServiceProviderMappingDAO.insert(packageId,serviceProviderList);
	}

	@Override
	public void activate(long packageDetailsId) {
		packageServiceProviderMappingDAO.activate(packageDetailsId);
	}

	@Override
	public void deleteByPackageId(long packageId) {
		packageServiceProviderMappingDAO.deleteByPackageId(packageId);
	}

}
