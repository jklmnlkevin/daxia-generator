<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>dev utils</title>
<link href="../res/dev/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="../res/dev/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
</head>
<body style="width:1300px;margin:auto;">
	<div style="margin-top: 10px;">
		<form action="${ctx }/dev/query" method="post">
			<textarea rows="5" style="width: 100%" cols="" name="sql">${sql}</textarea><br/>
			<div style="text-align: center;">
			<input type="submit" class="btn btn-primary"/>
			</div>
		</form>	
	</div>
	<div>
		<table class="table table-bordered">
			<thead>
				<c:forEach items="${result.columnNames }" var="c">
					<th>
						${c }
					</th>
				</c:forEach>
			</thead>
			<tbody>
				<c:forEach items="${result.results }" var="row">
					<tr>
						<c:forEach items="${row }" var="c">
							<td>${c  }</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<script src="../res/dwz/js/jquery-1.7.2.min.js"></script>
	<script src="../res/dev/ry-ui-1.10.3.min.js" type="text/javascript"></script>
	<script src="../res/dev/dev.js" type="text/javascript"></script>
	
</body>
</html>