package com.cakir.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.cakir.model.Ort;
import com.cakir.model.PasswordResetToken;
import com.cakir.model.Role;
import com.cakir.model.User;
import com.cakir.model.VerificationToken;
import com.cakir.repository.RoleRepository;
import com.cakir.repository.UserRepository;
import com.cakir.repository.VerificationTokenRepository;
import com.cakir.service.OrtService;
import com.cakir.service.UserService;
import com.cakir.web.dto.UserRegistrationDto;
import com.cakir.web.error.EmailExistsException;
import com.cakir.web.error.UserAlreadyEnabledException;
import com.cakir.web.error.UserAlreadyExistException;
import com.cakir.web.error.UserNotEnabledException;

@SpringBootTest
public class UserServiceImplIntegrationTest {

	@InjectMocks
	private UserServiceImpl mockUserService;

	@Mock
	private UserRepository mockUserRepository;

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
		assertFalse(user.isEnabled());

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

	// Exception Kontrolle
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

	// Exception Kontrolle
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
	public void givenUserAndVerificationToken_whenTokenValid_thenDeleteToken_thenUserEnabled() {

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

	@Test
	public void givenVerificationToken_whenTokenInvalid_thenReturnInvalid() {

		String result = userService.validateVerificationToken(UUID.randomUUID().toString());
		assertNotNull(result);
		assertEquals(result, UserServiceImpl.TOKEN_INVALID);

	}

	@Test
	public void givenUserAndVerificationToken_whenTokenTimeExpired_thenDeleteToken_thenReturnTokenExpired() {

		User user = userService.registerNewAccount(createUserDto("test12@email.com"));
		String token = UUID.randomUUID().toString();
		userService.createVerificationTokenForUser(user, token);

		VerificationToken foundToken = userService.getVerificationToken(token);
		foundToken.setExpiryDate(Date.from(foundToken.getExpiryDate().toInstant().minus(2, ChronoUnit.DAYS)));
		verificationTokenrepository.saveAndFlush(foundToken);

		String result = userService.validateVerificationToken(token);
		VerificationToken findToken = userService.getVerificationToken(token);
		assertNull(findToken);
		assertNotNull(result);
		assertEquals(result, UserServiceImpl.TOKEN_EXPIRED);

	}

	@Test
	public void givenUsers_whenCallAllUsers_thenReturnAllUser() {

		User user1 = userService.registerNewAccount(createUserDto(UUID.randomUUID().toString()));
		User user2 = userService.registerNewAccount(createUserDto(UUID.randomUUID().toString()));

		List<User> listUser = userService.allUsers();
		assertNotNull(listUser);
		assertTrue(listUser.size() > 1);
	}

	@Test
	public void givenNewUser_whenUserNotEnabled_thenFalseUserAlreadyEnabled() {

		User user = userService.registerNewAccount(createUserDto(UUID.randomUUID().toString()));
		assertFalse(user.isEnabled());

		boolean result = userService.UserAlreadyEnabled(user.getId());
		assertFalse(result);

	}

	// Exception Kontrolle
	@Test
	public void givenEnabledUser_whenUserSetEnable_thenUserAlreadyEnabledException() {

		User user = userService.registerNewAccount(createUserDto(UUID.randomUUID().toString()));
		user.setEnabled(true);

		userRepository.saveAndFlush(user);
		User findUser = userService.findUserById(user.getId()).get();
		assertTrue(findUser.isEnabled());

		assertThrows(UserAlreadyEnabledException.class, () -> {
			userService.UserAlreadyEnabled(findUser.getId());
		});

	}

	@Test
	public void givenUserAndPasswordResetToken_whenSave_thenCorrect() {
		User user = userService.registerNewAccount(createUserDto(UUID.randomUUID().toString()));
		String token = UUID.randomUUID().toString();

		userService.createPasswordResetTokenForUser(user, token);
		PasswordResetToken pToken = userService.getPasswordResetToken(token);
		assertNotNull(pToken);
		assertEquals(user.getId(), pToken.getUser().getId());
		assertEquals(token, pToken.getToken());
	}

	@Test
	public void givenUserAndPasswordResetToken_whenUserByPasswordResetToken_thenReturnUser_thenCorrect() {
		User user = userService.registerNewAccount(createUserDto(UUID.randomUUID().toString()));
		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);

		User foundUser = userService.getUserByPasswordResetToken(token);

		assertNotNull(foundUser);
		assertEquals(foundUser.getId(), user.getId());
	}

