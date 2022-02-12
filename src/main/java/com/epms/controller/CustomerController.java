package com.epms.controller;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.epms.authentication.CustomUserDetailsDTO;
import com.epms.dto.AddressDTO;
import com.epms.dto.EnuCityDTO;
import com.epms.dto.EnuStateDTO;
import com.epms.dto.UserDetailsDTO;
import com.epms.email.configuration.IMailService;
import com.epms.email.configuration.Mail;
import com.epms.service.IAddressService;
import com.epms.service.IEnuCityService;
import com.epms.service.IEnuCountryService;
import com.epms.service.IEnuStateService;
import com.epms.service.IUserDetailsService;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarpicocli.CommandLine.Parameters;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
public class CustomerController {
	@Autowired
	IEnuCityService enuCityService;

	@Autowired
	IEnuStateService enuStateService;

	@Autowired
	IEnuCountryService enuCountryService;

	@Autowired
	IUserDetailsService userDetailsService;

	@Autowired
	IAddressService addressService;

	@Autowired
	IMailService mailService;

	@GetMapping("index")
	public ModelAndView homePage() {
		return new ModelAndView("index");
	}

	@GetMapping("getStates/{countryId}")
	public List<EnuStateDTO> getStates(@PathVariable @NotNull Integer countryId) {
		log.info("Loading states in registration page");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("countryId", countryId);
		return enuStateService.findByNamedParameters(paramSource);
	}

	@GetMapping("getCities/{stateId}")
	public List<EnuCityDTO> getCities(@PathVariable @NotNull Integer stateId) {
		log.info("Loading cities in registration page");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("stateId", stateId);
		return enuCityService.findByNamedParameters(paramSource);
	}

	@GetMapping("customer-registration")
	public ModelAndView loadCustomerRegistrationPage() {
		log.info("Load customer registration page");
		final ModelAndView modelandmap = new ModelAndView("customerRegisteration");
		modelandmap.addObject("countries", enuCountryService.findAll());
		modelandmap.addObject("userDetailsDTO", new UserDetailsDTO());
		modelandmap.addObject("addressDTO", new AddressDTO());
		return modelandmap;
	}

	public BindingResult checkCustomerResults(@Valid @ModelAttribute("userDetailsDTO") UserDetailsDTO userDetailsDTO,
			BindingResult userResult) {
		// Valid Email
		final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(userDetailsDTO.getEmail());
		boolean isValidEmail = matcher.find();
		if (isValidEmail == false) {
			userResult.addError(new FieldError("userDetailsDTO", "email", "Please enter valid email address."));
		}

		// Unique Email
		List<UserDetailsDTO> emailDTO = userDetailsService.isUniqueEmail(userDetailsDTO.getEmail());
		if (emailDTO.isEmpty() != true) {
			if (emailDTO.get(0).getIsActive() == true) {
				userResult.addError(new FieldError("userDetailsDTO", "email", "Email address is already registered."));
			} else {
				userResult.addError(new FieldError("userDetailsDTO", "email",
						"Email address is already registered and account is deactivated.\nPlease contact us to active it."));
			}
		}

		// Password Rules
		String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
		boolean isValidPassword = userDetailsDTO.getPassword().matches(pattern);
		if (isValidPassword == false) {
			userResult.addError(
					new FieldError("userDetailsDTO", "password", "Please enter password according to rules."));
		}

		// Unique Mobile Number
		List<UserDetailsDTO> mobileNumberDTO = userDetailsService
				.isUniqueMobileNumber(userDetailsDTO.getMobileNumber());
		if (mobileNumberDTO.isEmpty() != true) {
			if (mobileNumberDTO.get(0).getIsActive() == true) {
				userResult.addError(
						new FieldError("userDetailsDTO", "mobileNumber", "Mobile Number is already registered."));
			} else {
				userResult.addError(new FieldError("userDetailsDTO", "mobileNumber",
						"Mobile Number is already registered and account is deactivated.\nPlease contact us to active it."));
			}
		}

		return userResult;
	}

	@PostMapping("customer-registration")
	public ModelAndView submitCustomerRegistration(
			@Valid @ModelAttribute("userDetailsDTO") UserDetailsDTO userDetailsDTO, BindingResult userResult,
			@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO, BindingResult addressResult,
			ModelAndView modelandmap) {
		userResult = checkCustomerResults(userDetailsDTO, userResult);
		if (userResult.hasErrors() == true) {
			modelandmap = new ModelAndView("customerRegisteration");
			modelandmap.addObject("userDetailsDTO", userDetailsDTO);
			modelandmap.addObject("countries", enuCountryService.findAll());
			modelandmap.addObject("addressDTO", addressDTO);
			return modelandmap;
		}

		AddressDTO insertAddressDTO = addressService.insert(addressDTO);
		userDetailsDTO.setAddressId(insertAddressDTO.getAddressId());

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(userDetailsDTO.getPassword());
		userDetailsDTO.setPassword(encodedPassword);
		UserDetailsDTO insertUserDetailsDTO = userDetailsService.insert(userDetailsDTO);

		modelandmap = new ModelAndView("index");
		modelandmap.addObject("userDetailsDTO", insertUserDetailsDTO);
		return modelandmap;
	}

