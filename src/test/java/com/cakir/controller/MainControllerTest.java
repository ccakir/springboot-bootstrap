package com.cakir.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.cakir.model.User;
import com.cakir.model.VerificationToken;
import com.cakir.repository.UserRepository;
import com.cakir.repository.VerificationTokenRepository;
import com.cakir.service.OrtService;
import com.cakir.service.UserService;
import com.cakir.service.impl.OrtServiceImpl;
import com.cakir.web.dto.UserRegistrationDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;

@SpringBootTest
@WebAppConfiguration
class MainControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private OrtService ortservice;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private VerificationTokenRepository tokenRepository;
	
	private UserRegistrationDto userDto;
	private User user;

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();

		String email = UUID.randomUUID().toString();
		userDto = new UserRegistrationDto();
		userDto.setFirstName("test");
		userDto.setLastName("test");
		userDto.setEmail(email);
		userDto.setConfirmEmail(email);
		userDto.setPassword("test");
		userDto.setConfirmPassword("test");
		userDto.setOrt(1L);
		user = userService.registerNewAccount(userDto);
		
		
		
	}

	
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void givenRoleAdmin_thenReturnLocationAdmin() throws Exception {
		
		mockMvc.perform(get("/welcome").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(view().name("admin/index"))
		.andExpect(forwardedUrl("/WEB-INF/jsp/admin/index.jsp"));
		
		mockMvc.perform(get("/welcome/index"))
		.andExpect(status().isOk())
		.andExpect(view().name("admin/index"))
		.andExpect(forwardedUrl("/WEB-INF/jsp/admin/index.jsp"));
		
	}
	
	@Test
	@WithMockUser(roles = "USER")
	public void givenRoleUser_thenReturnLocationUser() throws Exception {
		
		mockMvc.perform(get("/welcome"))
		.andExpect(status().isOk())
		.andExpect(view().name("user/index"))
		.andExpect(forwardedUrl("/WEB-INF/jsp/user/index.jsp"));
		
		mockMvc.perform(get("/welcome/index"))
		.andExpect(status().isOk())
		.andExpect(view().name("user/index"))
		.andExpect(forwardedUrl("/WEB-INF/jsp/user/index.jsp"));
		
	}
	
	@Test
	@WithAnonymousUser
	public void givenAnonymausUser_thenReturnLocationLogin() throws Exception {
		
		mockMvc.perform(get("/welcome"))
		.andExpect(status().isOk())
		.andExpect(view().name("welcome/login"))
		.andExpect(forwardedUrl("/WEB-INF/jsp/welcome/login.jsp"));
		
		mockMvc.perform(get("/welcome/index"))
		.andExpect(status().isOk())
		.andExpect(view().name("welcome/login"))
		.andExpect(forwardedUrl("/WEB-INF/jsp/welcome/login.jsp"));
		
	}
	
	@Test
	void getRegistrationForm_thenReturnRegistrationPage() throws Exception {

		ResultActions mvcResult = mockMvc.perform(get("/welcome/registration"));
		mvcResult.andExpect(model().size(2));
		mvcResult.andExpect(model().attribute("ortList", ortservice.alleOrte()));
		mvcResult.andExpect(status().isOk());
		mvcResult.andExpect(forwardedUrl("/WEB-INF/jsp/welcome/registration.jsp"));
		mvcResult.andExpect(view().name("welcome/registration"));

	}
	
	@Test
	public void registrationValidation_givenNotFirstnameAndLastname_thenError() throws Exception {
		
		this.mockMvc
        .perform(
                post("/welcome/registration")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", "")
                        .param("lastName", "")
                        .param("email", "info@test.com")
                        .param("confirmEmail", "info@test.com")
                        .param("password", "123456Ww%")
                        .param("confirmPassword", "123456Ww%")
                        .param("ort", "1")
                        
        )
        .andExpect(model().hasErrors())
        .andExpect(model().attributeHasFieldErrors("userForm", "firstName", "lastName"))
        .andExpect(status().isOk())
        .andExpect(forwardedUrl("/WEB-INF/jsp/welcome/registration.jsp"));
	}
	
	@Test
	public void registrationValidation_givenNotEmail_thenError() throws Exception {
		
		this.mockMvc
        .perform(
                post("/welcome/registration")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", "firstName")
                        .param("lastName", "lastName")
                        .param("email", "")
                        .param("confirmEmail", "")
                        .param("password", "123456Ww%")
                        .param("confirmPassword", "123456Ww%")
                        .param("ort", "1")
                        
        )
        .andExpect(model().hasErrors())
        .andExpect(model().attributeHasFieldErrors("userForm", "confirmEmail", "email"))
        .andExpect(forwardedUrl("/WEB-INF/jsp/welcome/registration.jsp"));	
      }
	
	@Test
	public void registrationValidation_givenEmailNotMatching_thenError() throws Exception {
		
		this.mockMvc
        .perform(
                post("/welcome/registration")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", "firstName")
                        .param("lastName", "lastName")
                        .param("email", "3@test.com")
                        .param("confirmEmail", "2@test.com")
                        .param("password", "123456Ww%")
                        .param("confirmPassword", "123456Ww%")
                        .param("ort", "1")
                        
        )
        .andExpect(model().hasErrors())
        .andExpect(model().attributeHasFieldErrors("userForm", "confirmEmail"))
        .andExpect(forwardedUrl("/WEB-INF/jsp/welcome/registration.jsp"));	
      }
	
	@Test
	public void registrationValidation_givenInvalidEmail_thenError() throws Exception {
		
		this.mockMvc
        .perform(
                post("/welcome/registration")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", "firstName")
                        .param("lastName", "lastName")
                        .param("email", "test")
                        .param("confirmEmail", "test")
                        .param("password", "123456Ww%")
                        .param("confirmPassword", "123456Ww%")
                        .param("ort", "1")
                        
        )
        .andExpect(model().hasErrors())
        .andExpect(model().attributeHasFieldErrors("userForm", "confirmEmail", "email"))
        .andExpect(forwardedUrl("/WEB-INF/jsp/welcome/registration.jsp"));	
      }
	
	@Test
	public void registrationValidation_givenPasswordNotMatching_thenError() throws Exception {
		
		this.mockMvc
        .perform(
                post("/welcome/registration")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", "firstName")
                        .param("lastName", "lastName")
                        .param("email", "info@test.com")
                        .param("confirmEmail", "info@test.com")
                        .param("password", "123456Ww%")
                        .param("confirmPassword", "123456Ww%555")
                        .param("ort", "1")
                        
        )
        .andExpect(model().hasErrors())
        .andExpect(model().attributeHasFieldErrors("userForm", "confirmPassword"))
        .andExpect(forwardedUrl("/WEB-INF/jsp/welcome/registration.jsp"));	
      }
	
	@Test
	public void registrationValidation_givenAllData_whenValid_thenCreate() throws Exception {
		
		this.mockMvc
        .perform(
                post("/welcome/registration")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", "firstName")
                        .param("lastName", "lastName")
                        .param("email", "info@test.com")
                        .param("confirmEmail", "info@test.com")
                        .param("password", "123456Ww%")
                        .param("confirmPassword", "123456Ww%")
                        .param("ort", "1")
                        
        )
        
        .andExpect(model().hasNoErrors())
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/welcome/registration?register=success&lang=en"))
        .andExpect(status().isFound());
		
		
	}
	
	@Test
	public void RegistrationConfirm_givenUserAndToken_whenAllOk_thenReturnCorrect() throws Exception {
		
		String token = UUID.randomUUID().toString();
		VerificationToken vToken = new VerificationToken(token, user);
		vToken.setExpiryDate(Date.from(Instant.now().plus(2, ChronoUnit.DAYS)));
		tokenRepository.saveAndFlush(vToken);
		
		assertNotNull(user);
		
		assertNotNull(vToken);
		
		
		ResultActions resultActions = this.mockMvc
				.perform(get("/welcome/userRegistrationConfirm?id="+user.getId()+"&token="+token).with(csrf()));
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(view().name("verification/userRegistrationConfirm"));
		resultActions.andExpect(model().attributeExists("token", "username", "password"));
		
	}
	
	
	@Test
	public void RegistrationConfirm_givenInvalidUserAndToken_thenReturnException() throws Exception {
		
		String token = UUID.randomUUID().toString();
		
		Long generatedLong = new Random().nextLong();
		
		
		ResultActions resultActions = this.mockMvc
				.perform(get("/welcome/userRegistrationConfirm?id="+generatedLong+"&token="+token).with(csrf()));
		resultActions.andReturn().getResolvedException().getMessage();
		resultActions.andExpect(view().name("exception/error_404"));
		
		
	}
	

}
