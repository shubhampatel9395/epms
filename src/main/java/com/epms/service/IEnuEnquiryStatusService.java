package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.EnuEnquiryStatusDTO;

public interface IEnuEnquiryStatusService {
	public List<EnuEnquiryStatusDTO> findAll();
	public EnuEnquiryStatusDTO findById(Long id);	
	public List<EnuEnquiryStatusDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<EnuEnquiryStatusDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public EnuEnquiryStatusDTO insert(EnuEnquiryStatusDTO EnuEnquiryStatusDTO);
	public void delete(Long id);
	public EnuEnquiryStatusDTO update(EnuEnquiryStatusDTO entity);
}
