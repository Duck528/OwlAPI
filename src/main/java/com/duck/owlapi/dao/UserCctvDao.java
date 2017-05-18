package com.duck.owlapi.dao;

import org.springframework.stereotype.Repository;

import com.duck.owlapi.vo.UserCctv;

@Repository
public class UserCctvDao extends BaseDaoImpl<UserCctv, Integer> {
	
	public UserCctvDao() {
		super.namespace = "com.duck.owlcctv.mapper.UserCctvMapper";
	}
}
