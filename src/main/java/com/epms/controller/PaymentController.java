package com.epms.controller;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PaymentController {
	@GetMapping("/pay")
	public ModelAndView paymentPage() {
		ModelAndView modelAndView = new ModelAndView("temp");
		return modelAndView;
	}

	@PostMapping("/create_order") 
	public String createOrder(@RequestBody Map<String,Object> data) throws Exception {
		RazorpayClient razorpayClient = new RazorpayClient("rzp_test_IoPDrcNcQJRond", "32vpdmrOBsG0eimt2PqQzCnV");
		JSONObject options = new JSONObject();
		options.put("amount", 5000);
		options.put("currency", "INR");
		options.put("receipt", "txn_123456");
		Order order = razorpayClient.Orders.create(options);
		
		return order.toString();
	}
}