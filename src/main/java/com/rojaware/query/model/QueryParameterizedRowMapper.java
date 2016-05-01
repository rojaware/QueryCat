package com.rojaware.query.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class QueryParameterizedRowMapper implements ParameterizedRowMapper<Query>
{
	private static final Logger LOG = Logger.getLogger(QueryParameterizedRowMapper.class);
	
	public Query mapRow(ResultSet rs, int rowNum) throws SQLException {
		LOG.info("json 123:: start");
		Query query = new Query();
		query.setId(rs.getInt("ID"));
		query.setName(rs.getString("NAME"));
		
		query.setSql(rs.getString("SQL"));
		String json = rs.getString("MAP");
		LOG.info("json :: "+json);
		query.setMap(json);
		return query;
	}

	
	
}
