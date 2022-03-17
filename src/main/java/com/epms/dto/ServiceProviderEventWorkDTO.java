package com.epms.dto;

import java.sql.Time;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProviderEventWorkDTO {
	private Long eventId;
	private String eventTitle;
	private String objective;
	private String eventType;
	private Date startDate;
	private Time startTime;
	private Date endDate;
	private Time endTime;
	private Boolean isPublic;
	private Date updatedAt;
	private String customerName;
	private String email;
	private String mobileNumber;
	private String eventOrganizerName;
	private String eventOrganizerEmail;
	private String eventOrganizerMobileNumber;
	private String venueName;
	private Long addressId;
	private String venueEmail;
	private String contactNumber;
}