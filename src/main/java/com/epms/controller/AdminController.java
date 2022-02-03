package com.epms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
	@GetMapping("/dashboard")
	public ModelAndView homePage() {
		return new ModelAndView("admin/index");
	}
	
	@GetMapping("/list-customer")
	public ModelAndView listCustomer() {
		return new ModelAndView("admin/customer");
	}
	
}
