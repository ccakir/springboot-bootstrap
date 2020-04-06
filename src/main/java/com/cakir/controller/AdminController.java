package com.cakir.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AdminController {

	@GetMapping("auth/admin/")
	public ModelAndView adminPanel() {
		return new ModelAndView("admin/index");
	}
	
	@GetMapping("auth/user/")
	public ModelAndView userPanel() {
		return new ModelAndView("user/index");
	}
}
