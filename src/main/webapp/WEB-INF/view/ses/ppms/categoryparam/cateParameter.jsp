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
   				 <button class="btn btn-windows add" type="button">新增</button>
				 <button class="btn btn-windows edit" type="button">编辑</button>
				 <button class="btn btn-windows delete" type="submit">删除</button>
	          </div>
   		  </div>
	      <div class="col-md-12 col-sm-12 col-xs-12 mt20">
	         <ul id="uListId" class="list-unstyled ">
         		<li>
         			<div>
         				参数名称：
         				<input type="text" id="topic" />
         			</div>
         			<div>
         				参数类型：
         				<select >
				    	    <option value="1">选项一</option>
				    	    <option value="2">选项二</option>
				    	    <option value="3">选项三</option>
				    	</select>
         			</div>
         		</li>
         		<li>
         			
         		</li>
	         </ul>
	     </div>
	   </div>
     </div>
  </body>
</html>
