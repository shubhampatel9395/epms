package com.epms.dto;

import java.sql.Blob;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventBannerDTO {
	private Integer bannerId;
	private Blob banner;
	private Integer eventId;
	private Date createdAt;
	private Date updatedAt;
	private Boolean isActive;
	private Integer createdBy;
	private Integer updatedBy;
}