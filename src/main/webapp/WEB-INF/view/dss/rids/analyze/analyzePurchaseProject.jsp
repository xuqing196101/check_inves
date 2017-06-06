<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/map.jsp"%>
		<script type="text/javascript" src= "${pageContext.request.contextPath}/js/dss/rids/analyze/analyzePurchaseProject.js"></script>
		<script type="text/javascript" src= "${pageContext.request.contextPath}/js/system/analyze/echartsTemplate.js"></script>
		<title>统计页面</title>
	</head>
<body>
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:void(0)">首页</a></li>
				<li><a href="javascript:void(0)">决策支持</a></li>
				<li><a href="javascript:void(0)">采购资源综合展示</a></li>
				<li class="active"><a
					href="${pageContext.request.contextPath}/resAnalyze/list.html">采购资源展示</a>
				<li class="active"><a href="javascript:void(0)">采购项目</a>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container content job-content">
		<button class="btn btn-windows back mb20" type="button"
			onclick="history.go(-1)">返回</button>
		<div class="m-chart-head text-center">
			<span class="mch-tit">全网已完成采购项目总金额：</span> <span class="mch-num">${ totalMoney } 万元</span>
		</div>
		<ul class="ul_list">
			<h2 class="count_flow">
				<span class="m-chart-icon"></span> 5种采购方式项目比例
			</h2>
			<div id="purProjectRatioByWay" class="analyze"></div>
		</ul>
		<ul class="ul_list">
			<h2 class="count_flow">
				<span class="m-chart-icon"></span> 各采购机构完成采购项目数量及总金额
			</h2>
			<div id="purProjectTotal" class="analyze"></div>
		</ul>
	</div>
</body>
</html>
