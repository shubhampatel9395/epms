package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IEnquiryDAO;
import com.epms.dto.EnquiryDTO;
import com.epms.service.IEnquiryService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class EnquiryService implements IEnquiryService {
	@Autowired
	private IEnquiryDAO enquiryDAO;
	
	@Override
	public List<EnquiryDTO> findAll() {
		return enquiryDAO.findAll();
	}

	@Override
	public EnquiryDTO findById(Long id) {
		return enquiryDAO.findById(id);
	}

	@Override
	public List<EnquiryDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return enquiryDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<EnquiryDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return enquiryDAO.findByNamedParameters(paramSource);
	}

	@Override
	public EnquiryDTO insert(EnquiryDTO EnquiryDTO) {
		return enquiryDAO.insert(EnquiryDTO);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnquiryDTO update(EnquiryDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EnquiryDTO> findAllEnquiryWithStatus() {
		return enquiryDAO.findAllEnquiryWithStatus();
	}

	@Override
	public EnquiryDTO findEnquiryById(Long id) {
		return enquiryDAO.findEnquiryById(id);
	}

	@Override
	public void updateResponse(EnquiryDTO inquiry) {
		 enquiryDAO.updateResponse(inquiry);
		
	}

}
