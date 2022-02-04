package com.epms.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDTO {
	private Integer userDetailsId;
	private Integer addressId;
	private Boolean isCustomer;
	private Boolean isEmployee;
	private Boolean isAuth;
	private String firstName;
	private String lastName;
	private String serviceProviderName;
	private String email;
	private String password;
	private String mobileNumber;
	private Boolean isAdmin;
	private Boolean isServiceProvider;
	private Date createdAt;
	private Date updatedAt;
	private Boolean isActive;
	private Integer createdBy;
	private Integer updatedBy;
	private String roleName;
}