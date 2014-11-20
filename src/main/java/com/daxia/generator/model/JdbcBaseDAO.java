package com.daxia.generator.model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.daxia.generator.util.dev.JdbcUtils;

public class JdbcBaseDAO<T extends BaseModel> {
    
    protected String tableName;

	public void executeUpdate(String sql, Object[] paras) {

		Connection connection = JdbcUtils.getConnection();
		try {
	        PreparedStatement stmt = connection.prepareStatement(sql);
	        if (paras != null) {
		        for (int i = 0; i < paras.length; i++) {
		        	stmt.setObject(i + 1, paras[i]);
		        }
	        }
	        stmt.executeUpdate();
        } catch (Exception e) {
        	throw new RuntimeException(e.getMessage(), e);
        } finally {
        	JdbcUtils.returnConnection(connection);
        }
	
	}
	
	public List<T> find(String sql, Object[] paras, Class<T> clazz) {
	    sql = sql.toLowerCase();
		Connection connection = JdbcUtils.getConnection();
		try {
	        PreparedStatement stmt = connection.prepareStatement(sql);
	        if (paras != null) {
		        for (int i = 0; i < paras.length; i++) {
		        	stmt.setObject(i + 1, paras[i]);
		        }
	        }
	        ResultSet rs = stmt.executeQuery();
	        List<T> list = new ArrayList<T>();
	        ResultSetMetaData md = rs.getMetaData();
	        int columnCount = md.getColumnCount();
	        List<String> dbColumnNames = new ArrayList<String>();
	        for (int i = 1; i <= columnCount; i++) {
	        	dbColumnNames.add(md.getColumnName(i));
	        }
	        while (rs.next()) {
	        	List<Object> dbColumns = new ArrayList<Object>();
	        	for (int i = 1; i <= columnCount; i++) {
	        		dbColumns.add(rs.getObject(i));
	        	}
	        	T t = toModel(dbColumnNames, dbColumns, clazz);
	        	list.add(t);
	        }
	        return list;
        } catch (Exception e) {
        	throw new RuntimeException(e.getMessage(), e);
        } finally {
        	JdbcUtils.returnConnection(connection);
        }
	}
	
	private T toModel(List<String> dbColumnNames, List<Object> dbColumns, Class<T> clazz) {
		try {
	        T t = clazz.newInstance();
	        for (int i = 0; i < dbColumnNames.size(); i++) {
	        	String name = dbColumnNames.get(i);
	        	Method m = getSetter(name, clazz);
	        	if (m == null) {
	        	    continue;
	        	}
	        	Class<? extends Object> type = m.getParameterTypes()[0];
	        	Object value = dbColumns.get(i);
	        	if (type == Long.class || type == long.class) {
	        		if (value != null) {
	        			value = Long.valueOf(value.toString());
	        		}
	        	} else if (type == Integer.class || type == int.class) {
	        		if (value != null) {
	        		value = Integer.valueOf(value.toString());
	        		}
	        	}else if (type == Float.class || type == float.class) {
	        		if (value != null) {
					value = Float.valueOf(value.toString());
	        		}
	        	} else if (type == String.class) {
	        		if (value != null) {
	        		value = value.toString();
	        		}
	        	} else if (type == Double.class || type == double.class) {
	        		if (value != null) {
	        		value = Double.valueOf(value.toString());
	        		}
				} else if(type == String.class){
					if (value != null) {
					value = String.valueOf(value);
					}
	        	}else if(type == Date.class){
	        		if (value != null) {
	        		value = (Date)value;
	        		}
				}else {
					System.out.println("type="+type);
				}
	        	m.invoke(t, value);
	        }
	        return t;
        } catch (IllegalArgumentException e) {
	        e.printStackTrace();
        } catch (InstantiationException e) {
	        e.printStackTrace();
        } catch (IllegalAccessException e) {
	        e.printStackTrace();
        } catch (InvocationTargetException e) {
	        e.printStackTrace();
        }
		return null;
    }

	private Method getSetter(String name, Class<T> clazz) {
		Method[] ms = clazz.getDeclaredMethods();
		for (Method m : ms) {
	        if (("set" + name).equalsIgnoreCase(m.getName())) {
	        	return m;
	        } else if (("is" + name).equalsIgnoreCase(m.getName())) {
	            return m;
	        } else if (name.equalsIgnoreCase(m.getName())) {
	            return m;
	        }
        }
	    return null;
    }

	private List<Field> getFields(Class<T> clazz) {
		return Arrays.asList(clazz.getDeclaredFields());
    }

	public void create(T model) {
		List<Field> fields = getFields(model);
		String table = tableName;
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append("insert into ").append(table.toLowerCase());//) values()");
		hqlBuilder.append("(");
		for (int i = 0; i < fields.size(); i++) {
			hqlBuilder.append(fields.get(i).getName());
			if (i < fields.size() - 1) {
				hqlBuilder.append(", ");
			}
		}
		hqlBuilder.append(") values (?");
		
		if (fields.size() > 1) {
			hqlBuilder.append(StringUtils.repeat(", ?", fields.size() - 1));
		}
		hqlBuilder.append(")");
		
		Connection connection = JdbcUtils.getConnection();
		try {
	        PreparedStatement stmt = connection.prepareStatement(hqlBuilder.toString());
	        for (int i = 0; i < fields.size(); i++) {
	        	fields.get(i).setAccessible(true);
	        	stmt.setObject(i + 1, fields.get(i).get(model));
	        	fields.get(i).setAccessible(false);
	        }
	        stmt.executeUpdate();
        } catch (Exception e) {
        	throw new RuntimeException(e.getMessage(), e);
        } finally {
        	JdbcUtils.returnConnection(connection);
        }
		
		// System.out.println(hqlBuilder);
	}

	private List<Field> getFields(BaseModel model) {
		return Arrays.asList(model.getClass().getDeclaredFields());
    }
	
	public static void main(String[] args) {
    }
}
