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
				  <div class="col-md-12 col-sm-12 col-xs-12 clear hide" id="baseInfoDiv">
				    <h2 class="count_flow"><i>1</i>基本信息</h2>
				    <div class="col-md-12 col-sm-12 col-xs-12" >
				    	<table class="table table-bordered" >
				    		<tbody id="baseInfoTbody">
				    			<tr id="trs">
				    				<td class="bggrey w50">产品名称：</td><td class="productName w100"></td>
				    				<td class="bggrey w50">类型：</td><td class="type w100"></td>
				    			</tr>
				    		</tbody>
					  	</table>
				    </div>
				  </div>
				  <div class="col-md-12 col-sm-12 col-xs-12 clear hide" id="productParamDiv">
				    <h2 class="count_flow"><i>2</i>产品参数</h2>
				    <div class=" col-md-12 col-sm-12 col-xs-12" >
				    	<table class="table table-bordered" >
				    		<thead>
				    			<tr>
				    				<td class="info w50 tc">序号</td>
				    				<td class="info tc">参数名称</td>
				    				<td class="info tc">参数类型</td>
				    			</tr>
				    		</thead>
				    		<tbody id="productParamTbody">
				    			
				    		</tbody>
					  	</table>
				    </div>
				  </div>
			 </div>
	    </div>
		<jsp:include page="/index_bottom.jsp"></jsp:include>
  </body>
</html>