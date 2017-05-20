package com.duck.owlapi;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.duck.owlapi.dao.UserDao;
import com.duck.owlapi.util.TokenUtil;
import com.duck.owlapi.vo.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/spring/context-*.xml", "classpath:config/test/test-context.xml"})
public class TokenUtilTest {
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void createAndValidateToken() throws InterruptedException {
		
		String email = "asa9105@naver.com";
		User u = this.userDao.selectOneByEmail(email);
		if (u != null) {
			String token = TokenUtil.createToken(u);
			System.out.println(token);
			
			Thread.sleep(5000);
			
			boolean isValidated = TokenUtil.validateToken(token, u);
			System.out.println(isValidated);
		}
	}
}
