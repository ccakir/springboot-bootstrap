package com.cakir.security;

import java.util.Arrays;
import java.util.Calendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cakir.model.PasswordResetToken;
import com.cakir.model.User;
import com.cakir.repository.PasswordResetTokenRepository;

@Service
@Transactional
public class SecurityUserServiceImpl implements SecurityUserService{
	
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Override
	public String validePasswordResetToken(long userId, String token) {
		
		final PasswordResetToken passwordToken = passwordResetTokenRepository.findByToken(token);
		
		if(passwordToken == null || (passwordToken.getUser().getId() != userId)) {
			return "invalidToken";
		}
		
		final Calendar cal = Calendar.getInstance();
		
		if((passwordToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			
			return "expired";
		}
		
		final User user = passwordToken.getUser();
		
		final Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		return null;
	}

}
