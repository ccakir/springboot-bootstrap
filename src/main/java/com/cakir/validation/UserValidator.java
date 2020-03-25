package com.cakir.validation;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.cakir.web.dto.UserRegistrationDto;

@Component
public class UserValidator implements Validator{
	
	

	@Override
	public boolean supports(Class<?> clazz) {
		
		return UserRegistrationDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		UserRegistrationDto userDto = (UserRegistrationDto) target;
		
		
		if(!userDto.getEmail().isEmpty() && !userDto.getEmail().equals(userDto.getConfirmEmail())) {
			
			errors.rejectValue("confirmEmail", "emailconfirm", "label.error.emailconfirm");
		}
		
		if(!userDto.getPassword().isEmpty() && !userDto.getConfirmPassword().isEmpty() && !userDto.getPassword().equals(userDto.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "passwordconfirm", "label.error.passwordconfirm");
		}
		
		
	}

}
