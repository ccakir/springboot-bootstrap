package com.cakir.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cakir.web.dto.PasswordDto;
import com.cakir.web.dto.UserRegistrationDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object>{
	
	 @Override
	    public void initialize(final PasswordMatches constraintAnnotation) {
	        //
	    }

	@Override
	public boolean isValid(final Object value, ConstraintValidatorContext context) {


		final PasswordDto passwordDto = (PasswordDto) value;
		return passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword());
	}

}
