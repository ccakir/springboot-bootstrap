package com.cakir.task;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cakir.repository.PasswordResetTokenRepository;
import com.cakir.repository.VerificationTokenRepository;

@Service
@Transactional
public class TokenDeleteTask {

	@Autowired
    VerificationTokenRepository tokenRepository;

    @Autowired
    PasswordResetTokenRepository passwordTokenRepository;
    
    @Scheduled(cron = "${purge.cron.expression}")
    public void pugeExpired() {
    	Date now = Date.from(Instant.now());
    	
    	passwordTokenRepository.deleteAllExpiredSince(now);
    	tokenRepository.deleteAllExpiredSince(now);
    }
}
