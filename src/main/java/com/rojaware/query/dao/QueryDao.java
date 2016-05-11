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

	public Query findById(int id);

	public List<Query> list();

	public int findTotalQuery();
	void insert(Query query);
	void save(Query query);

	void deleteById(int id);
}
