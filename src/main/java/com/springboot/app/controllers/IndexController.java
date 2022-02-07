package com.springboot.app.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@GetMapping({ "/index", "/", "/home" })
	public String index() {
		return "redirect:/login";
	}

}
