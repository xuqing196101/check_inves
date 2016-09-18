<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'view.jsp' starting page</title>
    
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
        用户名：${user.loginName}
    <br/>   
        真实姓名：${user.relName}
    <br/>
       创建时间：<fmt:formatDate value="${user.createdAt}" pattern="yyyy年MM月dd日   HH:mm:ss"/>
    <br/>
       创建人：${user.creater.loginName}
    <br/>
      修改时间：<fmt:formatDate value="${user.updatedAt}" pattern="yyyy年MM月dd日   HH:mm:ss"/>
     <br/>
     角色：<c:forEach items="${user.roles}" var="role" varStatus="vs">
     		<c:if test="${user.roles.size()>vs.index+1}">
     			${role.name},
     		</c:if>
     		<c:if test="${user.roles.size()<=vs.index+1}">
     			${role.name}
     		</c:if>
     	</c:forEach>
     <a  onclick="location.href='javascript:history.go(-1);'">返回</a>
  </body>
</html>
