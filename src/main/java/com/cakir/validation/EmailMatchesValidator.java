package com.cakir.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cakir.web.dto.UserRegistrationDto;

public class EmailMatchesValidator implements ConstraintValidator<EmailMatches, Object>{

	@Override
	public boolean isValid(final Object value, ConstraintValidatorContext context) {


		final UserRegistrationDto user = (UserRegistrationDto) value;
		return user.getEmail().equals(user.getConfirmEmail());
		
	}

}
