<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/public/ZHH/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"  src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
<script src="<%=basePath%>public/layer/layer.js"></script>
</head>
<body>
<!-- 表格开始-->  
   <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
        <table class="table table-striped table-bordered table-hover tc">
		<thead>
		<tr>
		  <th class="w50 info">序号</th>
		  <th class="info">名称</th>
		  <th class="info">单位</th>
		  <th class="info">数量</th>
		  <th class="info">预算</th>
		</tr>
		</thead>
		<c:forEach items="${listPd }" var="lpd" varStatus="vs">
			<tr>
			    <td>${vs.index+1 }</td>
				<td>${lpd.goodsName }</td>
				<td>${lpd.item }</td>
				<td>${lpd.purchaseCount }</td>
				<td>${lpd.budget }</td>
			</tr>
		</c:forEach> 
        </table>
        
        <h4>报价供应商</h4>
        <table class="table table-striped table-bordered table-hover tc">
		<thead>
		<tr>
		  <th class="w50 info">序号</th>
		  <th class="info">供应商名称</th>
		  <th class="info">联系人</th>
		  <th class="info">联系电话</th>
		  <th class="info">投标文件状态</th>
		  <th class="info">揭秘状态</th>
		</tr>
		</thead>
		<c:forEach items="${listSupplier }" var="ls" varStatus="vs">
			<tr>
			    <td>${vs.index+1 }</td>
				<td>${ls.name }</td>
				<td>${ls.concatName }</td>
				<td>${ls.mobile }</td>
				<td></td>
				<td></td>
			</tr>
		</c:forEach> 
        </table>
     </div>
   
   </div>
</body>
</html>
