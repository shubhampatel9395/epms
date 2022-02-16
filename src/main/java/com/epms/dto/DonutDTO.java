package com.epms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonutDTO {
	private String name;
	private Integer id;
	private Double quantity;
	private Double percentage;
}
