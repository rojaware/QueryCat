package com.rojaware.query.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.rojaware.query.exception.QueryException;
import com.rojaware.query.model.Query;

public interface QueryDao {
	/**
	 * @param query
	 * @deprecated use execute()
	 * @return
	 */
	public List<Map<String, Object>> run(Query query);
	public List<List<String>> execute(Query query) throws QueryException, SQLException;
	public String runToJson(Query query);

	 Query findById(int id, String db);
	List<Query> list(String db);
	

	public int findTotalQuery(String db);
	void insert(Query query);
	void save(Query query);
    List<List<Object>> getReport(String sql, String db);
	void deleteById(int id, String db);
	
    void changeEnv(String env);
    String getActiveEnv();
}
