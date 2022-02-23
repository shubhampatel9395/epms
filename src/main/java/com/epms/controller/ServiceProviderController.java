package com.epms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
import com.epms.dto.ServiceProviderDTO;
import com.epms.service.IAddressService;
import com.epms.service.IEnuCityService;
import com.epms.service.IEnuCountryService;
import com.epms.service.IEnuServiceTypeService;
import com.epms.service.IEnuStateService;
import com.epms.service.IServiceProviderService;
import com.epms.service.IUserDetailsService;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/serviceprovider")
@Slf4j
public class ServiceProviderController {
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

	@Autowired
	IEnuServiceTypeService enuServiceTypeService;

	@Autowired
	IServiceProviderService serviceProviderService;

	@GetMapping("/dashboard")
	public ModelAndView homePage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return new ModelAndView("redirect:/login");
		} else {
			return new ModelAndView("serviceProvider/index");
		}
	}
	
	@GetMapping("/service-settings")
	public ModelAndView serviceSettings() {
		ModelAndView modelandmap = new ModelAndView("/serviceprovider/service-settings");
		return modelandmap;
	}
	
	@GetMapping("/assigned-events")
	public ModelAndView assignedEvents() {
		ModelAndView modelandmap = new ModelAndView("/serviceprovider/assigned-events");
		return modelandmap;
	}
	
	@GetMapping("/event-history")
	public ModelAndView eventHistory() {
		ModelAndView modelandmap = new ModelAndView("/serviceprovider/event-history");
		return modelandmap;
	}
	
	@GetMapping("/payment-history")
	public ModelAndView paymentHistory() {
		ModelAndView modelandmap = new ModelAndView("/serviceprovider/payment-history");
		return modelandmap;
	}
	
	@GetMapping("/feedbacks")
	public ModelAndView feedback() {
		ModelAndView modelandmap = new ModelAndView("/serviceprovider/feedbacks");
		return modelandmap;
	}
	
	@GetMapping("logout")
	public ModelAndView logoutPage() {
		return new ModelAndView("redirect:/login");
	}

	@GetMapping("/getStates/{countryId}")
	public List<EnuStateDTO> getStates(@PathVariable @NotNull Integer countryId) {
		log.info("Loading states in registration page");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("countryId", countryId);
		return enuStateService.findByNamedParameters(paramSource);
	}

	@GetMapping("/getCities/{stateId}")
	public List<EnuCityDTO> getCities(@PathVariable @NotNull Integer stateId) {
		log.info("Loading cities in registration page");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("stateId", stateId);
		return enuCityService.findByNamedParameters(paramSource);
	}

	@GetMapping("/serviceprovider-registration")
	public ModelAndView loadServiceProviderRegistrationPage() {
		log.info("Load serviceprovider registration page");
		final ModelAndView modelandmap = new ModelAndView("serviceProviderRegistration");
		modelandmap.addObject("serviceTypes", enuServiceTypeService.findAllActive());
		modelandmap.addObject("countries", enuCountryService.findAll());
		modelandmap.addObject("serviceProviderDTO", new ServiceProviderDTO());
		modelandmap.addObject("addressDTO", new AddressDTO());
		return modelandmap;
	}

	@PostMapping("/serviceprovider-registration")
	public ModelAndView submitServiceProviderRegistration(
			@Valid @ModelAttribute("serviceProviderDTO") ServiceProviderDTO serviceProviderDTO,
			@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO) {
		AddressDTO insertAddressDTO = addressService.insert(addressDTO);
		serviceProviderDTO.setAddressId(insertAddressDTO.getAddressId());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(serviceProviderDTO.getPassword());
		serviceProviderDTO.setPassword(encodedPassword);
		ServiceProviderDTO insertServiceProviderDTO = serviceProviderService.insert(serviceProviderDTO);
		final ModelAndView modelandmap = new ModelAndView("serviceProvider/index");
		modelandmap.addObject("serviceProviderDTO", insertServiceProviderDTO);
		return modelandmap;
	}
}