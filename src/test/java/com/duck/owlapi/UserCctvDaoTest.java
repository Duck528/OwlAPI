package com.duck.owlapi;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.duck.owlapi.dao.UserCctvDao;
import com.duck.owlapi.dao.UserDao;
import com.duck.owlapi.vo.User;
import com.duck.owlapi.vo.UserCctv;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/spring/context-*.xml", "classpath:config/test/test-context.xml"})
public class UserCctvDaoTest {
	
	@Autowired
	private UserCctvDao ucDao;
	
	@Autowired
	private UserDao userDao;
	
	public void insert() {
		User u = this.userDao.selectOneByEmail("sdzaq@naver.com");
		if (u != null) {
			UserCctv uc = new UserCctv();
			uc.setAuthCode(7643);
			uc.setDescription("Test");
			uc.setId(9512);
			uc.setLocation("test location");
			uc.setRegisterDate("2016-10-23");
			uc.setUserId(u.getId());
			
			ucDao.insert(uc);
		}
	}
	
	public void selectOne() {
		UserCctv uc = ucDao.selectOne(9512);
		System.out.println(uc.getId());
		System.out.println(uc.getLocation());
	}
	
	public void selectListByUserId() {
		User u = this.userDao.selectOneByEmail("sdzaq@naver.com");
		if (u != null) {
			List<UserCctv> ucList = this.ucDao.selectListByUserId(u.getId());
			for (UserCctv uc : ucList) {
				System.out.println(uc.getLocation());
			}
		}
	}
	
	public void delete() {
		User u = this.userDao.selectOneByEmail("sdzaq@naver.com");
		List<UserCctv> ucList = this.ucDao.selectListByUserId(u.getId());
		for (UserCctv uc : ucList) {
			this.ucDao.deleteOne(uc.getId());
		}
	}
}
