<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'add.jsp' starting page</title>
    
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
    <form action="<%=basePath %>user/save.do" method="post">
	          用户名：<input autofocus="autofocus" id="user_login" name="loginName" placeholder="邮箱/用户名/已验证手机" size="30" type="text">
	          密码：<input id="user_password" name="password" placeholder="" size="30" type="password">
	          真实姓名：<input autofocus="autofocus"  name="relName" size="30" type="text">
	        <input value="保存" type="submit">
     </form>
  </body>
</html>
