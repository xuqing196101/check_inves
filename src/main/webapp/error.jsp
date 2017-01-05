<%@ page language="java" contentType="text/html; charset=Utf-8" isErrorPage="true" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
    <link href="${pageContext.request.contextPath}/public/backend/css/error.css" rel="stylesheet" type="text/css" >
  </head>
  <body>
 	<div id="mother">
	  <div id="errorBox">
	    <a href="${pageContext.request.contextPath}/" target="_parent" title="返回系统首页">
		  <div class="link" id="home"></div>
		</a>
		<a href="javascript:history.go(-1);" title="返回继续操作">
		  <div class="link" id="contact"></div>
		</a>
	  </div>
	</div>
  </body>
</html>