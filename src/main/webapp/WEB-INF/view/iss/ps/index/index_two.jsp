<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
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
<script src="<%=basePath%>public/ZHQ/js/hm.js"></script><script src="<%=basePath%>public/ZHQ/js/jquery.min.js"></script>
<!--导航js-->
<script src="<%=basePath%>public/ZHQ/js/jquery_ujs.js"></script>
<script src="<%=basePath%>public/ZHQ/js/bootstrap.min.js"></script>
</head>

<body>
  <div class="wrapper">
  <jsp:include page="/indexhead.jsp"></jsp:include>
  <div class="container content height-350 job-content ">
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">${typeName}</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
    <div class="col-md-12 p20 border1 margin-top-20">
        <div class="tab-v1">
          <h2 class="nav nav-tabs border0 padding-left-15">
            ${typeName}
		  </h2>
        </div>
          <div class="tab-content margin-bottom-20 margin-top-10">
            <div class="tab-pane fade active in">
              <div class="tag-box margin-bottom-0 padding-0">
                <ul class="categories li_square padding-left-15 margin-bottom-0">
                <c:forEach items="${indexList}" var="i">
                  <li>
                   <a href="<%=basePath %>index/selectArticleNewsById.do?id=${i.id}" title="${i.name }" target="_self"><span class="f18 mr5">·</span>${i.name }</a>
                   <span class="hex pull-right"><fmt:formatDate value='${i.publishedAt}' pattern="yyyy年MM月dd日 " /></span>
                  </li> 
                </c:forEach>             
                </ul>
              </div>
            </div>
          </div>
	     <div class="fenye">
           <div class="page_box fr">
	         <span class="pre_page page">上一页</span><span class="curr_page page">1</span><span class="page">2</span><span class="page">3</span><span class="page">4</span><span class="page">5</span><span class="page">6</span><span class="next_page page">下一页</span><span class="ml15">到</span><input type="text" class="page_input" value="1">页 <input type="submit" class="ml10 search_page" value="确 定">
	       </div>
	     </div>
        </div>
	  </div>
<!--底部代码开始-->
<jsp:include page="/indexbottom.jsp"></jsp:include>
</body>
</html>
