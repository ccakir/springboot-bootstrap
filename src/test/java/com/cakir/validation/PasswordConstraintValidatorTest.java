package com.cakir.validation;

import java.util.Collection;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cakir.model.Ort;
import com.cakir.web.dto.UserRegistrationDto;



@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class PasswordConstraintValidatorTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void submitRegistrationPasswordNotValid() throws Exception {
		
		this.mockMvc.perform(post("/benutzer/registration")
				.param("firstName", "firstname")
				.param("lastName", "lastname")
				.param("email", "new@memorynotfound.com")
                .param("confirmEmail", "new@memorynotfound.com")
                .param("password", "12345678Ww%")
                .param("confirmPassword", "12345678Ww%")
                .param("ort", ""))
				
		.andExpect(model().hasErrors())
		//.andExpect(model().attributeHasErrors("password"))
		.andExpect(status().isForbidden());
	}
	
	

}
