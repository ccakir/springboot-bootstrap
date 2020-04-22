package com.cakir.validation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.cakir.web.dto.UserRegistrationDto;

class EmailConstraintValidatorTest {

	private static Validator validator;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}

	@Test
	void testInvalidPassword() {
		UserRegistrationDto userRegistration = new UserRegistrationDto();
        userRegistration.setFirstName("test");
        userRegistration.setLastName("test");
        userRegistration.setEmail("email.com");
        userRegistration.setConfirmEmail("email.com");
        userRegistration.setPassword("123456Ww%");
        userRegistration.setConfirmPassword("123456Ww%");
        

        Set<ConstraintViolation<UserRegistrationDto>> constraintViolations = validator.validate(userRegistration);

        assertEquals(constraintViolations.size(), 2);	
	}
	
	@Test
	void testValidPassword() {
		UserRegistrationDto userRegistration = new UserRegistrationDto();
        userRegistration.setFirstName("test");
        userRegistration.setLastName("test");
        userRegistration.setEmail("test@test.com");
        userRegistration.setConfirmEmail("test@test.com");
        userRegistration.setPassword("123456Ww%");
        userRegistration.setConfirmPassword("123456Ww%");
        

        Set<ConstraintViolation<UserRegistrationDto>> constraintViolations = validator.validate(userRegistration);

        assertEquals(constraintViolations.size(), 0);	
	}

}
