package com.cakir.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cakir.model.User;
import com.cakir.service.UserService;

@Controller
@RequestMapping("/benutzer/myaccount")
public class MeinKontoController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/info")
	public ModelAndView accountInfo(Principal principal) {
		
		
		User user = userService.findByEmail(principal.getName());
		ModelAndView mav = new ModelAndView("benutzer/meinKonto");
		mav.addObject("user", user);
		
		return mav;
	}
	
	
	

}
