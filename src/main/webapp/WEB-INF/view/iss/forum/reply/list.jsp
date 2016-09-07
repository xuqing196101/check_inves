<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>回复管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <script type="text/javascript">
  	function view(id){
  		window.location.href="<%=basePath%>reply/view.html?id="+id;
  	}
  
  </script>
  <body>
  <jsp:include page="/backhead.jsp"></jsp:include>
  	<div >
		<a href="<%=basePath%>reply/add.html">新增</a>
	</div>
    <table >
		<thead>
			<tr>
			    <th width="30">id</th>
				<th width="50">回复名称</th>

			</tr>
		</thead>
		<c:forEach items="${list}" var="reply" varStatus="vs">
			<tr>
				<td >${reply.id }</td>
				<td onclick="view(${reply.id});">${reply.name}</td>

				<td>
					<a href="<%=basePath%>reply/edit.html?id=${reply.id}">修改</a>
					<a href="<%=basePath%>reply/delete.html?id=${reply.id}">删除</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<jsp:include page="/backbottom.jsp"></jsp:include>
  </body>
</html>
