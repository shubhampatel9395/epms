package com.epms.dto.form;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateEventPackageForm {
	// For Package
	private Integer packageDetailsId;
	
	// For individuals
	private Integer venueId;
	private List<String> facilities;
	private List<String> services;
}