package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestVo;

@Repository
public class GuestDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//리스트
	public List<GuestVo> guestSelect() {
		System.out.println("GuestDao.guestSelect()");
		
		List<GuestVo> gList=sqlSession.selectList("guest.select");
		
		return gList;
	}
	
	//삭제
	public int guestDelete(GuestVo guestVo) {
		System.out.println("GuestDao.guestDelete()");
		
		return sqlSession.delete("guest.delete", guestVo);
	}
	
	//등록
	public int guestInsert(GuestVo guestVo) {
		System.out.println("GuestDao.guestInsert()");
		
		return sqlSession.insert("guest.insert", guestVo);
	}
	
	//////////////////////////////////
	//ajax 등록
	public int insertSelectKey(GuestVo guestVo) {
		System.out.println("GuestDao.insertSelectKey()");
		
		//System.out.println(guestVo);	//no 비어있음
		int count=sqlSession.insert("guest.insertSelectKey", guestVo);
		//System.out.println(guestVo);	//no 있음
		
		return count;
	}
	
	//데이터 1개 가져오기
	public GuestVo guestSelectOne(int no) {
		System.out.println("GuestDao.guestSelectOne()");
		
		GuestVo guestVo=sqlSession.selectOne("guest.selectOne", no);
		
		return guestVo;
	}
	
	//삭제
	public int guestRemove(GuestVo guestVo) {
		System.out.println("GuestDao.guestRemove()");
		
		int count=sqlSession.delete("guest.delete", guestVo);
		
		return count;
	}

}
