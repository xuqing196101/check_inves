<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>信息列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
	<script type="text/javascript">
		$(function(){
			
		});
		
		function addArticle(){
			$.ajax({
				url:"<%=basePath%>article/addArticle.do",
				success:function(data){
					alert(1);
				}
			});
		}
	</script>
  </head>
  
  <body>
  <table>
	<tr>
	  <th>标题</th>
	</tr>
    <c:forEach items="${allArticleList }" var="a">
  		<tr>
  			<td>${a.name}</td>
  		</tr>  	
    </c:forEach>
  </table>
  <input type="button" onclick="addArticle()" value="新增"/>
  <input type="button" onclick="editArticle()" value="修改"/>
  <input type="button" onclick="delArticle()" value="删除"/>
  </body>
</html>
