package com.daxia.generator.model;

public class Menu extends BaseModel {
	private Long id;
	private int level;
	private String name;
	private String href;
	private Long parent_id;
	private Long authority_id;
	private String rel;
	private int seq;
	public Long getId() {
    	return id;
    }
	public void setId(Long id) {
    	this.id = id;
    }
	public int getLevel() {
    	return level;
    }
	public void setLevel(int level) {
    	this.level = level;
    }
	public String getName() {
    	return name;
    }
	public void setName(String name) {
    	this.name = name;
    }
	public String getHref() {
    	return href;
    }
	public void setHref(String href) {
    	this.href = href;
    }
	public Long getParent_id() {
    	return parent_id;
    }
	public void setParent_id(Long parent_id) {
    	this.parent_id = parent_id;
    }
	public Long getAuthority_id() {
    	return authority_id;
    }
	public void setAuthority_id(Long authority_id) {
    	this.authority_id = authority_id;
    }
	public String getRel() {
    	return rel;
    }
	public void setRel(String rel) {
    	this.rel = rel;
    }
	public int getSeq() {
    	return seq;
    }
	public void setSeq(int seq) {
    	this.seq = seq;
    }
	
}
