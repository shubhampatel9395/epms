package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dao.IEnuEnquiryStatusDAO;
import com.epms.dto.EnuEnquiryStatusDTO;
import com.epms.service.IEnuEnquiryStatusService;

public class EnuEnquiryStatusService implements IEnuEnquiryStatusService {
	@Autowired
	private IEnuEnquiryStatusDAO enuEnquiryStatusDAO;

	@Override
	public List<EnuEnquiryStatusDTO> findAll() {
		return enuEnquiryStatusDAO.findAll();
	}

	@Override
	public EnuEnquiryStatusDTO findById(Long id) {
		return enuEnquiryStatusDAO.findById(id);
	}

	@Override
	public List<EnuEnquiryStatusDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return enuEnquiryStatusDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<EnuEnquiryStatusDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return enuEnquiryStatusDAO.findByNamedParameters(paramSource);
	}

	@Override
	public EnuEnquiryStatusDTO insert(EnuEnquiryStatusDTO EnuEnquiryStatusDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnuEnquiryStatusDTO update(EnuEnquiryStatusDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
