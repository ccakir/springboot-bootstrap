package com.cakir.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.cakir.model.PasswordResetToken;
import com.cakir.model.User;
import com.cakir.model.VerificationToken;
import com.cakir.web.dto.UserRegistrationDto;
import com.cakir.web.error.UserAlreadyEnabledException;
import com.cakir.web.error.UserAlreadyExistException;

public interface UserService  extends UserDetailsService{
	
	User findByEmail(String email);
	
	User registerNewAccount(UserRegistrationDto registration) throws UserAlreadyExistException;
	
	List<User> allUsers();
	
	List<User> getAllUserPagination(Integer pageNo, Integer pageSize);
	
	Optional<User> findUserById(Long id);
	
	boolean UserAlreadyEnabled(Long id) throws UserAlreadyEnabledException;
	
	void createVerificationTokenForUser(User user, String token);
	
	VerificationToken getVerificationToken(String verificationToken);
	
	VerificationToken generateNewVerificationToken(String token);
	
	String validateVerificationToken(String token);
	
	void createPasswordResetTokenForUser(User user, String token);
	
	PasswordResetToken getPasswordResetToken(String token);
	
	User getUserByPasswordResetToken(String token);
	
	void changeUserPassword(User user, String password);
	
	void deleteUser(User user);

}
