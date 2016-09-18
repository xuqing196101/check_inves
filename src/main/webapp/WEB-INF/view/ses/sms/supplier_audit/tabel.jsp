<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>
	<div class="container clear margin-top-30">
   	<div class="col-md-12 tab-v2 job-content">
	    <ul class="nav nav-tabs bgdd">
		    <li class="active"><a class="s_news f18" href="${pageContext.request.contextPath}/supplierAudit/essential.html">基本信息</a></li>
		    <li class="active"><a class="s_news f18" href="${pageContext.request.contextPath}/supplierAudit/financial.html">财务信息</a></li>
	     	<li class="active"><a class="s_news f18" href="${pageContext.request.contextPath}/supplierAudit/shareholder.html">股东信息</a></li>
	     	<li class="active"><a class="s_news f18" href="${pageContext.request.contextPath}/supplierAudit/materialProduction.html">物资-生产型专业信息</a></li>
		     <li class="active"><a class="s_news f18">物资-销售型专业信息</a></li>
		    <li class="active"><a class="s_news f18">工程-专业信息</a></li>
		    <li class="active"><a class="s_news f18">服务-专业信息</a></li>
		    <li class="active"><a class="s_news f18">品目信息</a></li>
		    <li class="active"><a class="s_news f18">产品信息</a></li>
		    <li class="active"><a class="s_news f18">审核汇总</a></li>
	    </ul>
	</div>
   </div>
</body>
</html>
