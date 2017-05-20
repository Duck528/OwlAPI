package com.duck.owlapi.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.duck.owlapi.vo.UserCctv;

@Repository("userCctvDao")
public class UserCctvDao extends BaseDaoImpl<UserCctv, Integer> {
	
	public UserCctvDao() {
		super.namespace = "com.duck.owlcctv.mapper.UserCctvMapper";
	}
	
	public List<UserCctv> selectListByUserId(int userId) {
		return this.sqlSession.selectList(this.namespace + ".selectListByUserId", userId);
	}
}
