package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.TboardDao;
import com.javaex.vo.TboardVo;

@Service
public class TboardService {

	@Autowired
	private TboardDao tboardDao;
	
	//리스트3(검색O,페이징 O)
	public Map<String, Object> exeList3(int crtPage, String keyword){
		System.out.println("TboardService.exeList3()");
		System.out.println(keyword);
		
		//11번부터 10개
		//tboardDao.boardSelectList2(11, 10);
		
		/*****************
		 * 리스트 가져오기
		 ****************/
		//한 페이지당 출력 글 개수
		int listCnt=10;
		
		/*
		if(crtPage>0) {	//참
			//crtPage=crtPage;
		} else {
			crtPage=1;	//startRowNo 계산 시 음수가 되지 않도록
		}
		*/
		//crtPage
		crtPage=(crtPage>0)? crtPage:(crtPage=1);
		
		/*
		시작 row 번호
		1: 1~10, 2: 11~20, 3: 21~30
		
		코드 쓸 때는 하나 적게 써야 함
		1: 0 10, 2: 10 10, 3: 20 10 (a부터 b개)
		
		(1-1)10=0
		(2-1)10=10
		(3-1)10=20
		(crtPage-1)listCnt
		*/
		//startRowNo 구하기
		int startRowNo=(crtPage-1)*listCnt;
		
		//startRowNo, listCnt Map으로 묶는다
		Map<String, Object> limitMap=new HashMap<String, Object>();
		limitMap.put("startRowNo", startRowNo);
		limitMap.put("listCnt", listCnt);
		limitMap.put("keyword", keyword);
		
		//dao에 전달해서 현재 페이지의 리스트 10개를 받는다
		List<TboardVo> boardList = tboardDao.boardSelectList3(limitMap);
		
		/**********************
		 * 페이징 계산
		 *********************/
		//페이지당 버튼 개수
		int pageBtnCount=5;
			
		//전체 글 개수
		int totalCnt=tboardDao.selectTotalCnt3(keyword);
		
		//1: 1~5, 2: 1~5 ... 6: 6~10 ... 10: 6~10 ... 21: 21~25
		//1 5 => 올림(1/5)*5 -> 0.2(1)*5 => 5
		//2 5 => 올림(2/5)*5 -> 0.4(1)*5 => 5
		//6 5 => 올림(6/5)*5 -> 1.2(2)*5 => 10
		//21 5 => 올림(21/5)*5 -> 4.2(5)*5 => 25
		//마지막 버튼 번호
		int endPageBtnNo=(int)Math.ceil(crtPage/(double)pageBtnCount)*pageBtnCount;
		
		//시작 버튼 번호
		int startPageBtnNo=(endPageBtnNo-pageBtnCount)+1;
		
		//다음 화살표 유무
		boolean next=false;
		
		if(listCnt*endPageBtnNo<totalCnt) {
			//한 페이지당 글 개수(10)*마지막 버튼 번호(5)<전체 글 개수 102개
			next=true;
		} else {	//다음 화살표가 false일 때 마지막 버튼 번호 정확히 계산
			//187개 -> 19페이지 => 187/10=18.7 올림 -> 19
			endPageBtnNo=(int)Math.ceil(totalCnt/(double)listCnt);
			
		}
		
		//이전 화살표 유무
		boolean prev=false;
		
		if(startPageBtnNo!=1) {
			prev=true;
		}
		
		//5개 map으로 묶어서 controller한테 보낸다/리턴
		Map<String, Object> pMap=new HashMap<String, Object>();
		pMap.put("boardList", boardList);
		pMap.put("startPageBtnNo", startPageBtnNo);
		pMap.put("endPageBtnNo", endPageBtnNo);
		pMap.put("prev", prev);
		pMap.put("next", next);
		
		System.out.println(pMap);
		
		return pMap;
		
	}
	
	//리스트2(검색X,페이징 O)
	public Map<String, Object> exeList2(int crtPage){
		System.out.println("TboardService.exeList2()");
		System.out.println(crtPage);
		
		//11번부터 10개
		//tboardDao.boardSelectList2(11, 10);
		
		/*****************
		 * 리스트 가져오기
		 ****************/
		//한 페이지당 출력 글 개수
		int listCnt=10;
		
		/*
		if(crtPage>0) {	//참
			//crtPage=crtPage;
		} else {
			crtPage=1;	//startRowNo 계산 시 음수가 되지 않도록
		}
		*/
		//crtPage
		crtPage=(crtPage>0)? crtPage:(crtPage=1);
		
		/*
		시작 row 번호
		1: 1~10, 2: 11~20, 3: 21~30
		
		코드 쓸 때는 하나 적게 써야 함
		1: 0 10, 2: 10 10, 3: 20 10 (a부터 b개)
		
		(1-1)10=0
		(2-1)10=10
		(3-1)10=20
		(crtPage-1)listCnt
		*/
		//startRowNo 구하기
		int startRowNo=(crtPage-1)*listCnt;
		
		//startRowNo, listCnt Map으로 묶는다
		Map<String, Integer> limitMap=new HashMap<String, Integer>();
		limitMap.put("startRowNo", startRowNo);
		limitMap.put("listCnt", listCnt);
		
		//dao에 전달해서 현재 페이지의 리스트 10개를 받는다
		List<TboardVo> boardList = tboardDao.boardSelectList2(limitMap);
		
		/**********************
		 * 페이징 계산
		 *********************/
		//페이지당 버튼 개수
		int pageBtnCount=5;
			
		//전체 글 개수
		int totalCnt=tboardDao.selectTotalCnt();
		
		//1: 1~5, 2: 1~5 ... 6: 6~10 ... 10: 6~10 ... 21: 21~25
		//1 5 => 올림(1/5)*5 -> 0.2(1)*5 => 5
		//2 5 => 올림(2/5)*5 -> 0.4(1)*5 => 5
		//6 5 => 올림(6/5)*5 -> 1.2(2)*5 => 10
		//21 5 => 올림(21/5)*5 -> 4.2(5)*5 => 25
		//마지막 버튼 번호
		int endPageBtnNo=(int)Math.ceil(crtPage/(double)pageBtnCount)*pageBtnCount;
		
		//시작 버튼 번호
		int startPageBtnNo=(endPageBtnNo-pageBtnCount)+1;
		
		//다음 화살표 유무
		boolean next=false;
		
		if(listCnt*endPageBtnNo<totalCnt) {
			//한 페이지당 글 개수(10)*마지막 버튼 번호(5)<전체 글 개수 102개
			next=true;
		} else {	//다음 화살표가 false일 때 마지막 버튼 번호 정확히 계산
			//187개 -> 19페이지 => 187/10=18.7 올림 -> 19
			endPageBtnNo=(int)Math.ceil(totalCnt/(double)listCnt);
			
		}
		
		//이전 화살표 유무
		boolean prev=false;
		
		if(startPageBtnNo!=1) {
			prev=true;
		}
		
		//5개 map으로 묶어서 controller한테 보낸다/리턴
		Map<String, Object> pMap=new HashMap<String, Object>();
		pMap.put("boardList", boardList);
		pMap.put("startPageBtnNo", startPageBtnNo);
		pMap.put("endPageBtnNo", endPageBtnNo);
		pMap.put("prev", prev);
		pMap.put("next", next);
		
		System.out.println(pMap);
		
		return pMap;
		
	}
	
	//리스트(검색X,페이징 X)
	public List<TboardVo> exeList(){
		System.out.println("TboardService.exeList()");
		
		List<TboardVo> boardList = tboardDao.boardSelectList();
		
		return boardList;
		
	}
	
	
}
