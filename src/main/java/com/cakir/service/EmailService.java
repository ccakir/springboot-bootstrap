package com.cakir.service;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.cakir.model.User;

public interface EmailService {
	
	MimeMessage constructVerificationEmail(String contextPath, Locale locale, String newToken, User user, String message1, String message2, String reason) throws MessagingException;
	
	MimeMessage constructEmail(String subject, String message1, String url, String message2, User user) throws MessagingException;
	
	MimeMessage constructPasswordResetEmail(String contextPath, Locale locale, String newToken, User user, String message1, String message2, String reason) throws MessagingException;

}
