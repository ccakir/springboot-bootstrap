package com.cakir.controller;

import java.util.Collection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginController {
	
	@Autowired
	private LocaleResolver localeResolver;
	
	
	
	@GetMapping("/login")
    public ModelAndView login(Model model, String error, String logout, HttpServletRequest request) {
    	
    	Locale locale = localeResolver.resolveLocale(request);
        if (error != null)
            model.addAttribute("error", "Username und Password sind ungültig.");

        if (logout != null)
            model.addAttribute("message", "Sie haben sich erfolgreich ausgeloggt!");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("welcome/login");
        mav.addObject("lang", locale);
        return mav;
    }
	
	@GetMapping("/auth")
	public ModelAndView authLogin(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		
		
		if(request.isUserInRole("ROLE_ADMIN")) {
			mav.setViewName("redirect:auth/admin/");
		} else {
			mav.setViewName("redirect:auth/user/");
		}
		
		
		return mav;
	}

}
