package com.cakir.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.cakir.model.Ort;
import com.cakir.model.Role;
import com.cakir.model.User;
import com.cakir.model.VerificationToken;
import com.cakir.repository.RoleRepository;
import com.cakir.repository.UserRepository;
import com.cakir.repository.VerificationTokenRepository;
import com.cakir.service.OrtService;
import com.cakir.web.dto.UserRegistrationDto;
import com.cakir.web.error.EmailExistsException;
import com.cakir.web.error.UserAlreadyExistException;

@SpringBootTest
public class UserServiceImplIntegrationTest {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private OrtService ortService;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private VerificationTokenRepository verificationTokenrepository;

	@Test
	public void givenNewUser_whenRegistered_thenCorrect() throws EmailExistsException {

		final User user = userService.registerNewAccount(createUserDto("test10@email"));
		assertEquals(user.getEmail(), "test10@email");
		assertNotNull(user);
		assertNotNull(user.getId());

	}

	@Test
	public void givenUser_whenServiceFindById_thenCorrect() {

		final User user = userService.registerNewAccount(createUserDto("test@email.com"));

		User foundUser = userService.findUserById(user.getId()).get();

		assertEquals(user.getId(), foundUser.getId());
	}

	@Test
	public void givenUser_whenServiceFindByEmail_thenCorrect() {

		final User user = userService.registerNewAccount(createUserDto("test3@email.com"));

		User foundUser = userService.findByEmail(user.getEmail());

		assertEquals(user.getEmail(), foundUser.getEmail());

	}

	//Exception Kontrolle
	@Test
	public void givenNewUser_whenDuplicateUserEmail_thenException() {

		userService.registerNewAccount(createUserDto("test5@email.com"));

		assertThrows(UserAlreadyExistException.class, () -> {
			userService.registerNewAccount(createUserDto("test5@email.com"));
		});

	}

	@Test
	public void givenNewUser_whenRoleUser_thenCorrect() {

		Role role = roleRepository.findByName("ROLE_USER");
		if (role == null) {
			role = registerRole("ROLE_USER");
		}
		assertEquals(role.getName(), "ROLE_USER");

		final User user = userService.registerNewAccount(createUserDto("test6@email.com"));
		assertNotNull(user.getRoles());

		List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
		assertEquals(1, roles.size());
		assertEquals("ROLE_USER", roles.iterator().next());

	}

	@Transactional
	public void givenUser_whenRoleAdmin_thenUserNotAdmin() {

		Role role = roleRepository.findByName("ROLE_ADMIN");
		if (role == null) {
			role = registerRole("ROLE_ADMIN");
		}
		final UserRegistrationDto userDto = createUserDto("test7@email.com");
		final Long adminRoleId = role.getId();
		assertNotNull(role.getId());
		userDto.setRole(role.getId().intValue());

		final User user = userService.registerNewAccount(userDto);

		assertFalse(user.getRoles().stream().map(Role::getId).anyMatch(id -> id.equals(adminRoleId)));

	}
	
	@Test
	public void givenUserRegistered_whenCreateVerificationToken_theCorrect() {
		
		final User user = userService.registerNewAccount(createUserDto("test7@email.com"));
		String token = UUID.randomUUID().toString();
		
		userService.createVerificationTokenForUser(user, token);
		
	}
	
	
	//Exception Kontrolle
	@Test
	public void givenUserAndToken_whenDeleteUser_thenDataIVException() {
		
		final User user = userService.registerNewAccount(createUserDto("test8@email.com"));
		String token = UUID.randomUUID().toString();
		assertNotNull(token);
		userService.createVerificationTokenForUser(user, token);
		VerificationToken userToken = userService.getVerificationToken(token);
		assertNotNull(user.getId());
		
		assertThrows(DataIntegrityViolationException.class, () -> {
			userRepository.deleteById(user.getId());
		});
		
		
	}
	
	@Test
	public void givenUserAndToken_whenFirstVerificationTokenDeleteSecondUserDelete_thenCorrect() {
		final User user = userService.registerNewAccount(createUserDto("test9@email.com"));
		String token = UUID.randomUUID().toString();
		userService.createVerificationTokenForUser(user, token);
		VerificationToken vToken = userService.getVerificationToken(token);
		
		verificationTokenrepository.deleteById(vToken.getId());
		userRepository.delete(user);
	}
	
	@Test
	public void givenUserAndToken_whenNewTokenRequest_thenCorrect() {
		final User user = userService.registerNewAccount(createUserDto("test10@email.com"));
		String token = UUID.randomUUID().toString();
		userService.createVerificationTokenForUser(user, token);
		
		final VerificationToken originalToken = userService.getVerificationToken(token);
		final VerificationToken newToken = userService.generateNewVerificationToken(token);
		
		assertEquals(originalToken.getUser().getId(), newToken.getUser().getId());
		assertNotEquals(originalToken.getExpiryDate(), newToken.getExpiryDate());
		assertTrue(newToken.getExpiryDate().getTime() > originalToken.getExpiryDate().getTime());
		assertEquals(originalToken.getId(), newToken.getId());
	}
	
	@Test
	public void givenUserAndVerificationToken_whenTokenValidAndDeleteToken_thenCorrect() {
		
		User user = userService.registerNewAccount(createUserDto("test11@email.com"));
		assertFalse(user.isEnabled());
		String token = UUID.randomUUID().toString();
		userService.createVerificationTokenForUser(user, token);
		
		final String result = userService.validateVerificationToken(token);
		VerificationToken foundToken = verificationTokenrepository.findByToken(token);
		assertEquals(result, UserServiceImpl.TOKEN_VALID);
		user = userService.findByEmail(user.getEmail());
		assertTrue(user.isEnabled());
		assertNull(foundToken);
	}

	//Methode
	private UserRegistrationDto createUserDto(String email) {
		UserRegistrationDto userDto = new UserRegistrationDto();
		userDto.setEmail(email);
		userDto.setFirstName("Firstname");
		userDto.setLastName("Lastname");
		userDto.setConfirmEmail("test@email.com");
		userDto.setPassword("xxxxxx");
		userDto.setConfirmPassword("xxxxx");
		userDto.setOrt(createOrt().getId());

		return userDto;

	}

	private Ort createOrt() {
		Ort ort = new Ort();
		ort.setOrtsname("testOrt");
		ort.setPlz("testPlz");
		ort.setStadt("testStadt");
		ort.setAdresse("testAdresse");
		ort.setLand("testLand");

		return ortService.save(ort);

	}

	private Role registerRole(String role_role) {
		Role role = new Role(role_role);
		return roleRepository.save(role);

	}

}
