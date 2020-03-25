package com.cakir.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class MainController {
	
	

	    @GetMapping("/")
	    public String root() {
	        return "index";
	    }

	    @GetMapping("/login")
	    public String login(Model model, String error, String logout) {
	        if (error != null)
	            model.addAttribute("error", "Username und Password sind ungültig.");

	        if (logout != null)
	            model.addAttribute("message", "Sie haben sich erfolgreich ausgeloggt!");

	        return "login";
	    }

	   

}
