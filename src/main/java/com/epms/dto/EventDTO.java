package com.epms.dto;

import java.sql.Time;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EventDTO {
	private Integer eventId;
	private String eventTitle;
	private String objective;
	private Integer eventTypeId;
	private Integer eventSubTypeId;
	private Integer userDetailsId;
	private Integer packageDetailsId;
	private Integer eventOrganizerId;	// EmployeeId
	private Boolean isPublic;
	private Boolean isFree;
	private Date startDate;
	private Time startTime;
	private Date endDate;
	private Time endTime;
	private Integer estimatedGuest;
	private Double registrationFee;
	private Integer registrationAvailable;
	private Date createdAt;
	private Date updatedAt;
	private Boolean isActive;
	private Integer createdBy;
	private Integer updatedBy;
}