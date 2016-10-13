<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
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
	<link href="<%=basePath%>public/supplier/css/supplieragents.css" media="screen" rel="stylesheet">
</head>
<script type="text/javascript">
	function cheClick(){
		var roleIds="";
		var roleNames="";
		$('input[name="chkItem"]:checked').each(function(){
			var idName=$(this).val();
			var arr=idName.split(";");
			roleIds+=arr[0]+",";
			roleNames+=arr[1]+",";
		});
		$("#roleId").val(roleIds.substr(0,roleIds.length-1));
		$("#roleName").val(roleNames.substr(0,roleNames.length-1));
	}
</script>
<body>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑环境</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">用户管理</a></li><li class="active"><a href="#">增加用户</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
<!-- 修改订列表开始-->
   <div class="container">
   <div>
   <div class="headline-v2">
   <h2>查看站内消息</h2>
   </div>
   <ul class="list-unstyled list-flow p0_20">
   			<input class="span2" name="id" type="hidden" value="${StationMessage.id}">
    		 <li class="col-md-12 p0 " >
			   <span class="">标题：</span>
			   <div class="fl w60p">
		        <input class="col-md-12" disabled="disabled" name="title" type="text" value="${StationMessage.title}">
		       </div>
			 </li>
		     <li class="col-md-12  p0 " >
			   <span class="">内容：</span>
			   <div class="fl w60p">
		        <textarea class="col-md-12 h100"  disabled="disabled"  cols="3" rows="100" name="context" >${StationMessage.content}</textarea>
			   </div>
			 </li> 
   </ul>
  </div> 
  <div  class="col-md-12 tc clear">
  
    	  <c:choose>
				 	<c:when test="${StationMessage.isPublish==0}">
				 		<a class="btn btn-windows apply" href="updateSMIsIssuance.do?id=${StationMessage.id}&&isIssuance=1">发布</a>
				 	</c:when>
				 	<c:otherwise>
				 		<a class="btn btn-windows withdraw" href="updateSMIsIssuance.do?id=${StationMessage.id}&&isIssuance=0">撤回</a>
				 	</c:otherwise>
				 </c:choose>
    	<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
  </div>
 </div>
</body>
</html>
