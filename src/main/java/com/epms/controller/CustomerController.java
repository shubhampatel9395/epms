package com.epms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epms.dto.AddressDTO;
import com.epms.dto.EnuCityDTO;
import com.epms.dto.EnuStateDTO;
import com.epms.dto.UserDetailsDTO;
import com.epms.service.IAddressService;
import com.epms.service.IEnuCityService;
import com.epms.service.IEnuCountryService;
import com.epms.service.IEnuStateService;
import com.epms.service.IUserDetailsService;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
public class CustomerController {
	@Autowired
	IEnuCityService enuCityService;

	@Autowired
	IEnuStateService enuStateService;

	@Autowired
	IEnuCountryService enuCountryService;

	@Autowired
	IUserDetailsService userDetailsService;

	@Autowired
	IAddressService addressService;

	@GetMapping
	public ModelAndView homePage() {
		return new ModelAndView("index");
	}

	@GetMapping("getStates/{countryId}")
	public List<EnuStateDTO> getStates(@PathVariable @NotNull Integer countryId) {
		log.info("Loading states in registration page");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("countryId", countryId);
		return enuStateService.findByNamedParameters(paramSource);
	}

	@GetMapping("getCities/{stateId}")
	public List<EnuCityDTO> getCities(@PathVariable @NotNull Integer stateId) {
		log.info("Loading cities in registration page");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("stateId", stateId);
		return enuCityService.findByNamedParameters(paramSource);
	}

	@GetMapping("customer-registration")
	public ModelAndView loadCustomerRegistrationPage() {
		log.info("Load customer registration page");
		final ModelAndView modelandmap = new ModelAndView("customerRegisteration");
		modelandmap.addObject("countries", enuCountryService.findAll());
		modelandmap.addObject("userDetailsDTO", new UserDetailsDTO());
		modelandmap.addObject("addressDTO", new AddressDTO());
		return modelandmap;
	}

	@PostMapping("customer-registration")
	public ModelAndView submitCustomerRegistration(
			@Valid @ModelAttribute("userDetailsDTO") UserDetailsDTO userDetailsDTO,
			@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO) {
		AddressDTO insertAddressDTO = addressService.insert(addressDTO);
		userDetailsDTO.setAddressId(insertAddressDTO.getAddressId());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(userDetailsDTO.getPassword());
		userDetailsDTO.setPassword(encodedPassword);
		UserDetailsDTO insertUserDetailsDTO = userDetailsService.insert(userDetailsDTO);
		final ModelAndView modelandmap = new ModelAndView("index");
		modelandmap.addObject("userDetailsDTO", insertUserDetailsDTO);
		return modelandmap;
	}
	
	@GetMapping("/login")
	public ModelAndView viewLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return new ModelAndView("login");
		}
		return new ModelAndView("index");

	}

	
	@GetMapping("events")
	public ModelAndView events()
	{
		final ModelAndView modelandmap =  new ModelAndView("events");
		return modelandmap;
	}
	
	@GetMapping("enquiry")
	public ModelAndView enquiry()
	{
		final ModelAndView modelandmap =  new ModelAndView("enquiry");
		return modelandmap;
	}
	
	@GetMapping("gallery")
	public ModelAndView gallery()
	{
		final ModelAndView modelandmap =  new ModelAndView("gallery");
		return modelandmap;
	}
	
	@GetMapping("packages")
	public ModelAndView packages()
	{
		final ModelAndView modelandmap =  new ModelAndView("packages");
		return modelandmap;
	}
}