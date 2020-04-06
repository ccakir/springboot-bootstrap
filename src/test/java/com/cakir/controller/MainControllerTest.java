package com.cakir.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import com.cakir.repository.UserRepository;
import com.cakir.service.OrtService;
import com.cakir.service.UserService;
import com.cakir.service.impl.OrtServiceImpl;
import com.cakir.web.dto.UserRegistrationDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
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

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();

	}

	
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void givenRoleAdmin_thenReturnLocationAdmin() throws Exception {
		
		mockMvc.perform(get("/welcome"))
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
	@WithAnonymousUser
	public void registrationValidation() throws Exception {
		
		UserRegistrationDto userDto = new UserRegistrationDto();
		
		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
		param.add("firstName", "fsdfsdf");
		param.add("lastName", "fsdfs");
		param.add("password", "sdfsd");
		param.add("confirmPassword", "sdfsdf");
		param.add("email", "sdfsdf");
		param.add("confirmEmail", "sdfsdf");
		
		mockMvc.perform(post("/welcome/registration").params(param))
		.andExpect(model().attribute("userForm", param))
		.andDo(print())
		.andExpect(status().is(400));
		
		
	}
	
	

}
