package com.epms.dao;

import javax.validation.Valid;

import com.epms.core.ICRUDRepository;
import com.epms.dto.PackageDetailsDTO;

public interface IPackageDetailsDAO extends ICRUDRepository<PackageDetailsDTO, Long> {

	public void activate(long packageDetailsId);

	public PackageDetailsDTO insertByCustomer(@Valid PackageDetailsDTO packageDetailsDTO);

}