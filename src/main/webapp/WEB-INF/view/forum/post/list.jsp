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
    
    <title>帖子管理</title>
    
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
  		window.location.href="<%=basePath%>post/view.do?id="+id;
  	}
  
  </script>
  <body>
  <jsp:include page="/backhead.jsp"></jsp:include>
  	<div >
		<a href="<%=basePath%>post/add.do">新增</a>
	</div>
    <table >
		<thead>
			<tr>
			    <th width="30">id</th>
				<th width="50">帖子名称</th>

			</tr>
		</thead>
		<c:forEach items="${list}" var="post" varStatus="vs">
			<tr>
				<td >${post.id }</td>
				<td onclick="view(${post.id});">${post.name}</td>

				<td>
					<a href="<%=basePath%>post/edit.do?id=${post.id}">修改</a>
					<a href="<%=basePath%>post/delete.do?id=${post.id}">删除</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<jsp:include page="/backbottom.jsp"></jsp:include>
  </body>
</html>
