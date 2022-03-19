package com.epms.controller;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
import com.epms.dto.AllServiceProvidersPackageDTO;
import com.epms.dto.EnuCityDTO;
import com.epms.dto.EnuStateDTO;
import com.epms.dto.ServiceProviderDTO;
import com.epms.dto.ServiceProviderDashboardDTO;
import com.epms.dto.ServiceProviderEventWorkDTO;
import com.epms.dto.ServiceProviderPackageDTO;
import com.epms.dto.ShowFeedbackDTO;
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
			ongoingEventWorkDTOs.sort(Comparator.comparing(ServiceProviderEventWorkDTO::getStartDate));
			modelandmap.addObject("ongoingEventWorkDTOs", ongoingEventWorkDTOs);
			
			List<ServiceProviderEventWorkDTO> completedEventWorkDTOs = serviceProviderService.getCompletedEventsDetails(currentUser.getServiceProviderId().longValue());
			completedEventWorkDTOs.sort(Comparator.comparing(ServiceProviderEventWorkDTO::getEndDate));
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
	
	@GetMapping("/packages")
	public ModelAndView participatedPackages() {
		ModelAndView modelandmap = new ModelAndView("/serviceprovider/packages");
		CustomUserDetailsDTO customUserDetailsDTO = (CustomUserDetailsDTO) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		ServiceProviderDTO currentUser = DataAccessUtils.singleResult(serviceProviderService.findByNamedParameters(new MapSqlParameterSource().addValue("userDetailsId", customUserDetailsDTO.getUserDetailsId())));
		List<ServiceProviderPackageDTO> participatedPackages = serviceProviderService.getPackageDetails(currentUser.getServiceProviderId().longValue());
		List<String> venueAddresses = participatedPackages.stream().map(packageEntry -> {
			return getAddress(addressService.findById(packageEntry.getAddressId()));
		}).collect(Collectors.toList());
		List<AllServiceProvidersPackageDTO> allServiceProviders = serviceProviderService.getAllServiceProvidersPackageDetails(currentUser.getServiceProviderId().longValue());
		modelandmap.addObject("participatedPackages", participatedPackages);
		modelandmap.addObject("venueAddresses", venueAddresses);
		modelandmap.addObject("allServiceProviders", allServiceProviders);
		return modelandmap;
	}
	
	public String getAddress(AddressDTO addressDTO) {
		String address;
		address = addressDTO.getAddress1();
		if (addressDTO.getAddress2() != null) {
			address += ", " + addressDTO.getAddress2();
		}
		address += ",\n " + enuCityService.findById(addressDTO.getCityId().longValue()).getCity() + ", "
				+ enuStateService.findById(addressDTO.getStateId().longValue()).getState() + ", "
				+ enuCountryService.findById(addressDTO.getCountryId().longValue()).getCountry() + " - "
				+ addressDTO.getPostalCode();
		return address;
	}
	
	@GetMapping("/view_event/{eventId}")
	public ModelAndView viewEvent(@PathVariable long eventId) {
		ModelAndView modelandmap = new ModelAndView("/serviceprovider/view_event");
		CustomUserDetailsDTO customUserDetailsDTO = (CustomUserDetailsDTO) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		ServiceProviderDTO currentUser = DataAccessUtils.singleResult(serviceProviderService.findByNamedParameters(new MapSqlParameterSource().addValue("userDetailsId", customUserDetailsDTO.getUserDetailsId())));
		ServiceProviderEventWorkDTO event = serviceProviderService.getEventsDetails(eventId,currentUser.getServiceProviderId().longValue());
		modelandmap.addObject("addressVenue", getAddress(addressService.findById(event.getAddressId())));
		modelandmap.addObject("event", event);
		return modelandmap;
	}
	
	@GetMapping("/assigned-events")
	public ModelAndView assignedEvents() {
		ModelAndView modelandmap = new ModelAndView("/serviceprovider/assigned-events");
		CustomUserDetailsDTO customUserDetailsDTO = (CustomUserDetailsDTO) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		ServiceProviderDTO currentUser = DataAccessUtils.singleResult(serviceProviderService.findByNamedParameters(new MapSqlParameterSource().addValue("userDetailsId", customUserDetailsDTO.getUserDetailsId())));
		List<ServiceProviderEventWorkDTO> ongoingEventWorkDTOs = serviceProviderService.getOngoingEventsDetails(currentUser.getServiceProviderId().longValue());
		ongoingEventWorkDTOs.sort(Comparator.comparing(ServiceProviderEventWorkDTO::getStartDate));
		modelandmap.addObject("ongoingEventWorkDTOs", ongoingEventWorkDTOs);
		return modelandmap;
	}
	
	@GetMapping("/event-history")
	public ModelAndView eventHistory() {
		ModelAndView modelandmap = new ModelAndView("/serviceprovider/event-history");
		CustomUserDetailsDTO customUserDetailsDTO = (CustomUserDetailsDTO) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		ServiceProviderDTO currentUser = DataAccessUtils.singleResult(serviceProviderService.findByNamedParameters(new MapSqlParameterSource().addValue("userDetailsId", customUserDetailsDTO.getUserDetailsId())));
		List<ServiceProviderEventWorkDTO> completedEventWorkDTOs = serviceProviderService.getCompletedEventsDetails(currentUser.getServiceProviderId().longValue());
		completedEventWorkDTOs.sort(Comparator.comparing(ServiceProviderEventWorkDTO::getEndDate));
		modelandmap.addObject("completedEventWorkDTOs", completedEventWorkDTOs);
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
		CustomUserDetailsDTO customUserDetailsDTO = (CustomUserDetailsDTO) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		ServiceProviderDTO currentUser = DataAccessUtils.singleResult(serviceProviderService.findByNamedParameters(new MapSqlParameterSource().addValue("userDetailsId", customUserDetailsDTO.getUserDetailsId())));
		List<ShowFeedbackDTO> completedEventWorkDTOs = serviceProviderService.getFeedbackDetails(currentUser.getServiceProviderId().longValue());
		modelandmap.addObject("completedEventWorkDTOs", completedEventWorkDTOs);
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
	
	public BindingResult checkCustomerResults(@Valid @ModelAttribute("userDetailsDTO") UserDetailsDTO userDetailsDTO,
			BindingResult userResult) {
		// Valid Email
		final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(userDetailsDTO.getEmail());
		boolean isValidEmail = matcher.find();
		if (isValidEmail == false) {
			userResult.addError(new FieldError("userDetailsDTO", "email", "Please enter valid email address."));
		}

		// Unique Email
		List<UserDetailsDTO> emailDTO = userDetailsService.isUniqueEmail(userDetailsDTO.getEmail());
		if (emailDTO.isEmpty() != true) {
			if (emailDTO.get(0).getIsActive() == true) {
				userResult.addError(new FieldError("userDetailsDTO", "email", "Email address is already registered."));
			} else {
				userResult.addError(new FieldError("userDetailsDTO", "email",
						"Email address is already registered and account is deactivated.\nPlease contact us to active it."));
			}
		}

		// Password Rules
		String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
		boolean isValidPassword = userDetailsDTO.getPassword().matches(pattern);
		if (isValidPassword == false) {
			userResult.addError(
					new FieldError("userDetailsDTO", "password", "Please enter password according to rules."));
		}

		// Unique Mobile Number
		List<UserDetailsDTO> mobileNumberDTO = userDetailsService
				.isUniqueMobileNumber(userDetailsDTO.getMobileNumber());
		if (mobileNumberDTO.isEmpty() != true) {
			if (mobileNumberDTO.get(0).getIsActive() == true) {
				userResult.addError(
						new FieldError("userDetailsDTO", "mobileNumber", "Mobile Number is already registered."));
			} else {
				userResult.addError(new FieldError("userDetailsDTO", "mobileNumber",
						"Mobile Number is already registered and account is deactivated.\nPlease contact us to active it."));
			}
		}

		return userResult;
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
			@Valid @ModelAttribute("serviceProviderDTO") ServiceProviderDTO serviceProviderDTO, BindingResult userResult,
			@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO, BindingResult addressResult,
			ModelAndView modelandmap,RedirectAttributes rm) {
		userResult = checkCustomerResults(serviceProviderDTO, userResult);
		if (userResult.hasErrors() == true) {
			modelandmap = new ModelAndView("serviceProviderRegistration");
			modelandmap.addObject("serviceTypes", enuServiceTypeService.findAllActive());
			modelandmap.addObject("serviceProviderDTO", serviceProviderDTO);
			modelandmap.addObject("countries", enuCountryService.findAll());
			modelandmap.addObject("addressDTO", addressDTO);
			
			MapSqlParameterSource paramSourceCountry = new MapSqlParameterSource();
			paramSourceCountry.addValue("countryId", addressDTO.getCountryId());
			modelandmap.addObject("states", enuStateService.findByNamedParameters(paramSourceCountry));

			MapSqlParameterSource paramSourceState = new MapSqlParameterSource();
			paramSourceState.addValue("stateId", addressDTO.getStateId());
			modelandmap.addObject("cities", enuCityService.findByNamedParameters(paramSourceState));
			return modelandmap;
		}
		
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