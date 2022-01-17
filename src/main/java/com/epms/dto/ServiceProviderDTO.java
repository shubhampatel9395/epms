package com.epms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProviderDTO extends UserDetailsDTO {
	private Integer serviceProviderId;
	private Integer serviceTypeId;
	private Double cost;
}