package com.daxia.hr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

<#list imports as i>
import ${i};
</#list>

import org.hibernate.annotations.GenericGenerator;

/**
 * ${mf.modelChineseName}
 */
@Entity
@Table(name = "${tableName}")
@SuppressWarnings("serial")
public class ${Model}Entity implements java.io.Serializable {
	<#list mf.names as n>
	/**
	 * ${mf.comments[n_index]}
	 */
	@Column(name = "${mf.dbNames[n_index]}")    
    private ${shortTypes[n_index]} ${n};
	</#list>
	<#list mf.names as n>
	
	/** 
	 * 获取值：${mf.comments[n_index]}
	 */
	public ${shortTypes[n_index]} get${upperNames[n_index]}() {
    	return ${n};
    }
	
	/** 
	 * 设置值：${mf.comments[n_index]}
	 */    
    public void set${upperNames[n_index]}(${shortTypes[n_index]} ${n}) {
    	this.${n} = ${n};
    }
	</#list>
}
