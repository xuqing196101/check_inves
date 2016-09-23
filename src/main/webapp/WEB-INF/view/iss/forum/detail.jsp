<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>论坛管理</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=basePath%>public/ZHQ/css/common.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHQ/css/bootstrap.min.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHQ/css/style.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/line-icons.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/app.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/application.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/header-v4.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/footer-v2.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/img-hover.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/page_job.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/shop.style.css" media="screen" rel="stylesheet">
	<script src="<%=basePath%>public/ZHQ/js/hm.js"></script>
	<script src="<%=basePath%>public/ZHQ/js/jquery.min.js"></script>
	<!--导航js-->
	<script src="<%=basePath%>public/ZHQ/js/jquery_ujs.js"></script>
	<script src="<%=basePath%>public/ZHQ/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
        <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/public/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/public/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/public/ueditor/lang/zh-cn/zh-cn.js"></script>
  <script type="text/javascript">
  $(function(){
	  
	  //alert("dasdasdasdsad");
  });
  function publishForPost(){
	  alert("123123123123");
      $.ajax({
          url:"",   
          contentType: "application/json;charset=UTF-8", 
          type:"POST",   //请求方式           
          success : function(topics) {     

          }
      });
  }
  function publishForReply(){
	  alert("123123123123");
  }
 </script>
  </head>
  <body>
  <div class="wrapper">
  <jsp:include page="/index_head.jsp"></jsp:include>

  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
           <ul class="breadcrumb margin-left-0">
           <li><a href="<%=basePath %>park/getIndex.do">论坛首页</a></li><li><a >帖子详情</a></li>
           </ul>
        <div class="clear"></div>
      </div>
   </div>
   
<div class="container content job-content ">
   <div class="col-md-12 p30_40 border1 margin-top-20">
     <h3 class="tc f30">
       <div class="title bbgrey ">${post.name }</div>
     </h3>
     <div class="p15_0" >
	     <div class="fr"><span>作者：${post.user.relName }</span>
	     <span class="ml15"><i class="mr5">
	     <img src="<%=basePath%>public/ZHQ/images/block.png"/></i>
	     <fmt:formatDate value='${post.publishedTime}' pattern="yyyy.MM.dd" />
	     </span>
	     </div>
     </div>
     
     <div class="clear margin-top-20 new_content f18">
        ${post.content }
     </div>
     
        <div class="fr">评论数:<span class="red">${post.replycount }</span></div>
     </div>
     
     <!-- 评论列表 -->
     <div class="col-md-12 p30_40 border1 margin-top-20">
     
        <c:forEach items="${post.replies }" var="reply" varStatus="vs">
        
        <div class="col-md-12 comment_main">
	        <div class="fl comment_pic mr10"><img src="<%=basePath%>public/ZHQ/images/logo.png"/></div>
	        <div class="comment_desc col-md-10 p0">
	          <div class="col-md-12 p0">
	          	          
	            <span class="comment_name">${vs.index+1 }楼  ${reply.user.relName }</span>
	            <span class="grey">[<fmt:formatDate value='${reply.publishedAt}' pattern="yyyy年MM月dd日" />]</span>
	            <span class="fr blue"><a onclick="publishForReply()">回复</a></span>
	            
	          </div>
              <div class="col-md-12 comment_cont p0">${reply.content }</div>
            </div>
        </div>  
             
        </c:forEach>
     </div>
     <!-- 分页Div -->
     
      <!-- 我要评论Div -->
     <div class="col-md-12 p30_40 border1 margin-top-20">
         <div class="clear col-md-12 p0">
          <span>评论标题：</span>  <input type="text" name="post.reply.name"/>
         </div>
         <div class="clear col-md-12 p0">
          <span>评论内容：</span>  <script id="editor" name="post.reply.content" type="text/plain" class= ""></script>
         </div>
         <div class="clear col-md-12 p0">
           <button class="btn btn-windows fr " type="submit" onclick="publishForPost()">发布</button>
         </div>    
     </div>
   </div>

 </div>
<!--底部代码开始-->
<jsp:include page="/index_bottom.jsp"></jsp:include>
   <script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
    </script>
</body>
</html>


