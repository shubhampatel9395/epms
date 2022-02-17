package com.epms.dto;

import java.sql.Time;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnquiryDTO {
	private Integer enquiryId;
	private Integer personName;
	private String mobileNumber;
	private String email;
	private Integer eventTypeId;
	private Date startDate;
	private Time startTime;
	private Date endDate;
	private Time endTime;
	private String description;
	private Boolean isPublic;
	private Boolean isFree;
	private Integer estimatedGuest;
	private Double minBudget;
	private Double maxBudget;
	private Integer enquiryStatusId;
	private String response;
	private Date responseDate;
	private Time responseTime;
	private Date createdAt;
	private Date updatedAt;
	private Boolean isActive;
	private Integer createdBy;
	private Integer updatedBy;
}