	@GetMapping("login")
	public ModelAndView viewLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return new ModelAndView("login");
		} else {
			CustomUserDetailsDTO userDetails = (CustomUserDetailsDTO) authentication.getPrincipal();
			if (userDetails.getRoleName().equalsIgnoreCase("ROLE_CUSTOMER")) {
				return new ModelAndView("index"); // customer index
			} else if (userDetails.getRoleName().equalsIgnoreCase("ROLE_ADMIN")) {
				return new ModelAndView("admin/dashboard");
			} else if (userDetails.getRoleName().equalsIgnoreCase("ROLE_SERVICEPROVIDER")) {
				return new ModelAndView("serviceprovider/index");
			}
		}
		return new ModelAndView("index");

	}

	@GetMapping("logout")
	public ModelAndView logoutPage() {
		return new ModelAndView("login");
	}

	@GetMapping("events")
	public ModelAndView events() {
//		CustomUserDetailsDTO userDetails = (CustomUserDetailsDTO) SecurityContextHolder.getContext().getAuthentication()
//				.getPrincipal();
//		System.out.println(userDetails.getUserDetailsId());
		final ModelAndView modelandmap = new ModelAndView("events");
		return modelandmap;
	}

	@GetMapping("enquiry")
	public ModelAndView enquiry() {
		final ModelAndView modelandmap = new ModelAndView("enquiry");
		return modelandmap;
	}

	@GetMapping("gallery")
	public ModelAndView gallery() {
		final ModelAndView modelandmap = new ModelAndView("gallery");
		return modelandmap;
	}

	@GetMapping("packages")
	public ModelAndView packages() {
		final ModelAndView modelandmap = new ModelAndView("packages");
		return modelandmap;
	}

	@GetMapping("/forgot-password")
	public ModelAndView showForgotPasswordForm() {
		return new ModelAndView("forgotPassword");
	}

	@PostMapping("/forgot-password")
	public ModelAndView processForgotPassword(HttpServletRequest request, ModelAndView model, RedirectAttributes rm) {

		String email = request.getParameter("email");

		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("email", email);
		parameterSource.addValue("isActive", true);
		UserDetailsDTO userDetailsDTO = DataAccessUtils
				.singleResult(userDetailsService.findByNamedParameters(parameterSource));
		if (userDetailsDTO != null) {
			try {
				String token = UUID.randomUUID().toString();
				userDetailsService.updateResetPasswordToken(token, email);

				String resetPasswordLink = getSiteURL(request) + "/reset-password?token=" + token;
				Mail mail = new Mail();
				mail.setMailTo(email);
				mail.setMailSubject("Here's the link to reset your password");
				mail.setContentType("text/html");
				mail.setMailContent("<p>Hello " + userDetailsDTO.getFirstName() + " " + userDetailsDTO.getLastName()
						+ ",</p>" + "<p>You have requested to reset your password.</p>"
						+ "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + resetPasswordLink
						+ "\">Change my password</a></p>" + "<br>"
						+ "<p>Ignore this email if you do remember your password, "
						+ "or you have not made the request.</p>");
				mailService.sendEmail(mail);
				rm.addFlashAttribute("message", "We have sent a reset password link to your email. Please check.");

			} catch (Exception e) {
				rm.addFlashAttribute("error", "Error while sending email");
			}
		} else {
			rm.addFlashAttribute("error", "Email is not register in the system.");
		}
		model.setViewName("redirect:/forgot-password");
		return model;
	}

	public static String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}

	@GetMapping("/reset_password")
	public ModelAndView showResetPasswordForm(@RequestParam(value = "token") String token,
			RedirectAttributes rm) {
		ModelAndView model = new ModelAndView("resetPassword");
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("resetPasswordToken", token);
		parameterSource.addValue("isActive", true);
		UserDetailsDTO userDetailsDTO = DataAccessUtils
				.singleResult(userDetailsService.findByNamedParameters(parameterSource));
		model.addObject("token", token);

		if (userDetailsDTO == null) {
			rm.addFlashAttribute("error", "Invalid Token");
		}
		return model;
	}
}