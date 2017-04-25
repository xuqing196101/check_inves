<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="/WEB-INF/view/common.jsp" %>
	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
	<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
	<title>产品信息 </title>
</head>
<body>
	
	<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0)"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">供应商后台管理</a>
          </li>
          <li>
            <a href="javascript:void(0)">产品库管理</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">产品信息</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <!-- 新增模板开始-->
    <div class="container container_box">
    	<button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
    	<!-- 商品基本信息 -->
    	<div>
	    	<%@ include file="/WEB-INF/view/ses/sms/supplier_product_lib/productBasicCommon.jsp" %>
		</div> 
		
		<!-- 加载审核信息 -->
		<c:if test="${ not empty productCheckRecord.advice }">
			  <div>
			  	<c:if test="${ not empty smsProductInfo.smsProductArguments }">
			    	<h2 class="count_flow"><i>4</i>审核意见</h2>
			    </c:if>
			    <c:if test="${ empty smsProductInfo.smsProductArguments }">
			    	<h2 class="count_flow"><i>3</i>审核意见</h2>
			    </c:if>
				<%@ include file="/WEB-INF/view/ses/sms/supplier_product_lib/check/productCheckCommon.jsp" %>
			  </div> 
		</c:if>
    </div>
</body>
</html>