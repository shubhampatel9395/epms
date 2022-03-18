package com.epms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllServiceProvidersPackageDTO {
	private Long packageDetailsId;
	private String serviceProviderName;
	private String email;
	private String service;
}