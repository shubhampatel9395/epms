package com.epms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnuVenueFacilityDTO {
	private Integer venueFacilityId;
	private String facility;
	private String description;
	private Boolean isActive;
}