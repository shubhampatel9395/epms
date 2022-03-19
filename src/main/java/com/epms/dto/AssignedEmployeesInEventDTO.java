package com.epms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignedEmployeesInEventDTO {
	public Long eventEmployeeMappingId;
	public String employeeName;
	public String employeeRole;
	public String email;
	public String mobileNumber;
	public String workDescription;
	public String workingStatus;
}