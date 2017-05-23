<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>投标结果</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
</head>

<body onload="OpenFile()">
	<div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">我的项目</a></li><li><a href="javascript:void(0);">标书管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
    </div>
    <div class="container clear mt20">
   		<div class="list-unstyled padding-10 breadcrumbs-v3">
   			<span>
				  <a href="${pageContext.request.contextPath}/mulQuo/openBid.html?projectId=${project.id}" class="img-v1">开标一览表</a>
				  <span class="green_link">→</span>
			</span>
			<span>
				  <a href="${pageContext.request.contextPath}/mulQuo/priceBuild.html?projectId=${project.id}" class="img-v1">价格构成表</a>
				  <span class="green_link">→</span>
			</span>
			<span>
				  <a href="${pageContext.request.contextPath}/mulQuo/priceView.html?projectId=${project.id}" class="img-v1">明细表</a><!--货物材料、部件、工具价格明细表  -->
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
    <div class="container content height-350 pt0 mt20">
    	
    	投标完成，请等待结果。。
   </div>
</body>
</html>
