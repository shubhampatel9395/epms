package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IPackageVenueFacilityMappingDAO;
import com.epms.dto.PackageVenueFacilityMappingDTO;
import com.epms.service.IPackageVenueFacilityMappingService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class PackageVenueFacilityMappingService implements IPackageVenueFacilityMappingService {
	@Autowired
	private IPackageVenueFacilityMappingDAO packageVenueFacilityMappingDAO;

	@Override
	public List<PackageVenueFacilityMappingDTO> findAll() {
		return packageVenueFacilityMappingDAO.findAll();
	}

	@Override
	public PackageVenueFacilityMappingDTO findById(Long id) {
		return packageVenueFacilityMappingDAO.findById(id);
	}

	@Override
	public List<PackageVenueFacilityMappingDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return packageVenueFacilityMappingDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<PackageVenueFacilityMappingDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return packageVenueFacilityMappingDAO.findByNamedParameters(paramSource);
	}

	@Override
	public PackageVenueFacilityMappingDTO insert(PackageVenueFacilityMappingDTO PackageVenueFacilityMappingDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public PackageVenueFacilityMappingDTO update(PackageVenueFacilityMappingDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
