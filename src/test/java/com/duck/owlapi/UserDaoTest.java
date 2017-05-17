package com.duck.owlapi;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.duck.owlapi.dao.UserDao;
import com.duck.owlapi.vo.User;
import com.duck.owlapi.vo.UserCctv;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/spring/context-*.xml"})
public class UserDaoTest {
	
	@Autowired
	private UserDao userDao;
	
	public void insertUser() {
		User u = new User();
		u.setEmail("sdzaq@naver.com");
		u.setId(321);
		u.setLastAccess("2017-05-23");
		u.setMobPhone("010-2312-3212");
		u.setPasswordHash("312312312312");
		u.setPasswordSalt("3213213132");
		u.setRegisterDate("2017-05-11");
		u.setStatusFlag(1);
		
		userDao.insert(u);
	}
	
	public void selectByPk() {
		User u = userDao.selectOne(123);
	}
	
	public void selectByEmail() {
		User u = userDao.selectOneByEmail("sdzaq@naver.com");
		System.out.println(u.getEmail());
	}
	
	public void selectList() {
		List<User> userList = userDao.selectList();
		for (User u : userList) {
			System.out.println(u.getEmail());
		}
	}
	
	public void selectUserCctvList() {
		List<UserCctv> cctvList = userDao.selectUserCctvListByEmail("sdzaq@naver.com");
		for (UserCctv c : cctvList) {
			System.out.println(c.getAuthCode());
		}
		
		List<UserCctv> cctvList2 = userDao.selectUserCctvListByUserId(9512);
		for (UserCctv c : cctvList2) {
			System.out.println(c.getAuthCode());
		}
	}
}
