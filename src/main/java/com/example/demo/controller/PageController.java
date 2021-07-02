package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
	
	@GetMapping("/home")
	public String getHomepage() {
		return "index";
	}
	
	@GetMapping("/login")
	public String getLoginform() {
		return "login";
	}
	
	@GetMapping("/test")
	public String getTest() {
		return "test";
	}
	
}