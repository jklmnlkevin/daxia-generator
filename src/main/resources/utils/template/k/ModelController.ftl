package com.daxia.hr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.daxia.hr.entity.${Model}Entity;
import com.daxia.hr.service.${Model}ServiceI;

@Controller
@RequestMapping("/${model}")
public class ${Model}Controller extends BaseController {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(${Model}Controller.class);

	@Autowired
	private ${Model}ServiceI ${model}Service;
	@Autowired
	private SystemService systemService;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * ${model}列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "${model}")
	public ModelAndView ${model}(HttpServletRequest request) {
		return new ModelAndView("${model}/${model}List");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagrid")
	public void datagrid(${Model}Entity ${model}, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(${Model}Entity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, ${model}, request.getParameterMap());
		try {
			// 自定义追加查询条件
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.${model}Service.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除${model}
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(${Model}Entity ${model}, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		${model} = systemService.getEntity(${Model}Entity.class, ${model}.getId());
		message = "${model}删除成功";
		try {
			${model}Service.delete(${model});
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "${model}删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除${model}
	 * 
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "${model}删除成功";
		try {
			for (String id : ids.split(",")) {
				${Model}Entity ${model} = systemService.getEntity(${Model}Entity.class, id);
				${model}Service.delete(${model});
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "${model}删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加${model}
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(${Model}Entity ${model}, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "${model}添加成功";
		try {
			${model}Service.save(${model});
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "${model}添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新${model}
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(${Model}Entity ${model}, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "${model}更新成功";
		${Model}Entity t = ${model}Service.get(${Model}Entity.class, ${model}.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(${model}, t);
			${model}Service.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "${model}更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * ${model}新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(${Model}Entity ${model}, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(${model}.getId())) {
			${model} = ${model}Service.getEntity(${Model}Entity.class, ${model}.getId());
			req.setAttribute("${model}Page", ${model});
		}
		return new ModelAndView("${model}/${model}-add");
	}

	/**
	 * ${model}编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(${Model}Entity ${model}, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(${model}.getId())) {
			${model} = ${model}Service.getEntity(${Model}Entity.class, ${model}.getId());
			req.setAttribute("${model}Page", ${model});
		}
		return new ModelAndView("${model}/${model}-update");
	}
}
