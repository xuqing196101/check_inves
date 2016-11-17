<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/tld/upload" prefix="up" %>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>查看</title>
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/upload/upload.css" type="text/css" />
    
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
		window.location.href="${ pageContext.request.contextPath }/article/sumbit.html?id="+id+"&status=1";
	});
}

</script>    
  </head>
  
  <body>
  
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">信息服务</a></li><li><a href="javascript:void(0)">信息管理</a></li><li class="active"><a href="javascript:void(0)">查看</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   <div class="container container_box">
     <div>
	   <h2 class="count_flow"><i>1</i>查看信息</h2>
	  <input type="hidden" name="id" id="id" value="${article.id }" disabled>
	  <input type="hidden" name="user.id" id="user.id" value="${article.user.id }" disabled>
	   <ul class="ul_list mb20">
	   
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">信息标题：</span>
	   <div class="input-append">
        <input class="span2" type="text" value="${article.name }" disabled>
        <span class="add-on">i</span>
       </div>
	 </li>
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">信息类型：</span>
	   <div class="mb5">
       <select id="articleTypeId" name="articleType.id" class="w220" disabled>
   		 	<option></option>
          	<c:forEach items="${list}" var="list" varStatus="vs">
          		<option value="${list.id }" >${list.name }</option>
		    </c:forEach>
         </select>
         </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">发布范围：</span>
	   <div class="input-append">
        <label class="fl margin-bottom-0"><input type="checkbox" name="range" value="0" disabled class="">内网</label>
        <label class="ml10 fl"><input type="checkbox" name="range" value="1" disabled class="">外网</label>
       </div>
	 </li>  
	  <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">文章来源：</span>
       <div class="input-append">
        <input class="span2" id="source" name="source" value="${article.source }"  type="text" disabled>
        <span class="add-on">i</span>
       </div>
	 </li> 
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5"><i class="red">＊</i>链接来源：</span>
       <div class="input-append">
        <input class="span2" id="sourceLink" name="sourceLink" value="${article.sourceLink }" type="text" disabled>
        <span class="add-on">i</span>
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0" id="picshow">
	   <span class="">图片展示：</span>
	   <div class="input-append">
        <input class="span2" id="isPicShow" name="isPicShow" type="text" value="${article.isPicShow }" disabled>
       </div>
	 </li> 
     <li class="col-md-11 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">信息正文：</span>
	   <div class="mb5">
         <script id="editor"  type="text/plain" class="col-md-12 p0"></script>
       </div>
	 </li>  
	 <li class="col-md-12 p0 mt10" id="picNone" >
	    <span class="fl">图片上传：</span>
	    <div class="fl">
			<up:show showId="artice_show" businessId="${article.id }" sysKey="${sysKey}" typeId="${attachTypeId }"/>
		</div>
	 </li>
	 <li class="col-md-12 p0 mt10">
	 <span class="fl">已上传的附件：</span>
	 <div class="fl mt5">
  	   <c:forEach items="${article.articleAttachments}" var="a">
  	   	${fn:split(a.fileName, '_')[1]},
  	   </c:forEach>
	 </div>
	 </li>
  	 </ul>
	 </div>

	  <c:if test="${article.status==2 }">
	   <div class="padding-top-10 clear">
	  <h2 class="count_flow"><i>2</i>审核结果</h2>
	   <ul class="ul_list mb20">
	  	<li class="col-md-3 margin-0 padding-0 ">  	
	  	<span class="col-md-12 padding-left-5">审核结果：</span>
	   <div class="input-append">
        <input class="span2" type="text" value="审核通过" disabled>
        <span class="add-on">i</span>
       </div>
	 	</li>  
		</ul>
	  </c:if>
	  
	  <c:if test="${article.status==3 }">
	   <div class="padding-top-10 clear">
	  <h2 class="count_flow"><i>2</i>审核结果</h2>
	   <ul class="ul_list mb20">
	  	<li class="col-md-11 margin-0 padding-0">
	   		<span class="col-md-12 padding-left-5">退回理由</span>
	   		<div class="mb5">
				<textarea class="h130 col-md-12 " id="reason" name="reason"  disabled>${article.reason }</textarea>
       		</div>
	 	</li> 
		</ul>
</c:if>
	 <div  class="col-md-12 tc">
	  <input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
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
