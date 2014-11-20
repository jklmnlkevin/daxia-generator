<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
	  <t:datagrid name="${model}List" checkbox="true" fitColumns="false" title="${model}" actionUrl="${model}.do?datagrid" idField="id" fit="true" queryMode="group">
		   <t:dgCol title="主键"  field="id"  hidden="false"  queryMode="single"  width="120"></t:dgCol>

		   <#list names as n>
		       <#if n != 'id'>		   
		   	<t:dgCol title="${shortComments[n_index] }"  field="${n }"  hidden="true" query="true" queryMode="single"  width="120"></t:dgCol>
		   	   </#if>
		   </#list>
		   
		   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
		   <t:dgDelOpt title="删除" url="${model}.do?doDel&id={id}" />
		   <t:dgToolBar title="录入" icon="icon-add" url="${model}.do?goAdd" funname="add"></t:dgToolBar>
		   <t:dgToolBar title="编辑" icon="icon-edit" url="${model}.do?goUpdate" funname="update"></t:dgToolBar>
		   <t:dgToolBar title="批量删除"  icon="icon-remove" url="${model}.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
		   <t:dgToolBar title="查看" icon="icon-search" url="${model}.do?goUpdate" funname="detail"></t:dgToolBar>
	  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/${model}/${model}List.js"></script>		
 <script type="text/javascript">
 ${'$'}(document).ready(function(){
 		//给时间控件加上样式
 });
 </script>