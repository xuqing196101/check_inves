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
<script type="text/javascript" src="<%=basePath%>public/lodop/LodopFuncs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
   for(var i=0;i<document.getElementById("type").options.length;i++)
    {
        if(document.getElementById("type").options[i].value == '${ir.type}')
        {
            document.getElementById("type").options[i].selected=true;
            break;
        }
    }
     for(var i=0;i<document.getElementById("status").options.length;i++)
    {
        if(document.getElementById("status").options[i].value == '${ir.status}')
        {
            document.getElementById("status").options[i].selected=true;
            break;
        }
    }
});
</script>
</head>
<body>


<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		  <li><a href="#"> 首页</a></li><li><a href="#">进口代理商</a></li><li><a href="#">进口代理商管理</a></li><li class="active"><a href="#">进口代理商修改</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 修改订列表开始-->
   <div class="container">
   <form action="<%= basePath%>importRecommend/update.html" method="post">
   <div>
   <div class="headline-v2">
   <h2>进口代理商修改</h2>
   </div>
   <ul class="list-unstyled list-flow p0_20">
     <li class="col-md-6 p0">
	   <span class="">登录名：</span>
	   <div class="input-append">
        <input class="span2" id="loginName" name="loginName" value="${ir.loginName }" type="text">
        <input class="span2" id="id" name="id" value="${ir.id }" type="hidden">
        <span class="add-on">i</span>
       </div>
	 </li>
     <li class="col-md-6  p0 ">
	   <span class="">登录密码：</span>
	   <div class="input-append">
        <input class="span2" id="password" name="password" value="${ir.password }" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>
     <li class="col-md-6  p0 ">
	   <span class="">企业名称：</span>
	   <div class="input-append">
        <input class="span2" id="name" name="name" value="${ir.name }" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
	  <li class="col-md-6  p0 ">
	   <span class="">企业地址：</span>
	   <div class="input-append">
        <input class="span2" id="address" name="address" value="${ir.address }" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
	 <li class="col-md-6  p0 ">
	   <span class="">法定代表人：</span>
	   <div class="input-append">
        <input class="span2" id="legalName" name="legalName" value="${ir.legalName }" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
	 <li class="col-md-6  p0 ">
	   <span class="">推荐单位：</span>
	   <div class="input-append">
        <input class="span2" id="recommend" name="recommend" value="${ir.recommendDep }" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
	 <li class="col-md-6 p0 ">
	   <span class=" ">进口代理商类型：</span>
         <div class="select_common mb10">
         <select class="w250" name="type" id="type">
           <option value="">请选择</option>
           <option value="1">正式代理商</option>
           <option value="2">临时代理商</option>
           <option value="3">通用代理商</option>
           <option value="4">专用代理商</option>
         </select>
         </div>
	 </li>
   </ul>
   </div>
    	  <div class="col-md-12 tc mt20" >
			   <button class="btn btn-windows save"  type="submit">保存</button>
			   <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
       	   </div>
   </form>
  </div> 
</body>
</html>
