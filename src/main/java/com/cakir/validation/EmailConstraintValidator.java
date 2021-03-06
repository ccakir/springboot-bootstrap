package com.cakir.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailConstraintValidator  implements ConstraintValidator<ValidEmail, String>{
	
	private Pattern pattern;
	private Matcher matcher;
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Override
	public boolean isValid(final String username, ConstraintValidatorContext context) {
		
		return (validateEmail(username));
	}

	private boolean validateEmail(final String username) {


		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(username);
		
		return matcher.matches();
	}

}
