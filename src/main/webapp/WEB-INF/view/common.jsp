<%@ page language="java" import="java.util.*,bss.util.PropUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link href="${pageContext.request.contextPath}/public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">	
<link href="${pageContext.request.contextPath}/public/backend/css/unify.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/backend/css/global.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/backend/css/btn.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/select2/css/select2.min.css"  rel="stylesheet">


<!-- js -->
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/> 
<script>
	var globalPath = "${contextPath}";
</script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/browser.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.ba-hashchange.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/common.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/masterslider.min.js"></script>
<!-- 待删除的 -->
<script src="${pageContext.request.contextPath}/public/accordion/SpryAccordion.js"></script>

<script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
<script src="${pageContext.request.contextPath}/public/select2/js/select2.min.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHH/js/main-menu.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/extend/layer.ext.js"></script>