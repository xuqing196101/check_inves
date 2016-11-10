<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title></title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

    <link href="${ pageContext.request.contextPath }/public/ZHQ/css/style.css" media="screen" rel="stylesheet">
    <link href="${ pageContext.request.contextPath }/public/ZHQ/css/forum.css" media="screen" rel="stylesheet">

  <script type="text/javascript">
  $(function(){
      $("#parkId").val("${parkId}");
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
                    //var postName = "${postName}";
                    var parkId = "${parkId}";
                    location.href = "${ pageContext.request.contextPath }/post/getHotlist.html?parkId="+parkId+"&page="+e.curr;
                }
            }
        });
  });
  
 </script>
  </head>
    
  <body>
  <jsp:include page="/index_head.jsp"></jsp:include>
  </div>
     <div class="margin-top-10 breadcrumbs ">
      <div class="container">
           <ul class="breadcrumb margin-left-0">
           <li><a href="${ pageContext.request.contextPath }/park/getIndex.html"> 论坛首页</a></li><li><a href="${ pageContext.request.contextPath }/post/getHotlist.html">社区精华帖</a></li>
           </ul>
        <div class="clear"></div>
      </div>
   </div>
    <div class="container content height-350 job-content ">

<div class="home_suggest_topics panel panel-default">

  <div class="panel-body topics row">
  
  <!-- 帖子div -->
  <c:forEach items="${list.list}" var="post">
    <div class="col-md-12 topics-group">
      <div class="topic media topic-31080">
        <!-- 左边头像 -->
        <div class="avatar media-left">
          <img class="media-object avatar-48" src="${ pageContext.request.contextPath }/public/ZHQ/images/boy.png" alt="196"></a>
        </div>
        <!-- 右边内容 -->
        <div class="infos media-body">
        <!-- 帖子名称 -->
         <div class="title media-heading">         
          <c:set value="${post.name}" var="content"></c:set>
          <c:set value="${fn:length(content)}" var="length"></c:set>
          <c:if test="${length>15}">
             <a  href='${ pageContext.request.contextPath }/post/getIndexDetail.html?postId=${post.id}' value='${fn:substring(content,0,15)}...'>${fn:substring(content,0,15)}...</a>
          </c:if>
          <c:if test="${length<15}">
             <a href='${ pageContext.request.contextPath }/post/getIndexDetail.html?postId=${post.id}' value='${post.name}'>${post.name}</a>
          </c:if>
         </div>
         <!-- 帖子关联关系以及其他消息 -->
         <div class="info">
            <span>${post.topic.name }</span>
                   •
               <span>${post.user.relName }</span>
               <span >
                   •                           
                <fmt:formatDate value='${post.publishedAt}' pattern="yyyy年MM月dd日 " />           
               </span>
              
               
         </div>        
        </div>
         <div class="count media-right">
         <span class="state-false" >${post.replycount }</span>
        </div>
       </div>
      </div>
      </c:forEach>
     <div id="pagediv" align="right"></div>    
    </div>
   </div>
  <div class="my_post f18">
  <a href='${ pageContext.request.contextPath }/post/publish.html'>我要发帖</a>
  </div>  
   
<!--底部代码开始-->
<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>

