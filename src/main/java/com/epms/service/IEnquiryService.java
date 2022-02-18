package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.EnquiryDTO;

public interface IEnquiryService {
	public List<EnquiryDTO> findAll();
	public EnquiryDTO findById(Long id);	
	public List<EnquiryDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<EnquiryDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public EnquiryDTO insert(EnquiryDTO EnquiryDTO);
	public void delete(Long id);
	public EnquiryDTO update(EnquiryDTO entity);
	List<EnquiryDTO> findAllEnquiryWithStatus();
	EnquiryDTO findEnquiryById(Long id);

}
