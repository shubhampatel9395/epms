package com.epms.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.rowset.serial.SerialBlob;
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

import com.epms.authentication.CustomUserDetailsDTO;
import com.epms.dto.AddressDTO;
import com.epms.dto.CustomerCreateEventTempDTO;
import com.epms.dto.EmployeeDTO;
import com.epms.dto.EnuEmployeeRoleDTO;
import com.epms.dto.EnuEventTypeDTO;
import com.epms.dto.EnuServiceTypeDTO;
import com.epms.dto.EnuVenueTypeDTO;
import com.epms.dto.EventBannerDTO;
import com.epms.dto.EventDTO;
import com.epms.dto.EventEmployeeMappingDTO;
import com.epms.dto.EventVenueDetailsDTO;
import com.epms.dto.FeedbackDTO;
import com.epms.dto.InvoiceServiceProviderDTO;
import com.epms.dto.PackageDetailsDTO;
import com.epms.dto.PackageServiceProviderMappingDTO;
import com.epms.dto.PackageTempDTO;
import com.epms.dto.ServiceProviderDTO;
import com.epms.dto.UserDetailsDTO;
import com.epms.dto.VenueDTO;
import com.epms.dto.VenueEventTypeMappingDTO;
import com.epms.email.configuration.IMailService;
import com.epms.email.configuration.Mail;
import com.epms.service.IAddressService;
import com.epms.service.IEmployeeService;
import com.epms.service.IEnquiryService;
import com.epms.service.IEnuCityService;
import com.epms.service.IEnuCountryService;
import com.epms.service.IEnuEmployeeRoleService;
import com.epms.service.IEnuEmployeeWorkingStatusService;
import com.epms.service.IEnuEnquiryStatusService;
import com.epms.service.IEnuEventStatusService;
import com.epms.service.IEnuEventTypeService;
import com.epms.service.IEnuServiceProviderWorkingStatusService;
import com.epms.service.IEnuServiceTypeService;
import com.epms.service.IEnuStateService;
import com.epms.service.IEnuVenueFacilityService;
import com.epms.service.IEnuVenueTypeService;
import com.epms.service.IEventBannerService;
import com.epms.service.IEventEmployeeMappingService;
import com.epms.service.IEventService;
import com.epms.service.IFeedbackService;
import com.epms.service.IInvoiceService;
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
@Slf4j
public class EventController {
	@Autowired
	IInvoiceService invoiceService;

	@Autowired
	IEnuCityService enuCityService;

	@Autowired
	IEnquiryService enquiryService;

	@Autowired
	IEnuStateService enuStateService;

	@Autowired
	IUserDetailsService userDetailsService;

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
	IVenueFacilityMappingService venueFacilityMappingService;

	@Autowired
	IVenueEventTypeMappingService venueEventTypeMappingService;

	@Autowired
	IVenueImageMappingService venueImageMappingService;

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

	@Autowired
	IPackageDetailsService packageDetailsService;

	@Autowired
	IServiceProviderService serviceProviderService;

	@Autowired
	IVenueService venueService;

	@Autowired
	IEventEmployeeMappingService eventEmployeeMappingService;

	@Autowired
	IEnuEmployeeWorkingStatusService enuEmployeeWorkingStatusService;

	@Autowired
	IEnuServiceProviderWorkingStatusService enuServiceProviderWorkingStatusService;

	@Autowired
	IFeedbackService feedbackService;

