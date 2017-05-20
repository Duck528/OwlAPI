package com.duck.owlapi.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.duck.owlapi.service.UserCctvService;
import com.duck.owlapi.service.UserService;
import com.duck.owlapi.vo.User;
import com.duck.owlapi.vo.UserCctv;



@RestController
@RequestMapping("owner")
public class OwnerRestController {
	
	@Resource(name="userCctvService")
	private UserCctvService ucService;
	
	@Resource(name="userService")
	private UserService userService;
	
	/**
	 * @param userEmail
	 * @return
	 * 
	 * 사용자가 등록한 CCTV 목록을 모두 가져온다
	 */
	@RequestMapping(value = {"/{userEmail}/cctvs"}, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getCctv(
			@PathVariable String userEmail) {
		
		Map<String, Object> m = new HashMap<>();
		User u = this.userService.getOneByEmail(userEmail);
		if (u != null) {
			List<UserCctv> ucList = this.ucService.getListByUserEmail(userEmail);
			m.put("code", 200);
			m.put("msg", "ok");
			m.put("cctvList", ucList);
			return new ResponseEntity<Map<String,Object>>(m, HttpStatus.OK);
		} else {
			m.put("code", 400);
			m.put("msg", "bad request");
			return new ResponseEntity<Map<String, Object>>(m, HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * @param userEmail
	 * @param cctvId
	 * @return
	 * 
	 * 사용자가 등록한 CCTV 목록중에서 특정 cctv의 정보를 가져온다
	 */
	@RequestMapping(value = {"/{userEmail}/cctvs/{cctvId}"}, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getCctv(
			@PathVariable String userEmail, @PathVariable Integer cctvId) {
		
		Map<String, Object> m = new HashMap<>();
		User u = this.userService.getOneByEmail(userEmail);
		if (u != null) {
			UserCctv uc = this.ucService.getOne(cctvId);
			m.put("code", 200);
			m.put("msg", "ok");
			m.put("cctv", uc);
			return new ResponseEntity<Map<String,Object>>(m, HttpStatus.OK);
		} else {
			m.put("code", 400);
			m.put("msg", "bad request");
			return new ResponseEntity<Map<String, Object>>(m, HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * @param cctvInfo
	 * @param userEmail
	 * @return
	 * 
	 * 사용자가 새로운 CCTV를 등록한다
	 */
	@RequestMapping(value = {"/{userEmail}/cctvs"}, method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> postCctv(
			@RequestBody Map<String, Object> cctvInfo,
			@PathVariable String userEmail) {
		
		cctvInfo.put("userEmail", userEmail);
		int nAdded = this.ucService.addOne(cctvInfo);
		System.out.println("nAdded: " + nAdded);
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
