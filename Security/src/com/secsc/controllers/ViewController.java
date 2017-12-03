package com.secsc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/")
public class ViewController {

	@RequestMapping("/business")
	public String business(){
		return "business";
	}
	
	@RequestMapping("/index")
	public String index(){
		return "redirect:/";
	}
	
}
