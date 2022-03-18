package com.epms.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProviderPackageDTO {
	private Long packageDetailsId;
	private String title;
	private String description;
	private String eventType;
	private Integer guestAmount;
	private Double totalCost;
	private Date createdAt;
	private String venueName;
	private Long addressId;
	private String venueEmail;
	private String contactNumber;
}