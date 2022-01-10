package com.epms.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
	private Integer addressId;
	private String address1;
	private String address2;
	private Integer cityId;
	private Integer stateId;
	private Integer countryId;
	private String postalCode;
	private Date createdAt;
	private Date updatedAt;
	private int isActive;
	private Integer createdBy;
	private Integer updatedBy;

}
