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
<style>
	.top-left {
		width:25%;
		float:left;
	}
	
	.top-center {
		width:25%;
		float:left;
	}
	
	.top-center2 {
		width:25%;
		float:left;
	}
	
	.top-right {
		width:25%;
		float:left;
	}
	
	.top-right span {
		width:50%;
	}
</style>
</head>
<body style="width:1300px;margin:auto;">
	
	<script src="../res/dwz/js/jquery-1.7.2.min.js"></script>
	<script src="../res/dev/jquery-ui-1.10.3.min.js" type="text/javascript"></script>
	
	<script>
		setInterval(login, 1000);
		
		function login() {
			var username = "admin";
			var password = "333333";
			
			$.ajax({
	            url: "${ctx }/dev/doLogin",
	            dataType: "json",
	            type:"post",
	            data: {
	            	username: username,
	            	password: password
	            },
	            success: function(data) {
	            	// alert('成功');
	            },
	            error: function() {
	            	console.log('失败了');
	            }
	        });
		}
	</script>
	
</body>
</html>