package com.epms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnuEventTypeDTO {
	private Integer eventTypeId;
	private String eventType;
	private String description;
	private Boolean isActive;
}