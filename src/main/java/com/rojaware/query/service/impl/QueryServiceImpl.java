package com.rojaware.query.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rojaware.query.dao.QueryDao;
import com.rojaware.query.exception.QueryException;
import com.rojaware.query.model.Query;
import com.rojaware.query.model.TableView;
import com.rojaware.query.service.QueryService;

@Service ("queryService")
public class QueryServiceImpl implements QueryService {
	final static Logger LOG = Logger.getLogger(QueryServiceImpl.class);
	
	@Autowired
	QueryDao dao;

	@Override
	public Query findById(int id) {
		return dao.findById(id);
	}

	@Override
	public void saveQuery(Query query) {
		dao.save(query);
	}

	/*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends. 
     */
	@Override
	public void updateQuery(Query query) {
		Query entity = dao.findById(query.getId());
        if(entity!=null){
            dao.save(query);
        } else {
        	dao.insert(query);
        }
	}

	@Override
	public void deleteQueryById(int id) {
		dao.deleteById(id);
	}

	@Override
	public List<Query> findAllQuerys() {
		return dao.list();
	}

	@Override
	public boolean isQueryUnique( Integer id, String name) {
		 Query query = this.findById(id);
	     return ( query == null || ((id != null) && (query.getSql().equalsIgnoreCase(name))));
	}

	@Override
	public String runToJson(Query query) {
		return dao.runToJson(query);
	}
	
	@Override
	public List<Map<String, Object>> run(Query query) {
		return dao.run(query);
	}

	@Override
	public TableView execute(Query query) throws QueryException {
		List<List<String>> list;
		try {
			list = dao.execute(query);
		} catch (SQLException e) {
			String msg = e.getMessage() + " :: " + e.getErrorCode();
			throw new QueryException(e.getSQLState() + " :: " + msg, e);
		}
		TableView view = new TableView(list);
		LOG.info("result :: " +view.toString());
		return view;
	}

	@Override
	public void getReport(TableView view) {
		// TODO Auto-generated method stub
		
	}

}
