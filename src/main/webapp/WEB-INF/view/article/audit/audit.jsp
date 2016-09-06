<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>审核信息</title>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>
    
<script type="text/javascript">

window.onload=function(){
    var range="${article.range}";
    $("input[name='range'][value="+range+"]").attr("checked",true); 
}

function sub(){
	var id = $("#id").val();
	alert(id);
	layer.confirm('您确定需要审核通过吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
		layer.close(index);
		window.location.href="<%=basePath%>article/audit.do?id="+id+"&status=2";
	});
}

function back(){
	var id = $("#id").val();
	var reason = $("#reason").val();
	layer.confirm('您确定需要退回吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
		layer.close(index);
		$.ajax({
			type:"post",
			//contentType: "application/json;charset=UTF-8",
			dataType:"json",
			url:"<%=basePath%>article/audit.do?id="+id+"&reason="+reason+"&status=3",
			
			success:function(){
				window.location.href="<%=basePath%>article/getAll.do";
			}
		});
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
	   <h2>审核信息</h2>
	 </div>
	  <input type="hidden" name="id" id="id" value="${article.id }" disabled>
	  <input type="hidden" name="user.id" id="user.id" value="${article.user.id }" disabled>
	   <ul class="list-unstyled list-flow p0_20">
     <li class="col-md-6 p0">
	   <span class="">信息标题：</span>
	   <div class="input-append">
        <input class="span2" type="text" value="${article.name }" disabled>
        <span class="add-on">i</span>
       </div>
	 </li>
     <li class="col-md-6  p0 ">
	   <span class="">发布范围：</span>
	   <div class="input-append">
        <label><input type="radio" name="range" id="0" value="0" disabled>内网</label>
        <label><input type="radio" name="range" id="1" value="1" disabled>外网</label>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">信息类型：</span>
	   <div class="input-append">
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
      </div>
	 </li> 
     <li class="col-md-12 p0">
	   <span class="fl">信息正文：</span>
	   <div class="col-md-12 pl200 fn mt5 pwr9">
         <script id="editor"  type="text/plain" class="ml125 mt20 w900"></script>
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
	  
	  <div class="headline-v2 clear">
	   <h2>审核</h2>
	  </div>
	  
       <div class="padding-top-10 clear">
		   <ul class="list-unstyled list-flow p0_20 ">
		     <li class="col-md-12 p0">
			   <span class="fl">退回理由：</span>
			   <div class="col-md-12 pl200 fn mt5 pwr9">
		        <textarea class="text_area col-md-12 " id="reason" name="reason" title="不超过250个字" placeholder="不超过250个字"></textarea>
		       </div>
			 </li> 
		   </ul>
	  </div>
	  
	         
	 <div  class="col-md-12">
	   <div class="fl padding-10">
	    <button class="btn btn-windows add" type="button" onclick="sub()">审核</button>
	    <button class="btn btn-windows reset" type="button" onclick="back()">驳回</button>
	    <input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
	</div>
  </div>
     
    </div>
  </body>
</html>
