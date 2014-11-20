package com.daxia.generator.model;


public class Authority extends BaseModel {
	private Long id;
	private Long parent_authority_id;
	private String name;
	private String code;
	public Long getId() {
    	return id;
    }
	public void setId(Long id) {
    	this.id = id;
    }
	public Long getParent_authority_id() {
    	return parent_authority_id;
    }
	public void setParent_authority_id(Long parent_authority_id) {
    	this.parent_authority_id = parent_authority_id;
    }
	public String getName() {
    	return name;
    }
	public void setName(String name) {
    	this.name = name;
    }
	public String getCode() {
    	return code;
    }
	public void setCode(String code) {
    	this.code = code;
    }
	
}
