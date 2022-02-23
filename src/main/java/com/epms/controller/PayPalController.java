package com.epms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.epms.service.impl.PayPalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PayPalController {
	@Autowired
	PayPalService service;

	public static final String SUCCESS_URL = "pay/success";
	public static final String CANCEL_URL = "pay/cancel";
	
	@GetMapping("/pay")
	public ModelAndView pay()
	{
		return new ModelAndView("temp");
	}
	//https://www.sandbox.paypal.com/myaccount/summary
	//https://support.thrivecart.com/help/paypal-unable-to-process-payment-message/#:~:text=Typically%20this%20means%20that%20there,funding%20source%20or%20PayPal%20account.&text=Please%20go%20back%20to%20the,using%20a%20different%20payment%20method.%E2%80%9D
//https://www.mindbowser.com/how-to-integrate-razorpay-in-spring-boot-angular/
	@PostMapping("/pay/{cost}")
	public String payment(@PathVariable("cost") String cost) {
		try {
			Payment payment = service.createPayment(Double.parseDouble(cost), "USD", "PayPal",
					"SALE", "Test" , "http://localhost:8080/" + CANCEL_URL,
					"http://localhost:8080/" + SUCCESS_URL);
			for (Links link : payment.getLinks()) {
				if (link.getRel().equals("approval_url")) {
					return "redirect:" + link.getHref();
				}
			}

		} catch (PayPalRESTException e) {

			e.printStackTrace();
		}
		return "redirect:/";
	}

	@GetMapping(value = CANCEL_URL)
	public String cancelPay() {
		return "cancel";
	}

	@GetMapping(value = SUCCESS_URL)
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
		try {
			Payment payment = service.executePayment(paymentId, payerId);
			System.out.println(payment.toJSON());
			if (payment.getState().equals("approved")) {
				return "success";
			}
		} catch (PayPalRESTException e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/";
	}
}