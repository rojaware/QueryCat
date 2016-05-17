package com.rojaware.query.dao.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rojaware.query.dao.QueryDao;
import com.rojaware.query.exception.QueryException;
import com.rojaware.query.model.Query;
import com.rojaware.query.model.QueryRowMapper;

@Component ("queryDao")
public class JdbcQueryDao extends JdbcDaoSupport implements QueryDao {
	final static Logger LOG = Logger.getLogger(JdbcQueryDao.class);
	
	@Autowired
	private DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}
	
	
	DecimalFormat dFormat = new DecimalFormat("####,###,###.00");
	// insert example
	public void insert(Query query) {
		this.changeEnv(query.getDb());
		String sql = "INSERT INTO QUERY " + "(NAME, SQL, MAP) VALUES (?, ?, ?)";

		getJdbcTemplate().update(sql, new Object[] { query.getName(), query.getSql(), query.getMapJson() });

	}

	// insert batch example
	public void insertBatch(final List<Query> querys) {
		Query q =querys.get(0);
		this.changeEnv(q.getDb());
		String sql = "INSERT INTO QUERY " + "(NAME, SQL, MAP) VALUES (?, ?, ?)";

		getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Query query = querys.get(i);
				ps.setString(1, query.getName());
				ps.setString(2, query.getSql());
				ps.setString(3, query.getMapJson());
			}

			@Override
			public int getBatchSize() {
				return querys.size();
			}
		});
	}

	// insert batch example with SQL
	public void insertBatchSQL(final String sql, String db) {

		getJdbcTemplate().batchUpdate(new String[] { sql });

	}
	public Query findById(int id, String db) {
		   changeEnv(db);
		   String sql = "SELECT * FROM QUERY WHERE ID = ?";

			try {
				Query query = (Query) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new QueryRowMapper());
				query.setDb(db);
				return query;
			} catch (EmptyResultDataAccessException e) {
				return null;
			}
		}
	
	public Query findByQueryId2(int id, String db) {
	   changeEnv(db);
	   String sql = "SELECT * FROM QUERY WHERE ID = ?";

		try {
			Query query = (Query) getJdbcTemplate().queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper(Query.class));
			query.setDb(db);
			return query;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public List<Query> list(String db) {
		changeEnv(db);
		String sql = "SELECT * FROM QUERY";

		List<Query> querys = new ArrayList<Query>();

		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
		for (Map<String, Object> row : rows) {
			Query query = new Query();
			
			Object obj = row.get("ID");
			int id = 0;
			if (obj instanceof BigDecimal) {
				id = ((BigDecimal)obj).intValue();
			} else {
				id = (Integer)obj;
			}
			query.setId((Integer) id);
			query.setName((String) row.get("NAME"));
			query.setSql((String) row.get("SQL"));
			query.setMap((String) row.get("MAP"));
			query.setDb(db);
			querys.add(query);
		}

		return querys;
	}
	

	// query mutiple rows with  (Query.class)
	public List<Query> findAll2(String db) {
		this.changeEnv(db);
		String sql = "SELECT * FROM QUERY";

		List<Query> querys = getJdbcTemplate().query(sql, new QueryRowMapper());
		for (Query q : querys) {
			q.setDb(db);
		}
		
		return querys;
	}

	public String findQueryNameById(int id, String db) {
		this.changeEnv(db);
		String sql = "SELECT NAME FROM QUERY WHERE ID = ?";
		try {
			String name = (String) getJdbcTemplate().queryForObject(sql, new Object[] { id }, String.class);

			return name;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public int findTotalQuery(String db) {
		this.changeEnv(db);
		String sql = "SELECT COUNT(*) FROM QUERY";
		return getJdbcTemplate().queryForInt(sql);
	}

	public void deleteById(String sql, int id, String db) {
		this.changeEnv(db);
		getJdbcTemplate().update(sql, id);
	}

	@Override
	public List<Map<String, Object>> run(Query query) {
		changeEnv(query.getDb());
		Map<String, Object> paramMap = query.getMap();
		if (paramMap == null) {
			return getJdbcTemplate().queryForList(query.getSql());
		}
		return getJdbcTemplate().queryForList(query.getSql(), paramMap);

	}
	@Override
	public List<List<String>> execute(Query query) throws QueryException, SQLException {
		changeEnv(query.getDb());
		if (query == null) {
			throw new QueryException("Query is empty");
		}
		
		if (query.getSql() == null) {
			Query dbQuery = this.findById(query.getId(), query.getDb());
		    query.setSql(dbQuery.getSql());
		}
		
		// add parameterized value into SQL if map exists
		Map<String, Object> params = query.getMap();
		if (params != null && params.size() >0) {
			// SELECT * FROM ACCOUNT WHERE BALANCE > {BALANCE} AND PRODUCT LIKE '%{PRODUCT}%'
			String sql = query.getSql();
			for (Map.Entry<String, Object> entry : params.entrySet()) {
		        String key = entry.getKey();
		        String argument = "{"+key+"}";
		        Object value = entry.getValue();
		        
		        if (sql.contains(argument)) {
					sql = sql.replace(argument, (String)value);
					LOG.info("Replaced SQL :: " + sql);
				} else {
					throw new QueryException("No matching text found by " + argument);
				}
		    }
			query.setSql(sql);
		}
		return getJdbcTemplate().query(query.getSql(), new ResultSetExtractor<List<List<String>>>() {
			@Override
			public List<List<String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				List<List<String>> list = new ArrayList<List<String>>();
				// add header
				List<String> header = new ArrayList<String>();
				ResultSetMetaData rsmd = rs.getMetaData();
				int count = rsmd.getColumnCount();
				List<Integer> moneyColumns = new ArrayList<Integer>();
				
				for (int i = 1; i <count+1 ; i++) {
					header.add(rsmd.getColumnName(i));
					if (rsmd.getColumnTypeName(i).equalsIgnoreCase("money")) {
						moneyColumns.add(i);
					}
				}
				
				list.add(header);
				// add rows
				while (rs.next()) {
					List<String> row = new ArrayList<String>();
					for (int i = 1; i <count+1 ; i++) {
						
						String s = rs.getString(i);
						if (StringUtils.isBlank(s)) {
							s = "";
						}
						if (NumberUtils.isParsable(s) && moneyColumns.contains(i)) {
							Double d = Double.parseDouble(s);
							row.add("$" + dFormat.format(d));
						} else {
						   row.add(s.trim());
						}
					}
					list.add(row);
				}
				return list;
			}
		});

	}
	public static boolean isDouble(String s)
    {
		if (NumberUtils.isParsable(s)) {
			double d = Double.parseDouble(s);
			return d != (int) d;
		}
		return false;
    }

	@Override
	public String runToJson(Query query) {

		List<Map<String, Object>> list = this.run(query);
		Gson gson = new Gson();
		return gson.toJson(list);
	}

	@Override
	public void save(Query query) {
		changeEnv(query.getDb());
		Integer id = query.getId();
		if (id == 0) {
			insert(query);
		} else {
			update(query);
		}
	}
	public void update(Query query) {
		String updateSql = "UPDATE QUERY SET name=?,sql=?,map=? WHERE id = ?";

		getJdbcTemplate().update(updateSql,
				new Object[] { query.getName(), query.getSql(), query.getMapJson(), query.getId() });
	}
	@Override
	public void deleteById(int id, String db) {
		changeEnv(db);
		LOG.debug("DELETING :: "+ id);
		deleteById("DELETE FROM QUERY WHERE id=?", id, db);
	}
	@Override
	public List<List<Object>> getReport(String sql, String db) {
		changeEnv(db);
		return getJdbcTemplate().query(sql, new ResultSetExtractor<List<List<Object>>>(){
			public List<List<Object>> extractData(ResultSet rs) throws SQLException, DataAccessException {

				List<List<Object>> data = new ArrayList<List<Object>>();
				List<Object> labels = new ArrayList<Object>();
				List<Object> types = new ArrayList<Object>();


				ResultSetMetaData md = rs.getMetaData();

				for(int c=1; c<= md.getColumnCount();c++){
					labels.add(md.getColumnName(c));
					types.add(md.getColumnTypeName(c));
				}
				data.add(labels);
				data.add(types);

				while(rs.next()){
					List<Object> values = new ArrayList<Object>();

					for(Object label: labels){
						int colpos = labels.lastIndexOf(label);
						values.add(rs.getObject(colpos+1));
					}
					data.add(values);
				}

				return data;
			}
		});
	}

	@Override
	public void changeEnv(String env) {
		MyRoutingDataSource myDataSource = (MyRoutingDataSource)dataSource;
		myDataSource.setEnv(env);
		setDataSource(dataSource);
	}

	@Override
	public String getActiveEnv() {
		MyRoutingDataSource myDataSource = (MyRoutingDataSource)dataSource;
		return myDataSource.getEnv();
	}
}
