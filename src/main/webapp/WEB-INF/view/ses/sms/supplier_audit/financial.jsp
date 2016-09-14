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
<title>财务信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/common.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/line-icons.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/application.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/header-v4.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/footer-v2.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/img-hover.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/page_job.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/shop.style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/upload/upload.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
</head>
<body>
	<!--页标签-->
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
   <!-- 表格开始-->
  <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
     	<h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;财务状况登记表</h3>
        <table class="table table-bordered table-condensed">
			<thead>
				<tr>
				  <th class="info w50">序号</th>
				  <th class="info">会计事务所名称</th>
				  <th class="info">事务所联系电话</th>
				  <th class="info">审计人姓名</th>
				  <th class="info">指标</th>
				  <th class="info">资产总额</th>
				  <th class="info">负债总额</th>
				  <th class="info">净资产总额</th>
				  <th class="info">营业收入</th>
				</tr>
				</thead>
				<c:forEach items="${expertList}" var="e" >
					<tr>
					  <td class="tc w50"></td>
					  <td class="tc"></td>
					  <td class="tc"></td>
					  <td class="tc"></td>
					  <td class="tc"></td>
					  <td class="tc"></td>
					  <td class="tc"></td>
					  <td class="tc"></td>
					  <td class="tc"></td>
					</tr>
				</c:forEach>
		</table>
   </div>
 </div>
	
	
<!--底部代码开始-->

</body>
</html>
