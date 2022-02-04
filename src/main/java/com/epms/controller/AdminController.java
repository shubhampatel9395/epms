package com.epms.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.epms.dto.ServiceProviderDTO;
import com.epms.dto.UserDetailsDTO;
import com.epms.service.IAddressService;
import com.epms.service.IEnuCityService;
import com.epms.service.IEnuCountryService;
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

	@GetMapping("/dashboard")
	public ModelAndView homePage() {
		return new ModelAndView("admin/index");
	}

	@GetMapping("/list-customer")
	public ModelAndView listCustomer() {
		ModelAndView modelandmap = new ModelAndView("admin/customer");
		List<UserDetailsDTO> customers = userDetailsService.findAllActive();
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
		List<ServiceProviderDTO> serviceProviders = serviceProviderService.findAllActive();
		List<UserDetailsDTO> userDetails =  new ArrayList<>();
		List<String> serviceTypes = new ArrayList<>();
		List<String> addresses = new ArrayList<>();
		
		for (int i = 0; i < serviceProviders.size(); i++) {
			userDetails.add(userDetailsService.findById(serviceProviders.get(i).getUserDetailsId().longValue()));
		}
		
		for (int i = 0; i < serviceProviders.size(); i++) {
			serviceTypes.add(enuserviceTypeService.findById(serviceProviders.get(i).getServiceTypeId().longValue()).getService());
		}
		
		for (int i = 0; i < userDetails.size(); i++) {
			addresses.add(getAddress(addressService.findById(userDetails.get(i).getAddressId().longValue())));
		}
		
		modelandmap.addObject("serviceProviders", serviceProviders);
		modelandmap.addObject("userDetails",userDetails);
		modelandmap.addObject("serviceTypes",serviceTypes);
		modelandmap.addObject("addresses", addresses);
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

	@GetMapping("/view_customer/{customerId}")
	public ModelAndView viewCustomer(@PathVariable("customerId") long userDetailsId) {
		ModelAndView modelandmap = new ModelAndView("admin/view_customer");
		UserDetailsDTO userDetailsDTO = userDetailsService.findById(userDetailsId);
		modelandmap.addObject("customer", userDetailsDTO);
		AddressDTO addressDTO = addressService.findById(userDetailsDTO.getAddressId().longValue());
		modelandmap.addObject("address", getAddress(addressDTO));
		return modelandmap;
	}

	@GetMapping("/delete_customer/{customerId}")
	public ModelAndView deleteCustomer(@PathVariable("customerId") long userDetailsId) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-customer");
		userDetailsService.delete(userDetailsId);
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
		
		if(!(addressDTO.getAddress1().equals(oldAddressDTO.getAddress1())))
		{
			oldAddressDTO.setAddress1(addressDTO.getAddress1());
		}
		
		if(!(addressDTO.getAddress2().equals(oldAddressDTO.getAddress2())))
		{
			oldAddressDTO.setAddress2(addressDTO.getAddress2());
		}
		
		if(!(addressDTO.getCityId().equals(oldAddressDTO.getCityId())))
		{
			oldAddressDTO.setCityId(addressDTO.getCityId());
		}
		
		if(!(addressDTO.getStateId().equals(oldAddressDTO.getStateId())))
		{
			oldAddressDTO.setStateId(addressDTO.getStateId());
		}
		
		if(!(addressDTO.getCountryId().equals(oldAddressDTO.getCountryId())))
		{
			oldAddressDTO.setCountryId(addressDTO.getCountryId());
		}
		
		if(!(addressDTO.getPostalCode().equals(oldAddressDTO.getPostalCode())))
		{
			oldAddressDTO.setPostalCode(addressDTO.getPostalCode());
		}

		addressService.update(oldAddressDTO);
		userDetailsService.update(oldUserDetailsDTO);
		return modelandmap;
	}
}
