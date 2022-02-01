package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IPackageDetailsDAO;
import com.epms.dto.PackageDetailsDTO;
import com.epms.service.IPackageDetailsService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class PackageDetailsService implements IPackageDetailsService {
	@Autowired
	private IPackageDetailsDAO packageDetailsDAO;

	@Override
	public List<PackageDetailsDTO> findAll() {
		return packageDetailsDAO.findAll();
	}

	@Override
	public PackageDetailsDTO findById(Long id) {
		return packageDetailsDAO.findById(id);
	}

	@Override
	public List<PackageDetailsDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return packageDetailsDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<PackageDetailsDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return packageDetailsDAO.findByNamedParameters(paramSource);
	}

	@Override
	public PackageDetailsDTO insert(PackageDetailsDTO PackageDetailsDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public PackageDetailsDTO update(PackageDetailsDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
