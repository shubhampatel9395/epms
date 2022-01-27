package com.epms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnuEnquiryStatusDTO {
	private Integer statusId;
	private String status;
}