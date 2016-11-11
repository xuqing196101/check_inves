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
   <div class="container bggrey border1 mt20">
   	   <sf:form action="${pageContext.request.contextPath}/dictionaryData/save.html" method="post" modelAttribute="dd">
		   <div>
			   		<h2 class="count_flow"><i>1</i>修改订单</h2>
			   <input type="hidden" name="kind" id="k"  value="${kind }">
			   <ul class="list-unstyled list-flow ul_list">
			   	 	<li class="col-md-6 p0">
					   	<span class="span2"><div class="fr">编码：</div><div class="red">*</div></span>
					   	<div class="input-append pr">
					        <input class="span2" name="code" value="${dd.code }" maxlength="40" type="text">
					        <span class="add-on">i</span>
					        <div class="b f14 red tip pa l260"><sf:errors path="code"/></div>
					        <div class="b f14 ml10 red hand">${exist}</div>
				       	</div>
				 	</li>
				 	<li class="col-md-6 p0">
					   	<span class="span2"><div class="fr">名称：</div><div class="red">*</div></span>
					   	<div class="input-append pr">
					        <input class="span2" name="name" value="${dd.name }"  type="text">
					        <span class="add-on">i</span>
					        <div class="b f14 red tip pa l260"><sf:errors path="name"/></div>
				       	</div>
				 	</li>
				 	<li class="col-md-12 p0">
					   	<span class="span2">描述：</span>
					   	<div class="col-md-12 pl200 fn mt5 pwr9">
				        	<textarea class="text_area col-md-12 " name="description"  title="" placeholder="请输入100字以内中文描述">${dd.description }</textarea>
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
