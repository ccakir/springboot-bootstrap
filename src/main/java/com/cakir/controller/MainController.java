package com.cakir.controller;

import java.util.Locale;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.ui.context.support.UiApplicationContextUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import com.cakir.model.PasswordResetToken;
import com.cakir.model.User;
import com.cakir.model.VerificationToken;
import com.cakir.repository.VerificationTokenRepository;
import com.cakir.security.SecurityUserService;
import com.cakir.service.EmailService;
import com.cakir.service.OrtService;
import com.cakir.service.UserService;
import com.cakir.web.dto.PasswordDto;
import com.cakir.web.dto.UserRegistrationDto;
import com.cakir.web.error.UserNotFoundException;
import com.google.common.collect.Sets.SetView;

@RestController
public class MainController {
	
	@Autowired
	private VerificationTokenRepository tokenRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
    private OrtService ortService;
		
	@Autowired
	private EmailService emailService;
	
	@Autowired
    public JavaMailSender emailSender;
	
	@Autowired
    private MessageSource messages;
	
	@Autowired
	private LocaleResolver localeResolver;
	
	@Autowired
	private SecurityUserService securityUserService;

	    @GetMapping("/")
	    public ModelAndView root() {
	    	return new ModelAndView("login");
	    }
	    @GetMapping("/index")
	    public ModelAndView index() {
	    	return new ModelAndView("index");
	    }
	    
	    @GetMapping(value = "/registration")
	    public ModelAndView showRegistrationForm() {
	    	ModelAndView mav = new ModelAndView("registration");
	    	mav.addObject("userForm", new UserRegistrationDto());
	    	mav.addObject("ortList", ortService.alleOrte());
	    	return mav;
	    }
	    
	    @PostMapping(value = "/registration")
	    public ModelAndView registerUserAccount(@ModelAttribute("userForm")@Valid UserRegistrationDto userDto,
	        BindingResult result, ModelMap model, final HttpServletRequest request) {
	    	
	    	ModelAndView mav = new ModelAndView();
	    	
	    	Locale locale = request.getLocale();
	    	
	    	if (result.hasErrors()) {
	    		mav.addObject("ortList", ortService.alleOrte());
	    		mav.addObject("status", "error");
	    		//mav.addObject("selectedOrt", userDto.getOrt());
	    		mav.setViewName("registration");
	        	return mav;  	
	        }
	    	final User registiried = userService.registerNewAccount(userDto);
	    	
	    	     
	    	mav.setViewName("redirect:/registration?register=success&lang=" + locale.getLanguage()); 
	    	return mav;
	    }

	    @GetMapping("/login")
	    public ModelAndView login(Model model, String error, String logout, HttpServletRequest request) {
	    	
	    	Locale locale = localeResolver.resolveLocale(request);
	        if (error != null)
	            model.addAttribute("error", "Username und Password sind ungültig.");

	        if (logout != null)
	            model.addAttribute("message", "Sie haben sich erfolgreich ausgeloggt!");
	        ModelAndView mav = new ModelAndView();
	        mav.setViewName("login");
	        mav.addObject("lang", locale);
	        return mav;
	    }
	    
	    @GetMapping(value = "/userRegistrationConfirm")
	    public ModelAndView registerConfirm(@RequestParam("id") final Long id, @RequestParam("token") final String token) {
	    	
	    	ModelAndView mav = new ModelAndView("verification/userRegistrationConfirm");
	    	User user = userService.findUserById(id).orElseThrow(() -> new UserNotFoundException());
	    	final String tokenResult = userService.validateVerificationToken(token);
	    	mav.addObject("token", tokenResult);
	    	mav.addObject("username", user.getEmail());
	    	mav.addObject("password", passwordEncoder.encode(user.getPassword()));
	    	
	    	return mav;
				
	    }
	    
	    @GetMapping("/forgetpassword")
	    public ModelAndView forgetPassword(HttpServletRequest request) {
	    	
	    	
	    	ModelAndView mav = new ModelAndView();
	    	mav.setViewName("passwordForget");
	    	return mav;
	    }
	    
	    @PostMapping(value = "/forgetpassword")
	    public ModelAndView forgetPasswordEmailSend(HttpServletRequest request, @RequestParam("email") String email) throws MailException, MessagingException {
	    	
	    	Locale locale = localeResolver.resolveLocale(request);
	    	
	    	ModelAndView mav = new ModelAndView();
	    	mav.setViewName("passwordForget");
	    	User user = userService.findByEmail(email);
	    	    	
	    	if(user == null) {
	    		mav.addObject("status","UserNotFound");
	    		return mav;
	    	}
	    	
	    	
	    	final String token = UUID.randomUUID().toString();
	    	userService.createPasswordResetTokenForUser(user, token);
	    	final String message1 = messages.getMessage("message.passwordresetemail", null, locale);
			final String message2 = messages.getMessage("message.passwordresetemail2", null, locale);
	    	emailSender.send(emailService.constructPasswordResetEmail(getAppUrl(request), locale, token, user, message1, message2, "Password Reset"));
	    	
	    	mav.addObject("status", "success");
	    	
	    	return mav;
	    	
	    }
	    
	    @GetMapping(value = "/changePassword")
	    public ModelAndView changePasswordPage(final Locale locale, @RequestParam("id") final Long id, @RequestParam("token") final String token, Model model) {
	    	
	    	String result = securityUserService.validePasswordResetToken(id, token);
	    	ModelAndView mav = new ModelAndView();
	    	if(result != null) {
	    		
	    		mav.addObject("message", messages.getMessage("auth.message."+ result, null, locale));
	    		mav.setViewName("redirect:/login?lang="+locale.getLanguage());
	    		return mav;
	    	}
	    	mav.setViewName("redirect:/updatePassword?lang="+locale.getLanguage());
	    	return mav;
	    }
	    
	    @GetMapping(value = "/updatePassword")
	    public ModelAndView updatePasswordPage() {
	    	ModelAndView mav = new ModelAndView("updatePassword");
	    	mav.addObject("passwordDtoForm", new PasswordDto());
	    	return mav;
	    }
	    
	    @PostMapping("/updatePassword")
	    public ModelAndView savePassword(@ModelAttribute("passwordDtoForm") @Valid PasswordDto passwordDto, BindingResult bindingResult, Locale locale) {
	    	ModelAndView mav = new ModelAndView();
	    	
	    	if(bindingResult.hasErrors()) {
	    		
	    		mav.setViewName("updatePassword");
	    		return mav;
	    	}
	    	
	    	//Wenn passworddto ok dann changepassword und zum login 
	    	User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    	userService.changeUserPassword(user, passwordDto.getNewPassword());
	    	mav.addObject("passwordReset", "success");
	    	mav.setViewName("redirect:/login?lang="+locale.getLanguage());
	    	return mav;
	    }

	    private String getAppUrl(HttpServletRequest request) {
			
			return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		}

}
