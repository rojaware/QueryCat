package com.rojaware.query.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.rojaware.query.dao.QueryDAO;
import com.rojaware.query.model.Query;
import com.rojaware.query.model.QueryRowMapper;

@Repository
public class JdbcQueryDAO extends JdbcDaoSupport implements QueryDAO {
	// insert example
	public void insert(Query query) {

		String sql = "INSERT INTO QUERY " + "(NAME, SQL, MAP) VALUES (?, ?, ?)";

		getJdbcTemplate().update(sql, new Object[] { query.getName(), query.getSql(), query.getMapJson() });

	}

	// insert with named parameter
	public void insertNamedParameter(Query query) {

		// not supported

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

	// insert batch with named parameter
	public void insertBatchNamedParameter(final List<Query> querys) {

		// not supported
	}

	// insert batch with named parameter
	public void insertBatchNamedParameter2(final List<Query> querys) {

		// not supported
	}

	// insert batch example with SQL
	public void insertBatchSQL(final String sql) {

		getJdbcTemplate().batchUpdate(new String[] { sql });

	}

	// query single row with RowMapper
	public Query findQueryById(int id) {

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

	// query mutiple rows with BeanPropertyRowMapper (Query.class)
	public List<Query> findAll2() {

		String sql = "SELECT * FROM QUERY";

		List<Query> querys = getJdbcTemplate().query(sql, new QueryRowMapper());
//		List<Query> querys = getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Query.class));

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

		int total = getJdbcTemplate().queryForInt(sql);

		return total;
	}

}
