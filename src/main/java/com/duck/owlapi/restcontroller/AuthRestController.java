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

@RestController
@RequestMapping("auth")
public class AuthRestController {
	
	@Resource(name="authService")
	private AuthService authService;
	
	@Resource(name="userService")
	private UserService userService;
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> postAuth(@RequestBody Map<String, String> logInfo) {
		
		boolean isAuthed = this.authService.authUser(logInfo);
		System.out.println(isAuthed);
		Map<String, Object> resp = new HashMap<>();
		if (isAuthed == true) {
			String email = logInfo.get("email");
			String pw = logInfo.get("pw");
			String jwt = TokenUtil.createToken(email, pw);
			resp.put("msg", "ok");
			resp.put("code", 200);
			resp.put("token", jwt);
			return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.OK);
		} else {
			resp.put("msg", "bad request");
			resp.put("code", 400);
			return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.BAD_REQUEST);
		}
	}
}
