<%@ page language="java" pageEncoding="UTF-8"%>
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
		   <li><a href="#"> 首页</a></li><li><a href="#">我的项目</a></li><li><a href="#">标书管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
    </div>
    <div class="container clear mt20">
   		<div class="list-unstyled padding-10 breadcrumbs-v3">
		    <span>
			  <a href="${pageContext.request.contextPath}/supplierProject/bidDocument.html?projectId=${project.id}" class="img-v1 green_link">编制标书</a>
			  <span class="green_link">→</span>
			</span>
			<span>
			  <a href="${pageContext.request.contextPath}/supplierProject/toBindingIndex.html?projectId==${project.id}" class="img-v1 green_link">绑定指标</a>
			  <span class="green_link">→</span>
			</span>
			<span>
			  <a href="javascript:void(0);"  class="img-v1 green_link">填写报价</a>
			  <span class="green_link">→</span>
			</span>
		    <span>
			  <a href="javascript:void(0);" class="img-v3">完成</a>
			</span>
   		</div>
  	</div>
    
    <div class="col-md-12 pl20 mt10">
	    <button class="btn btn-windows add" type="button" onclick="add()">投标完成</button>
   </div>
</body>
</html>
