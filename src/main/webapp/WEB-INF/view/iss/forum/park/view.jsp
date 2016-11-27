<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		   <li><a href="#"> 首页</a></li><li><a >论坛管理</a></li><li class="active"><a >版块管理</a></li><li class="active"><a>版块详情</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 新增页面开始 -->
     <div class="container container_box">
     	<div>
	   		<h2 class="list_title">
					版块详情
			</h2>
	   <ul class="ul_list mb20">	   		  
	   		 <li class="col-md-3 col-sm-6 col-xs-12 pl15">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">版块名称：</span>
			   <div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
		        <input type="text" value = '${park.name}' readonly="readonly">
		        
		       </div>
			 </li>
			 
			 <li class="col-md-3 col-sm-6 col-xs-12 ">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">版主：</span>				 	
	  			<div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
		        	<input type="text" value = '${park.user.relName}' readonly="readonly">
		        	
		       </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12 ">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">创建人：</span>				 	
	  			<div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
		        <input type="text" value = '${park.creater.relName}' readonly="readonly">
		        
		       </div>
			 </li>
			  <li class="col-md-3 col-sm-6 col-xs-12 ">
               <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">是否热门：</span>                 
                <div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
                 <c:choose>
                 <c:when test="${park.isHot == 0}">             
                    <input type="text" value = '不是热门' readonly="readonly" >
                 </c:when>
                 <c:otherwise >             
                    <input type="text" value = '热门' readonly="readonly"  >
                 </c:otherwise>
                 </c:choose>
                 
               </div>
             </li>
			 <li class="col-md-3 col-sm-6 col-xs-12 ">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">主题数：</span>				 	
	  			<div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
		        <input type="text" value = '${park.topiccount}' readonly="readonly">
		        
		       </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12 ">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">帖子数：</span>				 	
	  			<div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
		        <input type="text" value = '${park.postcount}' readonly="readonly">
		        
		       </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12 ">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">回复数：</span>				 	
	  			<div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
		        <input type="text" value = '${park.replycount}' readonly="readonly">
		        
		       </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">创建时间：</span>				 	
	  			<div  class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
		        <input type="text" value = "<fmt:formatDate value='${park.createdAt}' pattern="yyyy-MM-dd  HH:mm:ss" />" readonly="readonly">
		        
		       </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">更新时间：</span>				 	
	  			<div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
		         <input type="text" value = "<fmt:formatDate value='${park.updatedAt}' pattern="yyyy-MM-dd  HH:mm:ss" />" readonly="readonly">
		        
		       </div>
			 </li>
			 
			 
			<li class="col-md-12 col-xs-12 col-sm-12 ">	  	 			
				<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 版块介绍：</span>
				<div class="col-md-12 col-xs-12 col-sm-12 mt5 p0">
				<textarea  class="h130 col-md-12 col-xs-12 col-sm-12"  title="不超过800个字" readonly="readonly">${park.content}</textarea>		
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
