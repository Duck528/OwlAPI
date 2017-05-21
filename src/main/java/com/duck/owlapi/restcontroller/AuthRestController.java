package com.duck.owlapi.restcontroller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.duck.owlapi.service.AuthService;
import com.duck.owlapi.service.UserService;
import com.duck.owlapi.util.TokenUtil;
import com.duck.owlapi.vo.User;

@RestController
@RequestMapping("auth")
public class AuthRestController {
	
	@Resource(name="authService")
	private AuthService authService;
	
	@Resource(name="userService")
	private UserService userService;
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> postAuth(@RequestBody Map<String, String> logInfo) {
		User u = this.authService.authUser(logInfo);
		Map<String, Object> m = new HashMap<>();
		if (u != null) {
			String jwt = TokenUtil.createToken(u);
			m.put("msg", "ok");
			m.put("code", 200);
			m.put("token", jwt);
			return new ResponseEntity<Map<String,Object>>(m, HttpStatus.OK);
		} else {
			m.put("msg", "bad request");
			m.put("code", 400);
			return new ResponseEntity<Map<String,Object>>(m, HttpStatus.BAD_REQUEST);
		}
	}
}
