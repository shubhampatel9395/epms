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
	private Integer eventRating;
	private String eventDescription;
	private Integer venueId;
	private Integer venueRating;
	private String venueDescription;
	private Integer employeeId;
	private Integer employeeRating;
	private String employeeDescription;
	private Integer serviceProviderId;
	private Integer serviceProviderRating;
	private String serviceProviderDescription;
	private Date createdAt;
	private Date updatedAt;
	private Boolean isActive;
	private Integer createdBy;
	private Integer updatedBy;
}
