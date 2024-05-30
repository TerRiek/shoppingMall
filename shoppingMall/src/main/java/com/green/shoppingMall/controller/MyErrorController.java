package com.green.shoppingMall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController {
	
	@RequestMapping("/myerror")
	public String error() {
		
		return "/error/error";
	}
}
