package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dao.IEnquiryDAO;
import com.epms.dto.EnquiryDTO;
import com.epms.service.IEnquiryService;

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
		// TODO Auto-generated method stub
		return null;
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

}
