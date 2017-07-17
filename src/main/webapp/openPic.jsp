<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@ include file="/WEB-INF/view/common.jsp" %>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Picture Viewer</title>
</head>
<body>

</body>

<script type="text/javascript">
	
	window.onload=function(){ 
	
		function getQueryString(name) { 
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
		var r = window.location.search.substr(1).match(reg); 
		if (r != null) return unescape(r[2]); return null; 
	
	} 
	var businessId = getQueryString("bid");	
	var typeId = getQueryString("tid");
	var key = getQueryString("key");
	var id = getQueryString("id"); 
	
	openViewDIv(businessId,typeId,key,id,this);
	};
	
	
</script>
</html>