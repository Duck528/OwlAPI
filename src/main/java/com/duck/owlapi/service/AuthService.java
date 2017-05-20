package com.duck.owlapi.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duck.owlapi.dao.UserDao;
import com.duck.owlapi.vo.User;



@Service("authService")
public class AuthService {
	
	@Resource(name="userDao")
	private UserDao userDao;
	
	public boolean authUser(Map<String, String> logInfo) {
		String email = logInfo.get("email");
		String pw = logInfo.get("password");
		
		if (email != null && pw != null) {
			User u = this.userDao.selectOneByEmail(email);
			if (u != null && pw.equals(u.getPasswordHash())) {
				return true;	
			}
		}
		return false;
	}
}