	@GetMapping("/admin/list-event")
	public ModelAndView listEvent() {
		ModelAndView modelandmap = new ModelAndView("admin/event");

		List<EventDTO> events = eventService
				.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true));
		List<String> eventTypes = events.stream().map(event -> {
			return DataAccessUtils
					.singleResult(enuEventTypeService.findByNamedParameters(
							new MapSqlParameterSource().addValue("eventTypeId", event.getEventTypeId())))
					.getEventType();
		}).collect(Collectors.toList());
		List<String> customers = events.stream().map(event -> {
			return DataAccessUtils
					.singleResult(userDetailsService.findByNamedParameters(
							new MapSqlParameterSource().addValue("userDetailsId", event.getUserDetailsId())))
					.getFirstName();
		}).collect(Collectors.toList());
		List<String> eventStatuses = events.stream().map(event -> {
			return DataAccessUtils.singleResult(enuEventStatusService
					.findByNamedParameters(new MapSqlParameterSource().addValue("statusId", event.getEventStatusId())))
					.getStatus();
		}).collect(Collectors.toList());

		List<Boolean> canComplete = events.stream().map(event -> {
			LocalDateTime endDateTime = LocalDateTime.of(new java.sql.Date(event.getEndDate().getTime()).toLocalDate(),
					event.getEndTime().toLocalTime());
			// If end DateTime is less than current DateTime, then only complete
			return endDateTime.compareTo(LocalDateTime.now()) < 0 ? true : false;
		}).collect(Collectors.toList());

		modelandmap.addObject("events", events);
		modelandmap.addObject("eventTypes", eventTypes);
		modelandmap.addObject("customers", customers);
		modelandmap.addObject("eventStatuses", eventStatuses);
		modelandmap.addObject("canComplete", canComplete);
		return modelandmap;
	}

	@GetMapping("/customer/manage-events")
	public ModelAndView manageEvent() {
		ModelAndView modelandmap = new ModelAndView("customer/manage_event");
		CustomUserDetailsDTO customUserDetailsDTO = (CustomUserDetailsDTO) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		List<EventDTO> events = eventService.findByNamedParameters(new MapSqlParameterSource()
				.addValue("isActive", true).addValue("userDetailsId", customUserDetailsDTO.getUserDetailsId()));
		List<String> eventTypes = events.stream().map(event -> {
			return DataAccessUtils
					.singleResult(enuEventTypeService.findByNamedParameters(
							new MapSqlParameterSource().addValue("eventTypeId", event.getEventTypeId())))
					.getEventType();
		}).collect(Collectors.toList());
		List<String> eventStatuses = events.stream().map(event -> {
			return DataAccessUtils.singleResult(enuEventStatusService
					.findByNamedParameters(new MapSqlParameterSource().addValue("statusId", event.getEventStatusId())))
					.getStatus();
		}).collect(Collectors.toList());

		List<Boolean> canComplete = events.stream().map(event -> {
			LocalDateTime endDateTime = LocalDateTime.of(new java.sql.Date(event.getEndDate().getTime()).toLocalDate(),
					event.getEndTime().toLocalTime());
			// If end DateTime is less than current DateTime, then only complete
			return endDateTime.compareTo(LocalDateTime.now()) < 0 ? true : false;
		}).collect(Collectors.toList());

		// System.out.println(canComplete);

		modelandmap.addObject("events", events);
		modelandmap.addObject("eventTypes", eventTypes);
		modelandmap.addObject("eventStatuses", eventStatuses);
		modelandmap.addObject("canComplete", canComplete);
		return modelandmap;
	}

	@PostMapping("/getEventCost")
	public Double getEventCost(@RequestBody List<String> data) {
		System.out.println(data);
		Double cost = 0.0;
		if (data.size() == 0) {
			return -1.0;
		}

		if (data.get(0).equalsIgnoreCase("Static")) {
			cost += DataAccessUtils.singleResult(packageDetailsService
					.findByNamedParameters(new MapSqlParameterSource().addValue("packageDetailsId", data.get(1))))
					.getTotalCost();
		} else if (data.get(0).equalsIgnoreCase("Dynamic")) {
			cost += venueService.findById(Long.parseLong(data.get(1))).getCost();
			for (int i = 2; i < data.size() - 2; i++) {
				cost += serviceProviderService.findById(Long.parseLong(data.get(i))).getCost();
			}
		}

		if (data.get(data.size() - 2).equalsIgnoreCase("true")) {
			cost += 500.0;
		}
		if (!data.get(data.size() - 1).equalsIgnoreCase("true")) {
			cost += 500.0;
		}

		return cost;
	}

	@PostMapping("/getEventCostVerify")
	public Double getEventCostVerify(@RequestBody List<String> data) {
		System.out.println(data);
		Double cost = 0.0;
		if (data.size() == 0) {
			return -1.0;
		}

		cost += venueService.findById(Long.parseLong(data.get(0))).getCost();
		for (int i = 1; i < data.size() - 2; i++) {
			cost += serviceProviderService.findById(Long.parseLong(data.get(i))).getCost();
		}

		if (data.get(data.size() - 2).equalsIgnoreCase("true")) {
			cost += 500.0;
		}
		if (!data.get(data.size() - 1).equalsIgnoreCase("true")) {
			cost += 500.0;
		}

		return cost;
	}

	@GetMapping("/admin/add_event")
	public ModelAndView addEvent() {
		ModelAndView modelandmap = new ModelAndView("admin/add_event");
		List<EmployeeDTO> employees = employeeService
				.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true).addValue("employeeRoleId",
						enuEmployeeRoleService
								.findByNamedParameters(new MapSqlParameterSource().addValue("role", "Event Organizer"))
								.get(0).getEmployeeRoleId()));

		employees.forEach(employee -> {
			UserDetailsDTO user = userDetailsService.findById(employee.getUserDetailsId().longValue());
			employee.setFirstName(user.getFirstName());
			employee.setLastName(user.getLastName());
		});

		List<ServiceProviderDTO> serviceProviders = serviceProviderService.findAllActive();
		PackageDetailsDTO packageDetailsDTO = new PackageDetailsDTO();
		packageDetailsDTO.setIsStatic(true);
		EventDTO eventDTO = new EventDTO();
		eventDTO.setIsFree(true);
		eventDTO.setIsPublic(true);

		for (int i = 0; i < serviceProviders.size(); i++) {
			serviceProviders.get(i)
					.setServiceProviderName(
							userDetailsService
									.findByNamedParameters(new MapSqlParameterSource().addValue("userDetailsId",
											serviceProviders.get(i).getUserDetailsId()))
									.get(0).getServiceProviderName());
		}

		modelandmap.addObject("serviceProviders", serviceProviders);
		modelandmap.addObject("venueNames",
				venueService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		modelandmap.addObject("serviceTypes",
				enuServiceTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		modelandmap.addObject("packageTempDTO", new PackageTempDTO());

		modelandmap.addObject("eventDTO", eventDTO);
		modelandmap.addObject("packageDetailsDTO", packageDetailsDTO);
		modelandmap.addObject("customers", userDetailsService.findByNamedParameters(
				new MapSqlParameterSource().addValue("isActive", true).addValue("isCustomer", true)));
		modelandmap.addObject("employees", employees);
		modelandmap.addObject("bannerDTO", new EventBannerDTO());
		modelandmap.addObject("eventTypes",
				enuEventTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		return modelandmap;
	}

	@PostMapping("/admin/add_event")
	public ModelAndView saveEvent(@Valid @ModelAttribute("eventDTO") EventDTO eventDTO,
			@Valid @ModelAttribute("packageDetailsDTO") PackageDetailsDTO packageDetailsDTO,
			@Valid @ModelAttribute("packageTempDTO") PackageTempDTO packageTempDTO,
			@RequestParam("banner") MultipartFile banner) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-event");

		if (packageDetailsDTO.getIsStatic() != true) {
			eventDTO.setPackageId(packageDetailsService.insert(packageDetailsDTO).getPackageDetailsId());
			if (packageTempDTO.getServiceProviderIdList() != null) {
				packageServiceProviderMappingService.insert(eventDTO.getPackageId().longValue(),
						packageTempDTO.getServiceProviderIdList());
			}
		}
		eventDTO.setEventStatusId(DataAccessUtils
				.singleResult(enuEventStatusService
						.findByNamedParameters(new MapSqlParameterSource().addValue("status", "Verified")))
				.getStatusId());
		EventDTO newEventDTO = eventService.insertByAdmin(eventDTO);

		if (!banner.isEmpty()) {
			EventBannerDTO obj = new EventBannerDTO();
			try {
				obj.setBanner(new SerialBlob(banner.getBytes()));
				obj.setEventId(newEventDTO.getEventId());
			} catch (SQLException | IOException e) {
				log.error(e.toString());
			}
			eventBannerService.insert(obj);
		}

		Mail mail = new Mail();
		mail.setMailTo(userDetailsService.findById(newEventDTO.getUserDetailsId().longValue()).getEmail());
		mail.setMailSubject("Event Created");
		mail.setContentType("text/html");
		String content = "<p>We have created an Event<strong>" + newEventDTO.getEventTitle() + " - "
				+ newEventDTO.getObjective() + "</strong> on Unico - Event Planning and Management website.</p>"
				+ "<p>You will be notified about your event details.</p>";
		mail.setMailContent(content);
		mailService.sendEmail(mail);

		eventDetailsMail(newEventDTO.getEventId());

		return modelandmap;
	}

	public void eventDetailsMail(long eventId) {
		EventDTO newEventDTO = eventService.findById(eventId);
		String isPublic = (newEventDTO.getIsPublic() == true) ? "Yes" : "No";
		String isFree = (newEventDTO.getIsFree() == true) ? "Yes" : "No";

		Mail mail = new Mail();
		mail.setMailTo(userDetailsService.findById(newEventDTO.getUserDetailsId().longValue()).getEmail());
		mail.setMailSubject("Event Details");
		mail.setContentType("text/html");
		String content = "<p><strong>" + newEventDTO.getEventTitle() + " - " + newEventDTO.getObjective()
				+ " Event Details</strong></p>" + "<br/><p><strong>Event Type : </strong>"
				+ DataAccessUtils
						.singleResult(enuEventTypeService.findByNamedParameters(
								new MapSqlParameterSource().addValue("eventTypeId", newEventDTO.getEventTypeId())))
						.getEventType()
				+ "</p>" + "<p><strong>Start Date: </strong>" + newEventDTO.getStartDate() + "</p>"
				+ "<p><strong>Start Time: </strong>" + newEventDTO.getStartTime() + "</p>"
				+ "<p><strong>End Date: </strong>" + newEventDTO.getEndDate() + "</p>"
				+ "<p><strong>End Time: </strong>" + newEventDTO.getEndTime() + "</p>"
				+ "<p><strong>Estimated Guest: </strong>" + newEventDTO.getEstimatedGuest() + "</p>"
				+ "<p><strong>Public Event: </strong>" + isPublic + "</p>" + "<p><strong>Free Event: </strong>" + isFree
				+ "</p>" + "<p><strong>Total Cost: </strong>" + newEventDTO.getTotalCost() + "</p>";
		if (newEventDTO.getIsFree() != true) {
			content += "<p><strong>Registration Fee: </strong>" + newEventDTO.getRegistrationFee() + "</p>"
					+ "<p><strong>Registrations Available: </strong>" + newEventDTO.getRegistrationAvailable() + "</p>";
		}
		content += "<br/><p><strong>" + newEventDTO.getEventTitle() + " - " + newEventDTO.getObjective()
				+ " Services Details</strong></p>";

		List<PackageServiceProviderMappingDTO> packageServiceProviderMappings = packageServiceProviderMappingService
				.findByNamedParameters(new MapSqlParameterSource().addValue("packageId", newEventDTO.getPackageId()));
		for (PackageServiceProviderMappingDTO entry : packageServiceProviderMappings) {
			String serviceWithProviders = new String("<p>");
			String service = enuServiceTypeService.findById(entry.getServiceTypeId().longValue()).getService();
			UserDetailsDTO serviceProvider = userDetailsService.findById(serviceProviderService
					.findById(entry.getServiceProviderId().longValue()).getUserDetailsId().longValue());

			if (entry.getServiceProviderId() != null) {
				serviceWithProviders += service + " - " + serviceProvider.getServiceProviderName() + " ("
						+ serviceProvider.getEmail() + ")";
			} else {
				serviceWithProviders += service;
			}
			serviceWithProviders += "</p>";
			content += serviceWithProviders;
		}

		content += "<br/><p><strong>" + newEventDTO.getEventTitle() + " - " + newEventDTO.getObjective()
				+ " Event Organizer Details</strong></p>";
		if (newEventDTO.getEventOrganizerId() != null) {
			UserDetailsDTO eventorganizer = userDetailsService.findById(employeeService
					.findById(newEventDTO.getEventOrganizerId().longValue()).getUserDetailsId().longValue());
			content += "<p><strong>" + eventorganizer.getFirstName() + " " + eventorganizer.getLastName()
					+ "</strong> (" + eventorganizer.getEmail() + ")</p>";
		}

		mail.setMailContent(content);
		mailService.sendEmail(mail);
	}

	@GetMapping("/getVenueTypeOnEventType/{eventTypeId}")
	public List<EnuVenueTypeDTO> getVenueTypeOnEventType(@PathVariable("eventTypeId") long eventTypeId) {
		List<VenueEventTypeMappingDTO> venueEventTypeMappings = venueEventTypeMappingService.findByNamedParameters(
				new MapSqlParameterSource().addValue("eventTypeId", eventTypeId).addValue("isActive", true));
		List<Long> venueTypeIds = venueEventTypeMappings.stream().map(entry -> {
			return venueService.findById(entry.getVenueId().longValue()).getVenueTypeId().longValue();
		}).collect(Collectors.toList());
		Set<Long> uniquEvenueTypeIds = new HashSet<Long>(venueTypeIds);
		List<EnuVenueTypeDTO> enuVenueTypes = uniquEvenueTypeIds.stream().map(entry -> {
			return enuVenueTypeService.findById(entry);
		}).collect(Collectors.toList());

		return enuVenueTypes;
	}

	@GetMapping("/add_event")
	public ModelAndView addCustomerEvent() {
		ModelAndView modelandmap = new ModelAndView("customer/create_event");
		PackageDetailsDTO packageDetailsDTO = new PackageDetailsDTO();
		packageDetailsDTO.setIsStatic(true);
		EventDTO eventDTO = new EventDTO();
		eventDTO.setIsFree(true);
		eventDTO.setIsPublic(true);
		modelandmap.addObject("serviceTypes",
				enuServiceTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		modelandmap.addObject("eventDTO", eventDTO);
		modelandmap.addObject("packageDetailsDTO", packageDetailsDTO);
		modelandmap.addObject("serviceIds", new CustomerCreateEventTempDTO());
		modelandmap.addObject("eventTypes",
				enuEventTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		modelandmap.addObject("venueTypes",
				enuVenueTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		return modelandmap;
	}

	@PostMapping("/add_event")
	public ModelAndView saveCustomerEvent(@Valid @ModelAttribute("eventDTO") EventDTO eventDTO,
			@Valid @ModelAttribute("packageDetailsDTO") PackageDetailsDTO packageDetailsDTO,
			@Valid @ModelAttribute("serviceIds") CustomerCreateEventTempDTO serviceIds,
			@RequestParam("banner") MultipartFile banner) {
		System.out.println(serviceIds.getServiceTypeIds());
		ModelAndView modelandmap = new ModelAndView("redirect:/customer/index");
		CustomUserDetailsDTO customUserDetailsDTO = (CustomUserDetailsDTO) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		eventDTO.setUserDetailsId(customUserDetailsDTO.getUserDetailsId());
		eventDTO.setEventStatusId(DataAccessUtils
				.singleResult(enuEventStatusService
						.findByNamedParameters(new MapSqlParameterSource().addValue("status", "Registered")))
				.getStatusId());
		if (packageDetailsDTO.getIsStatic() != true) {
			eventDTO.setPackageId(packageDetailsService.insertByCustomer(packageDetailsDTO).getPackageDetailsId());
			if (serviceIds.getServiceTypeIds() != null) {
				packageServiceProviderMappingService.insertByCustomer(eventDTO.getPackageId().longValue(),
						serviceIds.getServiceTypeIds());
			}
		} else {
			double cost = 0.0;
			if (eventDTO.getIsPublic() == true) {
				cost += 500.0;
			}
			if (!eventDTO.getIsFree() == true) {
				cost += 500.0;
			}
			eventDTO.setTotalCost(
					packageDetailsService.findById(eventDTO.getPackageId().longValue()).getTotalCost() + cost);
		}

		EventDTO newEventDTO = eventService.insertByCustomer(eventDTO);

		if (!banner.isEmpty()) {
			EventBannerDTO obj = new EventBannerDTO();
			try {
				obj.setBanner(new SerialBlob(banner.getBytes()));
				obj.setEventId(newEventDTO.getEventId());
			} catch (SQLException | IOException e) {
				log.error(e.toString());
			}
			eventBannerService.insert(obj);
		}

		Mail mail = new Mail();
		mail.setMailTo(userDetailsService.findById(customUserDetailsDTO.getUserDetailsId().longValue()).getEmail());
		mail.setMailSubject("Event Created");
		mail.setContentType("text/html");
		String content = "<p>You have created an Event <strong>" + newEventDTO.getEventTitle() + " - "
				+ newEventDTO.getObjective() + "</strong> on Unico - Event Planning and Management website.</p>"
				+ "<p>You will have to wait until we verify your event details.</p>"
				+ "<p>You will be notified about your event details.</p>";
		mail.setMailContent(content);
		mailService.sendEmail(mail);

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

	@RequestMapping(value = "/admin/banner/{bannerId}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getImage(@PathVariable("bannerId") Long bannerId) throws IOException {
		Blob img = eventBannerService.findById(bannerId).getBanner();
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

	@GetMapping("/admin/view_event/{eventId}")
	public ModelAndView viewEvent(@PathVariable("eventId") long eventId) {
		ModelAndView modelandmap = new ModelAndView("admin/view_event");
		EventDTO eventDTO = eventService.findById(eventId);
		UserDetailsDTO customerDTO = userDetailsService.findById(eventDTO.getUserDetailsId().longValue());
		PackageDetailsDTO packageDetailsDTO = packageDetailsService.findById(eventDTO.getPackageId().longValue());
		VenueDTO venueDTO = null;
		if (packageDetailsDTO.getVenueId() != null) {
			venueDTO = venueService.findById(packageDetailsDTO.getVenueId().longValue());
			modelandmap.addObject("addressVenue",
					getAddress(addressService.findById(venueDTO.getAddressId().longValue())));
		}

		modelandmap.addObject("eventDTO", eventDTO);
		modelandmap.addObject("customer", customerDTO);
		modelandmap.addObject("address", getAddress(addressService.findById(customerDTO.getAddressId().longValue())));
		modelandmap.addObject("eventType",
				enuEventTypeService.findById(eventDTO.getEventTypeId().longValue()).getEventType());
		modelandmap.addObject("eventStatus",
				enuEventStatusService.findById(eventDTO.getEventStatusId().longValue()).getStatus());
		modelandmap.addObject("package", packageDetailsDTO);

		List<PackageServiceProviderMappingDTO> packageServiceProviderMappings = packageServiceProviderMappingService
				.findByNamedParameters(
						new MapSqlParameterSource().addValue("packageId", packageDetailsDTO.getPackageDetailsId()));
		Map<String, String> serviceWithProviders = new HashMap<>();
		for (PackageServiceProviderMappingDTO entry : packageServiceProviderMappings) {
			String service = enuServiceTypeService.findById(entry.getServiceTypeId().longValue()).getService();

			if (entry.getServiceProviderId() != null) {
				serviceWithProviders.put(service,
						userDetailsService.findById(serviceProviderService
								.findById(entry.getServiceProviderId().longValue()).getUserDetailsId().longValue())
								.getServiceProviderName());
			} else {
				serviceWithProviders.put(service, null);
			}
		}

		if (eventDTO.getEventOrganizerId() != null) {
			UserDetailsDTO eventorganizer = userDetailsService.findById(employeeService
					.findById(eventDTO.getEventOrganizerId().longValue()).getUserDetailsId().longValue());
			modelandmap.addObject("eventorganizer", eventorganizer);
		}

		modelandmap.addObject("venue", venueDTO);
		modelandmap.addObject("venueType",
				enuVenueTypeService.findById(packageDetailsDTO.getVenueTypeId().longValue()).getVenueType());
		modelandmap.addObject("serviceWithProviders", serviceWithProviders);
		modelandmap.addObject("banner", DataAccessUtils.singleResult(eventBannerService.findByNamedParameters(
				new MapSqlParameterSource().addValue("eventId", eventId).addValue("isActive", true))));

		return modelandmap;
	}

	@GetMapping("/customer/view_event/{eventId}")
	public ModelAndView viewManageEvent(@PathVariable("eventId") long eventId) {
		ModelAndView modelandmap = new ModelAndView("customer/view_event");
		EventDTO eventDTO = eventService.findById(eventId);
		PackageDetailsDTO packageDetailsDTO = packageDetailsService.findById(eventDTO.getPackageId().longValue());
		VenueDTO venueDTO = null;
		if (packageDetailsDTO.getVenueId() != null) {
			venueDTO = venueService.findById(packageDetailsDTO.getVenueId().longValue());
			modelandmap.addObject("addressVenue",
					getAddress(addressService.findById(venueDTO.getAddressId().longValue())));
		}

		modelandmap.addObject("eventDTO", eventDTO);
		modelandmap.addObject("eventType",
				enuEventTypeService.findById(eventDTO.getEventTypeId().longValue()).getEventType());
		modelandmap.addObject("eventStatus",
				enuEventStatusService.findById(eventDTO.getEventStatusId().longValue()).getStatus());
		modelandmap.addObject("package", packageDetailsDTO);

		List<PackageServiceProviderMappingDTO> packageServiceProviderMappings = packageServiceProviderMappingService
				.findByNamedParameters(
						new MapSqlParameterSource().addValue("packageId", packageDetailsDTO.getPackageDetailsId()));
		Map<String, String> serviceWithProviders = new HashMap<>();
		for (PackageServiceProviderMappingDTO entry : packageServiceProviderMappings) {
			String service = enuServiceTypeService.findById(entry.getServiceTypeId().longValue()).getService();

			if (entry.getServiceProviderId() != null) {
				serviceWithProviders.put(service,
						userDetailsService.findById(serviceProviderService
								.findById(entry.getServiceProviderId().longValue()).getUserDetailsId().longValue())
								.getServiceProviderName());
			} else {
				serviceWithProviders.put(service, null);
			}
		}

		if (eventDTO.getEventOrganizerId() != null) {
			UserDetailsDTO eventorganizer = userDetailsService.findById(employeeService
					.findById(eventDTO.getEventOrganizerId().longValue()).getUserDetailsId().longValue());
			modelandmap.addObject("eventorganizer", eventorganizer);
		}

		modelandmap.addObject("venue", venueDTO);
		modelandmap.addObject("venueType",
				enuVenueTypeService.findById(packageDetailsDTO.getVenueTypeId().longValue()).getVenueType());
		modelandmap.addObject("serviceWithProviders", serviceWithProviders);
		modelandmap.addObject("banner", DataAccessUtils.singleResult(eventBannerService.findByNamedParameters(
				new MapSqlParameterSource().addValue("eventId", eventId).addValue("isActive", true))));

		return modelandmap;
	}

	public List<VenueDTO> getVenuesOnEventType(Long eventTypeId) {
		List<VenueEventTypeMappingDTO> listVenues = venueEventTypeMappingService
				.findByNamedParameters(new MapSqlParameterSource().addValue("eventTypeId", eventTypeId));
		List<VenueDTO> venues = new ArrayList<>();

		for (int i = 0; i < listVenues.size(); i++) {
			venues.add(venueService.findById(listVenues.get(i).getVenueId().longValue()));
		}

		return venues;
	}

	@GetMapping("/admin/edit_event/{eventId}")
	public ModelAndView editEvent(@PathVariable("eventId") long eventId) {
		ModelAndView modelandmap = new ModelAndView("admin/edit_event");
		List<EmployeeDTO> employees = employeeService
				.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true).addValue("employeeRoleId",
						enuEmployeeRoleService
								.findByNamedParameters(new MapSqlParameterSource().addValue("role", "Event Organizer"))
								.get(0).getEmployeeRoleId()));

		employees.forEach(employee -> {
			UserDetailsDTO user = userDetailsService.findById(employee.getUserDetailsId().longValue());
			employee.setFirstName(user.getFirstName());
			employee.setLastName(user.getLastName());
		});

		List<ServiceProviderDTO> serviceProviders = serviceProviderService.findAllActive();
		EventDTO eventDTO = eventService.findById(eventId);
		PackageDetailsDTO packageDetailsDTO = packageDetailsService.findById(eventDTO.getPackageId().longValue());

		for (int i = 0; i < serviceProviders.size(); i++) {
			serviceProviders.get(i)
					.setServiceProviderName(
							userDetailsService
									.findByNamedParameters(new MapSqlParameterSource().addValue("userDetailsId",
											serviceProviders.get(i).getUserDetailsId()))
									.get(0).getServiceProviderName());
		}

		PackageTempDTO packageTempDTO = new PackageTempDTO();
		List<PackageServiceProviderMappingDTO> packageServiceProviderMappings = packageServiceProviderMappingService
				.findByNamedParameters(
						new MapSqlParameterSource().addValue("packageId", packageDetailsDTO.getPackageDetailsId()));
		List<String> serviceProvidersIdList = new ArrayList<>();
		for (PackageServiceProviderMappingDTO entry : packageServiceProviderMappings) {
			serviceProvidersIdList.add(entry.getServiceProviderId().toString());
		}
		packageTempDTO.setServiceProviderIdList(serviceProvidersIdList);

		modelandmap.addObject("serviceProviders", serviceProviders);
		modelandmap.addObject("venueNames", getVenuesOnEventType(eventDTO.getEventTypeId().longValue()));
		modelandmap.addObject("serviceTypes",
				enuServiceTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		modelandmap.addObject("packageTempDTO", packageTempDTO);

		modelandmap.addObject("eventDTO", eventDTO);
		modelandmap.addObject("packageDetailsDTO", packageDetailsDTO);
		modelandmap.addObject("customers", userDetailsService.findByNamedParameters(
				new MapSqlParameterSource().addValue("isActive", true).addValue("isCustomer", true)));
		modelandmap.addObject("employees", employees);
		modelandmap.addObject("bannerDTO", new EventBannerDTO());
		modelandmap.addObject("eventTypes",
				enuEventTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		return modelandmap;
	}

	@PostMapping("/admin/edit_event")
	public ModelAndView saveEditedEvent(@Valid @ModelAttribute("eventDTO") EventDTO eventDTO,
			@Valid @ModelAttribute("packageDetailsDTO") PackageDetailsDTO packageDetailsDTO,
			@Valid @ModelAttribute("packageTempDTO") PackageTempDTO packageTempDTO,
			@RequestParam("banner") MultipartFile banner) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-event");

		PackageDetailsDTO oldPackageDetailsDTO = packageDetailsService
				.findById(packageDetailsDTO.getPackageDetailsId().longValue());

		if (oldPackageDetailsDTO.getIsStatic() != true) {
			packageDetailsService.delete(oldPackageDetailsDTO.getPackageDetailsId().longValue());
			packageServiceProviderMappingService
					.deleteByPackageId(oldPackageDetailsDTO.getPackageDetailsId().longValue());
		}

		if (packageDetailsDTO.getIsStatic() != true) {
			eventDTO.setPackageId(packageDetailsService.insert(packageDetailsDTO).getPackageDetailsId());

			if (packageTempDTO.getServiceProviderIdList() != null) {
				packageServiceProviderMappingService.assign(eventDTO.getPackageId().longValue(),
						packageTempDTO.getServiceProviderIdList(),
						DataAccessUtils
								.singleResult(enuServiceProviderWorkingStatusService.findByNamedParameters(
										new MapSqlParameterSource().addValue("status", "Assigned")))
								.getStatusId().longValue());
			}
		}

		EventDTO oldEventDTO = eventService.findById(eventDTO.getEventId().longValue());
		if (!eventDTO.getEventTitle().equalsIgnoreCase(oldEventDTO.getEventTitle())) {
			oldEventDTO.setEventTitle(eventDTO.getEventTitle());
		}
		if (!eventDTO.getObjective().equalsIgnoreCase(oldEventDTO.getObjective())) {
			oldEventDTO.setObjective(eventDTO.getObjective());
		}
		if (!eventDTO.getEventTypeId().equals(oldEventDTO.getEventTypeId())) {
			oldEventDTO.setEventTypeId(eventDTO.getEventTypeId());
		}
		if (!eventDTO.getUserDetailsId().equals(oldEventDTO.getUserDetailsId())) {
			oldEventDTO.setUserDetailsId(eventDTO.getUserDetailsId());
		}
		if (!eventDTO.getPackageId().equals(oldEventDTO.getPackageId())) {
			oldEventDTO.setPackageId(eventDTO.getPackageId());
		}
		if (!eventDTO.getEventOrganizerId().equals(oldEventDTO.getEventOrganizerId())) {
			oldEventDTO.setEventOrganizerId(eventDTO.getEventOrganizerId());
		}
		if (!eventDTO.getIsFree().equals(oldEventDTO.getIsFree())) {
			oldEventDTO.setIsFree(eventDTO.getIsFree());
			if (oldEventDTO.getIsFree() == true) {
				oldEventDTO.setRegistrationFee(0.0);
				oldEventDTO.setRegistrationAvailable(0);
			} else {
				if (!eventDTO.getRegistrationFee().equals(oldEventDTO.getRegistrationFee())) {
					oldEventDTO.setRegistrationFee(eventDTO.getRegistrationFee());
				}
				if (!eventDTO.getRegistrationAvailable().equals(oldEventDTO.getRegistrationAvailable())) {
					oldEventDTO.setRegistrationAvailable(eventDTO.getRegistrationAvailable());
				}
			}
		}
		if (!eventDTO.getIsPublic().equals(oldEventDTO.getIsPublic())) {
			oldEventDTO.setIsPublic(eventDTO.getIsPublic());
		}
		if (!eventDTO.getStartDate().equals(oldEventDTO.getStartDate())) {
			oldEventDTO.setStartDate(eventDTO.getStartDate());
		}
		if (!eventDTO.getStartTime().equals(oldEventDTO.getStartTime())) {
			oldEventDTO.setStartTime(eventDTO.getStartTime());
		}
		if (!eventDTO.getEndDate().equals(oldEventDTO.getEndDate())) {
			oldEventDTO.setEndDate(eventDTO.getEndDate());
		}
		if (!eventDTO.getEndTime().equals(oldEventDTO.getEndTime())) {
			oldEventDTO.setEndTime(eventDTO.getEndTime());
		}
		if (!eventDTO.getEstimatedGuest().equals(oldEventDTO.getEstimatedGuest())) {
			oldEventDTO.setEstimatedGuest(eventDTO.getEstimatedGuest());
		}
		if (!eventDTO.getTotalCost().equals(oldEventDTO.getTotalCost())) {
			oldEventDTO.setTotalCost(eventDTO.getTotalCost());
		}
		eventService.updateByAdmin(oldEventDTO);

		if (!banner.isEmpty()) {
			eventBannerService.delete(oldEventDTO.getEventId().longValue());
			EventBannerDTO obj = new EventBannerDTO();
			try {
				obj.setBanner(new SerialBlob(banner.getBytes()));
				obj.setEventId(oldEventDTO.getEventId());
			} catch (SQLException | IOException e) {
				log.error(e.toString());
			}
			eventBannerService.insert(obj);
		}

		Mail mail = new Mail();
		mail.setMailTo(userDetailsService.findById(oldEventDTO.getUserDetailsId().longValue()).getEmail());
		mail.setMailSubject("Event Updated");
		mail.setContentType("text/html");
		String content = "<p>Your event <strong>" + oldEventDTO.getEventTitle() + " - " + oldEventDTO.getObjective()
				+ "</strong> on Unico - Event Planning and Management website has been updated.</p>"
				+ "<p>You will be notified about your event details.</p>";
		mail.setMailContent(content);
		mailService.sendEmail(mail);

		eventDetailsMail(oldEventDTO.getEventId());

		return modelandmap;
	}

	@GetMapping("/customer/edit_event/{eventId}")
	public ModelAndView editEventByCustomer(@PathVariable("eventId") long eventId) {
		ModelAndView modelandmap = new ModelAndView("customer/edit_event");

		EventDTO eventDTO = eventService.findById(eventId);
		PackageDetailsDTO packageDetailsDTO = packageDetailsService.findById(eventDTO.getPackageId().longValue());
		CustomerCreateEventTempDTO customerCreateEventTempDTO = new CustomerCreateEventTempDTO();
		List<PackageServiceProviderMappingDTO> packageServiceProviderMappingDTOs = packageServiceProviderMappingService
				.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true).addValue("packageId",
						packageDetailsDTO.getPackageDetailsId()));
		List<String> serviceTypeIds = packageServiceProviderMappingDTOs.stream().map(entry -> {
			return entry.getServiceTypeId().toString();
		}).collect(Collectors.toList());
		customerCreateEventTempDTO.setServiceTypeIds(serviceTypeIds);

		modelandmap.addObject("serviceTypes",
				enuServiceTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		modelandmap.addObject("eventDTO", eventDTO);
		modelandmap.addObject("packageDetailsDTO", packageDetailsDTO);
		modelandmap.addObject("serviceIds", customerCreateEventTempDTO);
		modelandmap.addObject("eventTypes",
				enuEventTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		modelandmap.addObject("venueTypes", getVenueTypeOnEventType(eventDTO.getEventTypeId()));
		modelandmap.addObject("eventStatus",
				enuEventStatusService.findById(eventDTO.getEventStatusId().longValue()).getStatus());
		return modelandmap;
	}

	@PostMapping("/customer/edit_event")
	public ModelAndView saveEditedEventByCustomer(@Valid @ModelAttribute("eventDTO") EventDTO eventDTO,
			@Valid @ModelAttribute("packageDetailsDTO") PackageDetailsDTO packageDetailsDTO,
			@Valid @ModelAttribute("serviceIds") CustomerCreateEventTempDTO serviceIds,
			@RequestParam("banner") MultipartFile banner) {
		ModelAndView modelandmap = new ModelAndView("redirect:/customer/manage-events");

		EventDTO oldEventDTO = eventService.findById(eventDTO.getEventId().longValue());
		PackageDetailsDTO oldPackageDetailsDTO = packageDetailsService
				.findById(packageDetailsDTO.getPackageDetailsId().longValue());

		if (enuEventStatusService.findById(eventDTO.getEventStatusId().longValue()).getStatus().equals("Registered")) {
			if (oldPackageDetailsDTO.getIsStatic() != true) {
				packageDetailsService.delete(oldPackageDetailsDTO.getPackageDetailsId().longValue());
				packageServiceProviderMappingService
						.deleteByPackageId(oldPackageDetailsDTO.getPackageDetailsId().longValue());
			}

			if (packageDetailsDTO.getIsStatic() != true) {
				eventDTO.setPackageId(packageDetailsService.insertByCustomer(packageDetailsDTO).getPackageDetailsId());
				if (serviceIds.getServiceTypeIds() != null) {
					packageServiceProviderMappingService.insertByCustomer(eventDTO.getPackageId().longValue(),
							serviceIds.getServiceTypeIds());
				}
			} else {
				double cost = 0.0;
				if (eventDTO.getIsPublic() == true) {
					cost += 500.0;
				}
				if (!eventDTO.getIsFree() == true) {
					cost += 500.0;
				}
				eventDTO.setTotalCost(
						packageDetailsService.findById(eventDTO.getPackageId().longValue()).getTotalCost() + cost);
			}

			if (!eventDTO.getPackageId().equals(oldEventDTO.getPackageId())) {
				oldEventDTO.setPackageId(eventDTO.getPackageId());
			}
		}

		if (enuEventStatusService.findById(eventDTO.getEventStatusId().longValue()).getStatus().equals("Verified")) {
			double cost = oldEventDTO.getTotalCost();
			if (oldEventDTO.getIsPublic() == true) {
				cost -= 500.0;
			}
			if (!(oldEventDTO.getIsFree() == true)) {
				cost -= 500.0;
			}

			if (eventDTO.getIsPublic() == true) {
				cost += 500.0;
			}

			if (!eventDTO.getIsFree() == true) {
				cost += 500.0;
			}
			eventDTO.setTotalCost(cost);
		}

		if (!eventDTO.getEventTitle().equalsIgnoreCase(oldEventDTO.getEventTitle())) {
			oldEventDTO.setEventTitle(eventDTO.getEventTitle());
		}
		if (!eventDTO.getObjective().equalsIgnoreCase(oldEventDTO.getObjective())) {
			oldEventDTO.setObjective(eventDTO.getObjective());
		}
		if (!eventDTO.getEventTypeId().equals(oldEventDTO.getEventTypeId())) {
			oldEventDTO.setEventTypeId(eventDTO.getEventTypeId());
		}
		if (!eventDTO.getIsFree().equals(oldEventDTO.getIsFree())) {
			oldEventDTO.setIsFree(eventDTO.getIsFree());
			if (oldEventDTO.getIsFree() == true) {
				oldEventDTO.setRegistrationFee(0.0);
				oldEventDTO.setRegistrationAvailable(0);
			} else {
				if (!eventDTO.getRegistrationFee().equals(oldEventDTO.getRegistrationFee())) {
					oldEventDTO.setRegistrationFee(eventDTO.getRegistrationFee());
				}
				if (!eventDTO.getRegistrationAvailable().equals(oldEventDTO.getRegistrationAvailable())) {
					oldEventDTO.setRegistrationAvailable(eventDTO.getRegistrationAvailable());
				}
			}
		}
		if (!eventDTO.getIsPublic().equals(oldEventDTO.getIsPublic())) {
			oldEventDTO.setIsPublic(eventDTO.getIsPublic());
		}
		if (!eventDTO.getStartDate().equals(oldEventDTO.getStartDate())) {
			oldEventDTO.setStartDate(eventDTO.getStartDate());
		}
		if (!eventDTO.getStartTime().equals(oldEventDTO.getStartTime())) {
			oldEventDTO.setStartTime(eventDTO.getStartTime());
		}
		if (!eventDTO.getEndDate().equals(oldEventDTO.getEndDate())) {
			oldEventDTO.setEndDate(eventDTO.getEndDate());
		}
		if (!eventDTO.getEndTime().equals(oldEventDTO.getEndTime())) {
			oldEventDTO.setEndTime(eventDTO.getEndTime());
		}
		if (!eventDTO.getEstimatedGuest().equals(oldEventDTO.getEstimatedGuest())) {
			oldEventDTO.setEstimatedGuest(eventDTO.getEstimatedGuest());
		}
		if (!eventDTO.getTotalCost().equals(oldEventDTO.getTotalCost())) {
			oldEventDTO.setTotalCost(eventDTO.getTotalCost());
		}
		eventService.updateByAdmin(oldEventDTO);

		if (!banner.isEmpty()) {
			eventBannerService.delete(oldEventDTO.getEventId().longValue());
			EventBannerDTO obj = new EventBannerDTO();
			try {
				obj.setBanner(new SerialBlob(banner.getBytes()));
				obj.setEventId(oldEventDTO.getEventId());
			} catch (SQLException | IOException e) {
				log.error(e.toString());
			}
			eventBannerService.insert(obj);
		}

		return modelandmap;
	}

	public List<VenueDTO> getVenuesOnEventType(Long eventTypeId, Long venueTypeId) {
		List<VenueEventTypeMappingDTO> listVenues = venueEventTypeMappingService
				.findByNamedParameters(new MapSqlParameterSource().addValue("eventTypeId", eventTypeId));
		List<VenueDTO> venues = new ArrayList<>();

		for (int i = 0; i < listVenues.size(); i++) {
			VenueDTO temp = venueService.findById(listVenues.get(i).getVenueId().longValue());
			if (temp.getVenueTypeId().longValue() == venueTypeId) {
				venues.add(temp);
			}
		}

		return venues;
	}

	@GetMapping("/admin/verify_event/{eventId}")
	public ModelAndView verifyEvent(@PathVariable("eventId") long eventId) {
		ModelAndView modelandmap = new ModelAndView("admin/verify_event");
		EventDTO eventDTO = eventService.findById(eventId);
		PackageDetailsDTO packageDetailsDTO = packageDetailsService.findById(eventDTO.getPackageId().longValue());
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("isActive", true);
		List<EnuEventTypeDTO> eventTypes = enuEventTypeService.findByNamedParameters(paramSource);
		List<EnuVenueTypeDTO> venueTypes = enuVenueTypeService.findByNamedParameters(paramSource);
		List<PackageServiceProviderMappingDTO> packageServiceProviderMappings = packageServiceProviderMappingService
				.findByNamedParameters(
						new MapSqlParameterSource().addValue("packageId", packageDetailsDTO.getPackageDetailsId()));
		List<EnuServiceTypeDTO> serviceTypes = packageServiceProviderMappings.stream().map(entry -> {
			return enuServiceTypeService.findById(entry.getServiceTypeId().longValue());
		}).collect(Collectors.toList());
		List<EmployeeDTO> employees = employeeService
				.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true).addValue("employeeRoleId",
						enuEmployeeRoleService
								.findByNamedParameters(new MapSqlParameterSource().addValue("role", "Event Organizer"))
								.get(0).getEmployeeRoleId()));
		employees.forEach(employee -> {
			UserDetailsDTO user = userDetailsService.findById(employee.getUserDetailsId().longValue());
			employee.setFirstName(user.getFirstName());
			employee.setLastName(user.getLastName());
		});
		List<ServiceProviderDTO> serviceProviders = serviceProviderService.findAllActive();
		for (int i = 0; i < serviceProviders.size(); i++) {
			serviceProviders.get(i)
					.setServiceProviderName(
							userDetailsService
									.findByNamedParameters(new MapSqlParameterSource().addValue("userDetailsId",
											serviceProviders.get(i).getUserDetailsId()))
									.get(0).getServiceProviderName());
		}

		modelandmap.addObject("eventDTO", eventDTO);
		modelandmap.addObject("package", packageDetailsDTO);
		modelandmap.addObject("eventTypes", eventTypes);
		modelandmap.addObject("venueTypes", venueTypes);
		modelandmap.addObject("serviceTypes", serviceTypes);
		modelandmap.addObject("venues", getVenuesOnEventType(packageDetailsDTO.getEventTypeId().longValue(),
				packageDetailsDTO.getVenueTypeId().longValue()));
		modelandmap.addObject("employees", employees);
		modelandmap.addObject("serviceProviders", serviceProviders);
		modelandmap.addObject("packageTempDTO", new PackageTempDTO());
		return modelandmap;
	}

	@PostMapping("/admin/verify_event")
	public ModelAndView saveVerifyEvent(@Valid @ModelAttribute("eventDTO") EventDTO eventDTO,
			@Valid @ModelAttribute("package") PackageDetailsDTO packageDetailsDTO,
			@Valid @ModelAttribute("packageTempDTO") PackageTempDTO packageTempDTO) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-event");
		PackageDetailsDTO oldPackageDetailsDTO = packageDetailsService
				.findById(packageDetailsDTO.getPackageDetailsId().longValue());

		eventDTO.setEventStatusId(DataAccessUtils
				.singleResult(enuEventStatusService
						.findByNamedParameters(new MapSqlParameterSource().addValue("status", "Verified")))
				.getStatusId());

		if (oldPackageDetailsDTO.getIsStatic() != true) {
			packageServiceProviderMappingService.deleteByPackageId(packageDetailsDTO.getPackageDetailsId().longValue());
			if (packageTempDTO.getServiceProviderIdList() != null) {
				packageServiceProviderMappingService.assign(oldPackageDetailsDTO.getPackageDetailsId().longValue(),
						packageTempDTO.getServiceProviderIdList(),
						DataAccessUtils
								.singleResult(enuServiceProviderWorkingStatusService.findByNamedParameters(
										new MapSqlParameterSource().addValue("status", "Assigned")))
								.getStatusId().longValue());
			}
		} else {
			packageDetailsDTO.setVenueId(oldPackageDetailsDTO.getVenueId());
		}
		eventService.verifyEvent(eventDTO, packageDetailsDTO);

		EventDTO mailDTO = eventService.findById(eventDTO.getEventId().longValue());
		Mail mail = new Mail();
		mail.setMailTo(userDetailsService.findById(mailDTO.getUserDetailsId().longValue()).getEmail());
		mail.setMailSubject("Event Verified");
		mail.setContentType("text/html");
		String content = "<p>Your event <strong>" + mailDTO.getEventTitle() + " - " + mailDTO.getObjective()
				+ "</strong> on Unico - Event Planning and Management website has been verified by us.</p>"
				+ "<p>You will be notified about your event details.</p>";
		mail.setMailContent(content);
		mailService.sendEmail(mail);

		eventDetailsMail(eventDTO.getEventId().longValue());

		return modelandmap;
	}

	@GetMapping("/admin/unverify_event/{eventId}")
	public ModelAndView unVerifyEvent(@PathVariable("eventId") long eventId) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-event");
		eventService.unVerifyEvent(eventId);

		EventDTO eventDTO = eventService.findById(eventId);
		Mail mail = new Mail();
		mail.setMailTo(userDetailsService.findById(eventDTO.getUserDetailsId().longValue()).getEmail());
		mail.setMailSubject("Event Removed");
		mail.setContentType("text/html");
		String content = "<p>Your event <strong>" + eventDTO.getEventTitle() + " - " + eventDTO.getObjective()
				+ "</strong> on Unico - Event Planning and Management website has not been verified by us.</p>"
				+ "<p>We apologize for the inconvenience we caused you.</p>";
		mail.setMailContent(content);
		mailService.sendEmail(mail);
		return modelandmap;
	}

	@GetMapping("/assign_employee/{eventId}")
	public ModelAndView assignEmployees(@PathVariable("eventId") long eventId) {
		ModelAndView modelandmap = new ModelAndView("admin/assign_employee");
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("isActive", true);
		namedParams.addValue("eventId", eventId);
		List<EventEmployeeMappingDTO> employees = eventEmployeeMappingService.findByNamedParameters(namedParams);
		List<UserDetailsDTO> employeeBasicDetails = employees.stream().map(entry -> {
			return userDetailsService.findById(
					employeeService.findById(entry.getEmployeeId().longValue()).getUserDetailsId().longValue());
		}).collect(Collectors.toList());
		List<EnuEmployeeRoleDTO> employeeRoles = enuEmployeeRoleService
				.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true));
		List<String> employeeWorkingStatuses = employees.stream().map(entry -> {
			return enuEmployeeWorkingStatusService.findById(entry.getStatusId().longValue()).getStatus();
		}).collect(Collectors.toList());
		
		CustomUserDetailsDTO customUserDetailsDTO = (CustomUserDetailsDTO) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (customUserDetailsDTO.getRole().equalsIgnoreCase("ROLE_EVENTORGANIZER")) {
			modelandmap.addObject("layoutTitle", "Event Organizer");
			modelandmap.addObject("layoutPage", "employee/_layout");
		} else if(customUserDetailsDTO.getIsAdmin() == true) {
			modelandmap.addObject("layoutPage", "admin/_layout");
		}
		
		modelandmap.addObject("eventDTO", eventService.findById(eventId));
		modelandmap.addObject("employees", employees);
		modelandmap.addObject("employeeBasicDetails", employeeBasicDetails);
		modelandmap.addObject("employeeRoles", employeeRoles);
		modelandmap.addObject("employeeWorkingStatuses", employeeWorkingStatuses);
		return modelandmap;
	}

	@GetMapping("/view_assigned_employee/{eventEmployeeMappingId}")
	public ModelAndView viewAssignEmployees(@PathVariable("eventEmployeeMappingId") long eventEmployeeMappingId) {
		ModelAndView modelandmap = new ModelAndView("admin/view_assign_employee");

		EventEmployeeMappingDTO employee = eventEmployeeMappingService.findById(eventEmployeeMappingId);
		modelandmap.addObject("eventDTO", eventService.findById(employee.getEventId().longValue()));
		modelandmap.addObject("employee", employee);
		modelandmap.addObject("userDetailsDTO", userDetailsService.findById(
				employeeService.findById(employee.getEmployeeId().longValue()).getUserDetailsId().longValue()));
		modelandmap.addObject("employeeRole",
				enuEmployeeRoleService.findById(employee.getEmployeeTypeId().longValue()).getRole());

		CustomUserDetailsDTO customUserDetailsDTO = (CustomUserDetailsDTO) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (customUserDetailsDTO.getRole().equalsIgnoreCase("ROLE_EVENTORGANIZER")) {
			modelandmap.addObject("layoutTitle", "Event Organizer");
			modelandmap.addObject("layoutPage", "employee/_layout");
		} else if(customUserDetailsDTO.getIsAdmin() == true) {
			modelandmap.addObject("layoutPage", "admin/_layout");
		}
		return modelandmap;
	}

	@GetMapping("/getEmployees/{eventId}/{employeeRoleId}")
	public List<EmployeeDTO> getEmployees(@PathVariable("eventId") long eventId,
			@PathVariable("employeeRoleId") long employeeRoleId) {
		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("employeeRoleId", employeeRoleId);
		namedParams.addValue("isActive", true);
		List<EmployeeDTO> employees = employeeService.findByNamedParameters(namedParams);
		employees.forEach(employee -> {
			UserDetailsDTO user = userDetailsService.findById(employee.getUserDetailsId().longValue());
			employee.setFirstName(user.getFirstName());
			employee.setLastName(user.getLastName());
		});

		// Get Already Assigned Employees List
		List<EventEmployeeMappingDTO> alreadyAssignedEmployees = eventEmployeeMappingService.findByNamedParameters(
				new MapSqlParameterSource().addValue("isActive", true).addValue("eventId", eventId));
		List<String> employeeIdList = alreadyAssignedEmployees.stream().map(entry -> {
			return entry.getEmployeeId().toString();
		}).collect(Collectors.toList());

		// Filter it so they cannot be re-assigned
		List<EmployeeDTO> finalList = employees.stream()
				.filter(entry -> !(employeeIdList.contains(entry.getEmployeeId().toString())))
				.collect(Collectors.toList());

		return finalList;
	}

	@GetMapping("/assign_new_employee/{eventId}")
	public ModelAndView assignNewEmployee(@PathVariable("eventId") long eventId) {
		ModelAndView modelandmap = new ModelAndView("admin/assign_new_employee");
		modelandmap.addObject("eventDTO", eventService.findById(eventId));
		modelandmap.addObject("employee", new EventEmployeeMappingDTO());
		modelandmap.addObject("employeeRoles",
				enuEmployeeRoleService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		CustomUserDetailsDTO customUserDetailsDTO = (CustomUserDetailsDTO) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (customUserDetailsDTO.getRole().equalsIgnoreCase("ROLE_EVENTORGANIZER")) {
			modelandmap.addObject("layoutTitle", "Event Organizer");
			modelandmap.addObject("layoutPage", "employee/_layout");
		} else if(customUserDetailsDTO.getIsAdmin() == true) {
			modelandmap.addObject("layoutPage", "admin/_layout");
		}
		return modelandmap;
	}

	@PostMapping("/assign_new_employee")
	public ModelAndView saveAssignedNewEmployee(@Valid @ModelAttribute("eventDTO") EventDTO eventDTO,
			@Valid @ModelAttribute("employee") EventEmployeeMappingDTO eventEmployeeMappingDTO) {
		ModelAndView modelandmap = new ModelAndView("redirect:/assign_employee/" + eventDTO.getEventId().toString());
		eventEmployeeMappingDTO.setEventId(eventDTO.getEventId());
		eventEmployeeMappingService.insert(eventEmployeeMappingDTO);
		return modelandmap;
	}

	@GetMapping("/edit_assigned_employee/{eventEmployeeMappingId}")
	public ModelAndView getAssignedEmployee(@PathVariable("eventEmployeeMappingId") long eventEmployeeMappingId) {
		ModelAndView modelandmap = new ModelAndView("admin/edit_assign_employee");
		EventEmployeeMappingDTO employee = eventEmployeeMappingService.findById(eventEmployeeMappingId);

		MapSqlParameterSource namedParams = new MapSqlParameterSource();
		namedParams.addValue("employeeRoleId", employee.getEmployeeTypeId());
		namedParams.addValue("isActive", true);
		List<EmployeeDTO> employees = employeeService.findByNamedParameters(namedParams);
		employees.forEach(e -> {
			UserDetailsDTO user = userDetailsService.findById(e.getUserDetailsId().longValue());
			e.setFirstName(user.getFirstName());
			e.setLastName(user.getLastName());
		});

		modelandmap.addObject("eventDTO", eventService.findById(employee.getEventId().longValue()));
		modelandmap.addObject("employee", employee);
		modelandmap.addObject("selectedEmployees", employees);
		modelandmap.addObject("employeeRoles",
				enuEmployeeRoleService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		modelandmap.addObject("workingStatuses", enuEmployeeWorkingStatusService
				.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		CustomUserDetailsDTO customUserDetailsDTO = (CustomUserDetailsDTO) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (customUserDetailsDTO.getRole().equalsIgnoreCase("ROLE_EVENTORGANIZER")) {
			modelandmap.addObject("layoutTitle", "Event Organizer");
			modelandmap.addObject("layoutPage", "employee/_layout");
		} else if(customUserDetailsDTO.getIsAdmin() == true) {
			modelandmap.addObject("layoutPage", "admin/_layout");
		}
		return modelandmap;
	}

	@PostMapping("/edit_assigned_employee")
	public ModelAndView saveAssignedEditedEmployee(@Valid @ModelAttribute("eventDTO") EventDTO eventDTO,
			@Valid @ModelAttribute("employee") EventEmployeeMappingDTO eventEmployeeMappingDTO) {
		ModelAndView modelandmap = new ModelAndView("redirect:/assign_employee/" + eventDTO.getEventId().toString());
		EventEmployeeMappingDTO oldEventEmployeeMappingDTO = eventEmployeeMappingService
				.findById(eventEmployeeMappingDTO.getEventEmployeeMappingId().longValue());

		if (!oldEventEmployeeMappingDTO.getWorkDescription()
				.equalsIgnoreCase(eventEmployeeMappingDTO.getWorkDescription())) {
			oldEventEmployeeMappingDTO.setWorkDescription(eventEmployeeMappingDTO.getWorkDescription());
		}
		if (!oldEventEmployeeMappingDTO.getStatusId().equals(eventEmployeeMappingDTO.getStatusId())) {
			oldEventEmployeeMappingDTO.setStatusId(eventEmployeeMappingDTO.getStatusId());
		}

		eventEmployeeMappingService.update(oldEventEmployeeMappingDTO);
		return modelandmap;
	}

	@GetMapping("/delete_assigned_employee/{eventEmployeeMappingId}")
	public ModelAndView deleteAssignedEmployee(@PathVariable("eventEmployeeMappingId") long eventEmployeeMappingId) {
		EventEmployeeMappingDTO eventEmployeeMappingDTO = eventEmployeeMappingService.findById(eventEmployeeMappingId);
		ModelAndView modelandmap = new ModelAndView(
				"redirect:/assign_employee/" + eventEmployeeMappingDTO.getEventId().toString());
		eventEmployeeMappingService.delete(eventEmployeeMappingId);
		return modelandmap;
	}

	@GetMapping("/admin/complete_event/{eventId}")
	public ModelAndView completeEvent(@PathVariable("eventId") long eventId) {
		// ModelAndView modelandmap = new ModelAndView("admin/complete_event");
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-event");
		eventService.complete(eventId);
		// Ask for complete payment

		EventDTO eventDTO = eventService.findById(eventId);
		Mail mail = new Mail();
		mail.setMailTo(userDetailsService.findById(eventDTO.getUserDetailsId().longValue()).getEmail());
		mail.setMailSubject("Event Completed");
		mail.setContentType("text/html");
		String content = "<p>Your event <strong>" + eventDTO.getEventTitle() + " - " + eventDTO.getObjective()
				+ "</strong> on Unico - Event Planning and Management website has been completed.</p>"
				+ "<p>We apologize for the inconvenience we caused you at any stage of event planning.</p>"
				+ "<p>You can provide your invaluable feedback at the website.</p>"
				+ "<p>Hoping to meet you again on the next event organized by us.</p>";
		mail.setMailContent(content);
		mailService.sendEmail(mail);

		return modelandmap;
	}

	@GetMapping("/view_bill/{eventId}")
	public ModelAndView getBill(@PathVariable("eventId") long eventId) {
		ModelAndView modelandmap = new ModelAndView("admin/invoice");
		EventVenueDetailsDTO eventVenueDetailsDTO = invoiceService.getEventVenueDetails(eventId);
		modelandmap.addObject("eventVenueDetails", eventVenueDetailsDTO);
		modelandmap.addObject("venueAddress",
				getAddress(addressService.findById(eventVenueDetailsDTO.getAddressId().longValue())));

		// If isStatic then get Package Cost + additional Cost
		// Else venue cost,each serviceprovider cost(at creation time) + additional Cost
		List<InvoiceServiceProviderDTO> serviceProviderDTOs = invoiceService.getServiceProviderDetails(eventId);
		List<String> serviceProviderAddresses = serviceProviderDTOs.stream().map(serviceprovider -> {
			return getAddress(addressService.findById(serviceprovider.getAddressId()));
		}).collect(Collectors.toList());
		modelandmap.addObject("serviceProviders", serviceProviderDTOs);
		modelandmap.addObject("serviceProviderAddresses", serviceProviderAddresses);
		modelandmap.addObject("finalTotal", eventVenueDetailsDTO.getTotalCost());

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			modelandmap.addObject("layoutPage", "_layout");
		} else {
			CustomUserDetailsDTO userDetails = (CustomUserDetailsDTO) authentication.getPrincipal();
			if (userDetails.getIsCustomer() == true) {
				modelandmap.addObject("layoutPage", "customer/_layout");
			} else if (userDetails.getIsAdmin() == true) {
				modelandmap.addObject("layoutPage", "admin/_layout");
			} else if (userDetails.getIsServiceProvider() == true) {
				modelandmap.addObject("layoutPage", "serviceprovider/_layout");
			} else if (userDetails.getRole().equals("ROLE_EVENTORGANIZER")) {
				modelandmap.addObject("layoutPage", "eventorganizer/_layout");
			} else if (userDetails.getIsEmployee() == true) {
				modelandmap.addObject("layoutPage", "employee/_layout");
			} else {
				modelandmap.addObject("layoutPage", "_layout");
			}
		}
		return modelandmap;
	}

	@GetMapping("/admin/delete_event/{eventId}")
	public ModelAndView deleteEvent(@PathVariable("eventId") long eventId) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-event");
		PackageDetailsDTO packageDetailsDTO = packageDetailsService
				.findById(eventService.findById(eventId).getPackageId().longValue());

		eventService.delete(eventId);
		if (packageDetailsDTO.getIsStatic() == false) {
			packageDetailsService.delete(packageDetailsDTO.getPackageDetailsId().longValue());
			packageServiceProviderMappingService.removedFromEvent(packageDetailsDTO.getPackageDetailsId().longValue(),
					DataAccessUtils
							.singleResult(enuServiceProviderWorkingStatusService
									.findByNamedParameters(new MapSqlParameterSource().addValue("status", "Removed")))
							.getStatusId().longValue());
		}
		eventEmployeeMappingService.removedFromEvent(eventId);
		eventBannerService.delete(eventId);

		EventDTO eventDTO = eventService.findById(eventId);
		Mail mail = new Mail();
		mail.setMailTo(userDetailsService.findById(eventDTO.getUserDetailsId().longValue()).getEmail());
		mail.setMailSubject("Event Deleted");
		mail.setContentType("text/html");
		String content = "<p>Your event <strong>" + eventDTO.getEventTitle() + " - " + eventDTO.getObjective()
				+ "</strong> on Unico - Event Planning and Management website has been removed by us.</p>"
				+ "<p>We apologize for the inconvenience we caused you.</p>";
		mail.setMailContent(content);
		mailService.sendEmail(mail);

		// event isActive False
		// if dynamic Pacakge set isactive false && packageserviceprovidermapping set
		// isactive false
		// eventemployeemapping set isactive false
		// eventbanner set isactive false
		return modelandmap;
	}

	@GetMapping("/customer/complete_event/{eventId}")
	public ModelAndView completeEventByCustomer(@PathVariable("eventId") long eventId) {
		// ModelAndView modelandmap = new ModelAndView("admin/complete_event");
		ModelAndView modelandmap = new ModelAndView("redirect:/customer/manage-events");
		eventService.complete(eventId);
		// Ask for complete payment

		EventDTO eventDTO = eventService.findById(eventId);
		Mail mail = new Mail();
		mail.setMailTo(userDetailsService.findById(eventDTO.getUserDetailsId().longValue()).getEmail());
		mail.setMailSubject("Event Completed");
		mail.setContentType("text/html");
		String content = "<p>Your event <strong>" + eventDTO.getEventTitle() + " - " + eventDTO.getObjective()
				+ "</strong> on Unico - Event Planning and Management website has been completed.</p>"
				+ "<p>We apologize for the inconvenience we caused you at any stage of event planning.</p>"
				+ "<p>You can provide your invaluable feedback at the website.</p>"
				+ "<p>Hoping to meet you again on the next event organized by us.</p>";
		mail.setMailContent(content);
		mailService.sendEmail(mail);

		return modelandmap;
	}

	@GetMapping("/customer/delete_event/{eventId}")
	public ModelAndView deleteEventByCustomer(@PathVariable("eventId") long eventId) {
		ModelAndView modelandmap = new ModelAndView("redirect:/customer/manage-events");
		PackageDetailsDTO packageDetailsDTO = packageDetailsService
				.findById(eventService.findById(eventId).getPackageId().longValue());

		eventService.delete(eventId);
		if (packageDetailsDTO.getIsStatic() == false) {
			packageDetailsService.delete(packageDetailsDTO.getPackageDetailsId().longValue());
			packageServiceProviderMappingService.removedFromEvent(packageDetailsDTO.getPackageDetailsId().longValue(),
					DataAccessUtils
							.singleResult(enuServiceProviderWorkingStatusService
									.findByNamedParameters(new MapSqlParameterSource().addValue("status", "Removed")))
							.getStatusId().longValue());
		}
		eventEmployeeMappingService.removedFromEvent(eventId);
		eventBannerService.delete(eventId);

		EventDTO eventDTO = eventService.findById(eventId);
		Mail mail = new Mail();
		mail.setMailTo(userDetailsService.findById(eventDTO.getUserDetailsId().longValue()).getEmail());
		mail.setMailSubject("Event Deleted");
		mail.setContentType("text/html");
		String content = "<p>Your event <strong>" + eventDTO.getEventTitle() + " - " + eventDTO.getObjective()
				+ "</strong> on Unico - Event Planning and Management website has been removed by you.</p>"
				+ "<p>We apologize for the inconvenience we caused you.</p>";
		mail.setMailContent(content);
		mailService.sendEmail(mail);

		// event isActive False
		// if dynamic Pacakge set isactive false && packageserviceprovidermapping set
		// isactive false
		// eventemployeemapping set isactive false
		// eventbanner set isactive false
		return modelandmap;
	}

	@GetMapping("/customer/feedback/{eventId}")
	public ModelAndView giveFeedback(@PathVariable("eventId") long eventId) {
		ModelAndView modelandmap = new ModelAndView("customer/feedback");
		EventDTO eventDTO = eventService.findById(eventId);
		PackageDetailsDTO packageDetailsDTO = packageDetailsService.findById(eventDTO.getPackageId().longValue());

		List<PackageServiceProviderMappingDTO> packageServiceProviderMappings = packageServiceProviderMappingService
				.findByNamedParameters(
						new MapSqlParameterSource().addValue("packageId", packageDetailsDTO.getPackageDetailsId()));
		Map<String, ServiceProviderDTO> serviceWithProviders = new HashMap<>();
		for (PackageServiceProviderMappingDTO entry : packageServiceProviderMappings) {
			String service = enuServiceTypeService.findById(entry.getServiceTypeId().longValue()).getService();

			if (entry.getServiceProviderId() != null) {
				ServiceProviderDTO tempServiceProvider = serviceProviderService
						.findById(entry.getServiceProviderId().longValue());
				tempServiceProvider.setServiceProviderName(userDetailsService
						.findById(tempServiceProvider.getUserDetailsId().longValue()).getServiceProviderName());

				serviceWithProviders.put(service, tempServiceProvider);
			} else {
				serviceWithProviders.put(service, null);
			}
		}

		modelandmap.addObject("eventDTO", eventDTO);
		modelandmap.addObject("packageDetailsDTO", packageDetailsDTO);
		modelandmap.addObject("serviceWithProviders", serviceWithProviders);

		return modelandmap;
	}

	@GetMapping("/event-rating/{eventId}")
	public ModelAndView eventRating(@PathVariable("eventId") long eventId) {
		ModelAndView modelandmap = new ModelAndView("fragments :: eventRating");
		List<FeedbackDTO> feedbackDTO = feedbackService
				.findByNamedParameters(new MapSqlParameterSource().addValue("eventId", eventId));
		feedbackDTO = feedbackDTO.stream().filter(e -> e.getEventRating() != null).collect(Collectors.toList());
		if (feedbackDTO == null || feedbackDTO.size() == 0) {
			FeedbackDTO newFeedbackDTO = new FeedbackDTO();
			newFeedbackDTO.setEventRating(0.0);
			modelandmap.addObject("feedbackDTO", newFeedbackDTO);
		} else {
			modelandmap.addObject("feedbackDTO", feedbackDTO.get(0));
		}

		EventDTO eventDTO = eventService.findById(eventId);
		modelandmap.addObject("eventDTORating", eventDTO);
		return modelandmap;
	}

	@PostMapping("/event-rating")
	public ModelAndView saveEventRating(@Valid @ModelAttribute("eventDTORating") EventDTO eventDTO,
			@Valid @ModelAttribute("feedbackDTO") FeedbackDTO feedbackDTO) {
		ModelAndView modelandmap = new ModelAndView("redirect:/customer/feedback/" + eventDTO.getEventId());
		List<FeedbackDTO> oldFeedbackDTO = feedbackService
				.findByNamedParameters(new MapSqlParameterSource().addValue("eventId", eventDTO.getEventId()));
		oldFeedbackDTO = oldFeedbackDTO.stream().filter(e -> e.getEventRating() != null).collect(Collectors.toList());
		if (oldFeedbackDTO == null) {
			feedbackDTO.setEventId(eventDTO.getEventId());
			feedbackService.insert(feedbackDTO);
		} else {
			FeedbackDTO testFeedbackDTO = oldFeedbackDTO.get(0);
			if (!(testFeedbackDTO.getEventRating().equals(feedbackDTO.getEventRating()))) {
				testFeedbackDTO.setEventRating(feedbackDTO.getEventRating());
			}
			if (!(testFeedbackDTO.getEventDescription().equals(feedbackDTO.getEventDescription()))) {
				testFeedbackDTO.setEventDescription(feedbackDTO.getEventDescription());
			}

			feedbackService.update(testFeedbackDTO);
		}

		return modelandmap;
	}

	@GetMapping("/serviceprovider-rating/{eventId}/{serviceproviderId}")
	public ModelAndView serviceproviderRating(@PathVariable("eventId") long eventId,
			@PathVariable("serviceproviderId") long serviceproviderId) {
		ModelAndView modelandmap = new ModelAndView("fragments :: serviceproviderRating");
		FeedbackDTO feedbackDTO = DataAccessUtils
				.singleResult(feedbackService.findByNamedParameters(new MapSqlParameterSource()
						.addValue("eventId", eventId).addValue("serviceProviderId", serviceproviderId)));
		if (feedbackDTO == null) {
			FeedbackDTO newFeedbackDTO = new FeedbackDTO();
			newFeedbackDTO.setServiceProviderRating(0.0);
			newFeedbackDTO.setServiceProviderId((int) serviceproviderId);
			modelandmap.addObject("feedbackDTO", newFeedbackDTO);
		} else {
			modelandmap.addObject("feedbackDTO", feedbackDTO);
		}

		EventDTO eventDTO = eventService.findById(eventId);
		modelandmap.addObject("eventDTORating", eventDTO);
		return modelandmap;
	}

	@PostMapping("/serviceprovider-rating")
	public ModelAndView saveServiceproviderRating(@Valid @ModelAttribute("eventDTORating") EventDTO eventDTO,
			@Valid @ModelAttribute("feedbackDTO") FeedbackDTO feedbackDTO) {
		ModelAndView modelandmap = new ModelAndView("redirect:/customer/feedback/" + eventDTO.getEventId());
		FeedbackDTO oldFeedbackDTO = DataAccessUtils.singleResult(feedbackService
				.findByNamedParameters(new MapSqlParameterSource().addValue("eventId", eventDTO.getEventId())
						.addValue("serviceProviderId", feedbackDTO.getServiceProviderId())));
		if (oldFeedbackDTO == null) {
			feedbackDTO.setEventId(eventDTO.getEventId());
			feedbackService.insert(feedbackDTO);
		} else {
			if (!(oldFeedbackDTO.getServiceProviderRating().equals(feedbackDTO.getServiceProviderRating()))) {
				oldFeedbackDTO.setServiceProviderRating(feedbackDTO.getServiceProviderRating());
			}
			if (!(oldFeedbackDTO.getServiceProviderDescription().equals(feedbackDTO.getServiceProviderDescription()))) {
				oldFeedbackDTO.setServiceProviderDescription(feedbackDTO.getServiceProviderDescription());
			}

			feedbackService.update(oldFeedbackDTO);
		}

		return modelandmap;
	}

	@GetMapping("/package-rating/{eventId}/{packageId}")
	public ModelAndView packageRating(@PathVariable("eventId") long eventId,
			@PathVariable("packageId") long packageId) {
		ModelAndView modelandmap = new ModelAndView("fragments :: packageRating");
		FeedbackDTO feedbackDTO = DataAccessUtils.singleResult(feedbackService.findByNamedParameters(
				new MapSqlParameterSource().addValue("eventId", eventId).addValue("packageId", packageId)));
		if (feedbackDTO == null) {
			FeedbackDTO newFeedbackDTO = new FeedbackDTO();
			newFeedbackDTO.setPackageRating(0.0);
			newFeedbackDTO.setPackageId((int) packageId);
			modelandmap.addObject("feedbackDTO", newFeedbackDTO);
		} else {
			modelandmap.addObject("feedbackDTO", feedbackDTO);
		}

		EventDTO eventDTO = eventService.findById(eventId);
		modelandmap.addObject("eventDTORating", eventDTO);
		return modelandmap;
	}

	@PostMapping("/package-rating")
	public ModelAndView savePackageRating(@Valid @ModelAttribute("eventDTORating") EventDTO eventDTO,
			@Valid @ModelAttribute("feedbackDTO") FeedbackDTO feedbackDTO) {
		ModelAndView modelandmap = new ModelAndView("redirect:/customer/feedback/" + eventDTO.getEventId());
		FeedbackDTO oldFeedbackDTO = DataAccessUtils
				.singleResult(feedbackService.findByNamedParameters(new MapSqlParameterSource()
						.addValue("eventId", eventDTO.getEventId()).addValue("packageId", feedbackDTO.getPackageId())));
		if (oldFeedbackDTO == null) {
			feedbackDTO.setEventId(eventDTO.getEventId());
			feedbackService.insert(feedbackDTO);
		} else {
			if (!(oldFeedbackDTO.getPackageRating().equals(feedbackDTO.getPackageRating()))) {
				oldFeedbackDTO.setPackageRating(feedbackDTO.getPackageRating());
			}
			if (!(oldFeedbackDTO.getPackageDescription().equals(feedbackDTO.getPackageDescription()))) {
				oldFeedbackDTO.setPackageDescription(feedbackDTO.getPackageDescription());
			}

			feedbackService.update(oldFeedbackDTO);
		}

		return modelandmap;
	}
}