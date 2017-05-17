package com.duck.owlapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.duck.owlapi.dao.UserCctvDao;
import com.duck.owlapi.vo.UserCctv;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/spring/context-*.xml"})
public class UserCctvDaoTest {
	
	@Autowired
	private UserCctvDao ucDao;
	
	public void insert() {
		UserCctv uc = new UserCctv();
		uc.setAuthCode(7643);
		uc.setDescription("Test");
		uc.setId(9512);
		uc.setLocation("test location");
		uc.setRegisterDate("2016-10-23");
		uc.setUserId(321);
		
		ucDao.insert(uc);
	}
	
	public void selectOne() {
		UserCctv uc = ucDao.selectOne(9512);
		System.out.println(uc.getId());
		System.out.println(uc.getLocation());
	}
}
