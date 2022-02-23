package com.epms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.epms.dto.EnquiryDTO;
import com.epms.dto.EnuEventSubTypeDTO;
import com.epms.dto.EnuStateDTO;
import com.epms.dto.form.CreateEventBasicInfoForm;
import com.epms.dto.form.CreateEventPackageForm;
import com.epms.dto.form.CreateEventPaymentForm;
import com.epms.dto.form.CreateEventTicketsForm;
import com.epms.service.IEnuEventSubTypeService;
import com.epms.service.IEnuEventTypeService;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/customer")
@Slf4j
public class RegisteredCustomerController {
	@Autowired
	IEnuEventTypeService enuEventTypeService;

	@Autowired
	IEnuEventSubTypeService enuEventSubTypeService;

	@GetMapping("/index")
	public ModelAndView homePage() {
		return new ModelAndView("customer/index");
	}
	
	// @GetMapping("/events/create")
	@RequestMapping(value = "/events/create", method = RequestMethod.GET)
	public ModelAndView createEvent() {
		log.info("Load create event page");
		final ModelAndView modelandmap = new ModelAndView("customer/create-event");
		modelandmap.addObject("createEventBasicInfoForm", new CreateEventBasicInfoForm());
		modelandmap.addObject("createEventPackageForm", new CreateEventPackageForm());
		modelandmap.addObject("createEventTicketsForm", new CreateEventTicketsForm());
		modelandmap.addObject("createEventPaymentForm", new CreateEventPaymentForm());
		System.out.println(enuEventTypeService.findAll());
		modelandmap.addObject("eventTypes", enuEventTypeService.findAll());
		modelandmap.addObject("eventSubTypes", enuEventSubTypeService.findAll());
		return modelandmap;
	}
	
//	@PostMapping("/events/create")
//	public ModelAndView saveEvent(@Valid @ModelAttribute("createEventBasicInfoForm") CreateEventBasicInfoForm createEventBasicInfoForm)
//	{
//		final ModelAndView modelandmap = new ModelAndView("customer/create-event");
//		System.out.println(createEventBasicInfoForm);
//		return modelandmap;
//	}

	// @PostMapping("/events/create", params="action=cancel")
//	@RequestMapping(value = "/events/create", method = RequestMethod.POST, params = "action=final")
//	public ModelAndView saveEvent(
//			@Valid @ModelAttribute("createEventBasicInfoForm") CreateEventBasicInfoForm createEventBasicInfoForm,
//			@Valid @ModelAttribute("createEventPackageForm") CreateEventPackageForm createEventPackageForm,
//			@Valid @ModelAttribute("createEventTicketsForm") CreateEventTicketsForm createEventTicketsForm,
//			@Valid @ModelAttribute("createEventPaymentForm") CreateEventPaymentForm createEventPaymentForm,
//			final RedirectAttributes redirectAttributes) {
//		final ModelAndView modelandmap = new ModelAndView("customer/create-event");
//		// saveEventPaymentForm(createEventPaymentForm, null)
//		return modelandmap;
//	}
//
	@GetMapping("/getEventSubTypes/{eventTypeId}")
	public List<EnuEventSubTypeDTO> getEventSubTypes(@PathVariable @NotNull Long eventTypeId) {
		log.info("Loading event sub-types");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("eventTypeId", eventTypeId);
		return enuEventSubTypeService.findByNamedParameters(paramSource);
	}
	
	@RequestMapping(value = "/events/create", method = RequestMethod.POST, params = "action=saveBasicInfoForm")
	public ModelAndView saveBasicInfoForm(
			@Valid @ModelAttribute("createEventBasicInfoForm") CreateEventBasicInfoForm createEventBasicInfoForm,
			final BindingResult bindingResult) {
		log.info("Save Basic-Info");
		final ModelAndView modelandmap = new ModelAndView("customer/events/create");
		modelandmap.addObject("activeTab", 2);
		System.out.println(createEventBasicInfoForm);
		return modelandmap;
	}

	@RequestMapping(value = "/events/create", method = RequestMethod.POST, params = "action=saveEventPackageForm")
	public int saveEventPackageForm(
			@Valid @ModelAttribute("createEventPackageForm") CreateEventPackageForm createEventPackageForm,
			final BindingResult bindingResult) {
		System.out.println(createEventPackageForm);
		return 1;
	}

	@RequestMapping(value = "/events/create", method = RequestMethod.POST, params = "action=saveEventTicketsForm")
	public int saveEventTicketsForm(
			@Valid @ModelAttribute("createEventTicketsForm") CreateEventTicketsForm createEventTicketsForm,
			final BindingResult bindingResult) {
		return 1;
	}

	@RequestMapping(value = "/events/create", method = RequestMethod.POST, params = "action=saveEventPaymentForm")
	public int saveEventPaymentForm(
			@Valid @ModelAttribute("createEventPaymentForm") CreateEventPaymentForm createEventPaymentForm,
			final BindingResult bindingResult) {
		return 1;
	}
}
