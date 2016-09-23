<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>  
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
  <div class="wrapper">
  <jsp:include page="/index_head.jsp"></jsp:include>
    <div class="container content height-350 job-content ">
      <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
           <ul class="breadcrumb margin-left-0">
           <li><a href="<%=basePath %>park/getIndex.do">论坛首页</a></li><li><a href="<%=basePath %>post/getIndexlist.html?parkId=${park.id }">${park.name}</a></li>
           </ul>
        <div class="clear"></div>
      </div>
   </div>
   
    <div class="col-md-12 p20 border1 margin-top-20">
        <div class="tab-v1">
          <h2 class="nav nav-tabs border0 padding-left-15">
            ${park.name}
          </h2>
        </div>
          <div class="tab-content margin-bottom-20 margin-top-10">
            <div class="tab-pane fade active in">
              <div class="tag-box margin-bottom-0 padding-0">
                <ul class="categories li_square padding-left-15 margin-bottom-0">
                <c:forEach items="${list}" var="post">
                  
                  <li>                                  
                    <span class="f18 mr5">·</span>
                        <span>${post.topic.name}</span>                      
                        <c:set value="${post.name}" var="content"></c:set>
                        <c:set value="${fn:length(content)}" var="length"></c:set>
                        <c:if test="${length>15}">
                            <a  href='<%=basePath %>post/getIndexDetail.html?postId=${post.id}' >${fn:substring(content,0,15)}...</a>
                        </c:if>
                        <c:if test="${length<15}">
                            <a href='<%=basePath %>post/getIndexDetail.html?postId=${post.id}' ></a>${post.name}
                        </c:if>
                   
                   <span class="hex pull-right"><fmt:formatDate value='${post.publishedTime}' pattern="yyyy年MM月dd日 " /></span>
                  </li>
                   
                </c:forEach>             
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
<!--底部代码开始-->
<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>

