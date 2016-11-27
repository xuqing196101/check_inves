<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
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
  </head>
  
  <body>        
    <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a>论坛管理</a></li><li class="active"><a >帖子管理</a></li><li class="active"><a >帖子详情</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 新增页面开始 -->
     <div class="container container_box">
    	<div>
	   	 <h2 class="list_title">帖子详情</h2>
	   
	   <ul class="ul_list mb20">
	   		  
	   		   <li class="col-md-3 col-sm-6 col-xs-12 pl15">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">帖子名称：</span>
			   <div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
		        <input type="text" value = '${post.name}' readonly="readonly">
		        
		       </div>
			 </li>

			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">所属版块：</span>				 	
	  			<div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
		        <input type="text" value = '${post.park.name}' readonly="readonly">
		        
		       </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">所属主题：</span>				 	
	  			<div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
		        <input type="text" value = '${post.topic.name}' readonly="readonly">
		        
		       </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">置顶：</span>
				<div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">	
				 <c:choose>
				 <c:when test="${post.isTop == 0}"> 			
					<input type="text" value = '不置顶' readonly="readonly" >
				 </c:when>
				 <c:otherwise > 			
					<input type="text" value = '置顶' readonly="readonly"  >
				 </c:otherwise>
				 </c:choose>
				 
            	</div>
			 </li>
			 
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">锁定：</span>				 	
	  			<div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
				 <c:choose>
				 <c:when test="${post.isLocking == 0}"> 			
					<input type="text" value = '不锁定' readonly="readonly"  >
				 </c:when>
				 <c:otherwise > 			
					<input type="text" value = '锁定' readonly="readonly"  >
				 </c:otherwise>
				 </c:choose>
				 
		       </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">发表人：</span>				 	
	  			<div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0 col-md-12 col-xs-12 col-sm-12 p0">
		        <input type="text" value = '${post.user.relName}' readonly="readonly">
		       </div>
			 </li>
			<li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">最后回复人：</span>				 	
	  			<div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
		        <input type="text" value = '${post.lastReplyer.relName}' readonly="readonly">
		        
		       </div>
			 </li>

			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">创建时间：</span>				 	
	  			<div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
		       <input type="text" value = "<fmt:formatDate value='${post.publishedAt}' pattern="yyyy年MM月dd日  HH:mm:ss" />" readonly="readonly">
		        
		       </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">最后回复时间：</span>				 	
	  			<div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
		        <input type="text" value = "<fmt:formatDate value='${post.lastReplyedAt}' pattern="yyyy-MM-dd  HH:mm:ss" />" readonly="readonly">
		        
		       </div>
			 </li>
             <li class="col-md-3 col-sm-6 col-xs-12">
               <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">回复数：</span>                  
                <div class="input_group input-append col-md-12 col-xs-12 col-sm-12 p0">
                <input type="text" value = '${post.replycount}' readonly="readonly">
                
               </div>
             </li>
			 
			<li class="col-md-12 col-sm-12 col-xs-12">
	   			<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">帖子内容：</span>
	  			<div class="border1 clear col-md-12 col-sm-12 col-xs-12 p0 h80">
	  				 <%--<script id="editor" name="content" type="text/plain" class="ml125 mt20 w900"></script>--%>
	  				${post.content}
	  			</div>
			 </li> 
			 <li class="col-md-12 col-sm-12 col-xs-12 mt15" id="file">
		     <span class="fl">已上传的附件：</span>
		     <div class="fl mt5">
                <up:show showId="post_attach_show" delete="false" businessId="${post.id}" sysKey="${sysKey}" typeId="${typeId}"/>
		     </div>
		     </li>
	  	 </ul>
	  	 	
	<!-- 底部按钮 -->			          
    <div class="col-md-12 tc">    
    <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
   </div>  
     </div>
     </div>
 <script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
    var content='${post.content}';
	ue.ready(function(){
  		ue.setContent(content);    
  		ue.setDisabled([]);
	});
    
</script>
  </body>
</html>

