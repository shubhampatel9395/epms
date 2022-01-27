package com.epms.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueDTO {
	private Integer venueId;
	private String venueName;
	private String description;
	private Integer addressId;
	private Double latitude;
	private Double longitude;
	private String contactNumber;
	private String email;
	private Integer guestCapacity;
	private Double cost;
	private Date createdAt;
	private Date updatedAt;
	private Boolean isActive;
	private Integer createdBy;
	private Integer updatedBy;
}