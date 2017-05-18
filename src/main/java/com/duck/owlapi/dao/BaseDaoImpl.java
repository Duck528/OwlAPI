package com.duck.owlapi.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDaoImpl<V, P> implements IBaseDao<V, P> {
	
	@Autowired
	protected SqlSession sqlSession;
	
	protected String namespace;
	
	@Override
	public int insert(V vo) {
		System.out.println("base dao: " + sqlSession);
		return this.sqlSession.insert(this.namespace + ".insert", vo);
	}

	@Override
	public V selectOne(P pk) {
		return this.sqlSession.selectOne(this.namespace + ".selectOne", pk);
	}

	@Override
	public List<V> selectList() {
		return this.sqlSession.selectList(this.namespace + ".selectList");
	}

	@Override
	public int updateOne(V vo) {
		return this.sqlSession.update(this.namespace + ".updateOne", vo);
	}

	@Override
	public int deleteOne(P pk) {
		return this.sqlSession.delete(this.namespace + ".deleteOne", pk);
	}

}
