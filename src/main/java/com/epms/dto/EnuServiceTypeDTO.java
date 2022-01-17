package com.epms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnuServiceTypeDTO {
	private Integer serviceTypeId;
	private String service;
	private String description;
	private Boolean isActive;
}