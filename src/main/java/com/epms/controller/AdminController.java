package com.epms.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.epms.dto.AddressDTO;
import com.epms.dto.EmployeeDTO;
import com.epms.dto.EnuEventTypeDTO;
import com.epms.dto.EnuServiceTypeDTO;
import com.epms.dto.EnuVenueFacilityDTO;
import com.epms.dto.EnuVenueTypeDTO;
import com.epms.dto.PackageDetailsDTO;
import com.epms.dto.ServiceProviderDTO;
import com.epms.dto.UserDetailsDTO;
import com.epms.dto.VenueDTO;
import com.epms.dto.VenueEventTypeMappingDTO;
import com.epms.dto.VenueFacilityMappingDTO;
import com.epms.dto.VenueImageMappingDTO;
import com.epms.dto.VenueTempDTO;
import com.epms.email.configuration.IMailService;
import com.epms.service.IAddressService;
import com.epms.service.IEmployeeService;
import com.epms.service.IEnuCityService;
import com.epms.service.IEnuCountryService;
import com.epms.service.IEnuEmployeeRoleService;
import com.epms.service.IEnuEventTypeService;
import com.epms.service.IEnuServiceTypeService;
import com.epms.service.IEnuStateService;
import com.epms.service.IEnuVenueFacilityService;
import com.epms.service.IEnuVenueTypeService;
import com.epms.service.IPackageDetailsService;
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

	public String getAddress(AddressDTO addressDTO) {
//		String address;
//		if (addressDTO.getIsActive() == true) {
//			address = addressDTO.getAddress1();
//			if (addressDTO.getAddress2() != null) {
//				address += ", " + addressDTO.getAddress2();
//			}
//			address += ",\n " + enuCityService.findById(addressDTO.getCityId().longValue()).getCity() + ", "
//					+ enuStateService.findById(addressDTO.getStateId().longValue()).getState() + ", "
//					+ enuCountryService.findById(addressDTO.getCountryId().longValue()).getCountry() + " - "
//					+ addressDTO.getPostalCode();
//		} else {
//			address = null;
//		}

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

	public String getVenueFacilities(Long venueId) {
		List<VenueFacilityMappingDTO> venueFacilities = venueFacilityMappingService
				.findByNamedParameters(new MapSqlParameterSource().addValue("venueId", venueId));
		String venueFacilitiesString = "";

		for (VenueFacilityMappingDTO item : venueFacilities) {
			venueFacilitiesString += "\n- "
					+ enuVenueFacilityService.findById(item.getFacilityId().longValue()).getFacility();
		}

		return venueFacilitiesString.substring(1);
	}

	public String getVenueEventTypes(Long venueId) {
		List<VenueEventTypeMappingDTO> venueEventTypes = venueEventTypeMappingService
				.findByNamedParameters(new MapSqlParameterSource().addValue("venueId", venueId));
		String venueEventTypesString = "";

		for (VenueEventTypeMappingDTO item : venueEventTypes) {
			venueEventTypesString += "\n- "
					+ enuEventTypeService.findById(item.getEventTypeId().longValue()).getEventType();
		}

		return venueEventTypesString.substring(1);
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

	@PostMapping("/add_employee")
	public ModelAndView addNewEmployee(@Valid @ModelAttribute("employeeDTO") EmployeeDTO employeeDTO,
			@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO) {
		final ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-employee");

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

	@PostMapping("/add_venue")
	public ModelAndView addNewVenue(@Valid @ModelAttribute("venueDTO") VenueDTO venueDTO,
			@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO,
			@Valid @ModelAttribute("venueTempDTO") VenueTempDTO venueTempDTO,
			@RequestParam("files") MultipartFile[] files) {
		final ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-venue");
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
		System.out.println(venueService.findById(venueId).getCost());
		return venueService.findById(venueId).getCost();
	}
	
	@GetMapping("/getServiceProviderCost/{serviceProviderId}")
	public Double getServiceProviderCost(@PathVariable Long serviceProviderId) {
		System.out.println(serviceProviderService.findById(serviceProviderId).getCost());
		return serviceProviderService.findById(serviceProviderId).getCost();
	}
	
	@PostMapping("/getPackageCost")
	public double getPackageCost(@RequestParam(value="packageData") List<String> packageData)
	{
		System.out.println(packageData);
		double cost = 0;
		cost += venueService.findById(new Long(packageData.get(0))).getCost();
		for(int i=0;i<packageData.size();i++)
		{
			cost += serviceProviderService.findById(new Long(packageData.get(i))).getCost();
		}
		return cost;
	}

	@GetMapping("/add_package")
	public ModelAndView addPackage() {
		ModelAndView modelandmap = new ModelAndView("admin/add_package");
		modelandmap.addObject("packageDetailsDTO", new PackageDetailsDTO());
		List<EnuServiceTypeDTO> serviceTypes = enuServiceTypeService.findAllActive();
		List<ServiceProviderDTO> serviceProviders = serviceProviderService.findAll();
		
		for(int i=0;i<serviceProviders.size();i++)
		{
			serviceProviders.get(i).setServiceProviderName(userDetailsService.findByNamedParameters(new MapSqlParameterSource().addValue("userDetailsId", serviceProviders.get(i).getUserDetailsId())).get(0).getServiceProviderName());
		}
	
		modelandmap.addObject("serviceTypes", serviceTypes);
		modelandmap.addObject("serviceProviders", serviceProviders);
		modelandmap.addObject("venueNames",
				venueService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		modelandmap.addObject("eventTypes",
				enuEventTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		modelandmap.addObject("serviceTypes",
				enuServiceTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		return modelandmap;
	}

	@PostMapping("/add_package")
	public ModelAndView addNewPackage(
			@Valid @ModelAttribute("serviceProviderDTO") ServiceProviderDTO serviceProviderDTO,
			@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO) {
		return new ModelAndView("redirect:/admin/list-package");
//		final ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-serviceprovider");
//
//		serviceProviderDTO.setAddressId(addressService.insert(addressDTO).getAddressId());
//
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		String encodedPassword = passwordEncoder.encode(serviceProviderDTO.getPassword());
//		serviceProviderDTO.setPassword(encodedPassword);
//
//		serviceProviderService.insert(serviceProviderDTO);
//		return modelandmap;
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
			@Valid @ModelAttribute("userDetailsDTO") UserDetailsDTO userDetailsDTO,
			@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO) {
		final ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-employee");

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
	public ModelAndView saveVenue(@Valid @ModelAttribute("venueDTO") VenueDTO venueDTO,
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

	@PostMapping("upload/files")
	public String handleFilesUpload(@RequestParam("files") MultipartFile[] files,
			@RequestParam("venueId") Integer venueId, ModelAndView map) {
		StringBuilder sb = new StringBuilder();

		for (MultipartFile file : files) {

			if (!file.isEmpty()) {
				VenueImageMappingDTO obj = new VenueImageMappingDTO();
				try {
					obj.setImage(new SerialBlob(file.getBytes()));
					obj.setVenueId(venueId);
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

//				try {
//					if (!Files.exists(Paths.get(UPLOAD_FOLDER))) {
//						try {
//							Files.createDirectories(Paths.get(UPLOAD_FOLDER));
//						} catch (IOException ioe) {
//							ioe.printStackTrace();
//						}
//					}
//
//					Files.copy(file.getInputStream(), Paths.get(UPLOAD_FOLDER, file.getOriginalFilename()));
//					sb.append("You successfully uploaded " + file.getOriginalFilename() + "!\n");
//
//					map.addAttribute("msg", sb.toString());
//				} catch (IOException | RuntimeException e) {
//					sb.append("Failued to upload " + (file != null ? file.getOriginalFilename() : "") + " => "
//							+ e.getMessage() + String.valueOf(e) + "\n");
//
//					map.addAttribute("msg", sb.toString());
//				}
//			} else {
//				sb.append("Failed to upload file\n");
//				map.addAttribute("msg", sb.toString());
			}
		}

		return "uploadfiles";
	}
}
