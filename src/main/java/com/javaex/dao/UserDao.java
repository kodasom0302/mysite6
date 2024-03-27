package com.javaex.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//수정하기 - 회원정보수정
	public void userUpdate(UserVo userVo) {
		System.out.println("UserDao.userUpdate()");
		
		sqlSession.update("user.update", userVo);
		System.out.println(userVo);
	}
	
	//등록하기 - 회원가입
	public void userInsert(UserVo userVo) {
		System.out.println("UserDao.userInsert()");
		
		sqlSession.insert("user.insert", userVo);
	}
	
	//1명 데이터 가져오기 - 로그인
	public UserVo userSelectByIdPw(UserVo userVo) {
		System.out.println("UserDao.userSelectByIdPw()");
		System.out.println(userVo);
		UserVo authUser=sqlSession.selectOne("user.selectByIdPw", userVo);
		System.out.println(authUser);
		return authUser;
	}

}
