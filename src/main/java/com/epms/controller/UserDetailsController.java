package com.epms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epms.service.IEnuCityService;
import com.epms.service.IEnuCountryService;
import com.epms.service.IEnuStateService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
public class UserDetailsController {

	@Autowired
	IEnuStateService enuStateService;

	@Autowired
	IEnuCityService enuCityService;

	@Autowired
	IEnuCountryService enuCountryService;

	@GetMapping
	public ModelAndView homePage() {
		 return new ModelAndView("index");
	}

	@GetMapping("load-customer-registration")
	public ModelAndView customerRegistrationPage() {
		log.info("Load customer registration page");
		final ModelAndView modelandmap = new ModelAndView("customerRegisteration");
		modelandmap.addObject("country", enuCountryService.findAll());
		return modelandmap;
	}

	/*
	 * @PostMapping("/registration") public PaymentResponseEiDTO
	 * makePayment(@PathVariable @NotNull Long customerAccountId,
	 * 
	 * @Valid @RequestBody CCPaymentEiDTO ccPaymentEiDTO,
	 * 
	 * @RequestParam(required = false) Boolean
	 * pbmRateAdjustmentBeforePaymentAllocation) throws TollApiException {
	 * log.info("*** makePayment {}", ccPaymentEiDTO);
	 * 
	 * 
	 * }
	 */

}
