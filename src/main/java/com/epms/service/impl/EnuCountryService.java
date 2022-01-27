/**
 * 
 */
package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IEnuCountryDAO;
import com.epms.dto.EnuCountryDTO;
import com.epms.service.IEnuCountryService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class EnuCountryService implements IEnuCountryService {
	
	@Autowired
	private IEnuCountryDAO enuCountryDAO;

	@Override
	public List<EnuCountryDTO> findAll() {
		return enuCountryDAO.findAll();
	}

	@Override
	public EnuCountryDTO findById(Long countryId) {
		return enuCountryDAO.findById(countryId);
	}

	@Override
	public List<EnuCountryDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return enuCountryDAO.findByNamedParameters(paramSource);
	}

	@Override
	public EnuCountryDTO insert(EnuCountryDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EnuCountryDTO update(EnuCountryDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EnuCountryDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return enuCountryDAO.findByFieldValue(fieldName, fieldValue);
	}

}
