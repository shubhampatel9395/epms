package com.epms.dto.form;

import java.sql.Blob;
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
public class CreateEventBasicInfoForm {
	private String eventTitle;
	private String objective;
	private Blob banner;
	private Integer eventTypeId;
	private Integer eventSubTypeId;
	private Integer estimatedGuest;
	private Date startDate;
	private Time startTime;
	private Date endDate;
	private Time endTime;
	private Boolean isPublic;
}