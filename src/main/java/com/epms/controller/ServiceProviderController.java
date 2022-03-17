package com.epms.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.support.DataAccessUtils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.epms.authentication.CustomUserDetailsDTO;
import com.epms.dto.AddressDTO;
import com.epms.dto.EnuCityDTO;
import com.epms.dto.EnuStateDTO;
import com.epms.dto.ServiceProviderDTO;
import com.epms.dto.ServiceProviderDashboardDTO;
import com.epms.dto.ServiceProviderEventWorkDTO;
import com.epms.dto.UserDetailsDTO;
import com.epms.email.configuration.IMailService;
import com.epms.service.IAddressService;
import com.epms.service.IEmployeeService;
import com.epms.service.IEnquiryService;
import com.epms.service.IEnuCityService;
import com.epms.service.IEnuCountryService;
import com.epms.service.IEnuEmployeeRoleService;
import com.epms.service.IEnuEnquiryStatusService;
import com.epms.service.IEnuEventStatusService;
import com.epms.service.IEnuEventTypeService;
import com.epms.service.IEnuServiceTypeService;
import com.epms.service.IEnuStateService;
import com.epms.service.IEnuVenueFacilityService;
import com.epms.service.IEnuVenueTypeService;
import com.epms.service.IEventBannerService;
import com.epms.service.IEventService;
import com.epms.service.IPackageDetailsService;
import com.epms.service.IPackageServiceProviderMappingService;
import com.epms.service.IServiceProviderService;
import com.epms.service.IUserDetailsService;
import com.epms.service.IVenueEventTypeMappingService;
import com.epms.service.IVenueFacilityMappingService;
import com.epms.service.IVenueImageMappingService;
import com.epms.service.IVenueService;

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
	
	@Autowired
	IEnquiryService enquiryService;

	@Autowired
	IEnuServiceTypeService enuserviceTypeService;
	
	@Autowired
	IEmployeeService employeeService;

	@Autowired
	IEnuEmployeeRoleService enuEmployeeRoleService;

	@Autowired
	IEnuEventTypeService enuEventTypeService;

	@Autowired
	IEnuVenueFacilityService enuVenueFacilityService;

	@Autowired
	IEnuVenueTypeService enuVenueTypeService;

	@Autowired
	IVenueService venueService;

	@Autowired
	IVenueFacilityMappingService venueFacilityMappingService;

	@Autowired
	IVenueEventTypeMappingService venueEventTypeMappingService;

	@Autowired
	IVenueImageMappingService venueImageMappingService;

	@Autowired
	IPackageDetailsService packageDetailsService;

	@Autowired
	IMailService mailService;

	@Autowired
	IPackageServiceProviderMappingService packageServiceProviderMappingService;

	@Autowired
	IEventService eventService;

	@Autowired
	IEnuEnquiryStatusService enquiryStatusService;

	@Autowired
	IEnuEventStatusService enuEventStatusService;

	@Autowired
	IEventBannerService eventBannerService;
	
	@Autowired
	Environment env;

	@GetMapping("/dashboard")
	public ModelAndView homePage() {
		ModelAndView modelandmap = new ModelAndView("serviceprovider/index");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return new ModelAndView("redirect:/login");
		} else {
			CustomUserDetailsDTO customUserDetailsDTO = (CustomUserDetailsDTO) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			ServiceProviderDTO currentUser = DataAccessUtils.singleResult(serviceProviderService.findByNamedParameters(new MapSqlParameterSource().addValue("userDetailsId", customUserDetailsDTO.getUserDetailsId())));
			
			ServiceProviderDashboardDTO serviceProviderDashboardDTO = new ServiceProviderDashboardDTO();
			serviceProviderDashboardDTO.setPackageCount(serviceProviderService.getTotalParticipatedPackages(currentUser.getServiceProviderId().longValue()));
			serviceProviderDashboardDTO.setParticipatedEventCount(serviceProviderService.getCompletedEvents(currentUser.getServiceProviderId().longValue()));
			serviceProviderDashboardDTO.setOngoingEventCount(serviceProviderService.getOngoingEvents(currentUser.getServiceProviderId().longValue()));
			serviceProviderDashboardDTO.setReviewCount(serviceProviderService.getAverageRatings(currentUser.getServiceProviderId().longValue()));
			modelandmap.addObject("serviceProviderDashboardDTO", serviceProviderDashboardDTO);
			
			List<ServiceProviderEventWorkDTO> ongoingEventWorkDTOs = serviceProviderService.getOngoingEventsDetails(currentUser.getServiceProviderId().longValue());
			// ongoingEventWorkDTOs.sort(Collections.reverseOrder(Comparator.comparing(ServiceProviderEventWorkDTO::getUpdatedAt)));
			modelandmap.addObject("ongoingEventWorkDTOs", ongoingEventWorkDTOs);
			
			List<ServiceProviderEventWorkDTO> completedEventWorkDTOs = serviceProviderService.getCompletedEventsDetails(currentUser.getServiceProviderId().longValue());
			// completedEventWorkDTOs.sort(Collections.reverseOrder(Comparator.comparing(ServiceProviderEventWorkDTO::getUpdatedAt)));
			modelandmap.addObject("completedEventWorkDTOs", completedEventWorkDTOs);
			
			return modelandmap;
		}
	}
	
	@GetMapping("/authentication-remaining")
	public ModelAndView waitForAuthentication(@ModelAttribute("fullname") String fullname) {
		ModelAndView modelandmap = new ModelAndView("authentication-remaining");
		modelandmap.addObject("serviceproviderName",fullname);
		return modelandmap;
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
			@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO,
			ModelAndView modelandmap,RedirectAttributes rm) {
		AddressDTO insertAddressDTO = addressService.insert(addressDTO);
		serviceProviderDTO.setAddressId(insertAddressDTO.getAddressId());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(serviceProviderDTO.getPassword());
		serviceProviderDTO.setPassword(encodedPassword);
		ServiceProviderDTO insertServiceProviderDTO = serviceProviderService.insert(serviceProviderDTO);
		modelandmap = new ModelAndView("redirect:/serviceprovider/authentication-remaining");
		rm.addFlashAttribute("fullname", serviceProviderDTO.getServiceProviderName());
		return modelandmap;
	}
	
	@GetMapping("/edit_profile")
	public ModelAndView editServiceProvider() {
		CustomUserDetailsDTO customUserDetailsDTO = (CustomUserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("userDetailsId", customUserDetailsDTO.getUserDetailsId().longValue());
		//paramSource.addValue("isServiceProvider", true);
		ModelAndView modelandmap = new ModelAndView("serviceprovider/edit_serviceprovider");

		// TODO make form object
		ServiceProviderDTO serviceProviderDTO = DataAccessUtils.singleResult(serviceProviderService.findByNamedParameters(paramSource));
		// Need it to show details
		UserDetailsDTO userDetailsDTO = userDetailsService.findById(customUserDetailsDTO.getUserDetailsId().longValue());
		AddressDTO addressDTO = addressService.findById(userDetailsDTO.getAddressId().longValue());

		modelandmap.addObject("serviceProviderDTO", serviceProviderDTO);
		modelandmap.addObject("userDetailsDTOEdit", userDetailsDTO);
		modelandmap.addObject("serviceTypes", enuServiceTypeService.findAllActive());
		modelandmap.addObject("addressDTO", addressDTO);

		modelandmap.addObject("countries", enuCountryService.findAll());

		MapSqlParameterSource paramSourceCountry = new MapSqlParameterSource();
		paramSourceCountry.addValue("countryId", addressDTO.getCountryId());
		modelandmap.addObject("states", enuStateService.findByNamedParameters(paramSourceCountry));

		MapSqlParameterSource paramSourceState = new MapSqlParameterSource();
		paramSourceState.addValue("stateId", addressDTO.getStateId());
		modelandmap.addObject("cities", enuCityService.findByNamedParameters(paramSourceState));
		return modelandmap;
	}

	@PostMapping("/update_serviceprovider")
	public ModelAndView updateServiceProvider(
			@Valid @ModelAttribute("serviceProviderDTO") ServiceProviderDTO serviceProviderDTO,
			@Valid @ModelAttribute("userDetailsDTOEdit") UserDetailsDTO userDetailsDTO,
			@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO) {
		final ModelAndView modelandmap = new ModelAndView("redirect:/serviceprovider/dashboard");

		ServiceProviderDTO oldserviceProviderDTO = serviceProviderService
				.findById(serviceProviderDTO.getServiceProviderId().longValue());
		AddressDTO oldAddressDTO = addressService.findById(addressDTO.getAddressId().longValue());


		serviceProviderDTO.setServiceProviderName(userDetailsDTO.getServiceProviderName());
		serviceProviderDTO.setMobileNumber(userDetailsDTO.getMobileNumber());
		serviceProviderDTO.setEmail(userDetailsDTO.getEmail());

		if (!(serviceProviderDTO.getServiceProviderName().equals(oldserviceProviderDTO.getServiceProviderName()))) {
			oldserviceProviderDTO.setServiceProviderName(serviceProviderDTO.getServiceProviderName());
		}

		if (!(serviceProviderDTO.getEmail().equals(oldserviceProviderDTO.getEmail()))) {
			oldserviceProviderDTO.setEmail(serviceProviderDTO.getEmail());
		}

		if (!(serviceProviderDTO.getMobileNumber().equals(oldserviceProviderDTO.getMobileNumber()))) {
			oldserviceProviderDTO.setMobileNumber(serviceProviderDTO.getMobileNumber());
		}

		if (!(serviceProviderDTO.getServiceTypeId().equals(oldserviceProviderDTO.getServiceTypeId()))) {
			oldserviceProviderDTO.setServiceTypeId(serviceProviderDTO.getServiceTypeId());
		}

		if (!(serviceProviderDTO.getCost().equals(oldserviceProviderDTO.getCost()))) {
			oldserviceProviderDTO.setCost(serviceProviderDTO.getCost());
		}

		if (!(addressDTO.getAddress1().equals(oldAddressDTO.getAddress1()))) {
			oldAddressDTO.setAddress1(addressDTO.getAddress1());
		}

		if (!(addressDTO.getAddress2().equals(oldAddressDTO.getAddress2()))) {
			oldAddressDTO.setAddress2(addressDTO.getAddress2());
		}

		if (!(addressDTO.getCityId().equals(oldAddressDTO.getCityId()))) {
			oldAddressDTO.setCityId(addressDTO.getCityId());
		}

		if (!(addressDTO.getStateId().equals(oldAddressDTO.getStateId()))) {
			oldAddressDTO.setStateId(addressDTO.getStateId());
		}

		if (!(addressDTO.getCountryId().equals(oldAddressDTO.getCountryId()))) {
			oldAddressDTO.setCountryId(addressDTO.getCountryId());
		}

		if (!(addressDTO.getPostalCode().equals(oldAddressDTO.getPostalCode()))) {
			oldAddressDTO.setPostalCode(addressDTO.getPostalCode());
		}

		addressService.update(oldAddressDTO);
		// userDetailsService.update(oldUserDetailsDTO);
		serviceProviderService.update(oldserviceProviderDTO);
		return modelandmap;
	}

}