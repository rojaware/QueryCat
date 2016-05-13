package com.rojaware.query.model;

import java.util.ArrayList;
import java.util.List;

public class CsvReportVO {
	private String reportName;
	private List<String> reportData = new ArrayList<String>();

	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public List<String> getReportData() {
		return reportData;
	}
	public void setReportData(List<String> reportData) {
		this.reportData = reportData;
	}
}
