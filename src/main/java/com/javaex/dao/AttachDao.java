package com.javaex.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AttachDao {
	
	@Autowired
	SqlSession sqlSession;
	
	public String upload() {
		System.out.println("AttachDao.upload()");
		
		//sqlSession.
		
		return "";
	}

}
