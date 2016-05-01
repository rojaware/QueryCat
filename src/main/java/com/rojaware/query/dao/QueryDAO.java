package com.rojaware.query.dao;

import java.util.List;

import com.rojaware.query.model.Query;

public interface QueryDAO 
{
	public void insert(Query query);
	
	public void insertNamedParameter(Query query);
			
	public void insertBatch(List<Query> query);
	
	public void insertBatchNamedParameter(List<Query> query);
	
	public void insertBatchNamedParameter2(List<Query> query);
			
	public void insertBatchSQL(String sql);
	
	public Query findQueryById(int custId);
	
	public Query findByQueryId2(int custId);

	public List<Query> list();
	
	public List<Query> findAll2();
	
	public String findQueryNameById(int custId);
	
	public int findTotalQuery();
	
}




