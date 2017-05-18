package com.duck.owlapi.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duck.owlapi.dao.UserDao;
import com.duck.owlapi.vo.User;
import com.duck.owlapi.vo.UserCctv;

@Service("userService")
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public int addOne(Map<String, String> uMap) {
		User u = new User();
		String email = uMap.get("email");
		if (email == null)
			return -1;
		String pw = uMap.get("password");
		if (pw == null)
			return -1;
		String mobPhone = uMap.get("mobPhone");
		if (mobPhone == null)
			return -1;
		
		u.setEmail(email);
		/*
		 * PasswordHash / Salt 부분은 나중에 수정하도록 하자
		 */
		u.setPasswordHash(pw);
		u.setPasswordSalt(pw);
		u.setMobPhone(mobPhone);
		String now = LocalDateTime.now().toString();
		u.setRegisterDate(now);
		u.setLastAccess(now);
		/*
		 * StatusFlag 
		 * 	1 - Active
		 *  0 - Deleted
		 */
		u.setStatusFlag(1);
		return this.userDao.insert(u);
	}
	
	public int updateOne(User user) {
		return this.userDao.updateOne(user);
	}	
	
	public int deleteOne(int pk) {
		return this.userDao.deleteOne(pk);
	}
	
	public List<User> getAll() {
		return this.userDao.selectList();
	}
	
	public User getOne(int pk) {
		return this.userDao.selectOne(pk);
	}
	
	public User getOneByEmail(String email) {
		return this.userDao.selectOneByEmail(email);
	}
	
	public List<UserCctv> getCctvByEmail(String email) {
		return this.userDao.selectUserCctvListByEmail(email);
	}
	
	public List<UserCctv> getCctvByUserId(int pk) {
		return this.userDao.selectUserCctvListByUserId(pk);
	}
}
