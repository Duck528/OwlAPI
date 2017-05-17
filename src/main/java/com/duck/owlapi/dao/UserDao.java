package com.duck.owlapi.dao;

import com.duck.owlapi.vo.User;

public class UserDao extends BaseDaoImpl<User, Integer> {

	@Override
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	/**
	 * @param email: ����� �̸��� (����ũ Ű)�� 2�� �̻��� Ʃ���� ������ �� �ȴ�.
	 * @return
	 */
	public User selectOneByEmail(String email) {
		return this.sqlSession.selectOne(this.namespace + ".selectOneByEmail", email);
	}
}
