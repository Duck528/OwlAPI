 package com.duck.owlapi.restcontroller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.duck.owlapi.service.UserService;
import com.duck.owlapi.vo.User;

@RestController
@RequestMapping("users")
public class UsersRestController {
	
	@Resource(name="userService")
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> postUser(@RequestBody Map<String, String> uMap) {
		System.out.println(uMap.get("email"));
		System.out.println(uMap.get("password"));
		System.out.println(uMap.get("mobPhone"));
		int nAdded = this.userService.addOne(uMap);
		
		Map<String, Object> m = new HashMap<>();
		if (nAdded == 1) {
			m.put("code", 200);
			m.put("msg", "ok");
			return new ResponseEntity<Map<String,Object>>(m, HttpStatus.OK);
		} else {
			m.put("code", 400);
			m.put("msg", "bad request");
			return new ResponseEntity<Map<String,Object>>(m, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public User getUser() {
		User u = new User();
		u.setEmail("sdzaq@naver.com");
		return u;
	}
}
