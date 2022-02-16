package com.epms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardDTO {
	public Integer customerCount;
	public Integer eventCount;
	public Integer serviceproviderCount;
	public Integer venueCount;
}