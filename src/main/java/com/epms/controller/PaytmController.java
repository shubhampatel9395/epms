package com.epms.controller;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epms.dto.PaytmDTO;
import com.paytm.pg.merchant.PaytmChecksum;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PaytmController {
	@Autowired
	private PaytmDTO paytmDetailPojo;
	@Autowired
	private Environment env;

	@PostMapping(value = "/submitPaymentDetail/{CUST_ID}/{TXN_AMOUNT}/{ORDER_ID}")
	public ModelAndView getRedirect(@PathVariable(name = "CUST_ID") String customerId,
			@PathVariable(name = "TXN_AMOUNT") String transactionAmount,
			@PathVariable(name = "ORDER_ID") String orderId) throws Exception {
		ModelAndView modelAndView = new ModelAndView("redirect:" + paytmDetailPojo.getPaytmUrl());
		TreeMap<String, String> parameters = new TreeMap<>();
		paytmDetailPojo.getDetails().forEach((k, v) -> parameters.put(k, v));
		parameters.put("MOBILE_NO", env.getProperty("paytm.mobile"));
		parameters.put("EMAIL", env.getProperty("paytm.email"));
		parameters.put("ORDER_ID", orderId);
		parameters.put("TXN_AMOUNT", transactionAmount);
		parameters.put("CUST_ID", customerId);
		String checkSum = getCheckSum(parameters);
		parameters.put("CHECKSUMHASH", checkSum);
		modelAndView.addAllObjects(parameters);
		return modelAndView;
	}

	@PostMapping(value = "/pgresponse")
	public ModelAndView getResponseRedirect(HttpServletRequest request, Model model) {

		Map<String, String[]> mapData = request.getParameterMap();
		TreeMap<String, String> parameters = new TreeMap<String, String>();
		mapData.forEach((key, val) -> parameters.put(key, val[0]));
		String paytmChecksum = "";
		if (mapData.containsKey("CHECKSUMHASH")) {
			paytmChecksum = mapData.get("CHECKSUMHASH")[0];
		}
		String result;

		boolean isValideChecksum = false;
		System.out.println("RESULT : " + parameters.toString());
		try {
			isValideChecksum = validateCheckSum(parameters, paytmChecksum);
			if (isValideChecksum && parameters.containsKey("RESPCODE")) {
				if (parameters.get("RESPCODE").equals("01")) {
					result = "Payment Successful";
				} else {
					result = "Payment Failed";
				}
			} else {
				result = "Checksum mismatched";
			}
		} catch (Exception e) {
			result = e.toString();
		}
		model.addAttribute("result", result);
		parameters.remove("CHECKSUMHASH");
		model.addAttribute("parameters", parameters);
		return new ModelAndView("report");
	}

	private boolean validateCheckSum(TreeMap<String, String> parameters, String paytmChecksum) throws Exception {
		return PaytmChecksum.verifySignature(parameters, paytmDetailPojo.getMerchantKey(), paytmChecksum);
	}

	private String getCheckSum(TreeMap<String, String> parameters) throws Exception {
		return PaytmChecksum.generateSignature(parameters, paytmDetailPojo.getMerchantKey());
	}

}
