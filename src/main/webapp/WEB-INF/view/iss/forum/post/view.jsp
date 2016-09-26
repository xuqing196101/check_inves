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
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.all.min.js"> </script>
	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript">
	$(function(){ 
    
		});
	</script>
  </head>
  
  <body>        
    <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">论坛管理</a></li><li class="active"><a href="#">帖子管理</a></li><li class="active"><a href="#">帖子详情</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 新增页面开始 -->
     <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
    <div>
	    <div class="headline-v2">
	   		<h2>帖子详情</h2>
	   </div>
	   <ul class="list-unstyled list-flow p0_20">
	   		  
	   		   <li class="col-md-12  p0 ">
			   <span class="fl">帖子名称：</span>
			   <div class="input-append">
		        <input class="span2 w745"  type="text" value = '${post.name}' readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>

			 <li class="col-md-6  p0 ">
			   <span class="fl">所属版块：</span>				 	
	  			<div class="input-append">
		        <input class="span2"  type="text" value = '${post.park.name}' readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">所属主题：</span>				 	
	  			<div class="input-append">
		        <input class="span2"  type="text" value = '${post.topic.name}' readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">置顶：</span>
				<div class="input-append">	
				 <c:choose>
				 <c:when test="${post.isTop == 0}"> 			
					<input class="span2"  type="text" value = '不置顶' readonly="readonly" >
				 </c:when>
				 <c:otherwise > 			
					<input class="span2"  type="text" value = '置顶' readonly="readonly"  >
				 </c:otherwise>
				 </c:choose>
            	</div>
			 </li>
			 
			 <li class="col-md-6  p0 ">
			   <span class="fl">锁定：</span>				 	
	  			<div class="input-append">
				 <c:choose>
				 <c:when test="${post.isLocking == 0}"> 			
					<input class="span2"  type="text" value = '不锁定' readonly="readonly"  >
				 </c:when>
				 <c:otherwise > 			
					<input class="span2"  type="text" value = '锁定' readonly="readonly"  >
				 </c:otherwise>
				 </c:choose>
		       </div>
			 </li>
			 
			 
			 <li class="col-md-6  p0 ">
			   <span class="fl">发表人：</span>				 	
	  			<div class="input-append">
		        <input class="span2"  type="text" value = '${post.user.relName}' readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			<li class="col-md-6  p0 ">
			   <span class="fl">最后回复人：</span>				 	
	  			<div class="input-append">
		        <input class="span2"  type="text" value = '${post.lastReplyer.relName}' readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>

			 <li class="col-md-6  p0 ">
			   <span class="fl">创建时间：</span>				 	
	  			<div class="input-append">
		       <input class="span2"  type="text" value = "<fmt:formatDate value='${post.publishedAt}' pattern="yyyy年MM月dd日  HH:mm:ss" />" readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">最后回复时间：</span>				 	
	  			<div class="input-append">
		        <input class="span2"  type="text" value = "<fmt:formatDate value='${post.lastReplyedAt}' pattern="yyyy-MM-dd  HH:mm:ss" />" readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
             <li class="col-md-6  p0 ">
               <span class="fl">回复数：</span>                  
                <div class="input-append">
                <input class="span2"  type="text" value = '${post.replycount}' readonly="readonly">
                <%--<span class="add-on">i</span>--%>
               </div>
             </li>
			 
			<li class="col-md-12 p0">
	   			<span class="fl">帖子内容：</span>
	  			<div class="col-md-12 pl200 fn mt5 pwr9">
	  				 <script id="editor" name="content" type="text/plain" class="ml125 mt20 w900"></script>
       			</div>
			 </li> 
	  	 </ul>
	  	 
	</div>  	
	<!-- 底部按钮 -->			          
  <div  class="col-md-12 ml185">
   <div class="fl padding-10">
    <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
	</div>
  </div>
     
     </div>
     </div>
 <script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
    var content="${post.content}";
	ue.ready(function(){
  		ue.setContent(content);    
  		ue.setDisabled([]);
	});
    
</script>
  </body>
</html>

