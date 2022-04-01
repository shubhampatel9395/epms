package com.epms.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.epms.dto.AddressDTO;
import com.epms.dto.AdminDashboardDTO;
import com.epms.dto.AdminLatestActivityDTO;
import com.epms.dto.DonutDTO;
import com.epms.dto.EmployeeDTO;
import com.epms.dto.EnquiryDTO;
import com.epms.dto.EnuEnquiryStatusDTO;
import com.epms.dto.EnuEventTypeDTO;
import com.epms.dto.EnuServiceTypeDTO;
import com.epms.dto.EnuVenueFacilityDTO;
import com.epms.dto.EnuVenueTypeDTO;
import com.epms.dto.EventDTO;
import com.epms.dto.PackageDetailsDTO;
import com.epms.dto.PackageServiceProviderMappingDTO;
import com.epms.dto.PackageTempDTO;
import com.epms.dto.ServiceProviderDTO;
import com.epms.dto.UserDetailsDTO;
import com.epms.dto.VenueDTO;
import com.epms.dto.VenueEventTypeMappingDTO;
import com.epms.dto.VenueFacilityMappingDTO;
import com.epms.dto.VenueImageMappingDTO;
import com.epms.dto.VenueTempDTO;
import com.epms.email.configuration.IMailService;
import com.epms.email.configuration.Mail;
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

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
	@Autowired
	IEnuCityService enuCityService;

	@Autowired
	IEnquiryService enquiryService;

	@Autowired
	IEnuStateService enuStateService;

	@Autowired
	IUserDetailsService userDetailsService;

	@Autowired
	IServiceProviderService serviceProviderService;

	@Autowired
	IEnuServiceTypeService enuserviceTypeService;

	@Autowired
	IEnuCountryService enuCountryService;

	@Autowired
	IAddressService addressService;

	@Autowired
	IEnuServiceTypeService enuServiceTypeService;

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

	public List<AdminLatestActivityDTO> getLatestActivities() {
		List<AdminLatestActivityDTO> activityDTOs = new ArrayList<>();
		List<UserDetailsDTO> customerDTOs = userDetailsService
				.getLastDayData(new MapSqlParameterSource().addValue("isCustomer", true));
		List<UserDetailsDTO> serviceProviderDTOs = userDetailsService
				.getLastDayData(new MapSqlParameterSource().addValue("isServiceProvider", true));
		List<EventDTO> eventDTOs = eventService.getLastDayData();

		activityDTOs.addAll(customerDTOs.stream().map(customerDTO -> {
			return new AdminLatestActivityDTO("CUSTOMER", customerDTO.getFirstName() + " " + customerDTO.getLastName(),
					" registered in the system.", customerDTO.getCreatedAt());
		}).collect(Collectors.toList()));

		activityDTOs.addAll(serviceProviderDTOs.stream().map(serviceproviderDTO -> {
			return new AdminLatestActivityDTO("SERVICEPROVIDER", serviceproviderDTO.getServiceProviderName(),
					" registered in the system. Verify to give access.", serviceproviderDTO.getCreatedAt());
		}).collect(Collectors.toList()));

		activityDTOs.addAll(eventDTOs.stream().map(eventDTO -> {
			return new AdminLatestActivityDTO("EVENT", eventDTO.getEventTitle(),
					" registered in the system. Review it.", eventDTO.getCreatedAt());
		}).collect(Collectors.toList()));
		activityDTOs.sort(Collections.reverseOrder(Comparator.comparing(AdminLatestActivityDTO::getCreatedAt)));
		return activityDTOs;
	}

	@GetMapping("/dashboard")
	public ModelAndView homePage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return new ModelAndView("redirect:/login");
		} else {
			ModelAndView modelandmap = new ModelAndView("admin/index");

			AdminDashboardDTO adminDashboardDTO = new AdminDashboardDTO();
			List<AdminLatestActivityDTO> activityDTOs = getLatestActivities();
			adminDashboardDTO.customerCount = userDetailsService.getCustomerCount();
			adminDashboardDTO.serviceproviderCount = userDetailsService.getServiceproviderCount();
			adminDashboardDTO.eventCount = eventService.getCount();
			adminDashboardDTO.venueCount = venueService.getCount();

			modelandmap.addObject("activityDTOs", activityDTOs);
			modelandmap.addObject("adminDashboardDTO", adminDashboardDTO);
			modelandmap.addObject("upcomingEvents", eventService.getUpcomingWeekEvents());
			return modelandmap;
		}
	}

	@GetMapping("/list-customer")
	public ModelAndView listCustomer() {
		ModelAndView modelandmap = new ModelAndView("admin/customer");

		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("isCustomer", true);

		// List<UserDetailsDTO> customers = userDetailsService.findAllActive();
		List<UserDetailsDTO> customers = userDetailsService.findByNamedParameters(parameterSource);
		List<String> addresses = new ArrayList<>();
		for (int i = 0; i < customers.size(); i++) {
			addresses.add(getAddress(addressService.findById(customers.get(i).getAddressId().longValue())));
		}
		modelandmap.addObject("customers", customers);
		modelandmap.addObject("addresses", addresses);
		return modelandmap;
	}

	@GetMapping("/list-serviceprovider")
	public ModelAndView listServiceProvider() {
		ModelAndView modelandmap = new ModelAndView("admin/serviceprovider");
		List<ServiceProviderDTO> serviceProviders = serviceProviderService.findAll();
		List<UserDetailsDTO> userDetails = new ArrayList<>();
		List<String> serviceTypes = new ArrayList<>();
		List<String> addresses = new ArrayList<>();

		for (int i = 0; i < serviceProviders.size(); i++) {
			userDetails.add(userDetailsService.findById(serviceProviders.get(i).getUserDetailsId().longValue()));
		}

		for (int i = 0; i < serviceProviders.size(); i++) {
			serviceTypes.add(enuserviceTypeService.findById(serviceProviders.get(i).getServiceTypeId().longValue())
					.getService());
		}

		for (int i = 0; i < userDetails.size(); i++) {
			addresses.add(getAddress(addressService.findById(userDetails.get(i).getAddressId().longValue())));
		}

		modelandmap.addObject("serviceProviders", serviceProviders);
		modelandmap.addObject("userDetails", userDetails);
		modelandmap.addObject("serviceTypes", serviceTypes);
		modelandmap.addObject("addresses", addresses);
		return modelandmap;
	}

	@GetMapping("/list-employee")
	public ModelAndView listEmployee() {
		ModelAndView modelandmap = new ModelAndView("admin/employee");
		List<EmployeeDTO> employees = employeeService.findAll();
		List<UserDetailsDTO> userDetails = employees.stream().map(employee -> {
			return userDetailsService.findById(employee.getUserDetailsId().longValue());
		}).collect(Collectors.toList());

		modelandmap.addObject("employees", employees);
		modelandmap.addObject("userDetails", userDetails);
		return modelandmap;
	}

	public String getVenueFacilities(Long venueId) {
		List<VenueFacilityMappingDTO> venueFacilities = venueFacilityMappingService
				.findByNamedParameters(new MapSqlParameterSource().addValue("venueId", venueId));
		String venueFacilitiesString = "";

		for (VenueFacilityMappingDTO item : venueFacilities) {
			venueFacilitiesString += "\n- "
					+ enuVenueFacilityService.findById(item.getFacilityId().longValue()).getFacility();
		}

		if (venueFacilitiesString == "") {
			return venueFacilitiesString;
		} else {
			return venueFacilitiesString.substring(1);
		}
	}

	public String getVenueEventTypes(Long venueId) {
		List<VenueEventTypeMappingDTO> venueEventTypes = venueEventTypeMappingService
				.findByNamedParameters(new MapSqlParameterSource().addValue("venueId", venueId));
		String venueEventTypesString = "";

		for (VenueEventTypeMappingDTO item : venueEventTypes) {
			venueEventTypesString += "\n- "
					+ enuEventTypeService.findById(item.getEventTypeId().longValue()).getEventType();
		}

		if (venueEventTypesString == "") {
			return venueEventTypesString;
		} else {
			return venueEventTypesString.substring(1);
		}
	}

	@GetMapping("/list-venue")
	public ModelAndView listVenue() {
		ModelAndView modelandmap = new ModelAndView("admin/venue");
		List<VenueDTO> venues = venueService.findAll();
		List<String> addresses = new ArrayList<>();
		List<String> venueTypes = new ArrayList<>();
//		List<String> venueFacilities = new ArrayList<>();
//		List<String> venueEventTypes = new ArrayList<>();

		for (int i = 0; i < venues.size(); i++) {
			addresses.add(getAddress(addressService.findById(venues.get(i).getAddressId().longValue())));
			venueTypes.add(enuVenueTypeService.findById(venues.get(i).getVenueTypeId().longValue()).getVenueType());
//			venueFacilities.add(getVenueFacilities(venues.get(i).getVenueId()));
//			venueEventTypes.add(getVenueEventTypes(venues.get(i).getVenueId()));
		}

		modelandmap.addObject("venues", venues);
		modelandmap.addObject("addresses", addresses);
//		modelandmap.addObject("venueFacilities", venueFacilities);
//		modelandmap.addObject("venueEventTypes", venueEventTypes);
		modelandmap.addObject("venueTypes", venueTypes);
		return modelandmap;
	}

	@GetMapping("/list-package")
	public ModelAndView listPackage() {
		ModelAndView modelandmap = new ModelAndView("admin/package");
		List<PackageDetailsDTO> packages = packageDetailsService
				.findByNamedParameters(new MapSqlParameterSource().addValue("isStatic", true));
		List<String> availableForEventType = packages.stream().map(packageDetails -> {
			return enuEventTypeService.findById(packageDetails.getEventTypeId().longValue()).getEventType();
		}).collect(Collectors.toList());
		List<String> availableInVenue = packages.stream().map(packageDetails -> {
			return venueService.findById(packageDetails.getVenueId().longValue()).getVenueName();
		}).collect(Collectors.toList());

		modelandmap.addObject("packages", packages);
		modelandmap.addObject("availableForEventType", availableForEventType);
		modelandmap.addObject("availableInVenue", availableInVenue);
		return modelandmap;
	}

	@GetMapping("/list-payment")
	public ModelAndView listPayment() {
		ModelAndView modelandmap = new ModelAndView("admin/payment");
		return modelandmap;
	}

	@GetMapping("/authenticate-serviceprovider/{serviceProviderId}")
	public ModelAndView authenticateServiceprovider(@PathVariable("serviceProviderId") long serviceProviderId) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-serviceprovider");
		userDetailsService.activate(serviceProviderService.findById(serviceProviderId).getUserDetailsId().longValue());
		serviceProviderService.authenticate(serviceProviderId);

		Mail mail = new Mail();
		mail.setMailTo(userDetailsService
				.findById(serviceProviderService.findById(serviceProviderId).getUserDetailsId().longValue())
				.getEmail());
		mail.setMailSubject("Authentication");
		mail.setContentType("text/html");
		String content = "<p>You have been authorized to use your Unico - Event Planning and Management profile.</p>";
		mail.setMailContent(content);
		mailService.sendEmail(mail);

		return modelandmap;
	}

	@GetMapping("/activate_customer/{customerId}")
	public ModelAndView activateCustomer(@PathVariable("customerId") long userDetailsId) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-customer");

		UserDetailsDTO userDetailsDTO = userDetailsService.findById(userDetailsId);
		addressService.activate(userDetailsDTO.getAddressId().longValue());
		userDetailsService.activate(userDetailsDTO.getUserDetailsId().longValue());

		return modelandmap;
	}

	@GetMapping("/activate_serviceprovider/{serviceProviderId}")
	public ModelAndView activateServiceProvider(@PathVariable("serviceProviderId") long serviceProviderId) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-serviceprovider");

		ServiceProviderDTO serviceProviderDTO = serviceProviderService.findById(serviceProviderId);
		UserDetailsDTO userDetailsDTO = userDetailsService.findById(serviceProviderDTO.getUserDetailsId().longValue());
		addressService.activate(userDetailsDTO.getAddressId().longValue());
		userDetailsService.activate(serviceProviderDTO.getUserDetailsId().longValue());
		serviceProviderService.authenticate(serviceProviderId);

		return modelandmap;
	}

	@GetMapping("/activate_employee/{employeeId}")
	public ModelAndView activateEmployee(@PathVariable("employeeId") long employeeId) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-employee");
		EmployeeDTO employeeDTO = employeeService.findById(employeeId);
		UserDetailsDTO userDetailsDTO = userDetailsService.findById(employeeDTO.getUserDetailsId().longValue());

		addressService.activate(userDetailsDTO.getAddressId().longValue());
		userDetailsService.activate(employeeDTO.getUserDetailsId().longValue());
		employeeService.activate(employeeId);
		return modelandmap;
	}

	@GetMapping("/activate_venue/{venueId}")
	public ModelAndView activateVenue(@PathVariable("venueId") long venueId) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-venue");
		VenueDTO venueDTO = venueService.findById(venueId);

		addressService.activate(venueDTO.getAddressId().longValue());
		venueService.activate(venueId);
		venueFacilityMappingService.activate(venueId);
		venueEventTypeMappingService.activate(venueId);
		return modelandmap;
	}

	@GetMapping("/activate_package/{packageDetailsId}")
	public ModelAndView activatePackage(@PathVariable("packageDetailsId") long packageDetailsId) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-package");
		packageDetailsService.activate(packageDetailsId);
		packageServiceProviderMappingService.activate(packageDetailsId);
		return modelandmap;
	}

	@GetMapping("/add_customer")
	public ModelAndView addCustomer() {
		ModelAndView modelandmap = new ModelAndView("admin/add_customer");
		modelandmap.addObject("countries", enuCountryService.findAll());

		// TODO make form object
		modelandmap.addObject("userDetailsDTO", new UserDetailsDTO());
		modelandmap.addObject("addressDTO", new AddressDTO());
		return modelandmap;
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

		// 10 digit Mobile Number
		if (userDetailsDTO.getMobileNumber().length() != 10) {
			userResult
					.addError(new FieldError("userDetailsDTO", "mobileNumber", "Please enter 10 digit mobile number."));
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

	@PostMapping("/add_customer")
	public ModelAndView addNewCustomer(@Valid @ModelAttribute("userDetailsDTO") UserDetailsDTO userDetailsDTO,
			BindingResult userResult, @Valid @ModelAttribute("addressDTO") AddressDTO addressDTO,
			BindingResult addressResult) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-customer");

		userResult = checkCustomerResults(userDetailsDTO, userResult);
		if (userResult.hasErrors() == true) {
			modelandmap = new ModelAndView("admin/add_customer");
			modelandmap.addObject("userDetailsDTO", userDetailsDTO);
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

		userDetailsDTO.setAddressId(addressService.insert(addressDTO).getAddressId());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(userDetailsDTO.getPassword());
		userDetailsDTO.setPassword(encodedPassword);
		userDetailsService.insert(userDetailsDTO);
		// modelandmap.addObject("userDetailsDTO", insertUserDetailsDTO);
		return modelandmap;
	}

	@GetMapping("/add_serviceprovider")
	public ModelAndView addServiceProvider() {
		ModelAndView modelandmap = new ModelAndView("admin/add_serviceprovider");
		// TODO make form object
		modelandmap.addObject("countries", enuCountryService.findAll());
		modelandmap.addObject("serviceTypes", enuServiceTypeService.findAllActive());
		modelandmap.addObject("serviceProviderDTO", new ServiceProviderDTO());
		modelandmap.addObject("addressDTO", new AddressDTO());
		return modelandmap;
	}

	@PostMapping("/add_serviceprovider")
	public ModelAndView addNewServiceProvider(
			@Valid @ModelAttribute("serviceProviderDTO") ServiceProviderDTO serviceProviderDTO,
			BindingResult userResult, @Valid @ModelAttribute("addressDTO") AddressDTO addressDTO,
			BindingResult addressResult) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-serviceprovider");

		userResult = checkCustomerResults(serviceProviderDTO, userResult);
		if (userResult.hasErrors() == true) {
			modelandmap = new ModelAndView("admin/add_serviceprovider");
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

		serviceProviderDTO.setAddressId(addressService.insert(addressDTO).getAddressId());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(serviceProviderDTO.getPassword());
		serviceProviderDTO.setPassword(encodedPassword);
		serviceProviderService.insert(serviceProviderDTO);
		return modelandmap;
	}

	@GetMapping("/add_employee")
	public ModelAndView addEmployee() {
		ModelAndView modelandmap = new ModelAndView("admin/add_employee");
		modelandmap.addObject("countries", enuCountryService.findAll());
		modelandmap.addObject("employeeRoles", enuEmployeeRoleService.findAll());
		modelandmap.addObject("employeeDTO", new EmployeeDTO());
		modelandmap.addObject("addressDTO", new AddressDTO());
		return modelandmap;
	}

	@PostMapping("/add_employee")
	public ModelAndView addNewEmployee(@Valid @ModelAttribute("employeeDTO") EmployeeDTO employeeDTO,
			BindingResult userResult, @Valid @ModelAttribute("addressDTO") AddressDTO addressDTO,
			BindingResult addressResult) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-employee");

		userResult = checkCustomerResults(employeeDTO, userResult);
		if (userResult.hasErrors() == true) {
			modelandmap = new ModelAndView("admin/add_employee");
			modelandmap.addObject("employeeRoles", enuEmployeeRoleService.findAll());
			employeeDTO.setHiringDate(null);
			employeeDTO.setDOB(null);
			modelandmap.addObject("employeeDTO", employeeDTO);
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

		employeeDTO.setAddressId(addressService.insert(addressDTO).getAddressId());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(employeeDTO.getPassword());
		employeeDTO.setPassword(encodedPassword);
		employeeService.insert(employeeDTO);
		return modelandmap;
	}

	@GetMapping("/add_venue")
	public ModelAndView addVenue() {
		ModelAndView modelandmap = new ModelAndView("admin/add_venue");

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("isActive", true);
		List<EnuEventTypeDTO> eventTypes = enuEventTypeService.findByNamedParameters(paramSource);
		List<EnuVenueFacilityDTO> facilities = enuVenueFacilityService.findByNamedParameters(paramSource);
		List<EnuVenueTypeDTO> venueTypes = enuVenueTypeService.findByNamedParameters(paramSource);
//		List<String> selectedEventTypes = new ArrayList<String>();
//		List<String> selectedFacilities = new ArrayList<String>();

		modelandmap.addObject("facilities", facilities);
		modelandmap.addObject("eventTypes", eventTypes);
		modelandmap.addObject("venueTypes", venueTypes);
		modelandmap.addObject("venueDTO", new VenueDTO());
		modelandmap.addObject("addressDTO", new AddressDTO());
		modelandmap.addObject("venueTempDTO", new VenueTempDTO());
//		modelandmap.addObject("selectedEventTypes", selectedEventTypes);
//		modelandmap.addObject("selectedFacilities", selectedFacilities);
		modelandmap.addObject("countries", enuCountryService.findAll());

		return modelandmap;
	}

	public BindingResult checkVenueResults(@Valid @ModelAttribute("venueDTO") VenueDTO venueDTO,
			BindingResult venueResult) {
		// Valid Email
		final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(venueDTO.getEmail());
		boolean isValidEmail = matcher.find();
		if (isValidEmail == false) {
			venueResult.addError(new FieldError("venueDTO", "email", "Please enter valid email address."));
		}

		// Unique Email
		List<VenueDTO> emailDTO = venueService
				.findByNamedParameters(new MapSqlParameterSource().addValue("email", venueDTO.getEmail()));
		if (emailDTO.isEmpty() != true) {
			if (emailDTO.get(0).getIsActive() == true) {
				venueResult.addError(new FieldError("venueDTO", "email", "Email address is already registered."));
			} else {
				venueResult.addError(new FieldError("venueDTO", "email",
						"Email address is already registered and account is deactivated.\nPlease contact us to active it."));
			}
		}

		// 10 digit Mobile Number
		if (venueDTO.getContactNumber().length() != 10) {
			venueResult.addError(new FieldError("venueDTO", "contactNumber", "Please enter 10 digit mobile number."));
		}

		// Unique Mobile Number
		List<VenueDTO> mobileNumberDTO = venueService.findByNamedParameters(
				new MapSqlParameterSource().addValue("contactNumber", venueDTO.getContactNumber()));
		if (mobileNumberDTO.isEmpty() != true) {
			if (mobileNumberDTO.get(0).getIsActive() == true) {
				venueResult
						.addError(new FieldError("venueDTO", "contactNumber", "Mobile Number is already registered."));
			} else {
				venueResult.addError(new FieldError("venueDTO", "contactNumber",
						"Mobile Number is already registered and account is deactivated.\nPlease contact us to active it."));
			}
		}

		return venueResult;
	}

	public BindingResult checkVenueResultsEdit(@Valid @ModelAttribute("venueDTO") VenueDTO venueDTO,
			BindingResult venueResult) {
		// Valid Email
		final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(venueDTO.getEmail());
		boolean isValidEmail = matcher.find();
		if (isValidEmail == false) {
			venueResult.addError(new FieldError("venueDTO", "email", "Please enter valid email address."));
		}

		// Unique Email
		List<VenueDTO> emailDTO = venueService
				.findByNamedParameters(new MapSqlParameterSource().addValue("email", venueDTO.getEmail()));
		if (emailDTO.isEmpty() != true) {
			if (!(emailDTO.get(0).getVenueId().equals(venueDTO.getVenueId()))) {
				if (emailDTO.get(0).getIsActive() == true) {
					venueResult.addError(new FieldError("venueDTO", "email", "Email address is already registered."));
				} else {
					venueResult.addError(new FieldError("venueDTO", "email",
							"Email address is already registered and account is deactivated.\nPlease contact us to active it."));
				}
			}
		}

		// 10 digit Mobile Number
		if (venueDTO.getContactNumber().length() != 10) {
			venueResult.addError(new FieldError("venueDTO", "contactNumber", "Please enter 10 digit mobile number."));
		}

		// Unique Mobile Number
		List<VenueDTO> mobileNumberDTO = venueService.findByNamedParameters(
				new MapSqlParameterSource().addValue("contactNumber", venueDTO.getContactNumber()));
		if (mobileNumberDTO.isEmpty() != true) {
			if (!(mobileNumberDTO.get(0).getVenueId().equals(venueDTO.getVenueId()))) {
				if (mobileNumberDTO.get(0).getIsActive() == true) {
					venueResult.addError(
							new FieldError("venueDTO", "contactNumber", "Mobile Number is already registered."));
				} else {
					venueResult.addError(new FieldError("venueDTO", "contactNumber",
							"Mobile Number is already registered and account is deactivated.\nPlease contact us to active it."));
				}
			}
		}

		return venueResult;
	}

	@PostMapping("/add_venue")
	public ModelAndView addNewVenue(@Valid @ModelAttribute("venueDTO") VenueDTO venueDTO, BindingResult venueResult,
			@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO,
			@Valid @ModelAttribute("venueTempDTO") VenueTempDTO venueTempDTO,
			@RequestParam("files") MultipartFile[] files) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-venue");

		venueResult = checkVenueResults(venueDTO, venueResult);
		if (venueResult.hasErrors() == true) {
			modelandmap = new ModelAndView("admin/add_venue");

			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("isActive", true);
			List<EnuEventTypeDTO> eventTypes = enuEventTypeService.findByNamedParameters(paramSource);
			List<EnuVenueFacilityDTO> facilities = enuVenueFacilityService.findByNamedParameters(paramSource);
			List<EnuVenueTypeDTO> venueTypes = enuVenueTypeService.findByNamedParameters(paramSource);

			modelandmap.addObject("facilities", facilities);
			modelandmap.addObject("eventTypes", eventTypes);
			modelandmap.addObject("venueTypes", venueTypes);
			modelandmap.addObject("venueDTO", venueDTO);
			modelandmap.addObject("addressDTO", addressDTO);
			modelandmap.addObject("venueTempDTO", venueTempDTO);
			modelandmap.addObject("countries", enuCountryService.findAll());
			MapSqlParameterSource paramSourceCountry = new MapSqlParameterSource();
			paramSourceCountry.addValue("countryId", addressDTO.getCountryId());
			modelandmap.addObject("states", enuStateService.findByNamedParameters(paramSourceCountry));

			MapSqlParameterSource paramSourceState = new MapSqlParameterSource();
			paramSourceState.addValue("stateId", addressDTO.getStateId());
			modelandmap.addObject("cities", enuCityService.findByNamedParameters(paramSourceState));

			return modelandmap;
		}

		venueDTO.setAddressId(addressService.insert(addressDTO).getAddressId());
		VenueDTO insertedDTO = venueService.insert(venueDTO);
		venueFacilityMappingService.insert(insertedDTO.getVenueId().longValue(), venueTempDTO.getSelectedFacilities());
		venueEventTypeMappingService.insert(insertedDTO.getVenueId().longValue(), venueTempDTO.getSelectedEventTypes());

		for (MultipartFile file : files) {
			if (!file.isEmpty()) {
				VenueImageMappingDTO obj = new VenueImageMappingDTO();
				try {
					obj.setImage(new SerialBlob(file.getBytes()));
					obj.setVenueId(insertedDTO.getVenueId());
				} catch (SerialException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				venueImageMappingService.insert(obj);
			}
		}

		return modelandmap;
	}

	@GetMapping("/getVenueCost/{venueId}")
	public Double getVenueCost(@PathVariable Long venueId) {
		// System.out.println(venueService.findById(venueId).getCost());
		return venueService.findById(venueId).getCost();
	}

	@GetMapping("/getServiceProviderCost/{serviceProviderId}")
	public Double getServiceProviderCost(@PathVariable Long serviceProviderId) {
		// System.out.println(serviceProviderService.findById(serviceProviderId).getCost());
		return serviceProviderService.findById(serviceProviderId).getCost();
	}

	@PostMapping("/getPackageCost")
	public double getPackageCost(@RequestBody List<String> packageCost) {
		// System.out.println(packageCost);
		double cost = 0;
		cost += venueService.findById(Long.parseLong(packageCost.get(0))).getCost();
		// System.out.println(cost);
		for (int i = 1; i < packageCost.size() - 1; i++) {
			cost += serviceProviderService.findById(Long.parseLong(packageCost.get(i))).getCost();
			// System.out.println(cost);
		}
		cost += Long.parseLong(packageCost.get(packageCost.size() - 1));
		// System.out.println(cost);

		return cost;
	}

	@GetMapping("/getVenues/{eventTypeId}")
	public List<VenueDTO> getVenuesOnEventType(@PathVariable Long eventTypeId) {
		List<VenueEventTypeMappingDTO> listVenues = venueEventTypeMappingService
				.findByNamedParameters(new MapSqlParameterSource().addValue("eventTypeId", eventTypeId));
		List<VenueDTO> venues = new ArrayList<>();

		for (int i = 0; i < listVenues.size(); i++) {
			venues.add(venueService.findById(listVenues.get(i).getVenueId().longValue()));
		}

		return venues;
	}

	@GetMapping("/add_package")
	public ModelAndView addPackage() {
		ModelAndView modelandmap = new ModelAndView("admin/add_package");
		PackageDetailsDTO packageDetailsDTO = new PackageDetailsDTO();
		packageDetailsDTO.setIsStatic(true);
		modelandmap.addObject("packageDetailsDTO", packageDetailsDTO);
		List<EnuServiceTypeDTO> serviceTypes = enuServiceTypeService.findAllActive();
		List<ServiceProviderDTO> serviceProviders = serviceProviderService.findAllActive();

		for (int i = 0; i < serviceProviders.size(); i++) {
			serviceProviders.get(i)
					.setServiceProviderName(
							userDetailsService
									.findByNamedParameters(new MapSqlParameterSource().addValue("userDetailsId",
											serviceProviders.get(i).getUserDetailsId()))
									.get(0).getServiceProviderName());
		}

		modelandmap.addObject("serviceTypes", serviceTypes);
		modelandmap.addObject("serviceProviders", serviceProviders);
		modelandmap.addObject("venueNames",
				venueService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		modelandmap.addObject("eventTypes",
				enuEventTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		modelandmap.addObject("serviceTypes",
				enuServiceTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		modelandmap.addObject("packageTempDTO", new PackageTempDTO());
		return modelandmap;
	}

	@PostMapping("/add_package")
	public ModelAndView addNewPackage(@Valid @ModelAttribute("packageDetailsDTO") PackageDetailsDTO packageDetailsDTO,
			@Valid @ModelAttribute("packageTempDTO") PackageTempDTO packageTempDTO) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-package");
		Long packageId = packageDetailsService.insert(packageDetailsDTO).getPackageDetailsId().longValue();
		if (packageTempDTO.getServiceProviderIdList() != null) {
			packageServiceProviderMappingService.insert(packageId, packageTempDTO.getServiceProviderIdList());
		}
		return modelandmap;
	}

	@GetMapping("getPackagesOfEvent/{eventTypeId}")
	public ModelAndView packagesByEventType(ModelAndView modelandmap, @PathVariable long eventTypeId) {
		List<PackageDetailsDTO> packageDetailsDTO;
		if (eventTypeId == -1) {
			packageDetailsDTO = packageDetailsService.findByNamedParameters(
					new MapSqlParameterSource().addValue("isActive", true).addValue("isStatic", true));
			modelandmap.setViewName("fragments :: resultsPackageEvent");
		} else {
			packageDetailsDTO = packageDetailsService.findByNamedParameters(new MapSqlParameterSource()
					.addValue("isActive", true).addValue("eventTypeId", eventTypeId).addValue("isStatic", true));
		}

		if (packageDetailsDTO.size() == 0) {
			modelandmap.setViewName("fragments :: resultsPackageEvent1");
		} else {
			modelandmap.setViewName("fragments :: resultsPackageEvent");
		}

//		List<PackageServiceProviderMappingDTO> packageServiceProviderMappings = packageServiceProviderMappingService
//				.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true));

		List<String> venueDetails = packageDetailsDTO.stream().map(packageDTO -> {
			return venueService.findById(packageDTO.getVenueId().longValue()).getVenueName() + ", "
					+ getAddress(addressService.findById(
							venueService.findById(packageDTO.getVenueId().longValue()).getAddressId().longValue()));
		}).collect(Collectors.toList());

		List<String> eventType = packageDetailsDTO.stream().map(packageDTO -> {
			return enuEventTypeService.findById(packageDTO.getEventTypeId().longValue()).getEventType();
		}).collect(Collectors.toList());

		List<Map<String, String>> serviceWithProviders = packageDetailsDTO.stream().map(item -> {
			Map<String, String> t = new HashMap<String, String>();
			List<PackageServiceProviderMappingDTO> packageServiceProviderMappings = packageServiceProviderMappingService
					.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true).addValue("packageId",
							item.getPackageDetailsId()));
			packageServiceProviderMappings.stream().map(item2 -> {
				ServiceProviderDTO temp = serviceProviderService.findById(item2.getServiceProviderId().longValue());
				return t.put(enuServiceTypeService.findById(temp.getServiceTypeId().longValue()).getService(),
						userDetailsService.findById(temp.getUserDetailsId().longValue()).getServiceProviderName());
			}).collect(Collectors.toList());
			return t;
		}).collect(Collectors.toList());

		modelandmap.addObject("eventDTO", new EventDTO());
		modelandmap.addObject("venueDetails", venueDetails);
		modelandmap.addObject("eventNames",
				enuEventTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		modelandmap.addObject("eventTypes", eventType);
		modelandmap.addObject("packageDetailsDTOs", packageDetailsDTO);
		modelandmap.addObject("serviceWithProviders", serviceWithProviders);
		// modelandmap.setViewName("fragments :: resultsList");
		return modelandmap;
	}

	@GetMapping("/view_customer/{customerId}")
	public ModelAndView viewCustomer(@PathVariable("customerId") long userDetailsId) {
		ModelAndView modelandmap = new ModelAndView("admin/view_customer");
		UserDetailsDTO userDetailsDTO = userDetailsService.findById(userDetailsId);
		modelandmap.addObject("customer", userDetailsDTO);
		AddressDTO addressDTO = addressService.findById(userDetailsDTO.getAddressId().longValue());
		modelandmap.addObject("address", getAddress(addressDTO));
		return modelandmap;
	}

	@GetMapping("/view_serviceprovider/{serviceProviderId}")
	public ModelAndView viewServiceProvider(@PathVariable("serviceProviderId") long serviceProviderId) {
		ModelAndView modelandmap = new ModelAndView("admin/view_serviceprovider");

		ServiceProviderDTO serviceProviderDTO = serviceProviderService.findById(serviceProviderId);
		UserDetailsDTO userDetailsDTO = userDetailsService.findById(serviceProviderDTO.getUserDetailsId().longValue());
		String serviceType = enuserviceTypeService.findById(serviceProviderDTO.getServiceTypeId().longValue())
				.getService();
		String address = getAddress(addressService.findById(userDetailsDTO.getAddressId().longValue()));

		modelandmap.addObject("serviceProviderDTO", serviceProviderDTO);
		modelandmap.addObject("userDetailsDTO", userDetailsDTO);
		modelandmap.addObject("serviceType", serviceType);
		modelandmap.addObject("address", address);

		return modelandmap;
	}

	@GetMapping("/view_employee/{employeeId}")
	public ModelAndView viewEmployee(@PathVariable("employeeId") long employeeId) {
		ModelAndView modelandmap = new ModelAndView("admin/view_employee");

		EmployeeDTO employeeDTO = employeeService.findById(employeeId);
		UserDetailsDTO userDetailsDTO = userDetailsService.findById(employeeDTO.getUserDetailsId().longValue());
		String address = getAddress(addressService.findById(userDetailsDTO.getAddressId().longValue()));
		String employeeRole = enuEmployeeRoleService.findById(employeeDTO.getEmployeeRoleId().longValue()).getRole();

		modelandmap.addObject("employeeDTO", employeeDTO);
		modelandmap.addObject("userDetailsDTO", userDetailsDTO);
		modelandmap.addObject("address", address);
		modelandmap.addObject("employeeRole", employeeRole);

		return modelandmap;
	}

	@RequestMapping(value = "/image/{image_id}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getImage(@PathVariable("image_id") Long imageId) throws IOException {
		Blob img = venueImageMappingService.findById(imageId).getImage();
		byte[] imageContent = null;
		try {
			imageContent = img.getBytes(1, (int) img.length());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
	}

	@GetMapping("/view_venue/{venueId}")
	public ModelAndView viewVenue(@PathVariable("venueId") long venueId) {
		ModelAndView modelandmap = new ModelAndView("admin/view_venue");

		VenueDTO venueDTO = venueService.findById(venueId);
		String address = getAddress(addressService.findById(venueDTO.getAddressId().longValue()));
		String venueType = enuVenueTypeService.findById(venueDTO.getVenueTypeId().longValue()).getVenueType();
		String venueFacilities;
		String venueEventTypes;
		List<VenueImageMappingDTO> images = venueImageMappingService
				.findByNamedParameters(new MapSqlParameterSource().addValue("venueId", venueId));

//		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		venueFacilities = getVenueFacilities(venueDTO.getVenueId().longValue());
		venueEventTypes = getVenueEventTypes(venueDTO.getVenueId().longValue());

		modelandmap.addObject("venue", venueDTO);
		modelandmap.addObject("address", address);
		modelandmap.addObject("venueFacilities", venueFacilities);
		modelandmap.addObject("venueEventTypes", venueEventTypes);
		modelandmap.addObject("venueType", venueType);
		modelandmap.addObject("images", images);

		return modelandmap;
	}

	@GetMapping("/view_package/{packageDetailsId}")
	public ModelAndView viewPackage(@PathVariable("packageDetailsId") long packageDetailsId) {
		ModelAndView modelandmap = new ModelAndView("admin/view_package");

		PackageDetailsDTO packageDetailsDTO = packageDetailsService.findById(packageDetailsId);
		List<PackageServiceProviderMappingDTO> packageServiceProviderMappings = packageServiceProviderMappingService
				.findByNamedParameters(new MapSqlParameterSource().addValue("packageId", packageDetailsId));
		Map<String, String> serviceWithProviders = new HashMap<String, String>();
		for (PackageServiceProviderMappingDTO entry : packageServiceProviderMappings) {
			ServiceProviderDTO temp = serviceProviderService.findById(entry.getServiceProviderId().longValue());
			serviceWithProviders.put(enuServiceTypeService.findById(temp.getServiceTypeId().longValue()).getService(),
					userDetailsService.findById(temp.getUserDetailsId().longValue()).getServiceProviderName());
		}
		String venueDetails = venueService.findById(packageDetailsDTO.getVenueId().longValue()).getVenueName() + ", "
				+ getAddress(addressService.findById(
						venueService.findById(packageDetailsDTO.getVenueId().longValue()).getAddressId().longValue()));
		String eventType = enuEventTypeService.findById(packageDetailsDTO.getEventTypeId().longValue()).getEventType();

		modelandmap.addObject("venueDetails", venueDetails);
		modelandmap.addObject("eventType", eventType);
		modelandmap.addObject("packageDetailsDTO", packageDetailsDTO);
		modelandmap.addObject("serviceWithProviders", serviceWithProviders);
		// System.out.println(serviceWithProviders);
		return modelandmap;
	}

	@GetMapping("/delete_customer/{customerId}")
	public ModelAndView deleteCustomer(@PathVariable("customerId") long userDetailsId) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-customer");
		UserDetailsDTO userDetailsDTO = userDetailsService.findById(userDetailsId);

		addressService.delete(userDetailsDTO.getAddressId().longValue());
		userDetailsService.delete(userDetailsId);

		return modelandmap;
	}

	@GetMapping("/delete_serviceprovider/{serviceProviderId}")
	public ModelAndView deleteServiceProvider(@PathVariable("serviceProviderId") long serviceProviderId) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-serviceprovider");
		UserDetailsDTO userDetailsDTO = userDetailsService
				.findById(serviceProviderService.findById(serviceProviderId).getUserDetailsId().longValue());

		addressService.delete(userDetailsDTO.getAddressId().longValue());
		userDetailsService.delete(userDetailsDTO.getUserDetailsId().longValue());
		serviceProviderService.delete(serviceProviderId);

		return modelandmap;
	}

	@GetMapping("/delete_employee/{employeeId}")
	public ModelAndView deleteEmployee(@PathVariable("employeeId") long employeeId) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-employee");
		UserDetailsDTO userDetailsDTO = userDetailsService
				.findById(employeeService.findById(employeeId).getUserDetailsId().longValue());

		addressService.delete(userDetailsDTO.getAddressId().longValue());
		userDetailsService.delete(userDetailsDTO.getUserDetailsId().longValue());
		employeeService.delete(employeeId);

		return modelandmap;
	}

	@GetMapping("/delete_venue/{venueId}")
	public ModelAndView deleteVenue(@PathVariable("venueId") long venueId) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-venue");
		VenueDTO venueDTO = venueService.findById(venueId);

		addressService.activate(venueDTO.getAddressId().longValue());
		venueService.delete(venueId);
		venueFacilityMappingService.delete(venueId);
		venueEventTypeMappingService.delete(venueId);
		return modelandmap;
	}

	@GetMapping("/delete_package/{packageDetailsId}")
	public ModelAndView deletePackage(@PathVariable("packageDetailsId") long packageDetailsId) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-package");
		packageDetailsService.delete(packageDetailsId);
		packageServiceProviderMappingService.delete(packageDetailsId);
		return modelandmap;
	}

	@GetMapping("/edit_customer/{customerId}")
	public ModelAndView editCustomer(@PathVariable("customerId") long userDetailsId) {
		ModelAndView modelandmap = new ModelAndView("admin/edit_customer");
		// modelandmap.addObject("cities", enuCountryService.findAll());

		// TODO make form object
		UserDetailsDTO userDetailsDTO = userDetailsService.findById(userDetailsId);
		AddressDTO addressDTO = addressService.findById(userDetailsDTO.getAddressId().longValue());

		modelandmap.addObject("userDetailsDTO", userDetailsDTO);
		modelandmap.addObject("addressDTO", addressDTO);

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		modelandmap.addObject("countries", enuCountryService.findAll());
		paramSource.addValue("countryId", addressDTO.getCountryId());
		modelandmap.addObject("states", enuStateService.findByNamedParameters(paramSource));

		paramSource = new MapSqlParameterSource();
		paramSource.addValue("stateId", addressDTO.getStateId());
		modelandmap.addObject("cities", enuCityService.findByNamedParameters(paramSource));
		return modelandmap;
	}

	public BindingResult checkCustomerResultsEdit(
			@Valid @ModelAttribute("userDetailsDTO") UserDetailsDTO userDetailsDTO, BindingResult userResult) {
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
			if (!(emailDTO.get(0).getUserDetailsId().equals(userDetailsDTO.getUserDetailsId()))) {
				if (emailDTO.get(0).getIsActive() == true) {
					userResult.addError(
							new FieldError("userDetailsDTO", "email", "Email address is already registered."));
				} else {
					userResult.addError(new FieldError("userDetailsDTO", "email",
							"Email address is already registered and account is deactivated.\nPlease contact us to active it."));
				}
			}
		}

		// 10 digit Mobile Number
		if (userDetailsDTO.getMobileNumber().length() != 10) {
			userResult
					.addError(new FieldError("userDetailsDTO", "mobileNumber", "Please enter 10 digit mobile number."));
		}

		// Unique Mobile Number
		List<UserDetailsDTO> mobileNumberDTO = userDetailsService
				.isUniqueMobileNumber(userDetailsDTO.getMobileNumber());
		if (mobileNumberDTO.isEmpty() != true) {
			if (!(mobileNumberDTO.get(0).getUserDetailsId().equals(userDetailsDTO.getUserDetailsId()))) {
				if (mobileNumberDTO.get(0).getIsActive() == true) {
					userResult.addError(
							new FieldError("userDetailsDTO", "mobileNumber", "Mobile Number is already registered."));
				} else {
					userResult.addError(new FieldError("userDetailsDTO", "mobileNumber",
							"Mobile Number is already registered and account is deactivated.\nPlease contact us to active it."));
				}
			}
		}

		return userResult;
	}

	@PostMapping("/edit_customer")
	public ModelAndView updateCustomer(@Valid @ModelAttribute("userDetailsDTO") UserDetailsDTO userDetailsDTO,
			BindingResult userResult, @Valid @ModelAttribute("addressDTO") AddressDTO addressDTO,
			BindingResult addressResult) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-customer");
		UserDetailsDTO oldUserDetailsDTO = userDetailsService.findById(userDetailsDTO.getUserDetailsId().longValue());
		AddressDTO oldAddressDTO = addressService.findById(addressDTO.getAddressId().longValue());

		if (!(userDetailsDTO.getFirstName().equals(oldUserDetailsDTO.getFirstName()))) {
			oldUserDetailsDTO.setFirstName(userDetailsDTO.getFirstName());
		}

		if (!(userDetailsDTO.getLastName().equals(oldUserDetailsDTO.getLastName()))) {
			oldUserDetailsDTO.setLastName(userDetailsDTO.getLastName());
		}

		if (!(userDetailsDTO.getEmail().equals(oldUserDetailsDTO.getEmail()))) {
			oldUserDetailsDTO.setEmail(userDetailsDTO.getEmail());
		}

		if (!(userDetailsDTO.getMobileNumber().equals(oldUserDetailsDTO.getMobileNumber()))) {
			oldUserDetailsDTO.setMobileNumber(userDetailsDTO.getMobileNumber());
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

		userResult = checkCustomerResultsEdit(oldUserDetailsDTO, userResult);
		if (userResult.hasErrors() == true) {
			modelandmap = new ModelAndView("admin/edit_customer");
			modelandmap.addObject("userDetailsDTO", userDetailsDTO);
			modelandmap.addObject("addressDTO", addressDTO);

			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			modelandmap.addObject("countries", enuCountryService.findAll());
			paramSource.addValue("countryId", addressDTO.getCountryId());
			modelandmap.addObject("states", enuStateService.findByNamedParameters(paramSource));

			paramSource = new MapSqlParameterSource();
			paramSource.addValue("stateId", addressDTO.getStateId());
			modelandmap.addObject("cities", enuCityService.findByNamedParameters(paramSource));
			return modelandmap;
		}

		addressService.update(oldAddressDTO);
		userDetailsService.update(oldUserDetailsDTO);
		return modelandmap;
	}

	@GetMapping("/edit_serviceprovider/{serviceProviderId}")
	public ModelAndView editServiceProvider(@PathVariable("serviceProviderId") long serviceProviderId) {
		ModelAndView modelandmap = new ModelAndView("admin/edit_serviceprovider");

		// TODO make form object
		ServiceProviderDTO serviceProviderDTO = serviceProviderService.findById(serviceProviderId);
		// Need it to show details
		UserDetailsDTO userDetailsDTO = userDetailsService.findById(serviceProviderDTO.getUserDetailsId().longValue());
		AddressDTO addressDTO = addressService.findById(userDetailsDTO.getAddressId().longValue());

		modelandmap.addObject("serviceProviderDTO", serviceProviderDTO);
		modelandmap.addObject("userDetailsDTO", userDetailsDTO);
		modelandmap.addObject("serviceTypes", enuServiceTypeService.findAllActive());
		modelandmap.addObject("addressDTO", addressDTO);

		modelandmap.addObject("countries", enuCountryService.findAll());

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("countryId", addressDTO.getCountryId());
		modelandmap.addObject("states", enuStateService.findByNamedParameters(paramSource));

		paramSource = new MapSqlParameterSource();
		paramSource.addValue("stateId", addressDTO.getStateId());
		modelandmap.addObject("cities", enuCityService.findByNamedParameters(paramSource));
		return modelandmap;
	}

	@PostMapping("/edit_serviceprovider")
	public ModelAndView updateServiceProvider(
			@Valid @ModelAttribute("serviceProviderDTO") ServiceProviderDTO serviceProviderDTO,
			@Valid @ModelAttribute("userDetailsDTO") UserDetailsDTO userDetailsDTO, BindingResult userResult,
			@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-serviceprovider");

		ServiceProviderDTO oldserviceProviderDTO = serviceProviderService
				.findById(serviceProviderDTO.getServiceProviderId().longValue());
		// UserDetailsDTO oldUserDetailsDTO =
		// userDetailsService.findById(userDetailsDTO.getUserDetailsId().longValue());
		AddressDTO oldAddressDTO = addressService.findById(addressDTO.getAddressId().longValue());

		/*
		 * if (!(userDetailsDTO.getServiceProviderName().equals(oldUserDetailsDTO.
		 * getServiceProviderName()))) {
		 * oldUserDetailsDTO.setServiceProviderName(userDetailsDTO.
		 * getServiceProviderName()); }
		 * 
		 * if (!(userDetailsDTO.getEmail().equals(oldUserDetailsDTO.getEmail()))) {
		 * oldUserDetailsDTO.setEmail(userDetailsDTO.getEmail()); }
		 * 
		 * if
		 * (!(userDetailsDTO.getMobileNumber().equals(oldUserDetailsDTO.getMobileNumber(
		 * )))) { oldUserDetailsDTO.setMobileNumber(userDetailsDTO.getMobileNumber()); }
		 */

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

		userResult = checkCustomerResultsEdit(oldserviceProviderDTO, userResult);
		if (userResult.hasErrors() == true) {
			modelandmap = new ModelAndView("admin/edit_serviceprovider");
			modelandmap.addObject("serviceProviderDTO", serviceProviderDTO);
			modelandmap.addObject("userDetailsDTO", userDetailsDTO);
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

		addressService.update(oldAddressDTO);
		// userDetailsService.update(oldUserDetailsDTO);
		serviceProviderService.update(oldserviceProviderDTO);
		return modelandmap;
	}

	@GetMapping("/edit_employee/{employeeId}")
	public ModelAndView editEmployee(@PathVariable("employeeId") long employeeId) {
		ModelAndView modelandmap = new ModelAndView("admin/edit_employee");

		// TODO make form object
		EmployeeDTO employeeDTO = employeeService.findById(employeeId);
		// Need it to show details
		UserDetailsDTO userDetailsDTO = userDetailsService.findById(employeeDTO.getUserDetailsId().longValue());
		employeeDTO.setFirstName(userDetailsDTO.getFirstName());
		employeeDTO.setLastName(userDetailsDTO.getLastName());
		employeeDTO.setEmail(userDetailsDTO.getEmail());
		employeeDTO.setMobileNumber(userDetailsDTO.getMobileNumber());

		AddressDTO addressDTO = addressService.findById(userDetailsDTO.getAddressId().longValue());

		modelandmap.addObject("employeeDTO", employeeDTO);
		modelandmap.addObject("userDetailsDTO", userDetailsDTO);
		modelandmap.addObject("addressDTO", addressDTO);

		modelandmap.addObject("countries", enuCountryService.findAll());
		modelandmap.addObject("employeeRoles", enuEmployeeRoleService.findAll());

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("countryId", addressDTO.getCountryId());
		modelandmap.addObject("states", enuStateService.findByNamedParameters(paramSource));

		paramSource = new MapSqlParameterSource();
		paramSource.addValue("stateId", addressDTO.getStateId());
		modelandmap.addObject("cities", enuCityService.findByNamedParameters(paramSource));
		return modelandmap;
	}

	@PostMapping("/edit_employee")
	public ModelAndView updateEmployee(@Valid @ModelAttribute("employeeDTO") EmployeeDTO employeeDTO,
			BindingResult userResult, @Valid @ModelAttribute("userDetailsDTO") UserDetailsDTO userDetailsDTO,
			@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO, BindingResult addressResult) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-employee");

		EmployeeDTO oldEmployeeDTO = employeeService.findById(employeeDTO.getEmployeeId().longValue());
		UserDetailsDTO oldUserDetailsDTO = userDetailsService.findById(employeeDTO.getUserDetailsId().longValue());
		AddressDTO oldAddressDTO = addressService.findById(addressDTO.getAddressId().longValue());

		oldEmployeeDTO.setFirstName(oldUserDetailsDTO.getFirstName());
		oldEmployeeDTO.setLastName(oldUserDetailsDTO.getLastName());
		oldEmployeeDTO.setEmail(oldUserDetailsDTO.getEmail());
		oldEmployeeDTO.setMobileNumber(oldUserDetailsDTO.getMobileNumber());

		if (!(employeeDTO.getFirstName().equals(oldUserDetailsDTO.getFirstName()))) {
			oldEmployeeDTO.setFirstName(employeeDTO.getFirstName());
		}

		if (!(employeeDTO.getLastName().equals(oldUserDetailsDTO.getLastName()))) {
			oldEmployeeDTO.setLastName(employeeDTO.getLastName());
		}

		if (!(employeeDTO.getEmail().equals(oldUserDetailsDTO.getEmail()))) {
			oldEmployeeDTO.setEmail(employeeDTO.getEmail());
		}

		if (!(employeeDTO.getMobileNumber().equals(oldUserDetailsDTO.getMobileNumber()))) {
			oldEmployeeDTO.setMobileNumber(employeeDTO.getMobileNumber());
		}

		if (!(employeeDTO.getEmployeeRoleId().equals(oldEmployeeDTO.getEmployeeRoleId()))) {
			oldEmployeeDTO.setEmployeeRoleId(employeeDTO.getEmployeeRoleId());
		}

		if (!(employeeDTO.getSalary().equals(oldEmployeeDTO.getSalary()))) {
			oldEmployeeDTO.setSalary(employeeDTO.getSalary());
		}

		if (!(employeeDTO.getGender().equals(oldEmployeeDTO.getGender()))) {
			oldEmployeeDTO.setGender(employeeDTO.getGender());
		}

		if (!(employeeDTO.getDOB().equals(oldEmployeeDTO.getDOB()))) {
			oldEmployeeDTO.setDOB(employeeDTO.getDOB());
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

		userResult = checkCustomerResultsEdit(oldEmployeeDTO, userResult);
		if (userResult.hasErrors() == true) {
			modelandmap = new ModelAndView("admin/edit_employee");
			modelandmap.addObject("employeeRoles", enuEmployeeRoleService.findAll());
			employeeDTO.setDOB(null);
			modelandmap.addObject("employeeDTO", employeeDTO);
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

		addressService.update(oldAddressDTO);
		employeeService.update(oldEmployeeDTO);
		return modelandmap;
	}

	@GetMapping("/edit_venue/{venueId}")
	public ModelAndView editVenue(@PathVariable("venueId") long venueId) {
		ModelAndView modelandmap = new ModelAndView("admin/edit_venue");

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("isActive", true);
		VenueDTO venueDTO = venueService.findById(venueId);
		AddressDTO addressDTO = addressService.findById(venueDTO.getAddressId().longValue());
		List<EnuEventTypeDTO> eventTypes = enuEventTypeService.findByNamedParameters(paramSource);
		List<EnuVenueFacilityDTO> facilities = enuVenueFacilityService.findByNamedParameters(paramSource);
		List<EnuVenueTypeDTO> venueTypes = enuVenueTypeService.findByNamedParameters(paramSource);
		List<String> venueEventTypesStrings = new ArrayList<>();
		List<String> venueFacilitiesStrings = new ArrayList<>();

		List<VenueFacilityMappingDTO> venueFacilities = venueFacilityMappingService
				.findByNamedParameters(new MapSqlParameterSource().addValue("venueId", venueId));
		for (VenueFacilityMappingDTO item : venueFacilities) {
			venueFacilitiesStrings.add(item.getFacilityId().toString());
		}
		List<VenueEventTypeMappingDTO> venueEventTypes = venueEventTypeMappingService
				.findByNamedParameters(new MapSqlParameterSource().addValue("venueId", venueId));
		for (VenueEventTypeMappingDTO item : venueEventTypes) {
			venueEventTypesStrings.add(item.getEventTypeId().toString());
		}
		VenueTempDTO venueTempDTO = new VenueTempDTO(venueEventTypesStrings, venueFacilitiesStrings);

		modelandmap.addObject("facilities", facilities);
		modelandmap.addObject("eventTypes", eventTypes);
		modelandmap.addObject("venueTypes", venueTypes);
		modelandmap.addObject("venueDTO", venueDTO);
		modelandmap.addObject("addressDTO", addressDTO);
		modelandmap.addObject("venueTempDTO", venueTempDTO);

		modelandmap.addObject("countries", enuCountryService.findAll());

		MapSqlParameterSource paramSource1 = new MapSqlParameterSource();
		paramSource1.addValue("countryId", addressDTO.getCountryId());
		modelandmap.addObject("states", enuStateService.findByNamedParameters(paramSource1));

		paramSource1 = new MapSqlParameterSource();
		paramSource1.addValue("stateId", addressDTO.getStateId());
		modelandmap.addObject("cities", enuCityService.findByNamedParameters(paramSource1));
		return modelandmap;
	}

	@PostMapping("/edit_venue")
	public ModelAndView saveVenue(@Valid @ModelAttribute("venueDTO") VenueDTO venueDTO, BindingResult venueResult,
			@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO,
			@Valid @ModelAttribute("venueTempDTO") VenueTempDTO venueTempDTO,
			@RequestParam("files") MultipartFile[] files) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-venue");

		VenueDTO oldVenueDTO = venueService.findById(venueDTO.getVenueId().longValue());
		AddressDTO oldAddressDTO = addressService.findById(venueDTO.getAddressId().longValue());

		if (!(venueDTO.getVenueName().equals(oldVenueDTO.getVenueName()))) {
			oldVenueDTO.setVenueName(venueDTO.getVenueName());
		}

		if (!(venueDTO.getDescription().equals(oldVenueDTO.getDescription()))) {
			oldVenueDTO.setDescription(venueDTO.getDescription());
		}

		if (!(venueDTO.getVenueTypeId().equals(oldVenueDTO.getVenueTypeId()))) {
			oldVenueDTO.setVenueTypeId(venueDTO.getVenueTypeId());
		}

		if (!(venueDTO.getEmail().equals(oldVenueDTO.getEmail()))) {
			oldVenueDTO.setEmail(venueDTO.getEmail());
		}

		if (!(venueDTO.getContactNumber().equals(oldVenueDTO.getContactNumber()))) {
			oldVenueDTO.setContactNumber(venueDTO.getContactNumber());
		}

		if (!(venueDTO.getLatitude().equals(oldVenueDTO.getLatitude()))) {
			oldVenueDTO.setLatitude(venueDTO.getLatitude());
		}

		if (!(venueDTO.getLongitude().equals(oldVenueDTO.getLongitude()))) {
			oldVenueDTO.setLongitude(venueDTO.getLongitude());
		}

		if (!(venueDTO.getCost().equals(oldVenueDTO.getCost()))) {
			oldVenueDTO.setCost(venueDTO.getCost());
		}

		if (!(venueDTO.getGuestCapacity().equals(oldVenueDTO.getGuestCapacity()))) {
			oldVenueDTO.setGuestCapacity(venueDTO.getGuestCapacity());
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
		
		venueResult = checkVenueResultsEdit(oldVenueDTO, venueResult);
		if (venueResult.hasErrors() == true) {
			modelandmap = new ModelAndView("admin/edit_venue");

			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("isActive", true);
			List<EnuEventTypeDTO> eventTypes = enuEventTypeService.findByNamedParameters(paramSource);
			List<EnuVenueFacilityDTO> facilities = enuVenueFacilityService.findByNamedParameters(paramSource);
			List<EnuVenueTypeDTO> venueTypes = enuVenueTypeService.findByNamedParameters(paramSource);

			modelandmap.addObject("facilities", facilities);
			modelandmap.addObject("eventTypes", eventTypes);
			modelandmap.addObject("venueTypes", venueTypes);
			modelandmap.addObject("venueDTO", venueDTO);
			modelandmap.addObject("addressDTO", addressDTO);
			modelandmap.addObject("venueTempDTO", venueTempDTO);
			modelandmap.addObject("countries", enuCountryService.findAll());
			MapSqlParameterSource paramSourceCountry = new MapSqlParameterSource();
			paramSourceCountry.addValue("countryId", addressDTO.getCountryId());
			modelandmap.addObject("states", enuStateService.findByNamedParameters(paramSourceCountry));

			MapSqlParameterSource paramSourceState = new MapSqlParameterSource();
			paramSourceState.addValue("stateId", addressDTO.getStateId());
			modelandmap.addObject("cities", enuCityService.findByNamedParameters(paramSourceState));

			return modelandmap;
		}

		addressService.update(oldAddressDTO);
		venueService.update(oldVenueDTO);
		venueEventTypeMappingService.update(venueDTO.getVenueId().longValue(), venueTempDTO.getSelectedEventTypes());
		venueFacilityMappingService.update(venueDTO.getVenueId().longValue(), venueTempDTO.getSelectedFacilities());

		for (MultipartFile file : files) {
			if (!file.isEmpty()) {
				VenueImageMappingDTO obj = new VenueImageMappingDTO();
				try {
					obj.setImage(new SerialBlob(file.getBytes()));
					obj.setVenueId(oldVenueDTO.getVenueId());
				} catch (SQLException | IOException e) {
					log.error("Error: {}", e);
				}
				venueImageMappingService.insert(obj);
			}
		}
		return modelandmap;
	}

	@PostMapping("upload/files")
	public String handleFilesUpload(@RequestParam("files") MultipartFile[] files,
			@RequestParam("venueId") Integer venueId, ModelAndView map) {

		for (MultipartFile file : files) {

			if (!file.isEmpty()) {
				VenueImageMappingDTO obj = new VenueImageMappingDTO();
				try {
					obj.setImage(new SerialBlob(file.getBytes()));
					obj.setVenueId(venueId);
				} catch (SQLException | IOException e) {
					log.error("Error: {}", e);
				}
				venueImageMappingService.insert(obj);

			}
		}

		return "uploadfiles";
	}

	@GetMapping("/edit_package/{packageDetailsId}")
	public ModelAndView editPackage(@PathVariable("packageDetailsId") long packageDetailsId) {
		ModelAndView modelandmap = new ModelAndView("admin/edit_package");

		PackageDetailsDTO packageDetailsDTO = packageDetailsService.findById(packageDetailsId);
		List<EnuServiceTypeDTO> serviceTypes = enuServiceTypeService.findAllActive();
		List<ServiceProviderDTO> serviceProviders = serviceProviderService.findAllActive();
		PackageTempDTO packageTempDTO = new PackageTempDTO();

		for (int i = 0; i < serviceProviders.size(); i++) {
			serviceProviders.get(i)
					.setServiceProviderName(
							userDetailsService
									.findByNamedParameters(new MapSqlParameterSource().addValue("userDetailsId",
											serviceProviders.get(i).getUserDetailsId()))
									.get(0).getServiceProviderName());
		}

		List<PackageServiceProviderMappingDTO> packageServiceProviderMappings = packageServiceProviderMappingService
				.findByNamedParameters(new MapSqlParameterSource().addValue("packageId", packageDetailsId));
		List<String> serviceProvidersIdList = new ArrayList<>();
		for (PackageServiceProviderMappingDTO entry : packageServiceProviderMappings) {
			serviceProvidersIdList.add(entry.getServiceProviderId().toString());
		}
		packageTempDTO.setServiceProviderIdList(serviceProvidersIdList);

		modelandmap.addObject("packageDetailsDTO", packageDetailsDTO);
		modelandmap.addObject("venueNames", getVenuesOnEventType(packageDetailsDTO.getEventTypeId().longValue()));
		modelandmap.addObject("eventTypes",
				enuEventTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		modelandmap.addObject("serviceTypes", serviceTypes);
		modelandmap.addObject("serviceProviders", serviceProviders);
		modelandmap.addObject("packageTempDTO", packageTempDTO);
		return modelandmap;
	}

	@PostMapping("/edit_package")
	public ModelAndView savePackage(@Valid @ModelAttribute("packageDetailsDTO") PackageDetailsDTO packageDetailsDTO,
			@Valid @ModelAttribute("packageTempDTO") PackageTempDTO packageTempDTO) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-package");

		PackageDetailsDTO oldPackage = packageDetailsService
				.findById(packageDetailsDTO.getPackageDetailsId().longValue());
		if (!(oldPackage.getTitle().equals(packageDetailsDTO.getTitle()))) {
			oldPackage.setTitle(packageDetailsDTO.getTitle());
		}
		if (!(oldPackage.getDescription().equals(packageDetailsDTO.getDescription()))) {
			oldPackage.setDescription(packageDetailsDTO.getDescription());
		}
		if (!(oldPackage.getGuestAmount().equals(packageDetailsDTO.getGuestAmount()))) {
			oldPackage.setGuestAmount(packageDetailsDTO.getGuestAmount());
		}
		if (!(oldPackage.getTotalCost().equals(packageDetailsDTO.getTotalCost()))) {
			oldPackage.setTotalCost(packageDetailsDTO.getTotalCost());
		}
		if (!(oldPackage.getEventTypeId().equals(packageDetailsDTO.getEventTypeId()))) {
			oldPackage.setEventTypeId(packageDetailsDTO.getEventTypeId());
		}
		if (!(oldPackage.getVenueId().equals(packageDetailsDTO.getVenueId()))) {
			oldPackage.setVenueId(packageDetailsDTO.getVenueId());
		}
		packageDetailsService.update(oldPackage);
		packageServiceProviderMappingService.deleteByPackageId(packageDetailsDTO.getPackageDetailsId().longValue());

		if (packageTempDTO.getServiceProviderIdList() != null) {
			packageServiceProviderMappingService.insert(packageDetailsDTO.getPackageDetailsId().longValue(),
					packageTempDTO.getServiceProviderIdList());
		}

		return modelandmap;
	}

	@GetMapping("/getChartData/")
	public List<DonutDTO> getDonutData() {
		List<EnuEventTypeDTO> eventTypes = enuEventTypeService
				.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true));
		List<String> eventTypeNames = new ArrayList<>();
		List<Integer> quantity = eventTypes.stream().map(eventType -> {
			eventTypeNames.add(eventType.getEventType());
			return eventService.getCountByEventType(eventType.getEventType());
		}).collect(Collectors.toList());
		Integer totalEvent = eventService.getCount();
		// Integer totalEvent = 10;
		List<Double> percentage = quantity.stream().map(q -> {
			return (double) (q / totalEvent) * 100;
		}).collect(Collectors.toList());

		List<DonutDTO> donutDTO = new ArrayList<>();
		for (int i = 0; i < eventTypeNames.size(); i++) {
			donutDTO.add(new DonutDTO(eventTypeNames.get(i), i + 1, (Double) quantity.get(i).doubleValue(),
					percentage.get(i)));
		}
		return donutDTO;
	}

	@GetMapping("/list-inquiry")
	public ModelAndView listInquire() {
		ModelAndView modelandmap = new ModelAndView("admin/inquiry");
		List<EnquiryDTO> inquiries = enquiryService.findAllEnquiryWithStatus();
		modelandmap.addObject("inquiries", inquiries);
		return modelandmap;
	}

	@GetMapping("/view_inquiry/{enquiryId}")
	public ModelAndView viewInquire(@PathVariable("enquiryId") long enquiryId) {
		ModelAndView modelandmap = new ModelAndView("admin/view_inquiry");
		EnquiryDTO inquiry = enquiryService.findEnquiryById(enquiryId);
		modelandmap.addObject("inquiry", inquiry);
		return modelandmap;
	}

	@GetMapping("/response_inquiry/{enquiryId}")
	public ModelAndView showResponseInquiry(@PathVariable("enquiryId") long enquiryId) {
		ModelAndView modelandmap = new ModelAndView("admin/response_inquiry");
		EnquiryDTO inquiry = enquiryService.findEnquiryById(enquiryId);
		modelandmap.addObject("inquiry", inquiry);
		return modelandmap;
	}

	@PostMapping("/response_inquiry")
	public ModelAndView inquiryResponse(@Valid @ModelAttribute("inquiry") EnquiryDTO inquiry) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("status", "Responded");
		EnuEnquiryStatusDTO enquiryStatusDTO = DataAccessUtils
				.singleResult(enquiryStatusService.findByNamedParameters(parameterSource));
		inquiry.setEnquiryStatusId(enquiryStatusDTO.getStatusId());
		enquiryService.updateResponse(inquiry);
		Mail mail = new Mail();
		mail.setMailTo(inquiry.getEmail());
		mail.setMailSubject("Enquiry Response");
		mail.setContentType("text/html");
		mail.setMailContent("<p>Hi " + inquiry.getPersonName() + ",</p><br/>" + inquiry.getResponse());
		mailService.sendEmail(mail);
		return new ModelAndView("redirect:/admin/list-inquiry");

	}

	@GetMapping("/review_inquiry/{enquiryId}")
	public ModelAndView reviewInquiry(@PathVariable("enquiryId") long enquiryId) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("status", "In-Review");
		EnuEnquiryStatusDTO enquiryStatusDTO = DataAccessUtils
				.singleResult(enquiryStatusService.findByNamedParameters(parameterSource));
		enquiryService.updateInreviewStatus(enquiryId, enquiryStatusDTO.getStatusId());
		return new ModelAndView("redirect:/admin/list-inquiry");
	}

}
