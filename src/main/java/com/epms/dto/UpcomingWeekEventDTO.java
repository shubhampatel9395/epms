package com.epms.dto;

import java.sql.Time;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpcomingWeekEventDTO {
	private Integer eventId;
	private String eventTitle;
	private Date startDate;
	private Time startTime;
	private String customerName;
	private String email;
	private String mobileNumber;
}