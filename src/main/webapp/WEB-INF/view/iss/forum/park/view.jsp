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
		   <li><a href="#"> 首页</a></li><li><a href="#">论坛管理</a></li><li class="active"><a href="#">版块管理</a></li><li class="active"><a href="#">版块详情</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 新增页面开始 -->
     <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
    <div>
	    <div class="headline-v2">
	   		<h2>版块详情</h2>
	   </div>
	   <ul class="list-unstyled list-flow p0_20">
	   		  
	   		   <li class="col-md-6  p0 ">
			   <span class="fl">版块名称：</span>
			   <div class="input-append">
		        <input class="span2"  type="text" value = '${park.name}' readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 
			 <li class="col-md-6  p0 ">
			   <span class="fl">版主：</span>				 	
	  			<div class="input-append">
		        <input class="span2"  type="text" value = '${park.user.relName}' readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">创建人：</span>				 	
	  			<div class="input-append">
		        <input class="span2"  type="text" value = '${park.creater.relName}' readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">主题数：</span>				 	
	  			<div class="input-append">
		        <input class="span2"  type="text" value = '${park.topiccount}' readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">帖子数：</span>				 	
	  			<div class="input-append">
		        <input class="span2"  type="text" value = '${park.postcount}' readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">评论数：</span>				 	
	  			<div class="input-append">
		        <input class="span2"  type="text" value = '${park.replycount}' readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">创建时间：</span>				 	
	  			<div class="">
		        <fmt:formatDate value='${park.createdAt}' pattern="yyyy年MM月dd日  HH:mm:ss" />
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">更新时间：</span>				 	
	  			<div class="">
		        <fmt:formatDate value='${park.updatedAt}' pattern="yyyy年MM月dd日  HH:mm:ss" />
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 
			 
			<li class="col-md-12  p0 ">	  	 			
				<span class="fl"> 版块介绍：</span>
				<div class="col-md-12 mt5 fn pl200 pwr9">
				<textarea  class="text_area col-md-12" readonly="readonly">${park.content}</textarea>		
				</div>			
	  	 	</li>
	  	 </ul>
	  	 
	</div>  	
	<!-- 底部按钮 -->			          
  <div  class="col-md-12 ml185">
   <div class="fl padding-10">
    <button class="btn btn-windows reset" onclick="history.go(-1)" type="button">返回</button>
	</div>
  </div>
     
     </div>
     </div>
  <!-- 底部代码 --> 
<div class="footer-v2" id="footer-v2">

      <div class="footer">

            <!-- Address -->
              <address class="">
			  Copyright © 2016 版权所有：中央军委后勤保障部 京ICP备09055519号
              </address>
              <div class="">
		       浏览本网主页，建议将电脑显示屏的分辨率调为1024*768
              </div> 
            <!-- End Address -->

<!--/footer--> 
    </div>
</div>
  </body>
</html>
