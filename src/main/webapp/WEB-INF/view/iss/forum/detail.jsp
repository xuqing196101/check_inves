<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>论坛管理</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=basePath%>public/ZHQ/css/common.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHQ/css/bootstrap.min.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHQ/css/style.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/line-icons.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/app.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/application.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/header-v4.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/footer-v2.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/img-hover.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/page_job.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/shop.style.css" media="screen" rel="stylesheet">
	<script src="<%=basePath%>public/ZHQ/js/hm.js"></script>
	<script src="<%=basePath%>public/ZHQ/js/jquery.min.js"></script>
	<!--导航js-->
	<script src="<%=basePath%>public/ZHQ/js/jquery_ujs.js"></script>
	<script src="<%=basePath%>public/ZHQ/js/bootstrap.min.js"></script>
  <script type="text/javascript">

 </script>
  </head>
  
  <body>
  
  <jsp:include page="/indexhead.jsp"></jsp:include>

   <div class="container margin-top-10">
     <div class="content padding-left-25 padding-right-25 padding-top-20">	
     	帖子名称：${post.name }<br/>
     	帖子内容：${post.content }<br/>
     	发布时间：<fmt:formatDate value='${post.publishedTime}' pattern="yyyy年MM月dd日  " /></span><br/>
     	
   		帖子作者：${post.user.relName }<br/>
		所属版块：${post.park.name }	<br/>
		所属主题：${post.topic.name }<br/>
		<c:forEach items="${post.replies }" var="reply" varStatus="vs">
		${reply.name }<br>
		</c:forEach>
     </div>
   </div>
   
  <jsp:include page="/indexbottom.jsp"></jsp:include>
  </body>
</html>


