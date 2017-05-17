package com.duck.owlapi.dao;

import java.util.List;

import com.duck.owlapi.vo.User;
import com.duck.owlapi.vo.UserCctv;

public class UserDao extends BaseDaoImpl<User, Integer> {

	@Override
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	/**
	 * @param email: 사용자 이메일 (유니크 키)로 2개 이상의 튜플이 나오면 안 된다.
	 * @return
	 */
	public User selectOneByEmail(String email) {
		return this.sqlSession.selectOne(this.namespace + ".selectOneByEmail", email);
	}
	
	public List<UserCctv> selectUserCctvListByEmail(String email) {
		return this.sqlSession.selectList(this.namespace + ".selectCctvListByEmail", email);
	}
	
	public List<UserCctv> selectUserCctvListByUserId(int userId) {
		return this.sqlSession.selectList(this.namespace + ".selectCctvListByUserId", userId);
	}
}
