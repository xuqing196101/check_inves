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
   <div class="container margin-top-10">
     <div class="content padding-left-25 padding-right-25 padding-top-20">		
		          <c:forEach items="${list}"  var="post" varStatus="vs">
			          <ul class="list-unstyled categories tab-content margin-0" id="postlist">
			          <li>
			          	 <span>${post.topic.name}</span>			          
						<c:set value="${post.name}" var="content"></c:set>
						<c:set value="${fn:length(content)}" var="length"></c:set>
						<c:if test="${length>15}">
							<a  href='<%=basePath %>post/getIndexDetail.html?postId=${post.id}' >${fn:substring(content,0,15)}...</a>
						</c:if>
						<c:if test="${length<15}">
							<a href='<%=basePath %>post/getIndexDetail.html?postId=${post.id}' ></a>${post.name}
						</c:if>
				
				          <span class='hex pull-right'><fmt:formatDate value='${post.publishedTime}' pattern="yyyy-MM-dd " /></span>
				       </li>			          
			          </ul>
		          </c:forEach>
     </div>
   </div>

  </body>
</html>

