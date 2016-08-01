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
    
    <title>My JSP 'list.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  	<div >
		<a href="<%=basePath%>user/add.do">新增</a>
	</div>
    <table >
		<thead>
			<tr>
			    <th width="30">id</th>
				<th width="30">用户名</th>
				<th>创建人</th>
				<th>创建日期</th>
				<th>操作</th>
			</tr>
		</thead>
		<c:forEach items="${list}" var="user" varStatus="vs">
			<tr>
				<td >${user.id }</td>
				<td >${user.loginName}</td>
				<td >${user.creater}</td>
				<td >${user.createDate}</td>
				<td>
					<a href="<%=basePath%>user/edit.do?id=${user.id}">修改</a>
					<a href="<%=basePath%>user/delete.do?id=${user.id}">删除</a>
				</td>
			</tr>
		</c:forEach>
	</table>
  </body>
</html>
