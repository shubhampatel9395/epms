package com.epms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnuEmployeeRoleDTO {
	private Integer employeeRoleId;
	private String role;
	private String description;
	private Boolean isActive;
}