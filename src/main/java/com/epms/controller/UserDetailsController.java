package com.epms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epms.dto.EnuStateDTO;
import com.epms.dto.UserDetailsDTO;
import com.epms.service.IEnuCountryService;
import com.epms.service.IEnuStateService;
import com.epms.service.IUserDetailsService;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
public class UserDetailsController {

	@Autowired
	IEnuStateService enuStateService;

	@Autowired
	IEnuCountryService enuCountryService;
	
	@Autowired
	IUserDetailsService userDetailsService;

	@GetMapping
	public ModelAndView homePage() {
		 return new ModelAndView("index");
	}

	@GetMapping("load-customer-registration")
	public ModelAndView customerRegistrationPage() {
		log.info("Load customer registration page");
		final ModelAndView modelandmap = new ModelAndView("customerRegisteration");
		modelandmap.addObject("countries", enuCountryService.findAll());
		return modelandmap;
	}
	
	@GetMapping("getStates/{countryId}")
	public List<EnuStateDTO> customerRegistrationPage(@PathVariable @NotNull Integer countryId) {
		log.info("Load customer registration page");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("countryId",countryId);
		return enuStateService.findByNamedParameters(paramSource);
	}
	
	@PostMapping("customer-registration")
	public ModelAndView customerRegistration(@Valid UserDetailsDTO userDetailsDTO) {
		userDetailsService.insert(userDetailsDTO);
		//final ModelAndView modelandmap = new ModelAndView("customerHomePage");
		//modelandmap.addObject("userDetailsDTO", userDetailsDTO);
		//return modelandmap;
		return null;
		
	}

}
