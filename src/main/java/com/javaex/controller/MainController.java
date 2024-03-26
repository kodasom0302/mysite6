package com.javaex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping("/main")
	//method={RequestMethod.GET, RequestMethod.POST} 생략 가능
	//value= 생략 가능
	public String index() {
		System.out.println("MainController.index()");
		
		return "main/index";
	}

}
