package com.epms.dao;

import java.util.List;

import com.epms.core.ICRUDRepository;
import com.epms.dto.EventDTO;

public interface IEventDAO extends ICRUDRepository<EventDTO, Long> {

	public Integer getCountByEventType(String eventType);

	public Integer getCount();

	public List<EventDTO> getLastDayData();

}
