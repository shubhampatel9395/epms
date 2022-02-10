package com.epms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueFacilityMappingDTO {
	private Integer venueFacilityMappingId;
	private Integer venueId;
	private Integer facilityId;
	private Boolean isActive;
}