<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/tld/upload" prefix="up" %>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
    <title>新增</title>
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/upload/upload.css" type="text/css" />
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/public/select2/js/select2.js"></script>
	<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
    
<script type="text/javascript">
	function cheClick(id,name){
		$("#articleTypeId").val(id);
		$("#articleTypeName").val(name);
	}
	
	function addAttach(){
		html="<input id='pic' type='file' class='toinline' name='attaattach'/><a href='#' onclick='deleteattach(this)' class='toinline red redhover'>x</a><br/>";
		$("#uploadAttach").append(html);
	}
	
	function deleteattach(obj){
		$(obj).prev().remove();
		$(obj).next().remove();
		$(obj).remove();
	}

	function typeInfo(){
	//	$("#picNone").hide();
		$("#picshow").hide();
		var typeId = $("#articleTypes").select2("data").text;
		if(typeId=="工作动态"){
		//	$("#picNone").show();
			$("#picshow").show();
		}else{
		//	$("#picNone").hide();
			$("#picshow").hide();
		}
	}
	
	$(function(){
	//	$("#picNone").hide();
		$("#picshow").hide();
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
		    	  var typeId = $("#articleTypes").select2("data").text;
			  		if(typeId=="工作动态"){
			  			document.getElementById("picshow").style.display="";
			  		}
		       }
		});
	})
	
	function goBack(){
		window.location.href="${ pageContext.request.contextPath }/article/getAll.html";
	}
	
</script>    
 </head>
  
  <body>
  
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">信息服务</a></li><li><a href="#">信息管理</a></li><li class="active"><a href="#">新增</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   <div class="container container_box">
    <form action="${pageContext.request.contextPath }/article/save.html" method="post">
     <div class="">
	   <h2 class="count_flow">新增信息</h2>
	 
	   <ul class="ul_list mb20">
     	<li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5"><i class="red fl">＊</i>信息标题：</span>
	   <div class="input-append">
	   	<input class="span2"  name="id" type="hidden" value="${articleId }">
        <input class="span2" id="name" name="name" value="${article.name }" type="text">
        <span class="add-on">i</span>
         <div class="cue">${ERR_name}</div>  
       </div>
	 </li>

     <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5"><div class="star_red">＊</div>信息类型：</span>
	   <div class="mb5 select_common">
       <select id="articleTypes" name="articleType.id" class="select w220" onchange="typeInfo()">
          </select>
          <div class="cue">${ERR_typeId}</div>
        </div>
	 </li> 
     <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5"><div class="star_red">＊</div>发布范围：</span>
	   <div class="input-append">
        <label class="fl margin-bottom-0"><input type="checkbox" name="ranges" value="0" class="mt0">内网</label>
        <label class="ml10 fl"><input type="checkbox" name="ranges" value="1" class="mt0">外网</label>
        <div class="cue">${ERR_range}</div>
       </div>
	 </li> 
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">文章来源：</span>
       <div class="input-append">
        <input class="span2" id="source" name="source" value="${article.source }" type="text">
        <span class="add-on">i</span>
         <div class="cue">${ERR_source}</div>
       </div>
	 </li> 
	 <li class="col-md-3 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5">链接来源：</span>
       <div class="input-append">
        <input class="span2" id="sourceLink" name="sourceLink" value="${article.sourceLink }" type="text">
         <span class="add-on">i</span>
       </div>
	 </li>

	 <li class="col-md-3 margin-0 padding-0" id="picshow" style="display:none">
	   <span class="">图片展示：</span>
	   <div class="input-append">
        <input class="span2" id="isPicShow" name="isPicShow" value="${article.isPicShow }" type="text">
      	<div class="cue">${ERR_isPicShow}</div>
       </div>
	 </li>
	 
     <li class="col-md-11 margin-0 padding-0">
	   <span class="col-md-12 padding-left-5"><i class="red fl">＊</i>信息正文：</span>
	   <div class="mb5">
	   <script id="editor" name="content" type="text/plain" class="col-md-12 p0"></script>
       </div>
	 </li> 

	 <li class="col-md-3 p0 mt10">
	    <span class="fl">附件上传：</span>
	    <div class="fl">
	        <up:upload id="artice_file_up" groups="artice_up,artice_file_up" businessId="${articleId }" sysKey="${articleSysKey}" typeId="${artiAttachTypeId }" multiple="true" auto="true" />
			<up:show showId="artice_file_show" groups="artice_show,artice_file_show" businessId="${articleId }" sysKey="${articleSysKey}" typeId="${artiAttachTypeId }" />
		</div>
	 </li>
	 
	 <li class="col-md-3 p0 mt10" id="picNone">
	    <span class="fl">图片上传：</span>
	    <div class="fl">
	        <up:upload id="artice_up" groups="artice_up,artice_file_up" businessId="${articleId }" sysKey="${sysKey}" typeId="${attachTypeId }" auto="true" />
			<up:show showId="artice_show" groups="artice_show,artice_file_show" businessId="${articleId }" sysKey="${sysKey}" typeId="${attachTypeId }"/>
		</div>
	 </li>
	 
  	 </ul> 
	         
	 <div  class="col-md-12 tc">
	    <button class="btn btn-windows save" type="submit">保存</button>
	    <input class="btn btn-windows back" value="返回" type="button" onclick="goBack()">
	</div>
  </div>
</form>
  </div>
    
<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
</script>
     
</body>
</html>
