package com.rojaware.query.model;

import java.util.ArrayList;
import java.util.List;

public class TableView {
	List<String> columns;
	List<List<String>> rows;
	
	
	public TableView() {
		columns = new ArrayList<String>();
		rows = new ArrayList<List<String>>();
	}
	public TableView(List<List<String>> list) {
		super();
		if (list != null) {
			columns = list.get(0);
			int end = list.size();
			rows =  new ArrayList<List<String>>();
			for (int i = 1; i < end ; i++) {
				rows.add(list.get(i));
			}
		}
	
	}
	public List<String> getColumns() {
		return columns;
	}
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	public List<List<String>> getRows() {
		return rows;
	}
	public void setRows(List<List<String>> rows) {
		this.rows = rows;
	}
	@Override
	public String toString() {
		return "TableView [headers=" + columns + ", rows=" + rows + "]";
	}
	
	
}
