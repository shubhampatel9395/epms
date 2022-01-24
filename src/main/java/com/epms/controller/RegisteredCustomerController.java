package com.epms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/customer")
@Slf4j
public class RegisteredCustomerController {
	@GetMapping
	public ModelAndView homePage() {
		return new ModelAndView("customer/index");
	}
	
	@GetMapping("/events/create")
	public ModelAndView createEvent() {
		return new ModelAndView("customer/create-event");
	}
}
