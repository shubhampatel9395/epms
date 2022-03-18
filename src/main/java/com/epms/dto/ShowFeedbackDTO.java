package com.epms.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowFeedbackDTO extends ServiceProviderEventWorkDTO {
	private Integer feedbackId;
	private Integer serviceProviderId;
	private Double serviceProviderRating;
	private String serviceProviderDescription;
	private Date updatedAt;
}