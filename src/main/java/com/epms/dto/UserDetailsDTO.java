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
	private boolean isCustomer;
	private boolean isEmployee;
	private boolean isAuth;
	private String firstName;
	private String lastName;
	private String serviceProviderName;
	private String email;
	private String password;
	private String mobileNumber;
	private boolean isAdmin;
	private boolean isServiceProvider;
	private Date createdAt;
	private Date updatedAt;
	private boolean isActive;
	private Integer createdBy;
	private Integer updatedBy;

}
