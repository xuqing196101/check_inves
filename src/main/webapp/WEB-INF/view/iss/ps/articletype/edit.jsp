<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">  
    <title></title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=basePath%>public/ZHQ/js/jquery.min.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->	
	<script type="text/javascript">    
    $(function(){ 
        $("#parent").val("${articletype.parent.id}");
        $("#oldName").val($("#typeName").val());
    }); 
	</script>
  </head>
  <body>
  
  
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a href="#">栏目管理</a></li><li class="active"><a href="#">栏目修改</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 新增页面开始 -->
     <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
    <form action="<%=basePath %>articletype/update.html" method="post">  
    <div>
	    <div class="headline-v2">
	   		<h2>修改栏目</h2>
	   </div>
	    <input class="span2" name ="articletypeId" type="hidden" value = '${articletype.id}'>
	   <ul class="list-unstyled list-flow p0_20">
	   		  
	   		   <li class="col-md-6  p0 ">
			   <span class="fl"><div class="red star_red">*</div>栏目名称：</span>
			   <div class="input-append">
		        <input class="span2" id="typeName" type="text" name="name" value = '${articletype.name}'>
		        <div class="validate">${ERR_name}</div>
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			<li class="col-md-12  p0 ">	  	 			
				<span class="fl">栏目介绍：</span>
				<div class="col-md-12 mt5 fn pl200 pwr9">
				<textarea  class="text_area col-md-12" name="describe">${articletype.describe}</textarea>		
				</div>			
	  	 	</li>
	  	 </ul>
	</div>  	
	<!-- 底部按钮 -->			          
  <div  class="col-md-12 tc mt20">
    <button class="btn btn-windows save" type="submit">更新</button>
    <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
  </div>
     </form>
     </div>
     </div>
  </body>
</html>
