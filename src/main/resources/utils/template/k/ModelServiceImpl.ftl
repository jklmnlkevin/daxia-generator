package com.daxia.hr.service.impl;

import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daxia.hr.entity.${Model}Entity;
import com.daxia.hr.service.${Model}ServiceI;

@Service("${model}Service")
@Transactional
public class ${Model}ServiceImpl extends CommonServiceImpl implements ${Model}ServiceI {

	public <T> void delete(T entity) {
		super.delete(entity);
		// 执行删除操作配置的sql增强
		this.doDelSql((${Model}Entity) entity);
	}

	public <T> Serializable save(T entity) {
		Serializable t = super.save(entity);
		// 执行新增操作配置的sql增强
		this.doAddSql((${Model}Entity) entity);
		return t;
	}

	public <T> void saveOrUpdate(T entity) {
		super.saveOrUpdate(entity);
		// 执行更新操作配置的sql增强
		this.doUpdateSql((${Model}Entity) entity);
	}

	/**
	 * 默认按钮-sql增强-新增操作
	 * 
	 * @param id
	 * @return
	 */
	public boolean doAddSql(${Model}Entity t) {
		return true;
	}

	/**
	 * 默认按钮-sql增强-更新操作
	 * 
	 * @param id
	 * @return
	 */
	public boolean doUpdateSql(${Model}Entity t) {
		return true;
	}

	/**
	 * 默认按钮-sql增强-删除操作
	 * 
	 * @param id
	 * @return
	 */
	public boolean doDelSql(${Model}Entity t) {
		return true;
	}

	/**
	 * 替换sql中的变量
	 * 
	 * @param sql
	 * @return
	 */
	public String replaceVal(String sql, ${Model}Entity t) {
	/*
		sql = sql.replace("${'#'}{id}", String.valueOf(t.getId()));
		sql = sql.replace("${'#'}{name}", String.valueOf(t.getName()));
		sql = sql.replace("${'#'}{UUID}", UUID.randomUUID().toString());
		return sql; */
		return null;
	}
}