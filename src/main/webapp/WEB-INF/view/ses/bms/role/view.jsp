<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
    
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
        用户名：${role.name}
    <br/>
       创建时间：<fmt:formatDate value="${role.createdAt}"  pattern="yyyy年MM月dd日   HH:mm:ss"/>
    <br/>
       描述：<textarea rows="3" cols="10" readonly="readonly">${role.description }</textarea>
       <br/>
     <a  onclick="location.href='javascript:history.go(-1);'">返回</a>
  </body>
</html>
