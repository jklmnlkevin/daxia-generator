package com.daxia.generator.model;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class MenuDAO extends JdbcBaseDAO<Menu> {
    public MenuDAO(String tableName) {
        super.tableName = tableName;
    }
    
	public Menu findByName(String name) {
		String sql = "select * from " + tableName + " where name = ? ";
		List<Menu> list = super.find(sql, new Object[] {name}, Menu.class);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		} else {
			return list.get(0);
		}
	}
}
