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
	
	public void createAndValidateToken() throws InterruptedException {
		
		String email = "sdzaq@naver.com";
		User u = this.userDao.selectOneByEmail(email);
		if (u != null) {
			String token = TokenUtil.createToken(u);
			System.out.println(token);
			
			Thread.sleep(5000);
			
			boolean isValidated = TokenUtil.validateToken(token, u);
			System.out.println(isValidated);
		}
	}
	
	@Test
	public void validateToken() {
		//String token = "sdzaq@naver.com:1526832894620:79f8af9db7404e7b98090e75c50e757b";
		//String token = "sdzaq@naver.com:1526833063251:b809eed0532bd041712d9ea5c4fd4cd6";
		String token = "sdzaq@naver.com:1526833891376:b79c3879c68bb7956705daa71ca60152";
		User u = this.userDao.selectOneByEmail("sdzaq@naver.com");
		
		boolean isValid = TokenUtil.validateToken(token, u);
		System.out.println(isValid);
	}
}
