<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改</title>
    
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.all.min.js"> </script>
	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/lang/zh-cn/zh-cn.js"></script>
    
<script type="text/javascript">

function cheClick(id,name){
	$("#articleTypeId").val(id);
	$("#articleTypeName").val(name);
}

window.onload=function(){
    var range="${article.range}";
   // alert(range);
    $("input[name='range'][value="+range+"]").attr("checked",true); 
}

</script>    
  </head>
  
  <body>
  
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">信息服务</a></li><li><a href="#">信息管理</a></li><li class="active"><a href="#">修改</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   <div class="container">
    <form action="<%=basePath %>article/update.do" method="post">
     <div class="headline-v2">
	   <h2>修改信息</h2>
	 </div>
	  <input type="hidden" name="id" id="id" value="${article.id }">
	  <input type="hidden" name="user.id" id="user.id" value="${article.user.id }">
	   <ul class="list-unstyled list-flow p0_20">
     <li class="col-md-6 p0">
	   <span class="">信息标题：</span>
	   <div class="input-append">
        <input class="span2" id="name" name="name" type="text" value="${article.name }">
        <span class="add-on">i</span>
       </div>
	 </li>
     <%--<li class="col-md-6  p0 ">
	   <span class="">录入时间：</span>
	   <div class="input-append">
        <input class="span2 Wdate w250" name="" id="" type="text" onclick='WdatePicker()'>
       </div>
	 </li> 
     --%>
     <li class="col-md-6  p0 ">
	   <span class="">发布范围：</span>
	   <div class="input-append">
        <label><input type="radio" name="range" id="0" value="0">内网</label>
        <label><input type="radio" name="range" id="1" value="1">外网</label>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">信息类型：</span>
	   <div class="input-append">
         <input class="span2" id="articleTypeId" name="articleType.id" type="hidden" value="${article.articleType.id }">
		 <input class="span2" id="articleTypeName" name="articleTypeName" type="text" value="${article.articleType.name }">
		 <div class="btn-group ">
          <button class="btn dropdown-toggle add-on" data-toggle="dropdown">
		  <img src="<%=basePath%>public/ZHH/images/down.png" class="margin-bottom-5"/>
          </button>
          <ul class="dropdown-menu list-unstyled">
          	<c:forEach items="${list}" var="list" varStatus="vs">
		          		<li class="select_opt">
		          			<input type="radio" name="chkItem" value="${list.id }" onclick="cheClick(${list.id },'${list.name }');" class="select_input">${list.name }
		          		</li>
		    </c:forEach>
          </ul>
       </div>
      </div>
	 </li> 
     <li class="col-md-12 p0">
	   <span class="fl">信息正文：</span>
	   <div class="col-md-12 pl200 fn mt5 pwr9">
         <script id="editor" name="content" type="text/plain" class="ml125 mt20 w900"></script>
       </div>
	 </li>  
  	 </ul> 
  	 <div class="headline-v2 clear">
	   <h2>上传附件</h2>
	  </div>
	  
       <div class="padding-left-40 padding-right-20 clear">
		   <ul class="list-unstyled  bg8 padding-20">
		    <li>1 . 仅支持jpg、jpeg、png、pdf等格式的文件;</li>
			<li>2 . 单个文件大小不能超过1M;</li>
			<li>3 . 上传文件的数量不超过10个;</li>
		   </ul>
	  </div>
	         
	 <div  class="col-md-12">
	   <div class="fl padding-10">
	    <button class="btn btn-windows save" type="submit">修改</button>
	    <input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
	</div>
  </div>
	         
     </form>
     
    </div>
<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
	var content="${article.content}";
	ue.ready(function(){
  		ue.setContent(content);    
	});
</script>
  </body>
</html>
