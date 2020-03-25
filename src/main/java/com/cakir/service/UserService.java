package com.cakir.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.cakir.model.User;
import com.cakir.web.dto.UserRegistrationDto;

public interface UserService  extends UserDetailsService{
	
	User findByEmail(String email);
	
	User save(UserRegistrationDto registration);
	
	List<User> allUsers();
	
	List<User> getAllUserPagination(Integer pageNo, Integer pageSize, String sortBy);
	

}
