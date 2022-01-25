package com.epms.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageDetailsDTO {
	private Integer packageDetailsId;
	private String title;
	private String description;
	private Integer guestAmount;
	private Double totalCost;
	private Boolean isStatic;
	private Integer eventTypeId;
	private Integer eventSubTypeId;
	private Integer venueId;
	private Date createdAt;
	private Date updatedAt;
	private Boolean isActive;
	private Integer createdBy;
	private Integer updatedBy;
}