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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图片上传demo</title>

 	<link rel="stylesheet" href="${ctx}/res/dwz/xheditor/common.css" type="text/css" media="screen" />
    <link href="${ctx}/res/dwz/xheditor/prettify/prettify.css" rel="stylesheet" type="text/css" />
   	<script src="${ctx}/res/dwz/js/jquery-1.7.2.js" type="text/javascript"></script>
	<script src="${ctx}/res/dwz/xheditor/xheditor-1.2.1.min.js" type="text/javascript"></script>
	<script src="${ctx}/res/dwz/xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/res/dwz/xheditor/prettify/prettify.js"></script>

	
	<script type="text/javascript">
		$(document).ready(function(){			
	        var editor;
        	$(pageInit);
	        function pageInit()
        	{
            	var allPlugin={
           			test7:{c:'testClassName',t:'测试7：showIframeModal (Ctrl+7)',s:'ctrl+7',e:function(){
           				alert(11);
           				var _this=this;
           				_this.saveBookmark();
           				_this.showIframeModal('测试showIframeModal接口','uploadgui.php',function(v){_this.loadBookmark();_this.pasteText('返回值：\r\n'+v);},500,300);
           			}}
            	};
            	editor=$('#content').xheditor({plugins:allPlugin,tools:'full',loadCSS:'../editor/prettify/prettify.css'});
        	}
		});
	</script>
</head>
<body>
	<div class="pageHeader">
		header
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<span>在线编辑器</span>
		</div>
		<form method="post" action="/Articles/articleEdit" class="pageForm required-validate" enctype="multipart/form-data"  onsubmit="return iframeCallback(this, dialogAjaxDone)" >  
			<textarea id="content" style="width:700px;height:300px;" rows="6" cols="40" name="content"></textarea>
         </form>
	</div>
</body>
</html>