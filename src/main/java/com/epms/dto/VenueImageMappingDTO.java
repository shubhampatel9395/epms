package com.epms.dto;

import java.sql.Blob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueImageMappingDTO {
	private Integer venueImageMappingId;
	private Integer venueId;
	private Blob image;
}