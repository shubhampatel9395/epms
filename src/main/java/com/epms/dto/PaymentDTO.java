package com.epms.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
	private String paymentId;
	private String orderId;
	private Long userDetailsId;
	private Long eventId;
	private String method;
	private Double amount;
	private String description;
	private String status;
	private String refundStatus;
	private Date createdAt;
}