package com.epms.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.rowset.serial.SerialBlob;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.epms.dto.AddressDTO;
import com.epms.dto.EmployeeDTO;
import com.epms.dto.EnuEmployeeRoleDTO;
import com.epms.dto.EnuEventTypeDTO;
import com.epms.dto.EnuServiceTypeDTO;
import com.epms.dto.EnuVenueTypeDTO;
import com.epms.dto.EventBannerDTO;
import com.epms.dto.EventDTO;
import com.epms.dto.EventEmployeeMappingDTO;
import com.epms.dto.PackageDetailsDTO;
import com.epms.dto.PackageServiceProviderMappingDTO;
import com.epms.dto.PackageTempDTO;
import com.epms.dto.ServiceProviderDTO;
import com.epms.dto.UserDetailsDTO;
import com.epms.dto.VenueDTO;
import com.epms.dto.VenueEventTypeMappingDTO;
import com.epms.email.configuration.IMailService;
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
import com.epms.service.IEnuServiceTypeService;
import com.epms.service.IEnuStateService;
import com.epms.service.IEnuVenueFacilityService;
import com.epms.service.IEnuVenueTypeService;
import com.epms.service.IEventBannerService;
import com.epms.service.IEventEmployeeMappingService;
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
@Slf4j
public class EventController {
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

		modelandmap.addObject("events", events);
		modelandmap.addObject("eventTypes", eventTypes);
		modelandmap.addObject("customers", customers);
		modelandmap.addObject("eventStatuses", eventStatuses);
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

	@GetMapping("/admin/view_event/{eventId}")
	public ModelAndView viewEvent(@PathVariable("eventId") long eventId) {
		ModelAndView modelandmap = new ModelAndView("admin/view_event");
		EventDTO eventDTO = eventService.findById(eventId);
		UserDetailsDTO customerDTO = userDetailsService.findById(eventDTO.getUserDetailsId().longValue());
		PackageDetailsDTO packageDetailsDTO = packageDetailsService.findById(eventDTO.getPackageId().longValue());
		VenueDTO venueDTO = venueService.findById(packageDetailsDTO.getVenueId().longValue());

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
		modelandmap.addObject("addressVenue", getAddress(addressService.findById(venueDTO.getAddressId().longValue())));
		modelandmap.addObject("serviceWithProviders", serviceWithProviders);

		return modelandmap;
	}

	@GetMapping("/admin/edit_event/{eventId}")
	public ModelAndView editEvent(@PathVariable("eventId") long eventId) {
		ModelAndView modelandmap = new ModelAndView("admin/edit_event");
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
				packageServiceProviderMappingService.insert(oldPackageDetailsDTO.getPackageDetailsId().longValue(),
						packageTempDTO.getServiceProviderIdList());
			}
		} else {
			packageDetailsDTO.setVenueId(oldPackageDetailsDTO.getVenueId());
		}
		eventService.verifyEvent(eventDTO, packageDetailsDTO);
		return modelandmap;
	}

	@GetMapping("/admin/unverify_event/{eventId}")
	public ModelAndView unVerifyEvent(@PathVariable("eventId") long eventId) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-event");
		eventService.unVerifyEvent(eventId);
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
		return modelandmap;
	}
	
	@PostMapping("/edit_assigned_employee")
	public ModelAndView saveAssignedEditedEmployee(@Valid @ModelAttribute("eventDTO") EventDTO eventDTO,
			@Valid @ModelAttribute("employee") EventEmployeeMappingDTO eventEmployeeMappingDTO) {
		ModelAndView modelandmap = new ModelAndView("redirect:/assign_employee/" + eventDTO.getEventId().toString());
		EventEmployeeMappingDTO oldEventEmployeeMappingDTO = eventEmployeeMappingService.findById(eventEmployeeMappingDTO.getEventEmployeeMappingId().longValue());
		
		if(!oldEventEmployeeMappingDTO.getWorkDescription().equalsIgnoreCase(eventEmployeeMappingDTO.getWorkDescription())) {
			oldEventEmployeeMappingDTO.setWorkDescription(eventEmployeeMappingDTO.getWorkDescription());
		}
		if(!oldEventEmployeeMappingDTO.getStatusId().equals(eventEmployeeMappingDTO.getStatusId())) {
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
		ModelAndView modelandmap = new ModelAndView("admin/complete_event");
		// Ask for complete payment
		return modelandmap;
	}

	@GetMapping("/admin/view_bill/{eventId}")
	public ModelAndView getBill(@PathVariable("eventId") long eventId) {
		ModelAndView modelandmap = new ModelAndView("admin/view_bill");
		// Show bill
		return modelandmap;
	}

	@GetMapping("/admin/delete_event/{eventId}")
	public ModelAndView deleteEvent(@PathVariable("eventId") long eventId) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-event");
		// event isActive False
		// if dynamic Pacakge set isactive false && packageserviceprovidermapping set
		// isactive false
		// eventemployeemapping set isactive false
		// eventbanner set isactive false
		return modelandmap;
	}
}
