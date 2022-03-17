package com.epms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InvoiceServiceProviderDTO {
	private Double cost;
	private String serviceProviderName;
	private Long addressId;
	private String service;
}