package com.daxia.generator.model;

public class RoleAuthority extends BaseModel {
	private Long id;
	private Long role_id;
	private Long authority_id;
	public RoleAuthority() {
    }
	
	public RoleAuthority(Long role_id, Long authority_id) {
	    super();
	    this.role_id = role_id;
	    this.authority_id = authority_id;
    }

	public Long getId() {
    	return id;
    }
	public void setId(Long id) {
    	this.id = id;
    }
	public Long getRole_id() {
    	return role_id;
    }
	public void setRole_id(Long role_id) {
    	this.role_id = role_id;
    }
	public Long getAuthority_id() {
    	return authority_id;
    }
	public void setAuthority_id(Long authority_id) {
    	this.authority_id = authority_id;
    }
	
}
