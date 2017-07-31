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
	
		/* function getQueryString(name) { 
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
		var r = window.location.search.substr(1).match(reg); 
		if (r != null) return unescape(r[2]); return null; 
	
	}  */
	var businessId = window.sessionStorage.getItem("bid");	
	var typeId = window.sessionStorage.getItem("tid");
	var key = sessionStorage.getItem("key");
	var id = window.sessionStorage.getItem("id"); 
	openViewDIv(businessId,typeId,key,id,this);
	};
	
	//禁用鼠标右键
	if (window.Event) 
		document.captureEvents(Event.MOUSEUP); 
	function nocontextmenu(){ 
		event.cancelBubble = true ;
		event.returnValue = false; 
		return false; 
	} 
	function norightclick(e){ 
		if (window.Event){ 
		if (e.which == 2 || e.which == 3) 
			return false; 
		} 
		else if (event.button == 2 || event.button == 3){ 
		   event.cancelBubble = true ;
		   event.returnValue = false; 
		   return false; 
		} 
	} 
	document.oncontextmenu = nocontextmenu; // for IE5+ 
	document.onmousedown = norightclick; // for  others  web browser
	
	
</script>
</html>