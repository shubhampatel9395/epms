package com.epms.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO extends UserDetailsDTO {
	private Integer employeeId;
	private Integer userDetailsId;
	private Integer employeeRoleId;
	private String gender;
	private Date DOB;
	private Date hiringDate;
	private Date leavingDate;
	private Double salary;
	private Date createdAt;
	private Date updatedAt;
	private Boolean isActive;
	private Integer createdBy;
	private Integer updatedBy;
}