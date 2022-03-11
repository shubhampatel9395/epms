package com.epms.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventEmployeeMappingDTO {
	private Integer eventEmployeeMappingId;
	private Integer eventId;
	private Integer employeeId;
	private Integer employeeTypeId;
	private String workDescription;
	private Integer statusId;
	private Date createdAt;
	private Date updatedAt;
	private Boolean isActive;
	private Integer createdBy;
	private Integer updatedBy;
}