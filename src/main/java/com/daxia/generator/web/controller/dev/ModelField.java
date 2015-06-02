package com.daxia.generator.web.controller.dev;

public class ModelField {
	Class<? extends Object> type;
	String name;
	String comment;
	
	@Override
	public String toString() {
		return name + "-" + type.getName() + "-" + comment;
	}

	public Class<? extends Object> getType() {
    	return type;
    }

	public void setType(Class<? extends Object> type) {
    	this.type = type;
    }

	public String getName() {
    	return name;
    }

	public void setName(String name) {
    	this.name = name;
    }

	public String getComment() {
    	return comment;
    }

	public void setComment(String comment) {
    	this.comment = comment;
    }
}
