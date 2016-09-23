<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>
    <script language="javascript" type="text/javascript"
    src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
</head>
<body>

<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">业务管理</a></li><li><a href="#">订单中心</a></li><li class="active"><a href="#">修改订单</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 修改订列表开始-->
   <div class="container">
   <form action="<%=basePath %>SupplierExtracts/listSupplier.do" method="post">
   <div>
   <div class="headline-v2">
   <h2>抽取条件设置</h2>
   </div>
   <ul class="list-unstyled list-flow p0_20">
   			<input class="span2" name="id" type="hidden" value="${StationMessage.id}">
    		 <li class="col-md-6 p0 " >
			   <span class="">抽取项目名称：</span>
			   <div class="input-append">
		        <input class="span2 w350 " name="title" type="text" value="${StationMessage.title}">
		       </div>
			 </li>
		     <li class="col-md-12  p0 " >
			   <span class="">抽取项目编号：</span>
		      <input class="span2 w350 " name="title" type="text" value="${StationMessage.title}">
			 </li> 
			   <li class="col-md-12  p0 " >
               <span class="">抽取时间：</span>
              <input class="span2 w350 " name="title" type="text"  onClick="WdatePicker()">
             </li> 
               <li class="col-md-12  p0 " >
               <span class="">抽取数量：</span>
              <input class="span2 w350 " name="title" type="text" >
             </li> 
              <li class="col-md-12  p0 " >
                <span class="">抽取记录：</span>
               <input class="span2 w350 " name="title" type="text" value="${StationMessage.title}">
             </li> 
   </ul>
  </div>   
  <div  class="col-md-12">
   	<div class="fl padding-10">
    	<button class="btn btn-windows reset" type="submit">抽取供应商</button>
    	<button class="btn btn-windows git" onclick="history.go(-1)" type="button">返回</button>
	</div>
  </div>
  </form>
 </div>
</body>
</html>
