package com.rojaware.query.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

public class QueryRowMapper implements RowMapper
{
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Query query = new Query();
		query.setId(rs.getInt("ID"));
		query.setName(rs.getString("NAME"));
		
		query.setSql(rs.getString("SQL"));
		String json = rs.getString("MAP");
		query.setMap(json);
		return query;
	}
	
}
