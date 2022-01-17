package com.epms.dao;

import java.util.List;

import com.epms.core.ICRUDRepository;
import com.epms.dto.EnuServiceTypeDTO;

public interface IEnuServiceTypeDAO extends ICRUDRepository<EnuServiceTypeDTO, Long> {
	public List<EnuServiceTypeDTO> findAllActive();
}