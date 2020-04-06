package com.cakir.security;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cakir.model.Role;
import com.cakir.model.User;
import com.cakir.repository.RoleRepository;
import com.cakir.repository.UserRepository;
import com.cakir.service.UserService;
import com.cakir.web.dto.UserRegistrationDto;

@SpringBootTest
class SpringSecurityRolesIntegrationTest {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	private Role role;
	private User user;

	@Test
	void givenUserWithRole_whenDeleteUser_thenRoleNotDelete() {

		role = new Role("ROLE_TEST");
		roleRepository.save(role);

		user = new User();
		user.setFirstName("test");
		user.setLastName("test");
		user.setEmail("test@email.com");
		user.setPassword(passwordEncoder.encode("123"));
		user.setOrt(null);
		user.setEnabled(true);
		user.setRoles(Arrays.asList(role));

		userRepository.save(user);

		assertNotNull(roleRepository.findByName(role.getName()));
		assertNotNull(userRepository.findByEmail(user.getEmail()));

		userRepository.delete(user);
		assertNotNull(roleRepository.findByName(role.getName()));
		assertNull(userRepository.findByEmail(user.getEmail()));

	}
	
	@Test
	public void test() {
		
		
		role = new Role("ROLE_TEST1");
		roleRepository.save(role);

		user = new User();
		user.setFirstName("test");
		user.setLastName("test");
		user.setEmail("test1@email.com");
		user.setPassword(passwordEncoder.encode("123"));
		user.setOrt(null);
		user.setEnabled(true);
		user.setRoles(Arrays.asList(role));

		userRepository.save(user);

		assertNotNull(roleRepository.findByName(role.getName()));
		assertNotNull(userRepository.findByEmail(user.getEmail()));
		
		
		user.setRoles(null);
		userRepository.saveAndFlush(user);
		roleRepository.delete(role);
		assertNull(roleRepository.findByName(role.getName()));
		assertNotNull(userRepository.findByEmail(user.getEmail()));
		
	}

}
