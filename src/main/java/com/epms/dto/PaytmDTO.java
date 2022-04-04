package com.epms.dto;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Component
@ConfigurationProperties("paytm.payment.sandbox")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaytmDTO {
	private String mid;
	private String merchantKey;
	private String channelId;
	private String website;
	private String industryTypeId;
	private String paytmUrl;
	private String callbackUrl;
	private Map<String, String> details;
}