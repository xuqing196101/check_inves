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
      laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${list.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total: "${list.total}",
          startRow: "${list.startRow}",
          endRow: "${list.endRow}",
          groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
            curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
                var page = location.search.match(/page=(\d+)/);
                return page ? page[1] : 1;
            }(), 
            jump: function(e, first){ //触发分页后的回调
                if(!first){ //一定要加此判断，否则初始时会无限刷新
                    var postId = "${post.id}";
                    location.href = "<%=basePath%>post/getIndexDetail.do?postId="+postId+"&page="+e.curr;
                }
            }
        });

  });
  function publishForPost(postId){
	  var ue = UE.getEditor('editor');
	  var text = ue.getContentTxt();
      $.ajax({
          url:"<%=basePath%>reply/save.html?postId="+postId+"&content="+text,   
          contentType: "application/json;charset=UTF-8", 
          type:"POST",   //请求方式           
          success : function() {     
        	  location.href = "<%=basePath%>post/getIndexDetail.do?postId="+postId;
          }
      });
  }
  function writeHtml(id){
	  var pu = $("#"+id);
	  var html = $("#publish").html();	  
	  alert(pu.next().has('div').length);
	  if(pu.next().has('div').length != 0 ){
		  pu.after(html);  
	      $("#publishButton").attr("onclick","publishForReply('"+id+"')");
	 }
	   
	  	   
  }
  function publishForReply(replyId){
	 var ue = UE.getEditor('editor');
	 var text = ue.getContentTxt();
	 var postId = "${post.id}";
	   $.ajax({
       url:"<%=basePath%>reply/save.html?postId="+postId+"&content="+text+"&replyId="+replyId,   
       contentType: "application/json;charset=UTF-8", 
       type:"POST",   //请求方式           
       success : function() {   
           var postId = "${post.id}";
           location.href = "<%=basePath%>post/getIndexDetail.do?postId="+postId;
           }
     });
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
	     <fmt:formatDate value='${post.publishedAt}' pattern="yyyy.MM.dd" />
	     </span>
	     <span class="ml15">评论数：<span class="red">${post.replycount }</span></span>
	     </div>
     </div>
     
     <div class="clear margin-top-20 new_content f18">
        ${post.content }
     </div>   
     </div>
     
     <!-- 回复列表 -->
     <div class="col-md-12 p30_40 border1 margin-top-20">
     
        <c:forEach items="${list.list}" var="reply" varStatus="vs">         
            <div id="${reply.id}" class="col-md-12 comment_main">
            <div class="fl comment_pic mr10"><img src="<%=basePath%>public/ZHQ/images/logo.png"/></div>
            <div class="comment_desc col-md-10 p0">
              <div class="col-md-12 p0">
                          
                <span class="comment_name">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}楼  </span>
                <span>@ ${reply.user.relName }</span>
                <span class="grey">[<fmt:formatDate value='${reply.publishedAt}' pattern="yyyy年MM月dd日" />]：${reply.content }</span>
                <c:forEach items="${reply.replies }" var="replytoreply">
                    <span>@ ${replytoreply.user.relName } [<fmt:formatDate value='${replytoreply.publishedAt}' pattern="yyyy年MM月dd日" />]：${replytoreply.content }</span>:                 
                </c:forEach>
                
                <span class="fr blue pointer" onclick="writeHtml('${reply.id}')">回复</span>
                
              </div>

              
            </div>
            </div> 
            
        </c:forEach>
     </div>
     <div></div>
     <!-- 分页Div -->
     <div id="pagediv" align="right"></div>  
      <!-- 我要评论Div -->
     <div class="col-md-12 p30_40 border1 margin-top-20" id="publish">
         <div class="clear col-md-12 p0">
          <span class="f18 b">我要回复</span> 
         </div>
         <div class="clear col-md-12 p0 mt10">
          <span>评论内容：</span>  <script id="editor" name="content" type="text/plain" class= ""></script>
         </div>
         <div class="clear col-md-12 p0">
           <button class="btn btn-windows fr " id ="publishButton" onclick="publishForPost('${post.id}')">发布</button>
         </div>    
     </div>
   </div>

 </div>
 
  <div class="my_post f18">
  <a href='<%=basePath %>post/publish.html'>我要发帖</a>
  </div>
<!--底部代码开始-->
<jsp:include page="/index_bottom.jsp"></jsp:include>
   <script type="text/javascript">
    //自定义实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
        var option ={
        toolbars: [[
                'undo', 'redo', '|',
                'bold', 'italic', 'underline',  'formatmatch', 'autotypeset', '|', 'forecolor', 'backcolor',                
                 'fontfamily', 'fontsize', '|',
                 'indent', '|',
                'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 
            ]]

    };
    UE.getEditor('editor',option);
    </script>
</body>3
</html>


