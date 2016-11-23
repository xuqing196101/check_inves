<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%@ include file="/WEB-INF/view/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ppms/categoryparam/cateParameter.js"></script>
  </head>
  <body>
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">产品管理</a></li><li><a href="#">产品参数管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <div class="container content height-350">
	  
	  <!-- left tree -->
	   <div class="col-md-3">
		 <div class="tag-box tag-box-v3">
		 	<div>
		 		<ul id="ztree" class="ztree" />
		 	</div>
		 </div>
	   </div>
	   
	   <!-- right -->
	   <div class="tag-box tag-box-v4 col-md-9" >
   		  <div class="col-md-12 col-sm-12 col-xs-12">
   			  <div class="pull-left">
   				 <button class="btn btn-windows add" onclick="addParams();" type="button">新增</button>
				 <button class="btn btn-windows delete" onclick="delParams();" type="submit">删除</button>
	          </div>
   		  </div>
	      <div class="col-md-12 col-sm-12 col-xs-12 mt20 clear">
	         <ul id="uListId" class="list-unstyled ">
	         </ul>
	     </div>
	     <div class="textc">
	     	<button class="btn btn-windows save" onclick="saveParams();" type="button">保存</button>
	     </div>
	   </div>
     </div>
  </body>
</html>
