package com.epms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnuVenueTypeDTO {
	public Integer enuVenueTypeId;
	public String venueType;
	public Boolean isActive;
}