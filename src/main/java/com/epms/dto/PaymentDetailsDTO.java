package com.epms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailsDTO extends PaymentDTO {
	private String customerName;
	private String email;
	private String mobileNumber;
}