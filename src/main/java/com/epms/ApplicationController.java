package com.epms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
	@GetMapping("/admin")
	public String adminDashboard() {
		return "/admin/index.html";
	}
	
	@GetMapping("/employee")
	public String employeeDashboard() {
		return "/employee/index.html";
	}
	
	@GetMapping("/eventorganizer")
	public String eventOrganizerDashboard() {
		return "/eventorganizer/index.html";
	}
	
	@GetMapping("/serviceprovider")
	public String serviceProviderDashboard() {
		return "/serviceprovider/index.html";
	}
}
