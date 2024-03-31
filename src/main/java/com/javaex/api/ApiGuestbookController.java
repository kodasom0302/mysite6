package com.javaex.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.GuestService;
import com.javaex.vo.GuestVo;


@Controller
public class ApiGuestbookController {
	
	@Autowired
	private GuestService guestService;
	
	//리스트
	@ResponseBody	//return의 데이터를 json 방식으로 변경해서 응답 문서(response)의 body에 붙여서 보내줘
	@RequestMapping(value="/api/guestbooks", method=RequestMethod.GET)
	public List<GuestVo> list() {
		System.out.println("ApiGuestbookController.list()");
		
		List<GuestVo> gList=guestService.exeList();
		System.out.println(gList);
		
		return gList;
	}
	
	//등록
	@ResponseBody
	@RequestMapping(value="/api/guestbooks", method=RequestMethod.POST)
	public GuestVo add(@ModelAttribute GuestVo guestVo) {
		System.out.println("ApiSGuestbookController.add()");
		System.out.println(guestVo);
		
		GuestVo gVo=guestService.exeAddandGuest(guestVo);
		
		return gVo;
	}
	
	//삭제
	@ResponseBody	//jsp 찾지 마라
	@RequestMapping(value="/api/guestbooks/{no}", method=RequestMethod.DELETE)
	public int remove(@PathVariable("no") int no, @ModelAttribute GuestVo guestVo) {
		System.out.println("ApiSGuestbookController.remove()");
		System.out.println(no);
		System.out.println(guestVo);
		
		int count=guestService.exeRemove(guestVo);
		
		return count;
	}
	
	/*
	//삭제2
		@ResponseBody	//jsp 찾지 마라
		@RequestMapping(value="/api/guestbooks/delete", method=RequestMethod.POST)
		public int remove2(@ModelAttribute GuestVo guestVo) {
			System.out.println("ApiSGuestbookController.remove2()");
			
			int count=guestService.exeRemove(guestVo);
			
			return count;
		}
	*/
	
}
