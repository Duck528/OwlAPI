package com.duck.owlapi.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDao {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public Object insert(String queryId, Object param) {
		return sqlSession.insert(queryId, param);
	}
	
	public Object update(String queryId, Object param) {
		return sqlSession.update(queryId, param);
	}
	
	public Object delete(String queryId, Object param) {
		return sqlSession.delete(queryId, param);
	}
	
	public Object selectOne(String queryId) {
		return sqlSession.selectOne(queryId);
	}
	
	@SuppressWarnings("rawtypes")
	public List selectList(String queryId) {
		return sqlSession.selectList(queryId);
	}
	
	@SuppressWarnings("rawtypes")
	public List selectList(String queryId, Object param) {
		return sqlSession.selectList(queryId, param);
	}
}
