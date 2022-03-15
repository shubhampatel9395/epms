package com.epms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventVenueDetailsDTO {
    private String venueName;
	private String venueType;
	private Long addressId;
	private String cost;
	private String eventType;
	private String eventTitle;
	private String eventStartDate;
	private String eventEndDate;
	private String firstName ;
	private String lastName ;
	private String mobileNumber;
}