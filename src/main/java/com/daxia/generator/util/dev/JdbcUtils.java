package com.daxia.generator.util.dev;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;

import com.google.common.collect.Lists;

public class JdbcUtils {
	public static Connection connection;
	public static Object query(Connection connection, String sql) {
		try {
	        return doQuery(connection, sql);
        } catch (Exception e) {
        	throw new RuntimeException(e.getMessage(), e);
        }
	}

	private static Object doQuery(Connection connection, String sql) throws Exception {
		ResultSet rs = connection.createStatement().executeQuery(sql);
		ResultSetMetaData md = rs.getMetaData();
		JdbcQueryResult result = new JdbcQueryResult();
		for (int i = 1; i <= md.getColumnCount(); i++) {
			result.getColumnNames().add(md.getColumnName(i));
		}
		List<List<Object>> results = Lists.newArrayList();
		while (rs.next()) {
			List<Object> list = Lists.newArrayList();
			for (int i = 1; i <= md.getColumnCount(); i++) {
				list.add(rs.getObject(i));
			}
			results.add(list);
		}
		result.setResults(results);
		
	    return result;
    }

	public static Connection getConnection() {
	    return connection;
    }

	public static void returnConnection(Connection connection) {
	    
    }
}
