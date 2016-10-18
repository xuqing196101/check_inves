<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'search.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/public/ztree/css/zTreeStyle.css"> 
<%-- <link rel="stylesheet" type="text/css" href="<%=basePath%>/public/ztree/css/demo.css"> --%>

<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.exedit.js"></script>
  </head>
  
  <body> <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a><li><a href="#">产品参数管理</a><li><a href="#">查询</a><li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
    <div class="container">
   <div class="col-md-3">
	 <div class="tag-box tag-box-v3 mt10">
	 <div><ul id="ztree" class="ztree "></ul></div>
	 </div>
	</div >
   <div>
   <span>产品名称：</span>
   <span><input class="mt10" type="text" /></span>
   <span><input type="button" class="btn" value="查询"/></span>
   <span><input type="button" class="btn" onclick="" value="目录查询"/></span>
        <table class="table table-bordered table-condensedb mt15">
            <thead>
                <tr>
                <th>序号</th>
                <th>产品名称</th>
                <th>管理部门</th>
                <th>更新时间</th>
                </tr>
            </thead>
        </table>
   </div>
   </div>
  </body>
</html>
