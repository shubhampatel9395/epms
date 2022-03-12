package com.epms.dao;

import com.epms.core.ICRUDRepository;
import com.epms.dto.EventEmployeeMappingDTO;

public interface IEventEmployeeMappingDAO extends ICRUDRepository<EventEmployeeMappingDTO, Long> {

	public void removedFromEvent(long eventId);

}
