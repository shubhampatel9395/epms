package com.epms.dao;

import com.epms.core.ICRUDRepository;
import com.epms.dto.PackageDetailsDTO;

public interface IPackageDetailsDAO extends ICRUDRepository<PackageDetailsDTO, Long> {

	public void activate(long packageDetailsId);

}
