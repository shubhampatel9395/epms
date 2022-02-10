package com.epms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VenueTempDTO {
	List<String> selectedEventTypes;
	List<String> selectedFacilities;
	List<Double> costs;
}