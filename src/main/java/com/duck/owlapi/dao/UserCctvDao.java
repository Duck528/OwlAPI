package com.duck.owlapi.dao;

import com.duck.owlapi.vo.UserCctv;

public class UserCctvDao extends BaseDaoImpl<UserCctv, Integer> {

	@Override
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

}
