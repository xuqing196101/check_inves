<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/tld/upload" prefix="up" %>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改</title>
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/upload/upload.css" type="text/css" />
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/public/select2/js/select2.js"></script>
	<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
	<script src="${pageContext.request.contextPath }/public/select2/js/select2_locale_zh-CN.js"></script>
    
<script type="text/javascript">
function cheClick(id,name){
	$("#articleTypeId").val(id);
	$("#articleTypeName").val(name);
}

$(function(){
	var range="${article.range}";
	if(range==2){
		$("input[name='ranges']").attr("checked",true); 
	}else{
		$("input[name='ranges'][value="+range+"]").attr("checked",true); 
	}
});

function addAttach(){
	html="<input id='pic' type='file' class='toinline' name='attaattach'/><a href='#' onclick='deleteattach(this)' class='toinline red redhover'>x</a><br/>";
	$("#uploadAttach").append(html);
}

function deleteattach(obj){
	$(obj).prev().remove();
	$(obj).next().remove();
	$(obj).remove();
}

var ids="";
function deleteAtta(id,obj){
	ids+=id+",";
	$("#ids").val(ids);
	alert(ids);
	$(obj).prev().remove();
	$(obj).remove();
}

$(function(){
	$.ajax({
		 contentType: "application/json;charset=UTF-8",
		  url:"${pageContext.request.contextPath }/article/selectAritcleType.do",
	      type:"POST",
	      dataType: "json",
	      success:function(articleTypes){
	    	  if(articleTypes){
	    		  $("#articleTypes").append("<option></option>");
	    		  $.each(articleTypes,function(i,articleType){
	    			  if(articleType.name != null && articleType.name != ''){
	    				  $("#articleTypes").append("<option value="+articleType.id+">"+articleType.name+"</option>");
	    			  }
	    		  });
	    	  }
	    	  $("#articleTypes").select2();
	    	  $("#articleTypes").select2("val", "${article.articleType.id }");
	       }
	});
})

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
   
   <div class="container container_box">
    <form id="newsForm" action="${pageContext.request.contextPath }/article/update.html" enctype="multipart/form-data" method="post">
    <input type="hidden" id="ids" name="ids"/>
	   <h2 class="count_flow"><i>1</i>修改信息</h2>
	  
	   <ul class="ul_list mb20">
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">信息标题：</span>
	   <div class="input-append">
		 <input type="hidden" name="id" value="${article.id }">
		  <input type="hidden" name="status" id="status" value="${article.status }">
		  <input type="hidden" name="user.id" id="user.id" value="${article.user.id }">
        <input class="span2" id="name" name="name" type="text" value="${article.name }">
       	<span class="add-on">i</span>
       </div>
       <div class="validate">${ERR_name}</div>
	 </li>
	 
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5"><i class="red fl">＊</i>信息类型：</span>
	   <div class="mb5">
       <select id="articleTypes" name="articleType.id" class="select w220" onchange="typeInfo()">
          </select>
          </div>
          <div class="validate">${ERR_typeId}</div>
	 </li> 
	 
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">发布范围：</span>
	   <div class="input-append">
        <label class="fl margin-bottom-0"><input type="checkbox" name="ranges" value="0" class="mt0">内网</label>
        <label class="ml10 fl"><input type="checkbox" name="ranges" value="1" class="mt0">外网</label>
       </div>
       <div class="validate">${ERR_range}</div>
	 </li> 
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">文章来源：</span>
       <div class="input-append">
        <input class="span2" id="source" name="source" value="${article.source }"  type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5"><i class="red fl">＊</i>链接来源：</span>
       <div class="input-append">
        <input class="span2" id="sourceLink" name="sourceLink" value="${article.sourceLink }" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>
	 <li class="col-md-3 margin-0 padding-0" id="picshow">
	   <span class="">图片展示：</span>
	   <div class="input-append">
        <input class="span2" id="isPicShow" name="isPicShow" type="text" value="${article.isPicShow }">
       	<span class="add-on">i</span>
       </div>
       <div class="validate">${ERR_isPicShow}</div>
	 </li> 
	 
     <li class="col-md-11 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">信息正文：</span>
	   <div class="mb5">
         <script id="editor" name="content" type="text/plain" class="col-md-12 p0"></script>
       </div>
	 </li>  
	 
	 <li class="col-md-12 p0 mt10" id="picNone" >
	    <span class="fl">图片上传：</span>
	    <div class="fl">
	        <up:upload id="artice_up" groups="artice_up,artice_file_up" businessId="${articleId }" sysKey="${sysKey}" typeId="${attachTypeId }" auto="true" />
			<up:show showId="artice_show" groups="artice_show,artice_file_show" businessId="${articleId }" sysKey="${sysKey}" typeId="${attachTypeId }"/>
		</div>
	 </li>
	 
	 <li class="col-md-12 p0 mt5">
	 <span class="fl">已上传的附件：</span>
	 <div class="fl">
	        <up:upload id="artice_file_up" groups="artice_up,artice_file_up" businessId="${articleId }" sysKey="${articleSysKey}" typeId="${artiAttachTypeId }" multiple="true" auto="true" />
			<up:show showId="artice_file_show" groups="artice_show,artice_file_show" businessId="${articleId }" sysKey="${articleSysKey}" typeId="${artiAttachTypeId }" />
	 </div>
	 </li>
	 <%--<li class="col-md-12 p0 mt5">
	    <span class="f14 fl">上传附件：</span>
	    <div class="fl" id="uploadAttach" >
	      <input id="pic" type="file" class="toinline" name="attaattach"/>
	      <input class="toinline btn" type="button" value="添加" onclick="addAttach()"/><br/>
	    </div>
	 </li>
  	 --%></ul> 
	         
	 <div  class="col-md-12 tc">
	    <button class="btn btn-windows edit" type="submit">修改</button>
	    <input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
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
