package com.duck.owlapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.duck.owlapi.dao.UserDao;
import com.duck.owlapi.vo.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/spring/context-*.xml"})
public class UserDaoTest {
	
	@Autowired
	private UserDao userDao;
	
	@Test
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
}
