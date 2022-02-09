package com.epms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epms.dto.AddressDTO;
import com.epms.dto.EmployeeDTO;
import com.epms.dto.ServiceProviderDTO;
import com.epms.dto.UserDetailsDTO;
import com.epms.service.IAddressService;
import com.epms.service.IEmployeeService;
import com.epms.service.IEnuCityService;
import com.epms.service.IEnuCountryService;
import com.epms.service.IEnuEmployeeRoleService;
import com.epms.service.IEnuServiceTypeService;
import com.epms.service.IEnuStateService;
import com.epms.service.IServiceProviderService;
import com.epms.service.IUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
	@Autowired
	IEnuCityService enuCityService;

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
	
	public String getAddress(AddressDTO addressDTO) {
		String address;
		if (addressDTO.getIsActive() == true) {
			address = addressDTO.getAddress1();
			if (addressDTO.getAddress2() != null) {
				address += ", " + addressDTO.getAddress2();
			}
			address += ",\n " + enuCityService.findById(addressDTO.getCityId().longValue()).getCity() + ", "
					+ enuStateService.findById(addressDTO.getStateId().longValue()).getState() + ", "
					+ enuCountryService.findById(addressDTO.getCountryId().longValue()).getCountry() + " - "
					+ addressDTO.getPostalCode();
		} else {
			address = null;
		}
		return address;
	}

	@GetMapping("/dashboard")
	public ModelAndView homePage() {
		return new ModelAndView("admin/index");
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

	
	@GetMapping("/authenticate-serviceprovider/{serviceProviderId}")
	public ModelAndView authenticateServiceprovider(@PathVariable("serviceProviderId") long serviceProviderId) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-serviceprovider");
		userDetailsService.activate(serviceProviderService.findById(serviceProviderId).getUserDetailsId().longValue());
		serviceProviderService.authenticate(serviceProviderId);
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

	@GetMapping("/add_customer")
	public ModelAndView addCustomer() {
		ModelAndView modelandmap = new ModelAndView("admin/add_customer");
		modelandmap.addObject("countries", enuCountryService.findAll());

		// TODO make form object
		modelandmap.addObject("userDetailsDTO", new UserDetailsDTO());
		modelandmap.addObject("addressDTO", new AddressDTO());
		return modelandmap;
	}

	@PostMapping("/add_customer")
	public ModelAndView addNewCustomer(@Valid @ModelAttribute("userDetailsDTO") UserDetailsDTO userDetailsDTO,
			@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO) {
		final ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-customer");

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
			@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO) {
		final ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-serviceprovider");

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
	
	@GetMapping("/add_venue")
	public ModelAndView addVenue() {
		ModelAndView modelandmap = new ModelAndView("admin/add_venue");
		modelandmap.addObject("countries", enuCountryService.findAll());
//		modelandmap.addObject("employeeRoles", enuEmployeeRoleService.findAll());
//		modelandmap.addObject("employeeDTO", new EmployeeDTO());
		modelandmap.addObject("addressDTO", new AddressDTO());
		return modelandmap;
	}
	
	@PostMapping("/add_employee")
	public ModelAndView addNewEmployee(
			@Valid @ModelAttribute("employeeDTO") EmployeeDTO employeeDTO,
			@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO) {
		final ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-employee");

		employeeDTO.setAddressId(addressService.insert(addressDTO).getAddressId());

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(employeeDTO.getPassword());
		employeeDTO.setPassword(encodedPassword);

		employeeService.insert(employeeDTO);
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
		UserDetailsDTO userDetailsDTO = userDetailsService.findById(serviceProviderService.findById(serviceProviderId).getUserDetailsId().longValue());
		
		addressService.delete(userDetailsDTO.getAddressId().longValue());
		userDetailsService.delete(userDetailsDTO.getUserDetailsId().longValue());
		serviceProviderService.delete(serviceProviderId);
		
		return modelandmap;
	}

	@GetMapping("/delete_employee/{employeeId}")
	public ModelAndView deleteEmployee(@PathVariable("employeeId") long employeeId) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-employee");
		UserDetailsDTO userDetailsDTO = userDetailsService.findById(employeeService.findById(employeeId).getUserDetailsId().longValue());
		
		addressService.delete(userDetailsDTO.getAddressId().longValue());
		userDetailsService.delete(userDetailsDTO.getUserDetailsId().longValue());
		employeeService.delete(employeeId);
		
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

		modelandmap.addObject("countries", enuCountryService.findAll());

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("countryId", addressDTO.getCountryId());
		modelandmap.addObject("states", enuStateService.findByNamedParameters(paramSource));

		paramSource = new MapSqlParameterSource();
		paramSource.addValue("stateId", addressDTO.getStateId());
		modelandmap.addObject("cities", enuCityService.findByNamedParameters(paramSource));
		return modelandmap;
	}

	@PostMapping("/edit_customer")
	public ModelAndView updateCustomer(@Valid @ModelAttribute("userDetailsDTO") UserDetailsDTO userDetailsDTO,
			@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO) {
		final ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-customer");
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
			@Valid @ModelAttribute("userDetailsDTO") UserDetailsDTO userDetailsDTO,
			@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO) {
		final ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-serviceprovider");
		
		ServiceProviderDTO oldserviceProviderDTO = serviceProviderService.findById(serviceProviderDTO.getServiceProviderId().longValue());
		// UserDetailsDTO oldUserDetailsDTO = userDetailsService.findById(userDetailsDTO.getUserDetailsId().longValue());
		AddressDTO oldAddressDTO = addressService.findById(addressDTO.getAddressId().longValue());

		/*
		if (!(userDetailsDTO.getServiceProviderName().equals(oldUserDetailsDTO.getServiceProviderName()))) {
			oldUserDetailsDTO.setServiceProviderName(userDetailsDTO.getServiceProviderName());
		}

		if (!(userDetailsDTO.getEmail().equals(oldUserDetailsDTO.getEmail()))) {
			oldUserDetailsDTO.setEmail(userDetailsDTO.getEmail());
		}
		
		if (!(userDetailsDTO.getMobileNumber().equals(oldUserDetailsDTO.getMobileNumber()))) {
			oldUserDetailsDTO.setMobileNumber(userDetailsDTO.getMobileNumber());
		}
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
		AddressDTO addressDTO = addressService.findById(userDetailsDTO.getAddressId().longValue());

		modelandmap.addObject("employeeDTO", employeeDTO);
		modelandmap.addObject("userDetailsDTO", userDetailsDTO);
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

	@PostMapping("/edit_employee")
	public ModelAndView updateEmployee(
			@Valid @ModelAttribute("employeeDTO") EmployeeDTO employeeDTO,
			@Valid @ModelAttribute("userDetailsDTO") UserDetailsDTO userDetailsDTO,
			@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO) {
		final ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-employee");
		
		EmployeeDTO oldEmployeeDTO = employeeService.findById(employeeDTO.getEmployeeId().longValue());
		AddressDTO oldAddressDTO = addressService.findById(addressDTO.getAddressId().longValue());

		employeeDTO.setFirstName(userDetailsDTO.getFirstName());
		employeeDTO.setLastName(userDetailsDTO.getLastName());
		employeeDTO.setEmail(userDetailsDTO.getEmail());
		employeeDTO.setMobileNumber(userDetailsDTO.getMobileNumber());
		if (!(employeeDTO.getFirstName().equals(userDetailsDTO.getFirstName()))) {
			oldEmployeeDTO.setFirstName(userDetailsDTO.getFirstName());
		}

		if (!(employeeDTO.getLastName().equals(userDetailsDTO.getLastName()))) {
			oldEmployeeDTO.setLastName(userDetailsDTO.getLastName());
		}

		if (!(employeeDTO.getEmail().equals(userDetailsDTO.getEmail()))) {
			oldEmployeeDTO.setEmail(userDetailsDTO.getEmail());
		}

		if (!(employeeDTO.getMobileNumber().equals(userDetailsDTO.getMobileNumber()))) {
			oldEmployeeDTO.setMobileNumber(userDetailsDTO.getMobileNumber());
		}

		if (!(employeeDTO.getSalary().equals(oldEmployeeDTO.getSalary()))) {
			oldEmployeeDTO.setSalary(employeeDTO.getSalary());
		}

		if (!(employeeDTO.getGender().equals(oldEmployeeDTO.getGender()))) {
			oldEmployeeDTO.setGender(employeeDTO.getGender());
		}
		
		if (!(employeeDTO.getHiringDate().equals(oldEmployeeDTO.getHiringDate()))) {
			oldEmployeeDTO.setHiringDate(employeeDTO.getHiringDate());
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
		employeeService.update(oldEmployeeDTO);
		return modelandmap;
	}
}
