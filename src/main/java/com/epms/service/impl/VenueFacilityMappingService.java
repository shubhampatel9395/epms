package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IVenueFacilityMappingDAO;
import com.epms.dto.VenueFacilityMappingDTO;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class VenueFacilityMappingService implements com.epms.service.IVenueFacilityMappingService {
	@Autowired
	private IVenueFacilityMappingDAO venueFacilityMappingDAO;

	@Override
	public List<VenueFacilityMappingDTO> findAll() {
		return venueFacilityMappingDAO.findAll();
	}

	@Override
	public VenueFacilityMappingDTO findById(Long id) {
		return venueFacilityMappingDAO.findById(id);
	}

	@Override
	public List<VenueFacilityMappingDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return venueFacilityMappingDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<VenueFacilityMappingDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return venueFacilityMappingDAO.findByNamedParameters(paramSource);
	}

	@Override
	public VenueFacilityMappingDTO insert(VenueFacilityMappingDTO VenueFacilityMappingDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public VenueFacilityMappingDTO update(VenueFacilityMappingDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Long venueId, List<String> list) {
		venueFacilityMappingDAO.insert(venueId, list);
	}

}
