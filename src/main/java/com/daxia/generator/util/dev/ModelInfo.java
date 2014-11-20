package com.daxia.generator.util.dev;

import org.springframework.web.bind.annotation.RequestParam;


public class ModelInfo {
	private String model;
	private String modelChineseName;
	private String parentMenu;
	private String menu;
	private String menuUrl;
	private String authorityCode;
	private String[] dbNames = new String[] {};
	private String[] shortComments = new String[] {};
	private String[] types = new String[] {};
	// 是否是项目里的model
	private boolean[] isModel;
	private boolean[] isUser;
	private boolean[] isCommunity;
	
	private String[] names = new String[] {};
	private String[] comments = new String[] {};
	private boolean[] asQueries = new boolean[] {};
	private boolean[] asLikeQueries = new boolean[] {};
	private String[] dateFormats = new String[] {};
	private String[] queryTypes = new String[] {};
	private String[] modelDisplayFields = new String[] {};
	public String getModel() {
    	return model;
    }
	public void setModel(String model) {
    	this.model = model;
    }
	public String getModelChineseName() {
    	return modelChineseName;
    }
	public void setModelChineseName(String modelChineseName) {
    	this.modelChineseName = modelChineseName;
    }
	public String getParentMenu() {
    	return parentMenu;
    }
	public void setParentMenu(String parentMenu) {
    	this.parentMenu = parentMenu;
    }
	public String getMenu() {
    	return menu;
    }
	public void setMenu(String menu) {
    	this.menu = menu;
    }
	public String getMenuUrl() {
    	return menuUrl;
    }
	public void setMenuUrl(String menuUrl) {
    	this.menuUrl = menuUrl;
    }
	public String getAuthorityCode() {
    	return authorityCode;
    }
	public void setAuthorityCode(String authorityCode) {
    	this.authorityCode = authorityCode;
    }
	public String[] getTypes() {
    	return types;
    }
	public void setTypes(@RequestParam("types[]") String[] types) {
    	this.types = types;
    	isModel = new boolean[types.length];
    	isUser = new boolean[types.length];
    	isCommunity = new boolean[types.length];
    	for (int i = 0; i < types.length; i++) {
    	    if (types[i].startsWith("com.daxi")) {
    	        isModel[i] = true;
    	        if (types[i].endsWith(".User")) {
    	            isUser[i] = true;
    	        } else if (types[i].endsWith(".Community")) {
    	            isCommunity[i] = true;
    	        }
    	    } else {
    	        isModel[i] = false;
    	        isUser[i] = false;
    	        isCommunity[i] = false;
    	    }
    	}
    	
    	
    }
	public String[] getNames() {
    	return names;
    }
	public void setNames(@RequestParam("names[]") String[] names) {
    	this.names = names;
    }
	public String[] getComments() {
    	return comments;
    }
	public void setComments(@RequestParam("comments[]") String[] comments) {
    	this.comments = comments;
    }
	public boolean[] getAsQueries() {
    	return asQueries;
    }
	public void setAsQueries(@RequestParam("asQueries[]") boolean[] asQueries) {
    	this.asQueries = asQueries;
    }
	public boolean[] getAsLikeQueries() {
    	return asLikeQueries;
    }
	public void setAsLikeQueries(@RequestParam("asLikeQueries[]") boolean[] asLikeQueries) {
    	this.asLikeQueries = asLikeQueries;
    }
	/**
     * @return the dbNames
     */
    public String[] getDbNames() {
    	return dbNames;
    }
	/**
     * @param dbNames the dbNames to set
     */
    public void setDbNames(String[] dbNames) {
    	this.dbNames = dbNames;
    }
	/**
     * @return the shortComments
     */
    public String[] getShortComments() {
    	return shortComments;
    }
	/**
     * @param shortComments the shortComments to set
     */
    public void setShortComments(String[] shortComments) {
    	this.shortComments = shortComments;
    }
	public String[] getDateFormats() {
    	return dateFormats;
    }
	public void setDateFormats(String[] dateFormats) {
    	this.dateFormats = dateFormats;
    	for (int i = 0; i < dateFormats.length; i++) {
    	    if (types[i].equals("java.util.Date")) {
    	        
    	    } else {
    	        this.dateFormats[i] = "";
    	    }
    	}
    }
	public String[] getQueryTypes() {
    	return queryTypes;
    }
	public void setQueryTypes(String[] queryTypes) {
    	this.queryTypes = queryTypes;
    }
    public boolean[] getIsModel() {
        return isModel;
    }
    public void setIsModel(boolean[] isModel) {
        this.isModel = isModel;
    }
    public boolean[] getIsUser() {
        return isUser;
    }
    public void setIsUser(boolean[] isUser) {
        this.isUser = isUser;
    }
    public boolean[] getIsCommunity() {
        return isCommunity;
    }
    public void setIsCommunity(boolean[] isCommunity) {
        this.isCommunity = isCommunity;
    }
    public String[] getModelDisplayFields() {
        return modelDisplayFields;
    }
    public void setModelDisplayFields(String[] modelDisplayFields) {
        this.modelDisplayFields = modelDisplayFields;
    }
    
}
