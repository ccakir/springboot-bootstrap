package com.cakir.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cakir.model.Ort;
import com.cakir.model.User;
import com.cakir.repository.UserRepository;
import com.cakir.service.OrtService;
import com.cakir.service.UserService;
import com.cakir.validation.UserValidator;
import com.cakir.web.dto.UserRegistrationDto;

@Controller
@RequestMapping("/benutzer")
public class UserRegistrationController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private OrtService ortService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserValidator userValidator;

    @ModelAttribute("userForm")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping(value = "/registration")
    public String showRegistrationForm(ModelMap model) {
    	
    	model.put("ortList", ortService.alleOrte());
    	return "benutzer/registration";
    }

    @PostMapping(value = "/registration")
    public ModelAndView registerUserAccount(@ModelAttribute("userForm") @Valid UserRegistrationDto userDto,
        BindingResult result, ModelMap model) {

    	ModelAndView mav = new ModelAndView();
    	
        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", "emailexisting", "label.error.emailexisting");
        }

        userValidator.validate(userDto, result);
        
        if (result.hasErrors()) {
        	model.put("ortList", ortService.alleOrte());
        	model.put("selectedOrt", userDto.getOrt().getId());
        	
        	mav.setViewName("benutzer/registration");
            return mav;
        }

        userService.save(userDto);
        mav.setViewName("redirect:/benutzer/registration?status=success");
        return mav;
    }
    
    @GetMapping(value = "/allebenutzer/")
    public ModelAndView alleBenutzer(@RequestParam(defaultValue = "1") Integer pageNo,
    		@RequestParam(defaultValue = "4") Integer pageSize, 
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
    
}
