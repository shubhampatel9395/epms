package com.epms.dao;

import java.util.List;

import com.epms.core.ICRUDRepository;
import com.epms.dto.EventDTO;
import com.epms.dto.PackageDetailsDTO;

public interface IEventDAO extends ICRUDRepository<EventDTO, Long> {

	public Integer getCountByEventType(String eventType);

	public Integer getCount();

	public List<EventDTO> getLastDayData();

	public EventDTO insertByAdmin(EventDTO eventDTO);

	public EventDTO verifyEvent(EventDTO eventDTO, PackageDetailsDTO packageDetailsDTO);

	public void unVerifyEvent(long eventId);
}
