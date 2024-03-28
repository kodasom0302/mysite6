package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.GuestService;
import com.javaex.vo.GuestVo;


@Controller
public class GuestbookController {
	
	@Autowired
	private GuestService guestService;
	
	//리스트
	@RequestMapping(value="/guest/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		System.out.println("GuestbookController.list()");
		
		List<GuestVo> gList=guestService.exeList();
		
		model.addAttribute("gList", gList);
		
		return "/guest/list";
	}
	
	//삭제
	@RequestMapping(value="/guest/delete", method={RequestMethod.GET, RequestMethod.POST})
	public String delete(GuestVo guestVo) {
		System.out.println("GuestbookController.delete()");
		
		guestService.exeDelete(guestVo);
		
		return "redirect:/list";
	}
	
	//삭제폼
	@RequestMapping(value="/guest/deleteform", method={RequestMethod.GET, RequestMethod.POST})
	public String deleteForm() {
		System.out.println("GuestbookController.deleteForm()");
		
		return "/guest/deleteForm";
	}
	
	//등록
	@RequestMapping(value="/guest/write", method={RequestMethod.GET, RequestMethod.POST})
	public String write(GuestVo guestVo) {
		System.out.println("GuestbookController.write()");
		
		guestService.exeWrite(guestVo);
		
		return "redirect:/list";
	}
	
	
	
	
	
	/////////////////////////////////////////////////////////////
	//ajax 방명록 메인
	@RequestMapping(value="guest/ajaxindex", method= {RequestMethod.GET, RequestMethod.POST})
	public String ajaxIndex() {
		System.out.println("GuestbookController.ajaxIndex()");
		
		return "guest/ajaxIndex";
	}

}