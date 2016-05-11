package com.rojaware.query.dao.impl;

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

		String sql = "INSERT INTO QUERY " + "(NAME, SQL, MAP) VALUES (?, ?, ?)";

		getJdbcTemplate().update(sql, new Object[] { query.getName(), query.getSql(), query.getMapJson() });

	}

	// insert batch example
	public void insertBatch(final List<Query> querys) {

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
	public void insertBatchSQL(final String sql) {

		getJdbcTemplate().batchUpdate(new String[] { sql });

	}

	// query single row with RowMapper
	public Query findById(int id) {

		String sql = "SELECT * FROM QUERY WHERE ID = ?";

		try {
			Query query = (Query) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new QueryRowMapper());

			return query;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	// query single row with BeanPropertyRowMapper (Query.class)
	public Query findByQueryId2(int id) {

		String sql = "SELECT * FROM QUERY WHERE ID = ?";

		try {
			Query query = (Query) getJdbcTemplate().queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper(Query.class));
			return query;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	// query mutiple rows with manual mapping
	public List<Query> list() {

		String sql = "SELECT * FROM QUERY";

		List<Query> querys = new ArrayList<Query>();

		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
		for (Map<String, Object> row : rows) {
			Query query = new Query();
			query.setId((Integer) row.get("ID"));
			query.setName((String) row.get("NAME"));
			query.setSql((String) row.get("SQL"));
			query.setMap((String) row.get("MAP"));
			querys.add(query);
		}

		return querys;
	}

	// query mutiple rows with  (Query.class)
	public List<Query> findAll2() {

		String sql = "SELECT * FROM QUERY";

		List<Query> querys = getJdbcTemplate().query(sql, new QueryRowMapper());
		
		return querys;
	}

	public String findQueryNameById(int id) {

		String sql = "SELECT NAME FROM QUERY WHERE ID = ?";
		try {
			String name = (String) getJdbcTemplate().queryForObject(sql, new Object[] { id }, String.class);

			return name;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public int findTotalQuery() {
		String sql = "SELECT COUNT(*) FROM QUERY";
		return getJdbcTemplate().queryForInt(sql);
	}

	public void deleteById(String sql, int id) {
		getJdbcTemplate().update(sql, id);
	}

	@Override
	public List<Map<String, Object>> run(Query query) {

		Map<String, Object> paramMap = query.getMap();
		if (paramMap == null) {
			return getJdbcTemplate().queryForList(query.getSql());
		}
		return getJdbcTemplate().queryForList(query.getSql(), paramMap);

	}
	@Override
	public List<List<String>> execute(Query query) throws QueryException, SQLException {

		if (query == null) {
			throw new QueryException("Query is empty");
		}
		String sql = null;
		if (query.getSql() == null) {
			Query dbQuery = this.findById(query.getId());
		    sql = dbQuery.getSql();
			// add params
			Map<String, Object> params = query.getMap();
			if (params != null) {
				
				// SELECT * FROM ACCOUNT WHERE BALANCE > {BALANCE} AND PRODUCT LIKE '%{PRODUCT}%'
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
	public void deleteById(int id) {
		LOG.debug("DELETING :: "+ id);
		deleteById("DELETE FROM QUERY WHERE id=?", id);
	}

}
