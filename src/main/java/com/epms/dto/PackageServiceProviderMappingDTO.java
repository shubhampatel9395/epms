package com.epms.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageServiceProviderMappingDTO {
	private Integer packageServiceProviderMappingId;
	private Integer packageDetailsId;
	private Integer serviceProviderId;
	private Integer serviceTypeId;
	private Integer statusId;
	private Date createdAt;
	private Date updatedAt;
	private Boolean isActive;
	private Integer createdBy;
	private Integer updatedBy;
}