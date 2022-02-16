package com.epms.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminLatestActivityDTO {
	// Event, Customer, Service Provider
	public String roleName;
	// Title, Name, Name
	public String title;
	// " registered in the system. Review it.", " registered in the system." , " registered in the system. Verify to give access."
	public String subTitle;
	public Date createdAt;
}