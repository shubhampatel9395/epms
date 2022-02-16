package com.epms.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {
public static void main(String[] args) {
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	String encodedPassword = passwordEncoder.encode("test");
	System.out.println(encodedPassword);
}
}
