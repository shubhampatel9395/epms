package com.epms.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageVenueFacilityMappingDTO {
	private Integer packageVenueFacilityMappingId;
	private Integer packageDetailsId;
	private Integer venueId;
	private Integer venueFacilityId;
	private Date createdAt;
	private Date updatedAt;
	private Boolean isActive;
	private Integer createdBy;
	private Integer updatedBy;
}