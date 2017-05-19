package com.duck.owlapi.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duck.owlapi.dao.UserCctvDao;

@Service("userCctvService")
public class UserCctvService {
	
	@Resource(name="userCctvDao")
	private UserCctvDao ucDao;
	
	
}
