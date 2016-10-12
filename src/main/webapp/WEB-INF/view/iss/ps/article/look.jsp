<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>查看</title>
    
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.all.min.js"> </script>
	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/lang/zh-cn/zh-cn.js"></script>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>
    
<script type="text/javascript">

function cheClick(id,name){
	$("#articleTypeId").val(id);
	$("#articleTypeName").val(name);
}

$(function(){
	var range="${article.range}";
	if(range==2){
		$("input[name='range']").attr("checked",true); 
	}else{
		$("input[name='range'][value="+range+"]").attr("checked",true); 
	}
	$("#articleTypeId").val("${article.articleType.id }");
});

function sub(){
	var id = $("#id").val();
	alert(id);
	layer.confirm('您确定需要提交吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
		layer.close(index);
		window.location.href="<%=basePath%>article/sumbit.html?id="+id+"&status=1";
	});
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
     <div class="headline-v2">
	   <h2>查看信息</h2>
	 </div>
	  <input type="hidden" name="id" id="id" value="${article.id }" disabled>
	  <input type="hidden" name="user.id" id="user.id" value="${article.user.id }" disabled>
	   <ul class="list-unstyled list-flow p0_20">
     <li class="col-md-6 p0 mb0">
	   <span class="">信息标题：</span>
	   <div class="input-append">
        <input class="span2" type="text" value="${article.name }" disabled>
       </div>
	 </li>
     <li class="col-md-6 p0 ">
	   <span class="">发布范围：</span>
	   <div class="input-append">
        <label class="fl margin-bottom-0"><input type="checkbox" name="range" value="0" disabled class="">内网</label>
        <label class="ml10 fl"><input type="checkbox" name="range" value="1" disabled class="">外网</label>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">信息类型：</span>
	   <%-- <div class="input-append">
         <input class="span2" type="hidden" value="${article.articleType.id }" disabled>
		 <input class="span2" type="text" value="${article.articleType.name }" disabled>
		 <div class="btn-group ">
          <button class="btn dropdown-toggle add-on" data-toggle="dropdown">
		  <img src="<%=basePath%>public/ZHH/images/down.png" class="margin-bottom-5"/>
          </button>
          	<c:forEach items="${list}" var="list" varStatus="vs">
		          			<input type="radio" name="chkItem" value="${list.name }"  disabled>${list.name }
		    </c:forEach>
       </div>
      </div> --%>
       <select id="articleTypeId" name="articleType.id" class="w220" disabled>
   		 	<option></option>
          	<c:forEach items="${list}" var="list" varStatus="vs">
          		<option value="${list.id }" >${list.name }</option>
		    </c:forEach>
         </select>
	 </li> 
	  <li class="col-md-6  p0 ">
	   <span class="">文章来源：</span>
       <div class="input-append">
        <input class="span2" id="source" name="source" value="${article.source }"  type="text" disabled>
       </div>
	 </li> 
     <li class="col-md-12 p0">
	   <span class="fl">信息正文：</span>
	   <div class="col-md-9 fl p0">
         <script id="editor"  type="text/plain" class="col-md-12 p0"></script>
       </div>
	 </li>  
	 <li class="col-md-12 p0">
	 <span class="fl">已上传的附件：</span>
	 <div class="fl mt5">
  	   <c:forEach items="${article.articleAttachments}" var="a">
  	   	${fn:split(a.fileName, '_')[1]},
  	   </c:forEach>
	 </div>
	 </li>
  	 </ul>
	  
	  <c:if test="${article.status==2 }">
		 <div class="headline-v2 clear">
		   <h2>审核 <span class="blue ml40">审核通过 </span>
		   </h2>
		  </div>
	  </c:if>
	  
	  <c:if test="${article.status==3 }">
		 <div class="headline-v2 clear">
		   <h2>审核</h2>
		  </div>
		  
	       <div class="padding-top-10 clear">
			   <ul class="list-unstyled list-flow p0_20 ">
			     <li class="col-md-12 p0">
				   <span class="fl">退回理由：</span>
				   <div class="col-md-12 pl200 fn mt5 pwr9">
			        <textarea class="text_area col-md-12 " id="reason" name="reason"  disabled>${article.reason }</textarea>
			       </div>
				 </li> 
			   </ul>
		  </div>
	  </c:if>
	  
	  
	 <div  class="col-md-12">
	   <div class="mt40 tc mb50">
	    <%--<button class="btn btn-windows add" type="button" onclick="sub()">提交</button>
	    --%><input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
	</div>
  </div>
     
    </div>
<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
	var content="${article.content}";
	ue.ready(function(){
  		ue.setContent(content); 
  		ue.setDisabled(true);
	});
	
</script>
  </body>
</html>
