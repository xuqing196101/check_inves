<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
	<script type="text/javascript" src="<%=basePath%>public/lodop/LodopFuncs.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
</head>
<body>

<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">用户管理</a></li><li class="active"><a href="#">查看用户</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 修改订列表开始-->
   <div class="container">
   <form action="" method="post">
   <div>
   <div class="headline-v2">
   <h2>查看用户</h2>
   </div>
   <ul class="list-unstyled list-flow p0_20">
	   	<li class="col-md-6 p0">
		   <span class="">用户名：</span>
		   <div class="input-append">
	        <input class="span2" name="loginName" readonly type="text" readonly="readonly" value="${user.loginName}">
	        <span class="add-on">i</span>
	       </div>
		 </li>
	     <li class="col-md-6  p0 ">
		   <span class="">真实姓名：</span>
		   <div class="input-append">
	        <input class="span2" name="relName" readonly type="text" value="${user.relName}">
	        <span class="add-on">i</span>
	       </div>
		 </li>
		 <li class="col-md-6 p0">
		   <span class="">性别：</span>
		   <div class="input-append">
		   	<c:if test="${'M' eq user.gender}">
		   		<input class="span2" class="span2" type="text" readonly value="男">
		   	</c:if>
		   	<c:if test="${'F' eq user.gender}">
				<input class="span2" class="span2" type="text" readonly value="女">
			</c:if>
			<c:if test="${'' eq user.gender || user.gender == null}">
				<input class="span2" class="span2" type="text" readonly value="">
			</c:if>
	        <span class="add-on">i</span>
	       </div>
		 </li>
		 <li class="col-md-6  p0 ">
		   <span class="">创建日期：</span>
		   <div class="input-append">
	        <input class="span2" name="mobile" readonly value="<fmt:formatDate value='${user.createdAt}' pattern='yyyy-MM-dd  HH:mm:ss'/>" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li>
		 <li class="col-md-6  p0 ">
		   <span class="">修改日期：</span>
		   <div class="input-append">
	        <input class="span2" name="mobile" readonly value="<fmt:formatDate value='${user.updatedAt}' pattern='yyyy-MM-dd  HH:mm:ss'/>" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li>
	     <li class="col-md-6  p0 ">
		   <span class="">手机：</span>
		   <div class="input-append">
	        <input class="span2" name="mobile" readonly value="${user.mobile }" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li>
	     <li class="col-md-6 p0">
		   <span class="">邮箱：</span>
		   <div class="input-append">
	        <input class="span2" name="email" readonly value="${user.email }" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li>
	     <li class="col-md-6  p0 ">
		   <span class="">职务：</span>
		   <div class="input-append">
	        <input class="span2" name="duties" readonly value="${user.duties }" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li>
		 <li class="col-md-6 p0">
		   <span class="">类型：</span>
		   <div class="input-append">
	        <c:if test="${0 eq user.typeName}">
		   		<input class="span2" class="span2" type="text" readonly value="采购管理人员">
		   	</c:if>
		   	<c:if test="${1 eq user.typeName}">
				<input class="span2" class="span2" type="text" readonly value="采购机构人员">
			</c:if>
			<c:if test="${2 eq user.typeName}">
		   		<input class="span2" class="span2" type="text" readonly value="需求人员">
		   	</c:if>
		   	<c:if test="${3 eq user.typeName}">
				<input class="span2" class="span2" type="text" readonly value="其他人员">
			</c:if>
			<c:if test="${4 eq user.typeName}">
		   		<input class="span2" class="span2" type="text" readonly value="供应商">
		   	</c:if>
		   	<c:if test="${5 eq user.typeName}">
				<input class="span2" class="span2" type="text" readonly value="专家">
			</c:if>
			<c:if test="${6 eq user.typeName}">
				<input class="span2" class="span2" type="text" readonly value="进口供应商">
			</c:if>
			<c:if test="${7 eq user.typeName}">
				<input class="span2" class="span2" type="text" readonly value="进口代理商">
			</c:if>
			<c:if test="${'' eq user.typeName || user.typeName == null}">
				<input class="span2" class="span2" type="text" readonly value="">
			</c:if>
	        <span class="add-on">i</span>
	       </div>
		 </li>
		 <li class="col-md-6  p0 ">
		   <span class="">所属机构：</span>
		   <div class="input-append">
		   	<input class="span2" type="text" readonly value="${user.org.name }">
	        <span class="add-on">i</span>
	       </div>
		 </li>
	     <li class="col-md-6  p0 ">
		   <span class="">联系电话：</span>
		   <div class="input-append">
	        <input class="span2" name="telephone" readonly="readonly" type="text" value="${user.telephone}">
	        <span class="add-on">i</span>
	       </div>
		 </li> 
		 <li class="col-md-6 p0">
		   <span class="">角色：</span>
		   <div class="input-append">
		   	<input class="span2"  type="text" readonly="readonly" value="${roleName}">
	        <span class="add-on">i</span>
	       </div>
		 </li>
		 <li class="col-md-12 p0">
		   <span class="fl">详细地址：</span>
		   <div class="col-md-12 pl200 fn mt5 pwr9">
	        <textarea class="text_area col-md-12 " address="address" readonly="readonly" title="" placeholder="">${user.address}</textarea>
	       </div>
		 </li>
			 
   </ul>
  </div> 
   
  <div class="col-md-12 tc mt20" >
    	<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
  </div>
  </form>
 </div>
</body>
</html>
