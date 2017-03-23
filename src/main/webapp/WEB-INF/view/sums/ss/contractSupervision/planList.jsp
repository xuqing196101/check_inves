<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >
<html>
  <head>
  	<jsp:include page="/WEB-INF/view/common.jsp"/>
    <title>项目列表</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="${pageContext.request.contextPath}/public/easyui/jquery.easyui.min.js"></script>
	<link href="${pageContext.request.contextPath}/public/easyui/themes/icon.css" media="screen" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/public/easyui/themes/default/easyui.css" media="screen" rel="stylesheet" type="text/css">
  <script type="text/javascript">
  function view(id,type){
      window.location.href = "${pageContext.request.contextPath}/projectSupervision/view.html?id="+id+"&type="+type;
    }
    </script>
  </head>
  
  <body>
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">业务监管系统</a></li><li><a href="#">采购业务监督</a></li><li><a href="#">采购合同监督</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <div class="container">
   <div class="headline-v2">
        <h2>计划列表
	    </h2>
   </div> 
<!-- 项目戳开始 -->
    
  <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
        <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
      </div>
   </div>
   
</body>
</html>
