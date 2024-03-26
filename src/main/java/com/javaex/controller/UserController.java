package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;

	
	//로그아웃
	@RequestMapping(value="/user/logout", method={RequestMethod.GET, RequestMethod.POST})
	public String logout(HttpSession session) {
		System.out.println("UserController.logout()");
		
		session.invalidate();

		return "redirect:/main";
	}
	
	//회원정보수정
	@RequestMapping(value="/user/modify", method={RequestMethod.GET, RequestMethod.POST})
	public String modify(@ModelAttribute UserVo userVo, HttpSession session) {
		System.out.println("UserController.modify()");
		
		userService.exeModify(userVo);
		
		//수정 정보를 세션에 올린다
		session.setAttribute("authUser", userVo);

		return "redirect:/main";
	}
	
	//회원정보수정폼
	@RequestMapping(value="/user/modifyform", method={RequestMethod.GET, RequestMethod.POST})
	public String modifyForm(HttpSession session) {
		System.out.println("UserController.modifyForm()");
		
		session.getAttribute("authUser");

		return "/user/modifyForm";
	}
	
	//회원가입
	@RequestMapping(value="/user/join", method={RequestMethod.GET, RequestMethod.POST})
	public String join(@ModelAttribute UserVo userVo) {
		System.out.println("UserController.join()");
		
		userService.exeJoin(userVo);

		return "/user/joinOk";
	}
	
	//회원가입폼
	@RequestMapping(value="/user/joinform", method={RequestMethod.GET, RequestMethod.POST})
	public String joinForm() {
		System.out.println("UserController.joinForm()");

		return "/user/joinForm";
	}
	
	//로그인
	@RequestMapping(value="/user/login", method={RequestMethod.GET, RequestMethod.POST})
	public String login(@ModelAttribute UserVo userVo, HttpSession session) {
		System.out.println("UserController.login()");

		UserVo authUser=userService.exeLogin(userVo);
		//System.out.println(authUser);
		
		if (authUser!=null) {
			//세션에 올린다
			session.setAttribute("authUser", authUser);
			
			return "redirect:/main";
			
		} else {
			return "redirect:/user/loginForm";
		}
	}
	
	//로그인폼
	@RequestMapping(value="/user/loginform", method={RequestMethod.GET, RequestMethod.POST})
	public String loginForm() {
		System.out.println("UserController.loginForm()");

		return "/user/loginForm";
	}

}
