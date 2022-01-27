package com.epms.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.epms.dto.FeedbackDTO;

public interface IFeedbackService {
	public List<FeedbackDTO> findAll();
	public FeedbackDTO findById(Long id);	
	public List<FeedbackDTO> findByFieldValue(String fieldName, Object fieldValue);
	public List<FeedbackDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	public FeedbackDTO insert(FeedbackDTO FeedbackDTO);
	public void delete(Long id);
	public FeedbackDTO update(FeedbackDTO entity);
}
