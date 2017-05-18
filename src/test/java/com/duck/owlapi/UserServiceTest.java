package com.duck.owlapi;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.duck.owlapi.service.UserService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/spring/context-*.xml", "/WEB-INF/spring/root-context.xml"})
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void addUserTest() {
		Map<String, String> m = new HashMap<>();
		m.put("email", "asa9105@naver.com");
		m.put("password", "1234");
		m.put("mobPhone", "010-1234-1234");
		
		int nAdded = userService.addOne(m);
		System.out.println(nAdded);
	}
}
