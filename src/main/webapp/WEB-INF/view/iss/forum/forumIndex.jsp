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
  <div class="container content padding-top-0">
   
     <div class=" magazine-page">
     
     <div class="partners">
      
     <c:forEach items="${list}" var="park" varStatus="vs">
       <div class="headline margin-top-15">
       		<h2 class="padding-left-15">${park.name}</h2>
       		 <span class="badge badge-light pull-right nobgcolor" ><a href="<%=basePath %>post/getIndexlist.html?parkId=${park.id }" >更多>></a></span>
       </div>
				<c:forEach items="${park.posts}"  var="post" varStatus="vs">
			          <ul class="list-unstyled categories tab-content margin-0"  id="postlist">
			          <li style="height: 30px" >
			          	 <span>${post.topic.name}</span>			          
						<c:set value="${post.name}" var="content"></c:set>
						<c:set value="${fn:length(content)}" var="length"></c:set>
						<c:if test="${length>15}">
							<a  href='<%=basePath %>post/getIndexDetail.html?postId=${post.id}' value='${fn:substring(content,0,15)}...'>${fn:substring(content,0,15)}...</a>
						</c:if>
						<c:if test="${length<15}">
							<a href='<%=basePath %>post/getIndexDetail.html?postId=${post.id}' value='${post.name}'>${post.name}</a>
						</c:if>
				
				          <span class='hex pull-right'><fmt:formatDate value='${post.publishedTime}' pattern="yyyy-MM-dd " /></span>
				       </li>			          
			          </ul>
		         </c:forEach>
	
	</c:forEach>
	 </div>
   </div>
  </div> 
  <div class="my_post">
  <a href='<%=basePath %>post/publish.html'>我要发帖</a>
  </div>

  </body>
</html>

