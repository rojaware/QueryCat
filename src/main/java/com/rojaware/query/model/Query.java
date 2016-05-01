/**
 * 
 */
package com.rojaware.query.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.google.gson.Gson;

public class Query implements Serializable {
	private int id;
	private String name;
	private String sql;
	private Map<String, Object> map;

	public Query() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Query(String name) {
		super();
		this.name = name;
	}

	public Query(String name, String sql) {
		super();
		this.name = name;
		this.sql = sql;
	}
	public Query(String name, String sql, String json) {
		super();
		this.name = name;
		this.sql = sql;
		this.setMap(json);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	
	public String getMapJson() {
		Gson gson = new Gson();
		String s =  gson.toJson(map);
		if (s.equalsIgnoreCase("null")) {
			s = null;
		}
			
		return s;
	}

	public void setMap(String json) {
		if (!StringHelper.isEmptyOrWhiteSpaceOrNull(json)) {
			
			Gson gson = new Gson();
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> fromJson = (Map<String, Object>) gson.fromJson(json, map.getClass());
			this.map = fromJson;
		}
	}

	@Override
	public String toString() {
		return "Query [id=" + id + ", name=" + name + ", sql=" + sql + ", map=" + map + "]";
	}

}