package com.cakir.registration;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.cakir.model.User;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent  extends ApplicationEvent{
	
	
	private final String appUrl;
    private final Locale locale;
    private final User user;
    
	public OnRegistrationCompleteEvent(User user, Locale locale, String appUrl) {
		super(user);
		this.appUrl = appUrl;
		this.locale = locale;
		this.user = user;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public User getUser() {
		return user;
	}
    
    

}
