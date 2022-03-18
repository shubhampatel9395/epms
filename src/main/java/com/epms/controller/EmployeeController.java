package com.epms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epms.authentication.CustomUserDetailsDTO;
import com.epms.dto.EmployeeDTO;
import com.epms.dto.EmployeeDashboardDTO;
import com.epms.dto.EmployeeEventWorkDTO;
import com.epms.dto.ServiceProviderEventWorkDTO;
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
			}
			if (customUserDetailsDTO.getRole().equalsIgnoreCase("ROLE_EVENTORGANIZER")) {
				employeeDashboardDTO.setParticipatedEventCount(employeeService
						.getEventOrganizerCompletedEventCount(currentEmployee.getEmployeeId().longValue()));
				employeeDashboardDTO.setUpcomingEventCount(employeeService
						.getEventOrganizerUpcomingEventCount(currentEmployee.getEmployeeId().longValue()));
				List<ServiceProviderEventWorkDTO> completedEventWorkDTOs = employeeService
						.getEventOrganizerCompletedEventDetails(currentEmployee.getEmployeeId().longValue());
				List<ServiceProviderEventWorkDTO> upcomingEventWorkDTOs = employeeService
						.getEventOrganizerUpcomingEventDetails(currentEmployee.getEmployeeId().longValue());
				modelandmap.addObject("layoutTitle", "Event Organizer");
				modelandmap.addObject("completedEventWorkDTOs", completedEventWorkDTOs);
				modelandmap.addObject("upcomingEventWorkDTOs", upcomingEventWorkDTOs);
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
			List<ServiceProviderEventWorkDTO> upcomingEventWorkDTOs = employeeService
					.getEventOrganizerUpcomingEventDetails(currentEmployee.getEmployeeId().longValue());
			modelandmap.addObject("layoutTitle", "Event Organizer");
			modelandmap.addObject("upcomingEventWorkDTOs", upcomingEventWorkDTOs);
		}
		modelandmap.addObject("layoutPage", "employee/_layout");
		return modelandmap;
	}

	@GetMapping("/event-history")
	public ModelAndView eventHistory() {
		ModelAndView modelandmap = new ModelAndView("employee/event-history");
		return modelandmap;
	}

	@GetMapping("/payment-history")
	public ModelAndView paymentHistory() {
		ModelAndView modelandmap = new ModelAndView("employee/payment-history");
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
