package com.epms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epms.dto.AddressDTO;
import com.epms.dto.UserDetailsDTO;
import com.epms.service.IEnuCountryService;
import com.epms.service.IUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
	@Autowired
	IUserDetailsService userDetailsService;
	
	@Autowired
	IEnuCountryService enuCountryService;

	@GetMapping("/dashboard")
	public ModelAndView homePage() {
		return new ModelAndView("admin/index");
	}

	@GetMapping("/list-customer")
	public ModelAndView listCustomer() {
		return new ModelAndView("admin/customer");
	}

	@GetMapping("/add_customer")
	public ModelAndView addCustomer() {
		ModelAndView modelandmap = new ModelAndView("admin/add_customer");
		modelandmap.addObject("countries", enuCountryService.findAll());
		
		//TODO make form object
		modelandmap.addObject("userDetailsDTO", new UserDetailsDTO());
		modelandmap.addObject("addressDTO", new AddressDTO());
		return modelandmap;
	}
	
	@GetMapping("/view_customer/{customerId}")
	public ModelAndView viewCustomer(@PathVariable("customerId") long userDetailsId) {
		ModelAndView modelandmap = new ModelAndView("admin/view_customer");
		modelandmap.addObject("customer", userDetailsService.findById(userDetailsId));
		return modelandmap;
	}
	
	@GetMapping("/delete_customer/{customerId}")
	public ModelAndView deleteCustomer(@PathVariable("customerId") long userDetailsId) {
		ModelAndView modelandmap = new ModelAndView("redirect:/admin/list-customer");
		userDetailsService.delete(userDetailsId);
		return modelandmap;
	}
}
