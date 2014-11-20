<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>dev utils</title>
	<link href="${ctx }/res/dev/bootstrap.css" rel="stylesheet" type="text/css" />
	<link href="${ctx }/res/dev/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
	<script src="${ctx }/res/js/jquery-1.7.1.min.js"></script>
</head>
<body>
	<div class="container222">
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
				    <label class="col-md-4 control-label">Model名</label>
				    <div class="col-md-8">
				      <input type="text" class="form-control" name="model" placeholder="大写开头，与数据库表名相同" value="Example"/>
				    </div>
				</div>
				<div class="form-group">
				    <label class="col-md-4 control-label">表名</label>
				    <div class="col-md-8">
				      <input type="text" class="form-control" name="tableName" id="tableName" placeholder="表名" value=""/>
				    </div>
				</div>
				<div class="form-group">
				    <label class="col-md-4 control-label">Model中文名</label>
				    <div class="col-md-8">
				      <input type="text" class="form-control" name="modelChineseName" placeholder="" value="又是测试"/>
				    </div>
				</div>
				<div class="form-group">
				    <label class="col-md-4 control-label">上级菜单</label>
				    <div class="col-md-8">
				      <input type="text" class="form-control" name="parentMenu" value="系统配置" placeholder="已有的一级菜单的名字，如系统配置" />
				    </div>
				</div>
				<div class="form-group">
				    <label class="col-md-4 control-label">生成来源</label>
				    <div class="col-md-8">
				      <input type="radio"
					name="fromDB" value="0" checked onclick="cleanTableInfo();"/>不从数据库生成 <input type="radio"
					name="fromDB" value="1" onclick="getTableInfo();" />从数据库生成 
				    </div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
				    <label class="col-md-4 control-label">项目路径</label>
				    <div class="col-md-8">
				      <input type="text" class="form-control" name="projectPath" placeholder="项目的根目录" value="${projectPath }" id="projectPath"/>
				    </div>
				</div>
				<div class="form-group">
				    <label class="col-md-4 control-label">基本包名</label>
				    <div class="col-md-8">
				      <input type="text" class="form-control" name="basePackage" id="basePackage" placeholder="基本的包名" value="${basePackage }"/>
				    </div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
				    <label class="col-md-4 control-label">数据库名</label>
				    <div class="col-md-8">
				      <input type="text" class="form-control" id="dbName" name="dbName" placeholder="" value="rsgl"/>
				    </div>
				</div>
				<div class="form-group">
				    <label class="col-md-4 control-label">数据库用户名</label>
				    <div class="col-md-8">
				      <input type="text" class="form-control" name="dbUsername" placeholder="" value="root"/>
				    </div>
				</div>
				<div class="form-group">
				    <label class="col-md-4 control-label">数据库密码</label>
				    <div class="col-md-8">
				      <input type="text" class="form-control" name="dbPassword" placeholder="" value="sa"/>
				    </div>
				</div>
			</div>
		</div>
		<div style="margin-top: 10px; border: 1px solid #ccc;">
			<table class="table table-bordered" id="table">
				<thead>
					<th>类型</th>
					<th>db字段名</th>
					<th>java字段名</th>
					<th>注释</th>
					<th>短注释</th>
					<th>查询</th>
					<th>操作</th>
				</thead>
				<tbody>
					<tr class="firstTr">
						<td><input type="text" field="type" value="java.lang.Long" />
						</td>
						<td><input type="text" class="input-small" value="id"
							field="dbName" /></td>
						<td><input type="text" value="id" field="name"
							class="input-medium" /></td>
						<td><input type="text" value="id" field="comment"
							class="input-small" /></td>
						<td><input type="text" value="id" field="shortComment"
							class="input-small" /></td>
						<td>
							<input type="checkbox" name="asQuery" value="id" />查询 
							<input type="checkbox" name="asLikeQuery" value="id" />模糊
							&nbsp;&nbsp;<a href="javascript:void(0);" onclick="switchDiv(this);">&gt;&gt;</a>
							<div style="display: none;">
								日期格式: 
									<select name="dateFormat">
										<option value="yyyy-MM-dd">yyyy-MM-dd</option>
										<option value="yyyy-MM-dd HH:mm:ss">yyyy-MM-dd HH:mm:ss</option>
									</select>
									<br/>
							    查询类型：
							    	<select name="queryType">
										<option value="text">文本框</option>
										<option value="select">下拉</option>
									</select>
									<br/>
							</div>
						</td>
						<td><button class="btn" onclick="addLine(this);">加</button>
							<button class="btn" onclick="deleteLine(this);">减</button>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="text-center">
		<button class="btn btn-primary" onclick="generate();">点我生成</button>
		</div>
	</div>
	
	<script src="${ctx }/res/dev/e_dev.js" type="text/javascript"></script>
	<script>
		var debug = false;
		if (debug) {
			setTimeout(function() {window.location.reload()}, 1000);
		}
		
		var str = document.cookie.split('; ');
		for (var i in str) {
			var n = str[i].split('=')[0];
			n = n.split('.')[1];
			var v = str[i].split('=')[1];
			$('#' + n).val(v);
		}
	</script>
</body>
</html>