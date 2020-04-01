package com.cakir.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.cakir.model.Ort;
import com.cakir.model.PasswordResetToken;
import com.cakir.model.Role;
import com.cakir.model.User;
import com.cakir.model.VerificationToken;
import com.cakir.repository.PasswordResetTokenRepository;
import com.cakir.repository.RoleRepository;
import com.cakir.repository.UserRepository;
import com.cakir.repository.VerificationTokenRepository;
import com.cakir.service.OrtService;
import com.cakir.service.UserService;
import com.cakir.web.dto.UserRegistrationDto;
import com.cakir.web.error.UserAlreadyEnabledException;
import com.cakir.web.error.UserAlreadyExistException;
import com.cakir.web.error.UserNotEnabledException;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private OrtService ortService;
	
	@Autowired
	private VerificationTokenRepository tokenRepository;
	
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	
	
	public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";
	

	@Override
	public UserDetails loadUserByUsername(String email) {
		User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid userna");
        } else if(user.isEnabled() == false) {
        	
        	throw new UserNotEnabledException();
        	
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
            user.getPassword(),
            mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		 return roles.stream()
		            .map(role -> new SimpleGrantedAuthority(role.getName()))
		            .collect(Collectors.toList());
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	

	@Override
	public List<User> allUsers() {
		return userRepository.findAll();
	}

	@Override
	public List<User> getAllUserPagination(Integer pageNo, Integer pageSize, String sortBy) {
		
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		
		Page<User> pagedResult = userRepository.findAll(paging);
		
		if(pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<User>();
		}
		
	}

	@Override
	public User registerNewAccount(UserRegistrationDto userDto) {
		
		if(emailExists(userDto.getEmail())) {
			throw new UserAlreadyExistException("There is an account with that email adress: "+userDto.getEmail());
		}
		
		Ort ort = ortService.findById(userDto.getOrt()).orElse(null);
		final User user = new User();
		
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setEmail(userDto.getEmail());
		user.setUsing2FA(userDto.isUsing2FA());
		user.setOrt(ort);
		user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
		return userRepository.save(user);
		
	}
	
	private boolean emailExists(final String email) {
		return userRepository.findByEmail(email) != null;
	}

	@Override
	public Optional<User> findUserById(Long id) {
		
		return userRepository.findById(id);
	}

	@Override
	public boolean UserAlreadyEnabled(Long id) throws UserAlreadyEnabledException {
		
		Optional<User> user = userRepository.findById(id);
		
		if(user.get().isEnabled()) {
			throw new UserAlreadyEnabledException();
		}
		
		return false;
	}

	@Override
	public void createVerificationTokenForUser(User user, String token) {
		
		VerificationToken myToken = new VerificationToken(token, user);
		tokenRepository.save(myToken);
	}

	@Override
	public VerificationToken getVerificationToken(String verificationToken) {
		
		return tokenRepository.findByToken(verificationToken);
	}

	@Override
	public VerificationToken generateNewVerificationToken(String existingToken) {
		VerificationToken vToken = tokenRepository.findByToken(existingToken);
		vToken.updateToken(UUID.randomUUID().toString());
		vToken = tokenRepository.save(vToken);
		return vToken;
	}

	@Override
	public String validateVerificationToken(String token) {
		final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
            .getTime()
            - cal.getTime()
                .getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        tokenRepository.delete(verificationToken);
        userRepository.save(user);
        return TOKEN_VALID;
	}

	@Override
	public void createPasswordResetTokenForUser(User user, String token) {
		PasswordResetToken pToken = new PasswordResetToken(token, user);
		passwordResetTokenRepository.save(pToken);
		
	}

	@Override
	public PasswordResetToken getPasswordResetToken(String token) {
		
		return passwordResetTokenRepository.findByToken(token);
	}

	@Override
	public User getUserByPasswordResetToken(String token) {
		
		return passwordResetTokenRepository.findByToken(token).getUser();
	}

	@Override
	public void changeUserPassword(User user, String password) {
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
		
	}

	@Override
	public void deleteUser(User user) {
		final VerificationToken verificationToken = tokenRepository.findByUser(user);

        if (verificationToken != null) {
            tokenRepository.delete(verificationToken);
        }

        final PasswordResetToken passwordToken = passwordResetTokenRepository.findByUser(user);

        if (passwordToken != null) {
        	passwordResetTokenRepository.delete(passwordToken);
        }

        userRepository.delete(user);
	}
	
	

}
