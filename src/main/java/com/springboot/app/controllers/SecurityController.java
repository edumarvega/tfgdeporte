package com.springboot.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

	
	@GetMapping("/accessDenied")
	public String getAccessDeniedPage() {
		
		return "accessDeniedPage";
	
	}
	
	@GetMapping("/layout")
	public String layout() {
		
		return "layout/layout";
	
	}
	
}
