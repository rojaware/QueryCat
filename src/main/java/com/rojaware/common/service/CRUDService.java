package com.rojaware.common.service;

import java.util.List;

import com.rojaware.query.model.Query;

public interface CRUDService {
	public void addQuery(Query query);
    public void updateQuery(Query query);
    public Query getQuery(int id);
    public void deleteQuery(int id);
    public List<Query> getQuerys();
}
