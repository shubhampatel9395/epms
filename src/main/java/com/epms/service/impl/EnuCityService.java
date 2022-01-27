package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IEnuCityDAO;
import com.epms.dto.EnuCityDTO;
import com.epms.service.IEnuCityService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class EnuCityService implements IEnuCityService {

	@Autowired
	private IEnuCityDAO enuCityDAO;
	
	@Override
	public List<EnuCityDTO> findAll() {
		return enuCityDAO.findAll();
	}

	@Override
	public EnuCityDTO findById(Long cityId) {
		return enuCityDAO.findById(cityId);
	}

	@Override
	public List<EnuCityDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return enuCityDAO.findByNamedParameters(paramSource);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
	}

	@Override
	public EnuCityDTO update(EnuCityDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnuCityDTO insert(EnuCityDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
