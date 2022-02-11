package com.epms.email.configuration;

import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dto.UserDetailsDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class MailServiceImpl implements IMailService {

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	private Environment env;

	@Override
	public void sendEmail(Mail mail) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setSubject(mail.getMailSubject());
			mimeMessageHelper.setFrom(new InternetAddress(env.getProperty("spring.mail.username")));
			mimeMessageHelper.setTo(mail.getMailTo());
			mimeMessageHelper.setText(mail.getMailContent());

			mail.getAttachments().stream().forEach(attachment -> {
				try {
					mimeMessageHelper.addAttachment(attachment.getName(), attachment);
				} catch (MessagingException e) {
					log.error("exception while mail attachment sending {} :", e);
				}
			});

			mailSender.send(mimeMessageHelper.getMimeMessage());

		} catch (MessagingException e) {
			log.error("exception while mail sending {} :", e);
		}
	}

}
