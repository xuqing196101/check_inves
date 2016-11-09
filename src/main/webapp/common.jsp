<%@ page language="java" import="java.util.*,bss.util.PropUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link href="${pageContext.request.contextPath}/public/ZHQ/css/common.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHQ/css/line-icons.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHQ/css/application.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHQ/css/header-v4.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHQ/css/footer-v2.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHQ/css/page_job.css" media="screen" rel="stylesheet">


<!-- js -->
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/> 
<script>
	var globalPath = "${contextPath}";
</script>
<script src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHQ/js/jquery_ujs.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${ pageContext.request.contextPath }/public/laypage-v1.3/laypage/laypage.js"></script>