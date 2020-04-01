package com.cakir.service.impl;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.cakir.model.User;
import com.cakir.service.EmailService;

@Component
public class EmailServiceImpl  implements EmailService{
	
	
    
    @Autowired
    public JavaMailSender emailSender;

	@Override
	public MimeMessage constructVerificationEmail(String contextPath, Locale locale, String newToken, User user, String message1, String message2, String reason) throws MessagingException {
		 
		return null;
		 
		 
	}

	@Override
	public MimeMessage constructEmail(String subject, String message1, String url, String message2, User user) throws MessagingException {
		final MimeMessage message = emailSender.createMimeMessage();
		 
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		
		String htmlMsg = "<h4>"+message1+"</h4><br><br>"
			 +"<a href='"+url+"'>"+"Password Reset"+"</a><br><br>"
				 +"<h5>"+message2+"</h5>"+url;
		message.setText(htmlMsg, "UTF-8", "html");
		
		
		helper.setTo(user.getEmail());
		helper.setSubject(subject);
		
		
		 return message;
	}

	@Override
	public MimeMessage constructPasswordResetEmail(String contextPath, Locale locale, String newToken, User user,
			String message1, String message2, String reason) throws MessagingException {
		final String url = contextPath + "/changePassword?id=" + user.getId() + "&token=" + newToken + "&lang="+locale;
		return constructEmail(reason, message1, url, message2, user);
	}

	

}