	@Test
	public void givenUser_whenChancePassword_thenSetPasswordAndSaveUser() {
		User user = userService.registerNewAccount(createUserDto(UUID.randomUUID().toString()));
		String password = user.getPassword();

		String newPassword = "testPassword";
		userService.changeUserPassword(user, newPassword);

		User foundUser = userService.findUserById(user.getId()).get();

		assertNotNull(foundUser);
		assertNotNull(foundUser.getPassword());
		assertNotEquals(foundUser.getPassword(), password);
	}

	@Test
	public void givenNewUserAndVerificationToeknForUserAndPasswordResetTokenForUser_whenDeleteUserAndDeleteTokens_thenCorrect() {

		User user = userService.registerNewAccount(createUserDto(UUID.randomUUID().toString()));
		Long userId = user.getId();
		String vToken = UUID.randomUUID().toString();
		String pToken = UUID.randomUUID().toString();
		userService.createVerificationTokenForUser(user, vToken);
		userService.createPasswordResetTokenForUser(user, pToken);

		VerificationToken verificationToken = userService.getVerificationToken(vToken);
		PasswordResetToken passwordResetToken = userService.getPasswordResetToken(pToken);
		User findUser = userService.findUserById(user.getId()).get();

		assertNotNull(findUser);
		assertNotNull(verificationToken);
		assertNotNull(passwordResetToken);

		userService.deleteUser(findUser);

		User userAfterDelete = userService.findUserById(userId).orElse(null);
		VerificationToken vTokenAfterDelete = userService.getVerificationToken(verificationToken.getToken());
		PasswordResetToken pTokenAfterDelete = userService.getPasswordResetToken(passwordResetToken.getToken());

		assertNull(userAfterDelete);
		assertNull(pTokenAfterDelete);
		assertNull(vTokenAfterDelete);
	}
	

	@Test
	public void givenUsers_whenRequestingFirstPageSizeThree_thenReturnPageOneWithTwoUsers() {

		int userSize = userService.allUsers().size();

		List<User> list = userService.getAllUserPagination(0, 3);
		assertTrue(list.size() == 3);

		

	}

	//Exception Kontrolle
	@Test
	public void whenLoadUserByUsernameAndUserNull_thenUsernameNotFoundException() {

		assertThrows(UsernameNotFoundException.class, () -> {
			userService.loadUserByUsername(UUID.randomUUID().toString());
		});
	}
	
	//Exception Kontrolle
	@Test
	public void givenUser_whenRegisteredUserLoadByUsernameButNotEnabled_thenReturnUserNotEnabledException() {
		User user = userService.registerNewAccount(createUserDto(UUID.randomUUID().toString()));
		String email = user.getEmail();
		
		assertFalse(user.isEnabled());
		assertThrows(UserNotEnabledException.class, () -> {
			userService.loadUserByUsername(email);
		});
		
	}
	
	@Test
	public void givenUser_whenRegisteredAndEnabled_thenReturnLoginAndUserDetails() {
		
		User user = userService.registerNewAccount(createUserDto(UUID.randomUUID().toString()));
		user.setEnabled(true);
		
		user = userRepository.saveAndFlush(user);
		
		assertNotNull(userService.loadUserByUsername(user.getEmail()));
	}
	

	// Methode
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
