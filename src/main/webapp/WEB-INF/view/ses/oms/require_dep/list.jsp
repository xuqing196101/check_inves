<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%@ include file="/WEB-INF/view/common.jsp"%>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/oms/purchase/list.js"></script>
</head>

<body>
   <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		<ul class="breadcrumb margin-left-0">
		  <li><a href="javascript:void(0)"> 首页</a></li>
		  <li><a href="javascript:void(0)">支撑系统</a></li>
		  <li><a href="javascript:void(0)">后台管理</a></li>
		  <li class="active"><a href="#">部门管理</a></li>
		 </ul>
	  </div>
   </div>
   
   <!-- 内容 -->
   <div class="container content height-350">
      
      <!-- tree -->
      <div class="col-md-3">
	    <div class="tag-box tag-box-v3">
		  <div>
		    <ul id="departTree" class="ztree" />
		  </div>
	     </div>
	   </div>
       
       <!-- right -->
	   <div class="tag-box tag-box-v4 col-md-9 col-xs-12 col-sm-9" > 
	     
	     <!--  button  -->
	     <div class="col-md-12 col-sm-12 col-xs-12">
	       <div class="pull-left">
		     <button class="btn btn-windows add" onclick="addTreeNode();" type="button">新增</button>
			 <button class="btn btn-windows edit" onclick="editTreeNode();" type="button">编辑</button>
			 <button class="btn btn-windows delete" onclick="delTreeNode();" type="submit">删除</button>
		   </div>
		 </div>
		 
		 <!-- 内容 -->
		 <div class="col-md-12 col-sm-12 col-xs-12 mt20 clear">
		 	<div id="treebody"></div>
		 </div>
	   </div>
    </div>
</body>
</html>
