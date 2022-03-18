package com.epms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventOrganizerEventWorkDTO extends ServiceProviderEventWorkDTO {
	public Boolean isFree;
	public Long packageId;
}