package com.epms.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateEventTicketsForm {
	private Boolean isFree;
	private Double registrationFee;
	private Integer registrationAvailable;
}