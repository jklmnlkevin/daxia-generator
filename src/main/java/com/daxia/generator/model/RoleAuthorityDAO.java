package com.daxia.generator.model;

public class RoleAuthorityDAO extends JdbcBaseDAO<RoleAuthority> {
    public RoleAuthorityDAO(String tableName) {
        super.tableName = tableName;
    }
}
