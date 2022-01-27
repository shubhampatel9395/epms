package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.EventBannerDTO;

public interface IEventBannerService {
	public List<EventBannerDTO> findAll();
	public EventBannerDTO findById(Long id);	
	public List<EventBannerDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<EventBannerDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public EventBannerDTO insert(EventBannerDTO EventBannerDTO);
	public void delete(Long id);
	public EventBannerDTO update(EventBannerDTO entity);
}
