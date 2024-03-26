package com.javaex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	//회원정보수정
	public void exeModify(UserVo userVo) {
		System.out.println("UserService.exeModify()");
		
		userDao.userUpdate(userVo);
	}
	
	//회원가입
	public void exeJoin(UserVo userVo) {
		System.out.println("UserService.exejoin()");
		
		userDao.userInsert(userVo);
	}
	
	//로그인
	public UserVo exeLogin(UserVo userVo) {
		System.out.println("UserService.exeLogin()");
		
		UserVo authUser=userDao.userSelectByIdPw(userVo);
		System.out.println(userVo);
		
		return authUser;
	}

}
