package com.epms.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDTO {
	private Integer feedbackId;
	private Integer eventId;
	private Double eventRating;
	private String eventDescription;
	private Integer venueId;
	private Double venueRating;
	private String venueDescription;
	private Integer employeeId;
	private Double employeeRating;
	private String employeeDescription;
	private Integer serviceProviderId;
	private Double serviceProviderRating;
	private String serviceProviderDescription;
	private Integer packageId;
	private Double packageRating;
	private String packageDescription;
	private Date createdAt;
	private Date updatedAt;
	private Boolean isActive;
	private Integer createdBy;
	private Integer updatedBy;
}