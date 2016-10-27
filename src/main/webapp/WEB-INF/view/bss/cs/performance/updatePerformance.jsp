<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<script type="text/javascript">
	$(function(){
		var value = "${performance.completedStatus}";
		$("#completedStatus").val(value);
	});
</script>
</head>
<body>


<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">履约情况管理</a></li><li><a href="#">履约情况修改</a></li></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 修改订列表开始-->

	<div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
     <div>
   <div class="headline-v2">
   		<h2>修改履约情况</h2>
   </div>
  	<form action="<%=basePath %>performance/updatePerformance.html" method="post" id="form">
  	<input type="hidden" name="contractId" value="${performance.contractId}"/>
  	<input type="hideen" name="id" value="${performance.id}"/>
  		<ul class="list-unstyled list-flow p0_20">
  	<li class="col-md-6  p0 ">
	   <span class="">合同草稿签订时间：</span>
	   <div class="input-append">
	   <input id="draftSignedAt" name="draftSignedAt" value="<fmt:formatDate value="${performance.draftSignedAt}" pattern="yyyy-MM-dd HH:mm:ss" />" type="text" class="Wdate mb0 w220" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2008-03-08 11:30:00',lang:'zh-cn'})"/>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">正式合同签订时间：</span>
	   <div class="input-append">
	   <input id="formalSignedAt" name="formalSignedAt" value="<fmt:formatDate value="${performance.formalSignedAt}" pattern="yyyy-MM-dd HH:mm:ss" />" type="text" class="Wdate mb0 w220" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2008-03-08 11:30:00',lang:'zh-cn'})"/>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">交付日期：</span>
	   <div class="input-append">
	   <input id="delivery" name="delivery" value="<fmt:formatDate value="${performance.delivery}" pattern="yyyy-MM-dd HH:mm:ss" />" type="text" class="Wdate mb0 w220" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2008-03-08 11:30:00',lang:'zh-cn'})"/>
       </div>
	 </li> 
	<li class="col-md-6 p0">
	   <span class="">交货进度：</span>
	   <div class="input-append">
        <input class="span2" id="deliverySchedule" value="${performance.deliverySchedule }" type="text" name="deliverySchedule">
       </div>
	 </li>
     <li class="col-md-6  p0 ">
	   <span class="">资金支付百分比：</span>
	   <div class="input-append">
        <input class="span2" id="fundsPaid" value="${performance.fundsPaid}" type="text" name="fundsPaid">
       </div>
	 </li>
     <li class="col-md-6  p0 ">
	   <span class="">质量检验结果：</span>
	   <div class="input-append">
        <input class="span2" id="checkMass" value="${performance.checkMass}" name="checkMass" type="text">
       </div>
	 </li>
     <li class="col-md-6  p0 ">
	   <span class="">合同执行状态：</span>
        <select name="completedStatus" id="completedStatus">
        	<option></option>
        	<option value="0">执行中</option>
        	<option value="1">终止</option>
        	<option value="2">变更</option>
        	<option value="3">完成</option>
        </select>
	 </li> 
  	</ul>
  	<!-- 按钮 -->
	<div class="padding-top-10 clear">
		<div class="mt20 tc">
		 	<input type="submit" value="保存" class="btn btn-windows save"/>
   			<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
 		</div>
 	</div>  	
  	</form>
  	</div>
  </div>
 </div>
</body>
</html>
