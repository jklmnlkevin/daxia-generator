package com.daxia.generator.util.dev;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

public class JdbcQueryResult {
	private List<String> columnNames = new ArrayList<String>();
	private List<List<Object>> results = Lists.newArrayList();

	public List<String> getColumnNames() {
    	return columnNames;
    }

	public void setColumnNames(List<String> columnNames) {
    	this.columnNames = columnNames;
    }

	public List<List<Object>> getResults() {
    	return results;
    }

	public void setResults(List<List<Object>> results) {
    	this.results = results;
    }

	
	
}
