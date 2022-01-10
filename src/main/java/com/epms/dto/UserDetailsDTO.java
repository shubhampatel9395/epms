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
	private int isCustomer;
	private int isEmployee;
	private int isAuth;
	private String firstName;
	private String lastName;
	private String serviceProviderName;
	private String email;
	private String password;
	private String mobileNumber;
	private int isAdmin;
	private int isServiceProvider;
	private Date createdAt;
	private Date updatedAt;
	private int isActive;
	private Integer createdBy;
	private Integer updatedBy;

}
