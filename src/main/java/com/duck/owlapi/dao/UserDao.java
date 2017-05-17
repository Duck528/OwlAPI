package com.duck.owlapi.dao;

import com.duck.owlapi.vo.User;

public class UserDao extends BaseDaoImpl<User, Integer> {

	@Override
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
}
