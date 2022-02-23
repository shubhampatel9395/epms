package com.epms.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {
	@GetMapping("/dashboard")
	public ModelAndView homePage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return new ModelAndView("redirect:/login");
		} else {
			return new ModelAndView("employee/index");
		}
	}
	
	@GetMapping("/assigned-work")
	public ModelAndView assignedWork() {
		ModelAndView modelandmap = new ModelAndView("employee/assigned-work");
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
