package com.cakir.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cakir.model.Ort;
import com.cakir.model.User;
import com.cakir.service.UserService;
import com.cakir.web.dto.UserRegistrationDto;
import com.cakir.web.error.UserAlreadyExistException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceIntegrationTest {

	@Autowired
	private UserService userService;
	
	@Test
	public void givenNewUser_whenRegistered_thenCorrect() throws UserAlreadyExistException {
		
		final String userEmail = UUID.randomUUID().toString();
		final UserRegistrationDto userDto = createUserRegistrationDto(userEmail);
		
		final User user = userService.registerNewAccount(userDto);
		
		assertNotNull(user);
	}
	
	
	private UserRegistrationDto createUserRegistrationDto(String email) {
		final UserRegistrationDto userDto = new UserRegistrationDto();
		userDto.setEmail(email);
		userDto.setPassword("SecretPassword");
        userDto.setConfirmPassword("SecretPassword");
        userDto.setFirstName("First");
        userDto.setOrt(createNewOrt().getId());
        userDto.setLastName("Last");
        userDto.setRole(0);
        return userDto;
	}
	
	private Ort createNewOrt() {
		Ort ort = new Ort();
		ort.setId(23L);
		ort.setOrtsname("Aspern");
		ort.setPlz("1220");
		ort.setStadt("Wien");
		ort.setLand("Österreich");
		ort.setAdresse("Hauptstrasse");
		return ort;
	}

}
