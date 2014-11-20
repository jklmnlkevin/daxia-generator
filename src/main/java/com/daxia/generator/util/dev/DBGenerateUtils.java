package com.daxia.generator.util.dev;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBGenerateUtils {
	public static void executeSQL(Connection connection, String sql) {
		try {
	        connection.createStatement().execute(sql);
        } catch (SQLException e) {
        	throw new RuntimeException(e.getMessage(), e);
        }
	}
	
	public static boolean exists(Connection connection, String sql) {
		try {
	        ResultSet rs = connection.createStatement().executeQuery(sql);
	        return rs.next();
        } catch (SQLException e) {
        	throw new RuntimeException(e.getMessage(), e);
        }
	}
}
