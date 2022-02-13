package com.epms.email.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {
	@Autowired
	private Environment env;

	@Bean
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(env.getProperty("spring.mail.host"));
		mailSender.setPort(Integer.valueOf(env.getProperty("spring.mail.port")));
		mailSender.setUsername(env.getProperty("spring.mail.username"));
		mailSender.setPassword(env.getProperty("spring.mail.password"));

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.transport.protocol", "smtp");
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		javaMailProperties.put("mail.debug", "true");
//		javaMailProperties.put("mail.smtp.ssl.enable", "true");
//		javaMailProperties.put("mail.smtp.ssl.trust", "theRemoteSmtpServer");
//		javaMailProperties.put("mail.smtp.socketFactory.fallback", "true");
//		javaMailProperties.put("mail.smtp.socketFactory.port", env.getProperty("spring.mail.port"));
//		javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}
}