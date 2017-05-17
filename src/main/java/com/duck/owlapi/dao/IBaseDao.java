package com.duck.owlapi.dao;

import java.util.List;

public interface IBaseDao <V, P> {
	
	public int insert(V vo);
	public V selectOne(P pk);
	public List<V> selectList();
	public int updateOne(V vo);
	public int deleteOne(P pk);
}
