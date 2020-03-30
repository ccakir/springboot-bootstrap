package com.cakir.validation;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cakir.web.dto.UserRegistrationDto;

@Component
public class UserValidator implements Validator{
	
	

	@Override
	public boolean supports(Class<?> clazz) {
		
		return UserRegistrationDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		UserRegistrationDto userDto = (UserRegistrationDto) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "message.firstName", "Firstname is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "message.lastName", "LastName is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "message.password", "LastName is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "message.email", "UserName is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ort", "message.ort", "Ort is required.");
		
	}

}
