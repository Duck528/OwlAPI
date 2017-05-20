package com.duck.owlapi;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.duck.owlapi.service.UserCctvService;
import com.duck.owlapi.service.UserService;
import com.duck.owlapi.vo.User;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/spring/context-*.xml", "classpath:config/test/test-context.xml"})
public class UserCctvServiceTest {

	@Autowired
	private UserCctvService ucService;
	
	@Autowired
	private UserService userService;
	
	public void addTest() {
		User u = this.userService.getOneByEmail("asa9105@naver.com");
		
		Map<String, Object> m = new HashMap<>();
		m.put("userEmail", u.getEmail());
		m.put("location", "Test Location");
		m.put("description", "Test Description");
		
		int nAdded = this.ucService.addOne(m);
		System.out.println(nAdded);
	}
}
