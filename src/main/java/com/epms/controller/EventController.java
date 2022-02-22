package com.epms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epms.service.IPackageDetailsService;
import com.epms.service.IServiceProviderService;
import com.epms.service.IVenueService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
public class EventController {
	@Autowired
	IPackageDetailsService packageDetailsService;
	
	@Autowired
	IServiceProviderService serviceProviderService;
	
	@Autowired
	IVenueService venueService;
	
	@PostMapping("getEventCost")
	public Double getEventCost(@RequestBody List<String> data) {
		System.out.println(data);
		Double cost = 0.0;
		if(data.size() == 0)
		{
			return -1.0;
		}
		
		if(data.get(0).equalsIgnoreCase("Static"))
		{
			cost += DataAccessUtils.singleResult(packageDetailsService.findByNamedParameters(new MapSqlParameterSource().addValue("packageDetailsId", data.get(1)))).getTotalCost();
		}
		else if(data.get(0).equalsIgnoreCase("Dynamic")) {
			cost += venueService.findById(Long.parseLong(data.get(1))).getCost();
			for (int i = 2; i < data.size() - 2; i++) {
				cost += serviceProviderService.findById(Long.parseLong(data.get(i))).getCost();
			}
		}
		
		if(data.get(data.size() - 2).equalsIgnoreCase("true")) {
			cost += 500.0;
		}
		if(!data.get(data.size() - 1).equalsIgnoreCase("true")) {
			cost += 500.0;
		}
		
		return cost;
	}
}
