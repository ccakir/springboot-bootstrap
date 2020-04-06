package com.cakir.security;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cakir.SpringbootBootstrapApplication;
import com.cakir.model.User;
import com.cakir.repository.UserRepository;
import com.cakir.service.OrtService;
import com.cakir.service.UserService;
import com.cakir.web.dto.UserRegistrationDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application.properties")
class SecurityConfigurationTest {

	private MockMvc mockMvc;
	
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
    private Environment environment;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;
	
	@Value("${server.servlet.context-path}")
    private String contextPath;

	
	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity())
				//.alwaysDo(print())
				.build();

		
	}

	
	
	
	@Test
	void invalidLogin() throws Exception {
		String loginErrorUrl = "/login?error";
		mockMvc.perform(formLogin().user("test").password("test"))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl(loginErrorUrl))
		.andExpect(unauthenticated());
		
		
		
		
	}

	@WithMockUser(username = "test_admin", roles = "ADMIN")
	@Test
	void givenMockAdmin_whenRoleAdmin_thenPermitAdminAndUserDirectory() throws Exception {
		
		mockMvc.perform(get("/auth/admin/"))
		.andExpect(status().isOk());
		
		mockMvc.perform(get("/auth/user/"))
		.andExpect(status().isOk());
	}
	
	@WithMockUser(username = "test_user", roles = "USER")
	@Test
	void givenMockUser_whenRoleUser_thenNotPermitAdminDirectory() throws Exception {
		
		mockMvc.perform(get("/auth/admin/"))
		.andExpect(status().isForbidden());
	}
	
	@WithMockUser(username = "test_user", roles = "USER")
	@Test
	void givenMockUser_whenRoleUser_thenPermitUserDirectory() throws Exception {
		
		mockMvc.perform(get("/auth/user/"))
		.andExpect(status().isOk());
	}
	

	@Test
	public void loginAvailableForAll() throws Exception {

		mockMvc.perform(get("/login")).andExpect(status().isOk());
	}
	
	

}
