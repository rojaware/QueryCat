package com.rojaware.query.service;

import java.util.List;
import java.util.Map;

import com.rojaware.query.exception.QueryException;
import com.rojaware.query.model.CsvReportVO;
import com.rojaware.query.model.Query;
import com.rojaware.query.model.TableView;

public interface QueryService {
	Query findById(int id);
	TableView execute(Query query) throws QueryException;
	
	void saveQuery(Query query);

	void updateQuery(Query query);

	void deleteQueryById(int id);

	List<Query> findAllQuerys();

	boolean isQueryUnique( Integer id, String name);

	String runToJson(Query query);

	List<Map<String, Object>> run(Query query);
	/**
	 * Create report favorable format from result set
	 * @param sql
	 * @return
	 */
	CsvReportVO getReport(String sql);
	void getReport(TableView view);
}
