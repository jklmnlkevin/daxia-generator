<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	<!-- content ends -->
			</div><!--/#content.span10-->
				</div><!--/fluid-row-->
				
		<hr>

		<div class="modal hide fade" id="myModal">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>Settings</h3>
			</div>
			<div class="modal-body">
				<p>Here settings can be configured...</p>
			</div>
			<div class="modal-footer">
				<a href="#" class="btn" data-dismiss="modal">Close</a>
				<a href="#" class="btn btn-primary">Save changes</a>
			</div>
		</div>

		<footer>
		</footer>
		
	</div><!--/.fluid-container-->

	<!-- external javascript
	================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->

	<!-- jQuery -->
	<script src="${ctx }/res/admin/js/jquery-1.7.2.min.js"></script>
	<!-- jQuery UI -->
	<script src="${ctx}/res/admin/js/jquery-ui-1.8.21.custom.min.js"></script>
	
	<script src="${ctx}/res/bootstrap/js/bootstrap.min.js"></script>
	
	<!-- library for cookie management -->
	<script src="${ctx}/res/admin/js/jquery.cookie.js"></script>
	<!-- calander plugin -->
	<script src='${ctx}/res/admin/js/fullcalendar.min.js'></script>
	<!-- data table plugin -->
	<script src='${ctx}/res/admin/js/jquery.dataTables.min.js'></script>

	<!-- chart libraries start -->
	<script src="${ctx}/res/admin/js/excanvas.js"></script>
	<script src="${ctx}/res/admin/js/jquery.flot.min.js"></script>
	<script src="${ctx}/res/admin/js/jquery.flot.pie.min.js"></script>
	<script src="${ctx}/res/admin/js/jquery.flot.stack.js"></script>
	<script src="${ctx}/res/admin/js/jquery.flot.resize.min.js"></script>
	<!-- chart libraries end -->

	<!-- select or dropdown enhancer -->
	<script src="${ctx}/res/admin/js/jquery.chosen.min.js"></script>
	<!-- checkbox, radio, and file input styler -->
	<script src="${ctx}/res/admin/js/jquery.uniform.min.js"></script>
	<!-- plugin for gallery image view -->
	<script src="${ctx}/res/admin/js/jquery.colorbox.min.js"></script>
	<!-- rich text editor library -->
	<script src="${ctx}/res/admin/js/jquery.cleditor.min.js"></script>
	<!-- notification plugin -->
	<script src="${ctx}/res/admin/js/jquery.noty.js"></script>
	<!-- file manager library -->
	<script src="${ctx}/res/admin/js/jquery.elfinder.min.js"></script>
	<!-- star rating plugin -->
	<script src="${ctx}/res/admin/js/jquery.raty.min.js"></script>
	<!-- for iOS style toggle switch -->
	<script src="${ctx}/res/admin/js/jquery.iphone.toggle.js"></script>
	<!-- autogrowing textarea plugin -->
	<script src="${ctx}/res/admin/js/jquery.autogrow-textarea.js"></script>
	<!-- multiple file upload plugin -->
	<script src="${ctx}/res/admin/js/jquery.uploadify-3.1.min.js"></script>
	<!-- history.js for cross-browser state change on ajax -->
	<script src="${ctx}/res/admin/js/jquery.history.js"></script>
	<!-- application script for Charisma demo -->
	<script src="${ctx}/res/admin/js/charisma.js"></script>
	
	<script src="${ctx}/res/sco/js/sco.ajax.js"></script>
	<script src="${ctx}/res/sco/js/sco.collapse.js"></script>
	<script src="${ctx}/res/sco/js/sco.confirm.js"></script>
	<script src="${ctx}/res/sco/js/sco.countdown.js"></script>
	<script src="${ctx}/res/sco/js/sco.message.js"></script>
	<script src="${ctx}/res/sco/js/sco.modal.js"></script>
	<script src="${ctx}/res/sco/js/sco.panes.js"></script>
	<script src="${ctx}/res/sco/js/sco.tab.js"></script>
	<script src="${ctx}/res/sco/js/sco.tooltip.js"></script>
	<script src="${ctx}/res/sco/js/sco.valid.js"></script>
	
	<script>
		function ajaxCall(url, data) {
            $.ajax({
                url: url,
                datatype: "jsoup",
                async: true,
                data: {

                }


            });
		}		
	</script>	
</body>
</html>
