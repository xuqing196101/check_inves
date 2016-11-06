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

	<!-- Meta -->
	<%@include file="common/meta.jsp" %>
	<!-- head -->
	<%@include file="common/head.jsp" %>

</head>
<body>
   	<!-- Navbar -->
   	<%@include file="common/nav.jsp" %>
	<script type="text/javascript" language="javascript">   
		function iFrameHeight() {   
			var ifm= document.getElementById("iframepage");   
			var subWeb = document.frames ? document.frames["iframepage"].document : ifm.contentDocument;   
			if(ifm != null && subWeb != null) {
			   ifm.height = subWeb.body.scrollHeight;
			   /*ifm.width = subWeb.body.scrollWidth;*/
			}   
		}   
	</script>
	<!-- 后台管理内容开始-->
	<div >
		<iframe  frameborder="0" name="home" id="iframepage" scrolling="auto" marginheight="0"  width="100%" onLoad="iFrameHeight()" src="<%=basePath%>login/home.do"></iframe>
	</div>

	<!--底部代码开始-->
	<%@include file="common/footer.jsp" %>
</body>
</html>
