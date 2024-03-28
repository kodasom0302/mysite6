package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.GuestDao;
import com.javaex.vo.GuestVo;

@Service
public class GuestService {
	
	@Autowired
	private GuestDao guestDao;
	
	//리스트
	public List<GuestVo> exeList() {
		System.out.println("GuestService.exeList()");
		
		List<GuestVo> gList=guestDao.guestSelect();
		
		return gList;
	}
	
	//삭제
	public int exeDelete(GuestVo guestVo) {
		System.out.println("GuestService.exeDelte()");
		
		return guestDao.guestDelete(guestVo);
	}
	
	//등록
	public int exeWrite(GuestVo guestVo) {
		System.out.println("GuestService.exeWrite()");
		
		return guestDao.guestInsert(guestVo);
	}
	
	/////////////////////////////////////////
	//ajax 등록	저장
	public GuestVo exeAddandGuest(GuestVo guestVo) {
		System.out.println("GuestService.exeAddandGuest()");
		System.out.println(guestVo);
		
		//저장
		guestDao.insertSelectKey(guestVo);
		
		//1명 데이터 가져오기
		GuestVo gVo=guestDao.guestSelectOne(guestVo.getNo());
		
		return gVo;
	}
	
	//삭제
	public int exeRemove(GuestVo guestVo) {
		System.out.println("GuestService.exeRemove()");
		
		int count=guestDao.guestRemove(guestVo);
		
		return count;
	}

}
