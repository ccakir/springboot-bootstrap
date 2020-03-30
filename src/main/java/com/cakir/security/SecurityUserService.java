package com.cakir.security;

public interface SecurityUserService {
	
	String validePasswordResetToken(long userId, String token);

}
