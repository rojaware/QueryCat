package com.rojaware.query.service;

import java.util.List;
import java.util.Map;

import com.rojaware.query.exception.QueryException;
import com.rojaware.query.model.Query;
import com.rojaware.query.model.TableView;

public interface QueryService {
	Query findById(int id, String db);
	TableView execute(Query query) throws QueryException;
	
	void saveQuery(Query query);

	void updateQuery(Query query);

	void deleteQueryById(int id, String db);
	 List<Query> list(String db);
	List<Query> findAllQuerys( String db);

	boolean isQueryUnique( Query query);

	String runToJson(Query query);

	List<Map<String, Object>> run(Query query);

	void changeDataSource(String db);
	String getActiveDataSource();
}
