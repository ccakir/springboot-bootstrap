package com.cakir.controller;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;


import com.cakir.model.Ort;
import com.cakir.model.User;
import com.cakir.model.VerificationToken;
import com.cakir.repository.UserRepository;
import com.cakir.repository.VerificationTokenRepository;
import com.cakir.service.EmailService;
import com.cakir.service.OrtService;
import com.cakir.service.UserService;
import com.cakir.web.dto.UserRegistrationDto;
import com.cakir.web.error.UserNotFoundException;

@Controller
@RequestMapping("benutzer")
public class UserRegistrationController {
	
	private final Log logger  = LogFactory.getLog(getClass());

    @Autowired
    private UserService userService;
    
    @Autowired
    private OrtService ortService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MessageSource messages;
    
    @Autowired
    private Environment env;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    public JavaMailSender emailSender;
    
    @Autowired
    private VerificationTokenRepository tokenRepository;
    
    @Autowired
    private LocaleResolver localeResolver;
    
    

    public UserRegistrationController() {
		super();
		// TODO Auto-generated constructor stub
	}

	

   

    @PostMapping(value = "/registration")
    public String registerUserAccount(@ModelAttribute("userForm")@Valid UserRegistrationDto userDto,
        BindingResult result, ModelMap model, final HttpServletRequest request) {

    	Locale locale = request.getLocale();
    	
    	if (result.hasErrors()) {
        	model.put("ortList", ortService.alleOrte());
        	model.put("status", "error");
        	//model.put("selectedOrt", userDto.getOrt().getId());
        	return "benutzer/registration";  	
        }
    	final User registiried = userService.registerNewAccount(userDto);
    	
    	     
       return "redirect:/benutzer/registration?register=success&lang=" + locale.getLanguage(); 
    }
    
   

	@GetMapping(value = "/allebenutzer/")
    public ModelAndView alleBenutzer(@RequestParam(defaultValue = "1") Integer pageNo,
    		@RequestParam(defaultValue = "20") Integer pageSize, 
    		@RequestParam(defaultValue = "id") String sortBy,
    		ModelMap model) {
    	long benutzerSize = userRepository.count();
    	long pages = benutzerSize / pageSize;
    	
    	if(benutzerSize % pageSize != 0) {pages++;}
    	
    	List<Ort> listOrt = ortService.alleOrte();
    	
    	model.put("benutzer", userService.getAllUserPagination(pageNo-1, pageSize, sortBy));
    	model.put("benutzerSize", benutzerSize);
    	model.put("pageSize", pageSize);
    	model.put("pageNo", pageNo);
    	model.put("pages", pages);
    	model.put("listOrt", listOrt);
    	ModelAndView mav = new ModelAndView("benutzer/benutzerList");
    	
    	return mav;

    	
	}
	
	
	@GetMapping(value = "/userverification/{id}")
	public ModelAndView sendVerificationEmail(@PathVariable("id") Long id) {
		
		Optional<User> user = userService.findUserById(id);
		
		userService.UserAlreadyEnabled(id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", user.get());
		mav.setViewName("benutzer/sendVerificationEmail");
		return mav;
		
	}
	
	@PostMapping(value = "/userverification/sendVerificationEmail")
	public ModelAndView sendVerificationEmail(@RequestParam("userId") Long id, HttpServletRequest request) {
		
		Locale locale = localeResolver.resolveLocale(request);
		
		User user = userService.findUserById(id).orElseThrow(() -> new UserNotFoundException());
		
		if(user != null) {
			
			VerificationToken userToken = tokenRepository.findByUser(user);
			String token = null;
			
			if(userToken != null) {
				
				VerificationToken newToken = userService.generateNewVerificationToken(userToken.getToken());
				token = newToken.getToken();
				
			} else {
				
				token = UUID.randomUUID().toString();
				userService.createVerificationTokenForUser(user, token);
			}
				
				try {
					emailSender.send(constructVerificationEmail(getAppUrl(request), locale, token, user));
				} catch (MailException | MessagingException e) {
					
					e.printStackTrace();
				}
			
			
			
			
	        
		}
		ModelAndView mav = new ModelAndView("benutzer/sendVerificationEmail");
		mav.addObject("status", "success");
		return mav;
		
	}
	///////////////////KEIN API////////////////////////////
	 private String getAppUrl(HttpServletRequest request) {
			
			return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		}
	 
	 
	
	 private MimeMessage constructVerificationEmail(final String contextPath, final Locale locale, final String newToken, User user) throws MessagingException {
		 
		 final String url = contextPath + "/userRegistrationConfirm?id=" + user.getId() + "&token=" + newToken;
		 
		 final String message1 = messages.getMessage("message.registrationConfirm1", null, locale);
		 final String message2 = messages.getMessage("message.registrationConfirm2", null, locale);
		 
		 return constructEmail("Registration Confirm", message1, url, message2, user);
		 
		 
	 }
	 
	 
	 private MimeMessage constructEmail(String subject, String message1, String url, String message2, User user) throws MessagingException {
		 
		 final MimeMessage message = emailSender.createMimeMessage();
		 
		 MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		 String htmlMsg = "<h4>"+message1+"</h4><br><br>"
				 +"<a href='"+url+"'>"+url+"</a><br><br>"
				 +"<h5>"+message2+"</h5>";
		 message.setContent(htmlMsg, "text/html");
		 helper.setTo(user.getEmail());
		 helper.setSubject(subject);
		
		 return message;
	 }
	   
    
}
