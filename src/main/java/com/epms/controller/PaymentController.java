package com.epms.controller;

import java.util.Date;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epms.dto.EventDTO;
import com.epms.dto.PaymentDTO;
import com.epms.service.IEventService;
import com.epms.service.IPaymentService;
import com.epms.service.IUserDetailsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PaymentController {
	@Autowired
	IUserDetailsService userDetailsService;
	
	@Autowired
	IPaymentService paymentService;
	
	@Autowired
	IEventService eventService;
	
	@GetMapping("/pay")
	public ModelAndView paymentPage() {
		ModelAndView modelAndView = new ModelAndView("temp");
		return modelAndView;
	}

	@PostMapping("/create_order") 
	public String createOrder(@RequestBody Map<String,Object> data) throws Exception {
		RazorpayClient razorpayClient = new RazorpayClient("rzp_test_IoPDrcNcQJRond", "32vpdmrOBsG0eimt2PqQzCnV");
		JSONObject options = new JSONObject();
		options.put("amount", 100 * Double.parseDouble(data.get("amount").toString()));
		options.put("currency", "INR");
		Order order = razorpayClient.Orders.create(options);
		return order.toString();
	}
	
	@GetMapping("/getEventDetails/{eventId}")
	public String getEventDetails(@PathVariable Long eventId) throws Exception {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(eventService.findById(eventId));
		return json;
	}
	
	@PostMapping("/save_payment")
	public void savePayment(@RequestBody Map<String,Object> data) throws Exception {
		PaymentDTO paymentDTO = new PaymentDTO();
		// System.out.println(data);
		RazorpayClient razorpayClient = new RazorpayClient("rzp_test_IoPDrcNcQJRond", "32vpdmrOBsG0eimt2PqQzCnV");
		Payment payment = razorpayClient.Payments.fetch(data.get("razorpay_payment_id").toString());
		// System.out.println(payment.get("contact").toString());
		paymentDTO.setPaymentId(payment.get("id").toString());
		paymentDTO.setOrderId(payment.get("order_id").toString());
		paymentDTO.setUserDetailsId(Long.parseLong(data.get("userDetailsId").toString()));
		paymentDTO.setMethod(payment.get("method").toString());
		paymentDTO.setAmount(Double.parseDouble(payment.get("amount").toString()) / 100);
		paymentDTO.setDescription(payment.get("description").toString());
		paymentDTO.setStatus(payment.get("status").toString());
		paymentDTO.setCreatedAt((Date) payment.get("created_at"));
		// System.out.println(paymentDTO);
		paymentService.insert(paymentDTO);
	}
	
	@PostMapping("/save_complete_payment")
	public void saveCompletePayment(@RequestBody Map<String,Object> data) throws Exception {
		PaymentDTO paymentDTO = new PaymentDTO();
		// System.out.println(data);
		RazorpayClient razorpayClient = new RazorpayClient("rzp_test_IoPDrcNcQJRond", "32vpdmrOBsG0eimt2PqQzCnV");
		Payment payment = razorpayClient.Payments.fetch(data.get("razorpay_payment_id").toString());
		// System.out.println(payment.get("contact").toString());
		paymentDTO.setPaymentId(payment.get("id").toString());
		paymentDTO.setOrderId(payment.get("order_id").toString());
		paymentDTO.setUserDetailsId(Long.parseLong(data.get("userDetailsId").toString()));
		paymentDTO.setEventId(Long.parseLong(data.get("eventId").toString()));
		paymentDTO.setMethod(payment.get("method").toString());
		paymentDTO.setAmount(Double.parseDouble(payment.get("amount").toString()) / 100);
		paymentDTO.setDescription(payment.get("description").toString());
		paymentDTO.setStatus(payment.get("status").toString());
		paymentDTO.setCreatedAt((Date) payment.get("created_at"));
		// System.out.println(paymentDTO);
		paymentService.insert(paymentDTO);
	}
	
	@GetMapping("/refund")
	public ModelAndView refundPage() {
		ModelAndView modelAndView = new ModelAndView("refund");
		return modelAndView;
	}
	
	public void refund(EventDTO eventDTO) throws Exception {
		RazorpayClient razorpayClient = new RazorpayClient("rzp_test_IoPDrcNcQJRond", "32vpdmrOBsG0eimt2PqQzCnV");
		PaymentDTO paymentDTO = DataAccessUtils.singleResult(paymentService.findByNamedParameters(new MapSqlParameterSource().addValue("eventId", eventDTO.getEventId()).addValue("userDetailsId", eventDTO.getUserDetailsId())));
		JSONObject options = new JSONObject();
		options.put("payment_id", paymentDTO.getPaymentId());
		options.put("amount", 100 * paymentDTO.getAmount());
		options.put("speed", "optimum");
		razorpayClient.Refunds.create(options);
	}
}