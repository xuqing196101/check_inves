<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>        
    <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a >论坛管理</a></li><li class="active"><a >评论管理</a></li><li class="active"><a >评论详情</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 新增页面开始 -->
     <div class="container container_box">
    <div>
	   	<h2 class="count_flow"><i>1</i>回复详情</h2>
	   <ul class="ul_list mb20">
	   		  


			 <li class="col-md-3 margin-0 padding-0">
			   <span class="col-md-12 padding-left-5">发布人：</span>				 	
	  			<div class="input-append">
		        <input class="span2"  type="text" value = '${reply.user.relName}' readonly="readonly">
		        <span class="add-on">i</span>
		       </div>
			 </li>


			 <li class="col-md-3 margin-0 padding-0">
			   <span class="col-md-12 padding-left-5">发布时间：</span>				 	
	  			<div class="input-append">
		       <input class="span2"  type="text" value = "<fmt:formatDate value='${reply.publishedAt}' pattern="yyyy-MM-dd  HH:mm:ss" />" readonly="readonly">
		        <span class="add-on">i</span>
		       </div>
			 </li>
			 <li class="col-md-3 margin-0 padding-0">
			   <span class="col-md-12 padding-left-5">更新时间：</span>				 	
	  			<div class="input-append">
		        <input class="span2"  type="text" value = "<fmt:formatDate value='${reply.updatedAt}' pattern="yyyy-MM-dd  HH:mm:ss" />" readonly="readonly">
		        <span class="add-on">i</span>
		       </div>
			 </li>
			 
			 
			<li class="col-md-12  p0 ">	  	 			
				<span class="col-md-12 padding-left-5"> 回复内容：</span>
				<div class="col-md-9 mt5 p0">
				${reply.content}
				</div>			
	  	 	</li>
	  	 </ul>
	  	 	
	<!-- 底部按钮 -->			          
    <div class="col-md-12 tc">    
    	<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
	</div>
  </div>
     </div>

  </body>
</html>

