<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
  </head>
  <body>
      <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">下载人管理</a></li><li class="active"><a href="javascript:void(0);">版块管理</a></li><li class="active"><a href="javascript:void(0);">下载人详情</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 新增页面开始 -->
     <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
    <div>
	    <div class="headline-v2">
	   		<h2>下载人详情</h2>
	   </div>
	   <ul class="list-unstyled list-flow p0_20">
	   		  
	   		   <li class="col-md-6  p0 ">
			   <span class="fl">主题名称：</span>
			   <div class="input-append">
		        <input class="span2"  type="text" value = '${topic.name}' readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">所属版块：</span>				 	
	  			<div class="input-append">
		        <input class="span2"  type="text" value = '${topic.park.name}' readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">创建人：</span>				 	
	  			<div class="input-append">
		        <input class="span2"  type="text" value = '${topic.user.relName}' readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>

			 <li class="col-md-6  p0 ">
			   <span class="fl">帖子数：</span>				 	
	  			<div class="input-append">
		        <input class="span2"  type="text" value = '${topic.postcount}' readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">评论数：</span>				 	
	  			<div class="input-append">
		        <input class="span2"  type="text" value = '${topic.replycount}' readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">创建时间：</span>				 	
	  			<div class="input-append">
		       <input class="span2"  type="text" value = "<fmt:formatDate value='${topic.createdAt}' pattern="yyyy年MM月dd日  HH:mm:ss" />" readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">更新时间：</span>				 	
	  			<div class="input-append">
		        <input class="span2"  type="text" value = "<fmt:formatDate value='${topic.updatedAt}' pattern="yyyy年MM月dd日  HH:mm:ss" />" readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 
			 
			<li class="col-md-12  p0 ">	  	 			
				<span class="fl"> 主题介绍：</span>
				<div class="col-md-12 mt5 fn pl200 pwr9">
				<textarea  class="text_area col-md-12" readonly="readonly">${topic.content}</textarea>		
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
    
  </body>
</html>
