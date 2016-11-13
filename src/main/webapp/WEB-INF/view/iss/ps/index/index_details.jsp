<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/tld/upload" prefix="up" %>
<%@ include file="../../../../../common.jsp"%>
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
</head>

<body>
  <jsp:include page="/index_head.jsp"></jsp:include>
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">${articleDetail.articleType.name}</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
<div class="container content height-350 job-content ">

   
   <div class="col-md-12 p30_40 border1 margin-top-20">
     <h3 class="tc f22">
	   <div class="title bbgrey ">${articleDetail.name}</div>
	 </h3>
	 <div class="source" ><div class="fr"><span>文章来源：${articleDetail.source}</span><span class="ml15"><i class="mr5"><img src="${pageContext.request.contextPath}/public/ZHQ/images/block.png"/></i><fmt:formatDate value='${articleDetail.createdAt}' pattern="yyyy.MM.dd" /></span></div></div>
	 <div class="clear margin-top-20 new_content">
	    ${articleDetail.content }
	 </div>
	 <div class="extra_file">
	 	<div class="">
			<up:show showId="artice_file_show" businessId="${articleId }" sysKey="${articleSysKey}" typeId="${artiAttachTypeId }" />
	 	</div>
	 </div>
	 </div>
   </div>
<!--底部代码开始-->
<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
