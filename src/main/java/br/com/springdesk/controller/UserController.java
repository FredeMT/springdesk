package br.com.springdesk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	
	@GetMapping("/login")
	public String login() {
		return "login/login";
	}
	
	@GetMapping("/login-error")
	public String loginError() {
		return "login/login-error";
	}

}
