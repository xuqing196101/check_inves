<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>地区管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  
<%--   <link href="<%=basePath%>public/ZHH/css/zTreeStyle.css" media="screen" rel="stylesheet" type="text/css">
 <script src="<%=request.getContextPath()%>public/ztree/jquery.ztree.core.js"></script>
 <script src="<%=request.getContextPath()%>public/ztree/jquery-1.4.4.min.js"></script>
 <script src="<%=basePath%>public/ZHH/js/jquery.min.js"></script> --%>
 <script src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
 <link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/public/ztree/css/zTreeStyle.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/public/ztree/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/jquery.ztree.excheck.js"></script>
	
 <script type="text/javascript">
    $(function(){
      var datas;
    
	  
	  
    /*树的设置*/
		var setting={
			async:{
				autoParam:["id"],
				enable:true,
				url:"<%=basePath%>area/listByOne.do",
				dataType:"json",
				type:"post",
			},
			data:{
				simpleData:{
					enable:true,
					idKey:"id",
					pId:"pId",
					rootPId:-1,
				}
			},
			callback:{
				onClick:zTreeOnClick
			}
		};
		var treeObj=$.fn.zTree.init($("#ztree"),setting,datas);
		treeObj.expandAll(false);
    });
 	

 	function zTreeOnClick(event,treeId,treeNode){
		 alert(treeNode.tId + ", " + treeNode.name);
	};
 	
 
 </script>
 </head>
  <body>
 	
   <div id="ztree" class="ztree"></div>
		
  </body>
</html>
