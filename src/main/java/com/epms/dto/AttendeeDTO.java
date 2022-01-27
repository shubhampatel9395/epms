package com.epms.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendeeDTO {
	private Integer attendeeId;
	private String attendeeName;
	private String mobileNumber;
	private String email;
	private Integer eventId;
	private Date createdAt;
	private Date updatedAt;
	private Boolean isActive;
	private Integer createdBy;
	private Integer updatedBy;
}