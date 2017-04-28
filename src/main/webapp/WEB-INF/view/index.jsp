<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
	<%@include file="common/meta.jsp" %>
	<%@include file="common/head.jsp" %>
	
</head>
<body>
    <%@include file="common/nav.jsp" %>
	<script type="text/javascript" language="javascript">   
     function getContentSize() {
		var he = document.documentElement.clientHeight;
	    var nav = $("#nav").height();
		ch = (he - nav - 5) + "px";
		document.getElementById("iframepage").style.height = ch;
	}
	window.onload = getContentSize;
	window.onresize = getContentSize;
   </script>
	<!-- 后台管理内容开始-->
<div>
	<iframe  frameborder="0" name="home" id="iframepage" scrolling="auto" marginheight="0"  width="100%" onLoad="getContentSize()" src="<%=basePath%>login/home.do"></iframe>
</div>

	<!--底部代码开始-->
	<%@include file="common/footer.jsp" %>
</body>
</html>
