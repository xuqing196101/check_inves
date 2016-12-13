<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<title>项目管理</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript"></script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="#"> 首页</a>
					</li>
					<li>
						<a href="#">我的项目</a>
					</li>
					<li>
						<a href="#">标书管理</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container clear mt20">
			<div class="list-unstyled padding-10 breadcrumbs-v3">
				<input id="projectId" name="projectId" value="${project.id }" type="hidden" />
				<span>
					  <a href="${pageContext.request.contextPath}/mulQuo/openBid.html" class="img-v1">开标一览表</a>
					  <span class="green_link">→</span>
				</span>
				<span>
					  <a href="${pageContext.request.contextPath}/mulQuo/priceBuild.html" class="img-v1">价格构成表</a>
					  <span class="green_link">→</span>
				</span>
				<span>
					  <a href="${pageContext.request.contextPath}/mulQuo/priceView.html" class="img-v1">明细表</a><!--货物材料、部件、工具价格明细表  -->
					  <span class="green_link">→</span>
				</span>
				<span>
		    	<c:if test="${std.bidFinish == 0}">
				  <a href="${pageContext.request.contextPath}/supplierProject/bidDocument.html?projectId=${project.id}" class="img-v2 orange_link">编制标书</a>
				  <span class="green_link">→</span>
				</c:if>
				<c:if test="${std.bidFinish != 0}">
					<a href="${pageContext.request.contextPath}/supplierProject/bidDocument.html?projectId=${project.id}" class="img-v1">编制标书</a>
					<span class="">→</span>
				</c:if>
				</span>
				<span>
				<c:if test="${std.bidFinish == 1}">
				  <a href="${pageContext.request.contextPath}/supplierProject/toBindingIndex.html?projectId=${project.id}" class="img-v2 orange_link">绑定指标</a>
				  <span class="green_link">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 2 || std.bidFinish == 3 || std.bidFinish == 4}">
					<a href="${pageContext.request.contextPath}/supplierProject/toBindingIndex.html?projectId=${project.id}" class="img-v1">绑定指标</a>
					<span class="">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 0}">
					<a href="javascript:void(0);" onclick="tishi('请先编制保存标书到服务器');" class="img-v3">绑定指标</a>
					<span class="">→</span>
				</c:if>
				</span>
				<span>
				<c:if test="${std.bidFinish == 2 }">
				  <a href="${pageContext.request.contextPath}/mulQuo/quoteHistory.html?projectId=${project.id}"  class="img-v2 orange_link">填写报价</a>
				  <span class="green_link">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 0}">
					<a href="javascript:void(0);" onclick="tishi('请先编制保存标书到服务器');" class="img-v3">填写报价</a>
					<span class="">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 1}">
					<a href="javascript:void(0);" onclick="tishi('请先绑定指标');" class="img-v3">填写报价</a>
					<span class="">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 3 || std.bidFinish == 4}">
					<a href="${pageContext.request.contextPath}/mulQuo/quoteHistory.html?projectId=${project.id}" class="img-v1">填写报价</a>
					<span class="">→</span>
				</c:if>
				</span>
				<span>
		    	<c:if test="${std.bidFinish == 3 }">
			  		<a href="${pageContext.request.contextPath}/supplierProject/result.html?projectId=${project.id}" class="img-v2  orange_link">完成</a>
		    	</c:if>
		    	<c:if test="${std.bidFinish == 0}">
				  <a href="javascript:void(0);" onclick="tishi('请先编制保存标书到服务器');" class="img-v3">完成</a>
				</c:if>
				<c:if test="${std.bidFinish == 1}">
				  <a href="javascript:void(0);" onclick="tishi('请先绑定指标');" class="img-v3">完成</a>
				</c:if>
				<c:if test="${std.bidFinish == 2}">
				  <a href="javascript:void(0);" onclick="tishi('请先填写报价');" class="img-v3">完成</a>
				</c:if>
				<c:if test="${std.bidFinish == 4}">
				  <a href="${pageContext.request.contextPath}/supplierProject/result.html?projectId=${project.id}"  class="img-v1">完成</a>
				</c:if>
			</span>
			</div>
		</div>
		<!-- 开标一览表-->
		<div class="container">
			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover table-striped">
					<tr>
						<td rowspan="2">货物名称</td>
						<td rowspan="2">规格型号</td>
						<td rowspan="2">计量单位</td>
						<td rowspan="2">数量</td>
						<td rowspan="2">总价</td>
						<td colspan="13" class="tc">价格组成</td>
					</tr>
					<tr>
						<td>单价</td>
						<td>直接<br/>材料费</td>
						<td>外购<br/>成件费</td>
						<td>燃料及<br/>动力费</td>
						<td>直接<br/>人工费</td>
						
						<td>废品<br/>损失费</td>
						<td>管理<br/>费用</td>
						<td>利润</td>
						<td>税金</td>
						
						<td>备件<br/>工具费</td>
						<td>安装<br/>调试费</td>
						<td>技术<br/>服务费</td>
						<td>运杂费</td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</table>
			</div>
		</div>
	</body>

</html>