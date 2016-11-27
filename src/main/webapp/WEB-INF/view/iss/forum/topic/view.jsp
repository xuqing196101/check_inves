<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'view.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>        
    <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a >论坛管理</a></li><li class="active"><a >主题管理</a></li><li class="active"><a >主题详情</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 新增页面开始 -->
     <div class="container container_box">
     	<div>

	   		<h2 class="list_title">主题详情</h2>
	  
	   <ul class="ul_list mb20">
	   		  
	   		   <li class="col-md-3 col-sm-6 col-xs-12 pl15">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">主题名称：</span>
			   <div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
		        <input type="text" value = '${topic.name}' readonly="readonly">
		        
		       </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">所属版块：</span>				 	
	  			<div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0 "> 
	  			<input type="text" value = '${topic.park.name}' readonly="readonly">
		        
		       </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">创建人：</span>				 	
	  			<div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
		        <input type="text" value = '${topic.user.relName}' readonly="readonly">
		        
		       </div>
			 </li>

			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">帖子数：</span>				 	
	  			<div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
		        <input type="text" value = '${topic.postcount}' readonly="readonly">
		        
		       </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">回复数：</span>				 	
	  			<div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
		        <input type="text" value = '${topic.replycount}' readonly="readonly">
		        
		       </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">创建时间：</span>				 	
	  			<div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
		       <input type="text" value = "<fmt:formatDate value='${topic.createdAt}' pattern="yyyy-MM-dd  HH:mm:ss" />" readonly="readonly">
		        
		       </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">更新时间：</span>				 	
	  			<div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
		        <input type="text" value = "<fmt:formatDate value='${topic.updatedAt}' pattern="yyyy-MM-dd  HH:mm:ss" />" readonly="readonly">
		        
		       </div>
			 </li>
			 
			 
			<li class="col-md-12 col-xs-12 col-sm-12">	  	 			
				<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 主题介绍：</span>
				<div class="col-md-12 col-xs-12 col-sm-12 p0">
					<textarea  class="col-md-12 col-xs-12 col-sm-12 h130" readonly="readonly">${topic.content}</textarea>		
				</div>			
	  	 	</li>
	  	 </ul>
	  	 
	</div>  	
	<!-- 底部按钮 -->			          
    <div class="col-md-12 col-xs-12 col-sm-12 tc">   
    <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
	
     </div>
     </div>
     </div>

  </body>
</html>

