<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    <jsp:include page="/index_head.jsp"></jsp:include>
	<link href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css" type="text/css" rel="stylesheet">
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ppms/categoryparam/publish_index.js"></script>
  </head>
  
  <body>
	    <div class="margin-top-10 breadcrumbs">
	      <div class="container">
			   	<ul class="breadcrumb margin-left-0">
			   		<li><a href="${pageContext.request.contextPath}/index/selectIndexNews.html">首页</a></li><li><a href="#">技术参数库</a></li>
			   	</ul>
				<div class="clear"></div>
		  	</div>
	   	</div>
	   	<div class="container content height-350">
	   		<!-- left -->
		    <div class="col-md-3">
			  <div class="tag-box tag-box-v3">
			    <div>
				  <ul id="ztree" class="ztree" />
				</div>
			  </div>
			</div>
		   
			<!-- right -->
		    <div class="tag-box tag-box-v4 col-md-9 col-xs-12 col-sm-9" >
				  <div class="col-md-12 col-sm-12 col-xs-12 mt20 clear">
					  <table class="table table-bordered mt10" id="uListId">
					  	
					  </table>
				  </div>
			 </div>
	    </div>
		<jsp:include page="/index_bottom.jsp"></jsp:include>
  </body>
</html>