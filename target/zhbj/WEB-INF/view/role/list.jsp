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
    
    <title>角色管理</title>
    
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
  		window.location.href="<%=basePath%>role/view.do?id="+id;
  	}
  
  </script>
  <body>
  	<div >
		<a href="<%=basePath%>role/toAdd.do">新增</a>
	</div>
    <table >
		<thead>
			<tr>
			    <th >id</th>
				<th >角色名</th>
				<th >创建时间</th>
			</tr>
		</thead>
		<c:forEach items="${list}" var="role" varStatus="vs">
			<tr>
				<td >${role.id }</td>
				<td onclick="view(${role.id});">${role.name}</td>
				<td >
					<fmt:formatDate value="${role.createdAt}" pattern="yyyy年MM月dd日   HH:mm:ss"/>
				</td>
				<td>
					<a href="<%=basePath%>role/edit.do?id=${role.id}">修改</a>
					<a href="<%=basePath%>role/delete.do?id=${role.id}">删除</a>
				</td>
			</tr>
		</c:forEach>
	</table>
  </body>
</html>
