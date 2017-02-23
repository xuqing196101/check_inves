<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<head>
<jsp:include page="/index_head.jsp"></jsp:include>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<script type="text/javascript">
$(document).ready(function(){  
		var text = "${fileSize}";
		if(text<=0){
			$("#extra_file").hide();
		}
});
</script>
</head>

<body>
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="${pageContext.request.contextPath}/index/selectIndexNews.html"> 首页</a></li><li><a href="#">${articleDetail.articleType.name}</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container content height-350 job-content ">
   <div class="col-md-12 col-sm-12 col-xs-12 border1 margin-top-20 details_box">
     <h3 class="tc f22">
	   <div class="title bbgrey ">${articleDetail.name}</div>
	 </h3>
	 <div class="source" ><div class="fr"><%-- <span>文章来源：${articleDetail.source}</span> --%><span class="ml15"><i class="mr5"><img src="${pageContext.request.contextPath}/public/portal/images/block.png"/></i><fmt:formatDate value='${articleDetail.publishedAt}' pattern="yyyy.MM.dd" /></span></div></div>
	 <div class="clear margin-top-20 new_content"><%--
	    ${articleDetail.content }
	 --%>
	 	<c:if test="${ipAddressType == '0' }">
	 		${articleDetail.content}
	 	</c:if>
	 	<c:if test="${ipAddressType == '1' }">
		 	<img src="${pageContext.request.contextPath}/index/downloadDetailsImage.html?id=${articleDetail.id}" width="100%" height="100%"/>
	 	</c:if>
	 </div>
	 <div class="extra_file">
	      <span id="extra_file" class="fl mt12"> 附件：</span>
	      <div class="mt10">
	          <u:show showId="artice_file_show" delete="false" businessId="${articleId}" sysKey="${articleSysKey}" typeId="${artiAttachTypeId }" />
	      </div>
	 </div>
	 </div>
   </div>
<!--底部代码开始-->
<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
