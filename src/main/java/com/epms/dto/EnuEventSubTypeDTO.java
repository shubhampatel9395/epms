package com.epms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnuEventSubTypeDTO {
	private Integer eventSubTypeId;
	private String eventSubType;
	private String description;
	private Boolean isActive;
	private Integer eventTypeId;
}