package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dao.IEventBannerDAO;
import com.epms.dto.EventBannerDTO;
import com.epms.service.IEventBannerService;

public class EventBannerService implements IEventBannerService {
	@Autowired
	private IEventBannerDAO eventBannerDAO;

	@Override
	public List<EventBannerDTO> findAll() {
		return eventBannerDAO.findAll();
	}

	@Override
	public EventBannerDTO findById(Long id) {
		return eventBannerDAO.findById(id);
	}

	@Override
	public List<EventBannerDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return eventBannerDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<EventBannerDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return eventBannerDAO.findByNamedParameters(paramSource);
	}

	@Override
	public EventBannerDTO insert(EventBannerDTO EventBannerDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EventBannerDTO update(EventBannerDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
