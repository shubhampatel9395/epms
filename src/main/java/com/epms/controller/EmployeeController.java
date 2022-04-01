package com.epms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epms.authentication.CustomUserDetailsDTO;
import com.epms.dto.AddressDTO;
import com.epms.dto.AllServiceProvidersPackageDTO;
import com.epms.dto.EmployeeDTO;
import com.epms.dto.EmployeeDashboardDTO;
import com.epms.dto.EmployeeEventWorkDTO;
import com.epms.dto.EventDTO;
import com.epms.dto.EventEmployeeMappingDTO;
import com.epms.dto.EventOrganizerEventWorkDTO;
import com.epms.dto.ServiceProviderDTO;
import com.epms.dto.ServiceProviderEventWorkDTO;
import com.epms.dto.UserDetailsDTO;
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
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {
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

	@Autowired
	IEventEmployeeMappingService eventEmployeeMappingService;

	@Autowired
	IEnuEmployeeWorkingStatusService enuEmployeeWorkingStatusService;

	@GetMapping("/dashboard")
	public ModelAndView homePage() {
		ModelAndView modelandmap = new ModelAndView("employee/index");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return new ModelAndView("redirect:/login");
		} else {
			CustomUserDetailsDTO customUserDetailsDTO = (CustomUserDetailsDTO) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			EmployeeDTO currentEmployee = DataAccessUtils.singleResult(employeeService.findByNamedParameters(
					new MapSqlParameterSource().addValue("userDetailsId", customUserDetailsDTO.getUserDetailsId())));

			EmployeeDashboardDTO employeeDashboardDTO = new EmployeeDashboardDTO();
			if (customUserDetailsDTO.getRole().equalsIgnoreCase("ROLE_EMPLOYEE")) {
				employeeDashboardDTO.setParticipatedEventCount(
						employeeService.getEmployeeCompletedEventCount(currentEmployee.getEmployeeId().longValue()));
				employeeDashboardDTO.setUpcomingEventCount(
						employeeService.getEmployeeUpcomingEventCount(currentEmployee.getEmployeeId().longValue()));
				List<EmployeeEventWorkDTO> completedEventWorkDTOs = employeeService
						.getEmployeeCompletedEventDetails(currentEmployee.getEmployeeId().longValue());
				List<EmployeeEventWorkDTO> upcomingEventWorkDTOs = employeeService
						.getEmployeeUpcomingEventDetails(currentEmployee.getEmployeeId().longValue());
				modelandmap.addObject("layoutTitle", "Employee");
				modelandmap.addObject("completedEventWorkDTOs", completedEventWorkDTOs);
				modelandmap.addObject("upcomingEventWorkDTOs", upcomingEventWorkDTOs);
				modelandmap.addObject("upcomingEvents",
						employeeService.getUpcomingWeekEventsEmployees(currentEmployee.getEmployeeId().longValue()));
			}
			if (customUserDetailsDTO.getRole().equalsIgnoreCase("ROLE_EVENTORGANIZER")) {
				employeeDashboardDTO.setParticipatedEventCount(employeeService
						.getEventOrganizerCompletedEventCount(currentEmployee.getEmployeeId().longValue()));
				employeeDashboardDTO.setUpcomingEventCount(employeeService
						.getEventOrganizerUpcomingEventCount(currentEmployee.getEmployeeId().longValue()));
				List<EventOrganizerEventWorkDTO> completedEventWorkDTOs = employeeService
						.getEventOrganizerCompletedEventDetails(currentEmployee.getEmployeeId().longValue());
				List<EventOrganizerEventWorkDTO> upcomingEventWorkDTOs = employeeService
						.getEventOrganizerUpcomingEventDetails(currentEmployee.getEmployeeId().longValue());
				modelandmap.addObject("layoutTitle", "Event Organizer");
				modelandmap.addObject("completedEventWorkDTOs", completedEventWorkDTOs);
				modelandmap.addObject("upcomingEventWorkDTOs", upcomingEventWorkDTOs);
				modelandmap.addObject("upcomingEvents", employeeService
						.getUpcomingWeekEventsEventOrganizer(currentEmployee.getEmployeeId().longValue()));
			}
			modelandmap.addObject("layoutPage", "employee/_layout");
			modelandmap.addObject("employeeDashboardDTO", employeeDashboardDTO);

			return modelandmap;
		}
	}

	@GetMapping("/assigned-work")
	public ModelAndView assignedWork() {
		// ModelAndView modelandmap = new ModelAndView("employee/assigned-work");
		ModelAndView modelandmap = new ModelAndView("employee/assigned-events");
		CustomUserDetailsDTO customUserDetailsDTO = (CustomUserDetailsDTO) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		EmployeeDTO currentEmployee = DataAccessUtils.singleResult(employeeService.findByNamedParameters(
				new MapSqlParameterSource().addValue("userDetailsId", customUserDetailsDTO.getUserDetailsId())));

		if (customUserDetailsDTO.getRole().equalsIgnoreCase("ROLE_EMPLOYEE")) {
			List<EmployeeEventWorkDTO> upcomingEventWorkDTOs = employeeService
					.getEmployeeUpcomingEventDetails(currentEmployee.getEmployeeId().longValue());
			modelandmap.addObject("layoutTitle", "Employee");
			modelandmap.addObject("upcomingEventWorkDTOs", upcomingEventWorkDTOs);
		}
		if (customUserDetailsDTO.getRole().equalsIgnoreCase("ROLE_EVENTORGANIZER")) {
			List<EventOrganizerEventWorkDTO> upcomingEventWorkDTOs = employeeService
					.getEventOrganizerUpcomingEventDetails(currentEmployee.getEmployeeId().longValue());
			modelandmap.addObject("layoutTitle", "Event Organizer");
			modelandmap.addObject("upcomingEventWorkDTOs", upcomingEventWorkDTOs);
		}
		modelandmap.addObject("layoutPage", "employee/_layout");
		return modelandmap;
	}

	@GetMapping("/edit_work_details/{eventId}")
	public ModelAndView editWorkDetails(@PathVariable Long eventId) {
		// ModelAndView modelandmap = new ModelAndView("employee/assigned-work");
		ModelAndView modelandmap = new ModelAndView("employee/edit_work_details");
		CustomUserDetailsDTO customUserDetailsDTO = (CustomUserDetailsDTO) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		EmployeeDTO currentEmployee = DataAccessUtils.singleResult(employeeService.findByNamedParameters(
				new MapSqlParameterSource().addValue("userDetailsId", customUserDetailsDTO.getUserDetailsId())));

		if (customUserDetailsDTO.getRole().equalsIgnoreCase("ROLE_EMPLOYEE")) {
			EventEmployeeMappingDTO employee = DataAccessUtils
					.singleResult(eventEmployeeMappingService.findByNamedParameters(new MapSqlParameterSource()
							.addValue("eventId", eventId).addValue("employeeId", currentEmployee.getEmployeeId())));

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
			modelandmap.addObject("employeeRoles", enuEmployeeRoleService
					.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
			modelandmap.addObject("workingStatuses", enuEmployeeWorkingStatusService
					.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
			modelandmap.addObject("layoutTitle", "Employee");
		}
		if (customUserDetailsDTO.getRole().equalsIgnoreCase("ROLE_EVENTORGANIZER")) {
			modelandmap.addObject("layoutTitle", "Event Organizer");
		}
		modelandmap.addObject("layoutPage", "employee/_layout");
		return modelandmap;
	}

	@PostMapping("/edit_work_details")
	public ModelAndView saveEditedEmployeeWorkDetails(@Valid @ModelAttribute("eventDTO") EventDTO eventDTO,
			@Valid @ModelAttribute("employee") EventEmployeeMappingDTO eventEmployeeMappingDTO) {
		ModelAndView modelandmap = new ModelAndView("redirect:/employee/assigned-work");
		EventEmployeeMappingDTO oldEventEmployeeMappingDTO = eventEmployeeMappingService
				.findById(eventEmployeeMappingDTO.getEventEmployeeMappingId().longValue());

		if (!oldEventEmployeeMappingDTO.getStatusId().equals(eventEmployeeMappingDTO.getStatusId())) {
			oldEventEmployeeMappingDTO.setStatusId(eventEmployeeMappingDTO.getStatusId());
		}

		eventEmployeeMappingService.update(oldEventEmployeeMappingDTO);
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
		ModelAndView modelandmap = new ModelAndView("/employee/view_event");
		CustomUserDetailsDTO customUserDetailsDTO = (CustomUserDetailsDTO) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		EmployeeDTO currentEmployee = DataAccessUtils.singleResult(employeeService.findByNamedParameters(
				new MapSqlParameterSource().addValue("userDetailsId", customUserDetailsDTO.getUserDetailsId())));

		if (customUserDetailsDTO.getRole().equalsIgnoreCase("ROLE_EMPLOYEE")) {
			EmployeeEventWorkDTO event = employeeService.getEmployeeEventsDetails(eventId,
					currentEmployee.getEmployeeId().longValue());
			modelandmap.addObject("layoutTitle", "Employee");
			modelandmap.addObject("event", event);
			modelandmap.addObject("addressVenue", getAddress(addressService.findById(event.getAddressId())));
			modelandmap.addObject("allEmployees", eventService.getAllAssignedEmployees(eventId));
		}
		if (customUserDetailsDTO.getRole().equalsIgnoreCase("ROLE_EVENTORGANIZER")) {
			EventOrganizerEventWorkDTO event = employeeService.getEventOrganizerEventDetails(eventId,
					currentEmployee.getEmployeeId().longValue());
			List<AllServiceProvidersPackageDTO> allServiceProviders = employeeService
					.getAllServiceProviderOnPackage(currentEmployee.getEmployeeId().longValue());
			modelandmap.addObject("layoutTitle", "Event Organizer");
			modelandmap.addObject("event", event);
			modelandmap.addObject("addressVenue", getAddress(addressService.findById(event.getAddressId())));
			modelandmap.addObject("allServiceProviders", allServiceProviders);
			modelandmap.addObject("allEmployees", eventService.getAllAssignedEmployees(eventId));
		}
		modelandmap.addObject("layoutPage", "employee/_layout");
		return modelandmap;
	}

	@GetMapping("/event-history")
	public ModelAndView eventHistory() {
		ModelAndView modelandmap = new ModelAndView("employee/event-history");
		CustomUserDetailsDTO customUserDetailsDTO = (CustomUserDetailsDTO) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		EmployeeDTO currentEmployee = DataAccessUtils.singleResult(employeeService.findByNamedParameters(
				new MapSqlParameterSource().addValue("userDetailsId", customUserDetailsDTO.getUserDetailsId())));

		if (customUserDetailsDTO.getRole().equalsIgnoreCase("ROLE_EMPLOYEE")) {
			List<EmployeeEventWorkDTO> completedEventWorkDTOs = employeeService
					.getEmployeeCompletedEventDetails(currentEmployee.getEmployeeId().longValue());
			modelandmap.addObject("layoutTitle", "Employee");
			modelandmap.addObject("completedEventWorkDTOs", completedEventWorkDTOs);
		}
		if (customUserDetailsDTO.getRole().equalsIgnoreCase("ROLE_EVENTORGANIZER")) {
			List<EventOrganizerEventWorkDTO> completedEventWorkDTOs = employeeService
					.getEventOrganizerCompletedEventDetails(currentEmployee.getEmployeeId().longValue());
			modelandmap.addObject("layoutTitle", "Event Organizer");
			modelandmap.addObject("completedEventWorkDTOs", completedEventWorkDTOs);
		}
		modelandmap.addObject("layoutPage", "employee/_layout");
		return modelandmap;
	}

	@GetMapping("/payment-history")
	public ModelAndView paymentHistory() {
		ModelAndView modelandmap = new ModelAndView("employee/payment-history");
		CustomUserDetailsDTO customUserDetailsDTO = (CustomUserDetailsDTO) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		EmployeeDTO currentEmployee = DataAccessUtils.singleResult(employeeService.findByNamedParameters(
				new MapSqlParameterSource().addValue("userDetailsId", customUserDetailsDTO.getUserDetailsId())));

		if (customUserDetailsDTO.getRole().equalsIgnoreCase("ROLE_EMPLOYEE")) {
			modelandmap.addObject("layoutTitle", "Employee");
		}
		if (customUserDetailsDTO.getRole().equalsIgnoreCase("ROLE_EVENTORGANIZER")) {
			modelandmap.addObject("layoutTitle", "Event Organizer");
		}
		modelandmap.addObject("layoutPage", "employee/_layout");
		return modelandmap;
	}

	@GetMapping("/feedbacks")
	public ModelAndView feedback() {
		ModelAndView modelandmap = new ModelAndView("employee/feedbacks");
		return modelandmap;
	}

	@GetMapping("logout")
	public ModelAndView logoutPage() {
		return new ModelAndView("redirect:/login");
	}
}
