<%@ page language="java" import="java.util.*,bss.util.PropUtil" pageEncoding="UTF-8"%>

<%
  //生产环境
  String environment = PropUtil.getProperty("environment");
  //内外网
  String ipAddressType = PropUtil.getProperty("ipAddressType");
%>

<link href="${pageContext.request.contextPath}/public/backend/images/favicon.ico"  rel="shortcut icon" type="image/x-icon" />
<link href="${pageContext.request.contextPath}/public/backend/css/common.css" media="screen" rel="stylesheet" type="text/css">  
<link href="${pageContext.request.contextPath}/public/backend/css/btn.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css">
<!-- js -->
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/> 
<script>
	var globalPath = "${contextPath}";
</script>
<script src="${pageContext.request.contextPath}/public/backend/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/public/backend/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/public/backend/js/common.js"></script>
<script src="${pageContext.request.contextPath}/public/backend/js/browser.js"></script>
<script src="${pageContext.request.contextPath}/public/backend/js/jquery.ba-hashchange.min.js"></script>
<script src="${pageContext.request.contextPath}/public/backend/js/masterslider.js"></script>

<script src="${pageContext.request.contextPath}/public/backend/js/main-menu.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/extend/layer.ext.js"></script>
<script src="${pageContext.request.contextPath}/public/backend/js/page.js" ></script>
<!-- 时间插件 -->
<script src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>

<!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/public/common/respond.src.js"></script>
<![endif]-->
