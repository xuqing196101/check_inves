<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
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
</head>

<script type="text/javascript">
	function back(){
		var kind = $("#k").val();
		window.location.href = '${pageContext.request.contextPath}/dictionaryData/list.html?page=1&kind='+kind;
	}
</script>
<body>
   <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
	   <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   	<li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">数据字典</a></li><li class="active"><a href="#">增加数据字典</a></li>
		   </ul>
		   <div class="clear"></div>
	   </div>
   </div>
   <div class="container container_box">
   	   <sf:form action="${pageContext.request.contextPath}/dictionaryData/save.html" method="post" modelAttribute="dictionaryData">
		   <div>
			   <h2 class="count_flow">添加数据</h2>
			   <input type="hidden" name="kind" id="k"  value="${kind }">
			   <ul class="ul_list">
			   	 	<li class="col-md-3 margin-0 padding-0">
			   	 		<span class="col-md-12 padding-left-5"><span class="red">*</span>编码</span>
					   	<div class="input-append">
					        <input class="span5" name="code" value="${dd.code }" maxlength="40" type="text">
					        <span class="add-on">i</span>
					        <div class="cue"><sf:errors path="code"/></div>
					        <div class="cue">${exist}</div>
				       	</div>
				 	</li>
				 	<li class="col-md-3 margin-0 padding-0">
					   	<span class="col-md-12 padding-left-5"><span class="red">*</span>名称</span>
					   	<div class="input-append">
					        <input class="span5" name="name" value="${dd.name }"  type="text">
					        <span class="add-on">i</span>
					        <div class="cue"><sf:errors path="name"/></div>
				       	</div>
				 	</li>
				 	<li class="col-md-11 margin-0 padding-0">
			 	   		<span class="col-md-12 padding-left-5">描述</span>
			 	   		<div class="">
			        		<textarea class="col-md-12" style="height:130px" name="description"  title="" placeholder="请输入100字以内中文描述">${dd.description }</textarea>
			      		</div>
				 	</li>
			   	</ul>
		   </div> 
	       <div class="col-md-12 tc mt20" >
			   <button class="btn btn-windows save"  type="submit">保存</button>
			   <button class="btn btn-windows back" onclick="back()" type="button">返回</button>
       	   </div>
  	   </sf:form>
   </div>
</body>
</html>
