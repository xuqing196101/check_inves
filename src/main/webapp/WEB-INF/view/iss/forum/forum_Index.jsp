<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../front.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    <title></title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

    

  <script type="text/javascript">

 </script>
  </head>
  
  <body> 
<jsp:include page="/index_head.jsp"></jsp:include>
<div class="container mt10">   
<!-- 4个热门版块 -->

<div class="row home-icons hidden-sm hidden-xs mt20">
  
  <div class="col-md-3">
    <div class="item item1">
    
      <div class="icon">
        <a href="${ pageContext.request.contextPath }/post/getIndexlist.html?parkId=${hotParks[0].id}">
        <i class="fa fa-comments-o">
        <img src="${ pageContext.request.contextPath }/public/ZHQ/images/icon_02.png"/>
        </i></a>
      </div>
      <div class="text">
        <a href="${ pageContext.request.contextPath }/post/getIndexlist.html?parkId=${hotParks[0].id}">
           ${hotParks[0].name}
       <i class="pull-right fa fa-arrow-right"></i></a>
      </div>
    </div>
  </div>
  
  <div class="col-md-3">
    <div class="item item2">
    
      <div class="icon">
        <a href="${ pageContext.request.contextPath }/post/getIndexlist.html?parkId=${hotParks[1].id}"><i class="fa fa-support"><img src="${ pageContext.request.contextPath }/public/ZHQ/images/icon_04.png"/></i></a>
      </div>
      <div class="text">
        <a href="${ pageContext.request.contextPath }/post/getIndexlist.html?parkId=${hotParks[1].id}">      
            ${hotParks[1].name} 
         <i class="pull-right fa fa-arrow-right"></i></a>
      </div>
    </div>
  </div>
  
  <div class="col-md-3">
    <div class="item item3">
    
      <div class="icon">
        <a href="${ pageContext.request.contextPath }/post/getIndexlist.html?parkId=${hotParks[2].id}"><i class="fa fa-users"><img src="${ pageContext.request.contextPath }/public/ZHQ/images/icon_03.png"/></i></a>
      </div>
      <div class="text">
        <a href="${ pageContext.request.contextPath }/post/getIndexlist.html?parkId=${hotParks[2].id}" >          
		 ${hotParks[2].name}
		 <i class="pull-right fa fa-arrow-right"></i></a>
      </div>
    </div>
  </div>
  <div class="col-md-3">
    <div class="item item4">
   
      <div class="icon">
        <a href="${ pageContext.request.contextPath }/post/getIndexlist.html?parkId=${hotParks[3].id}"><i class="fa fa-diamond"> <img src="${ pageContext.request.contextPath }/public/ZHQ/images/icon_08.png"/></i></a>
      </div>
      <div class="text">
        <a href="${ pageContext.request.contextPath }/post/getIndexlist.html?parkId=${hotParks[3].id}">
            ${hotParks[3].name}
        <i class="pull-right fa fa-arrow-right"></i></a>
      </div>
    </div>
  </div>
</div>

<!-- 精华帖 -->
<div class="home_suggest_topics panel panel-default">
  <div class="panel-heading panel-title">社区精华帖<span class="fr"><a href="${ pageContext.request.contextPath }/post/getHotlist.html" class="f14 b">更多>></a></span></div>
  <div class="panel-body topics row">
  
  <!-- 帖子div -->
  <c:forEach items="${hotPostList }" var="post">
    <div class="col-md-6 topics-group">
      <div class="topic media topic-31080">
      
        <!-- 左边头像 -->
        <div class="avatar media-left">
          <img class="media-object avatar-48" src="${ pageContext.request.contextPath }/public/ZHQ/images/boy.png" alt="196"></a>
        </div>
        
        <!-- 右边内容 -->
        <div class="infos media-body">
        <!-- 帖子名称 -->
         <div class="title media-heading">         
          <c:set value="${post.name}" var="content"></c:set>
          <c:set value="${fn:length(content)}" var="length"></c:set>
          <c:if test="${length>15}">
             <a  href='${ pageContext.request.contextPath }/post/getIndexDetail.html?postId=${post.id}' value='${fn:substring(content,0,15)}...'>${fn:substring(content,0,15)}...</a>
          </c:if>
          <c:if test="${length<=15}">
             <a href='${ pageContext.request.contextPath }/post/getIndexDetail.html?postId=${post.id}' value='${post.name}'>${post.name}</a>
          </c:if>
         </div>
         <!-- 帖子关联关系以及其他消息 -->
         <div class="info">
            <span>${post.topic.name }</span>
                   •
               <span>${post.user.relName }</span>
               <span class="">
                   •                           
               <fmt:formatDate value='${post.publishedAt}' pattern="yyyy年MM月dd日 " />           
               </span>                              
         </div>        
        </div>
        <div class="count media-right">
         <span class="state-false">${post.replycount }</span>
        </div>
       </div>
      </div>
      </c:forEach>
      
    </div>
   </div>
   
   <!-- 版块列表 -->

   <c:forEach items="${list }" var="park"> 
      <div class="home_suggest_topics panel panel-default">
    <div class="panel-heading panel-title">${park.name }<span class="fr"><a href="${ pageContext.request.contextPath }/post/getIndexlist.html?parkId=${park.id}" class="f14 b">更多>></a></span></div>
    <div class="panel-body topics row">
      <!-- 帖子div -->
  <c:forEach items="${park.posts }" var="post">
    <div class="col-md-6 topics-group">
      <div class="topic media topic-31080">
        <!-- 左边头像 -->
        <div class="avatar media-left">
          <img class="media-object avatar-48" src="${ pageContext.request.contextPath }/public/ZHQ/images/boy.png" alt="196"></a>
        </div>
        <!-- 右边内容 -->
        <div class="infos media-body">
        <!-- 帖子名称 -->
         <div class="title media-heading">         
          <c:set value="${post.name}" var="content"></c:set>
          <c:set value="${fn:length(content)}" var="length"></c:set>
          <c:if test="${length>15}">
             <a  href='${ pageContext.request.contextPath }/post/getIndexDetail.html?postId=${post.id}' value='${fn:substring(content,0,15)}...'>${fn:substring(content,0,15)}...</a>
          </c:if>
          <c:if test="${length<15}">
             <a href='${ pageContext.request.contextPath }/post/getIndexDetail.html?postId=${post.id}' value='${post.name}'>${post.name}</a>
          </c:if>
         </div>
         <!-- 帖子关联关系以及其他消息 -->
         <div class="info">
            <span>${post.topic.name }</span>
                   •
               <span>${post.user.relName }</span>
               <span class="">
                   •                           
                <fmt:formatDate value='${post.publishedAt}' pattern="yyyy年MM月dd日 " />        
               </span>          
         </div>        
        </div>
        <div class="count media-right">
         <span class="state-false">${post.replycount }</span>
        </div>
       </div>
      </div>
      </c:forEach>
    </div>
      </div>
  </c:forEach>  
  </div>
  </body>
  <div class="my_post f18">
  <a href='${ pageContext.request.contextPath }/post/mypost.html'>我的帖子</a>
  </div>
  <div class="publish_post f18">
  <a href='${ pageContext.request.contextPath }/post/publish.html'>我要发帖</a>
  </div> 
   <jsp:include page="/index_bottom.jsp"></jsp:include>
</html>

