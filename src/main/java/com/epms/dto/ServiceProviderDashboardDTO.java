package com.epms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProviderDashboardDTO {
	public Integer ongoingEventCount;
	public Integer participatedEventCount;
	public Integer packageCount;
	public Double reviewCount;
}