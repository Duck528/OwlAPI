package com.duck.owlapi.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.duck.owlapi.vo.User;
import com.duck.owlapi.vo.UserCctv;


@Repository("userDao")
public class UserDao extends BaseDaoImpl<User, Integer> {
	
	public UserDao() {
		super.namespace = "com.duck.owlcctv.mapper.UserMapper";
	}
	
	/**
	 * @param email: ����� �̸��� (����ũ Ű)�� 2�� �̻��� Ʃ���� ������ �� �ȴ�.
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
