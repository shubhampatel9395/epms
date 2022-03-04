package com.epms.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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

import com.epms.dto.EmployeeDTO;
import com.epms.dto.EventBannerDTO;
import com.epms.dto.EventDTO;
import com.epms.dto.PackageDetailsDTO;
import com.epms.dto.PackageTempDTO;
import com.epms.dto.ServiceProviderDTO;
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
	
	@GetMapping("/admin/list-event")
	public ModelAndView listEvent() {
		ModelAndView modelandmap = new ModelAndView("admin/event");
		
		List<EventDTO> events = eventService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true));
		List<String> eventTypes = events.stream().map(event -> {
			return DataAccessUtils.singleResult(enuEventTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("eventTypeId", event.getEventTypeId()))).getEventType();
		}).collect(Collectors.toList());
		List<String> customers = events.stream().map(event -> {
			return DataAccessUtils.singleResult(userDetailsService.findByNamedParameters(new MapSqlParameterSource().addValue("userDetailsId", event.getUserDetailsId()))).getFirstName();
		}).collect(Collectors.toList());
		List<String> eventStatuses = events.stream().map(event -> {
			return DataAccessUtils.singleResult(enuEventStatusService.findByNamedParameters(new MapSqlParameterSource().addValue("statusId", event.getEventStatusId()))).getStatus();
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
			eventDTO.setPackageDetailsId(packageDetailsService.insert(packageDetailsDTO).getPackageDetailsId());
			if (packageTempDTO.getServiceProviderIdList() != null) {
				packageServiceProviderMappingService.insert(eventDTO.getPackageDetailsId().longValue(),
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
	
	@GetMapping("/admin/view_event/{eventId}")
	public ModelAndView viewEvent(@PathVariable("eventId") long eventId) {
		ModelAndView modelandmap = new ModelAndView("admin/view_event");
		// If registered?
		// Show only services
		
		// If verified?
		// Show services-with-service-providers & event organizer details
		
		// If completed?
		// Show services-with-service-providers & event organizer details
		
		return modelandmap;
	}
	
	@GetMapping("/admin/edit_event/{eventId}")
	public ModelAndView editEvent(@PathVariable("eventId") long eventId) {
		ModelAndView modelandmap = new ModelAndView("admin/edit_event");
		return modelandmap;
	}
	
	@GetMapping("/admin/verify_event/{eventId}")
	public ModelAndView verifyEvent(@PathVariable("eventId") long eventId) {
		ModelAndView modelandmap = new ModelAndView("admin/verify_event");
		// Ask to set EO & serviceproviders
		
		return modelandmap;
	}
	
	@GetMapping("/admin/assign_employee/{eventId}")
	public ModelAndView assignEmployees(@PathVariable("eventId") long eventId) {
		ModelAndView modelandmap = new ModelAndView("admin/assign_employee");
		// Show assigned employees-list(With view,edit,delete) with Assign employee Button(Add)
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
		// if dynamic Pacakge set isactive false && packageserviceprovidermapping set isactive false
		// eventemployeemapping set isactive false
		// eventbanner set isactive false
		return modelandmap;
	}
}
