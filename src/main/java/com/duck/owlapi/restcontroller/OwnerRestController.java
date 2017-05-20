package com.duck.owlapi.restcontroller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.duck.owlapi.service.UserCctvService;



@RestController
@RequestMapping("owner")
public class OwnerRestController {
	
	@Resource(name="userCctvService")
	private UserCctvService ucService;
	
	@RequestMapping(value = {"/{userId}/cctvs"}, method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> postCctv(Map<String, Object> cctvInfo) {
		System.out.println("enter here");
		int nAdded = this.ucService.addOne(cctvInfo);
		Map<String, Object> m = new HashMap<>();
		
		if (nAdded == 1) {
			m.put("code", 200);
			m.put("msg", "ok");
			return new ResponseEntity<Map<String, Object>>(m, HttpStatus.OK);
		} else {
			m.put("code", 400);
			m.put("msg", "bad request");
			return new ResponseEntity<Map<String, Object>>(m, HttpStatus.BAD_REQUEST);
		}
	}
}
