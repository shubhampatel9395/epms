package com.epms.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class test {
public static void main(String[] args) {
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	String encodedPassword = passwordEncoder.encode("Admin@123");
	System.out.println(encodedPassword);
}
}
//$2a$10$X2B1lhkErcVuWMiXFeokluZNEhjGrsXL2iPEUroFTpMNVc55RR/VS
//$2a$10$ruJntpLro4dvedWmoMFrReMEoi/1Q7yoJoQhnuL9ldnrB0SbGLxsK