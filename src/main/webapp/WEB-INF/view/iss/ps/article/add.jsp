<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
    <title>新增</title>
    
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
	
	function addAttach(){
		html="<input id='pic' type='file' class='toinline' name='attaattach'/><a href='#' onclick='deleteattach(this)' class='toinline red redhover'>x</a><br/>";
		$("#uploadAttach").append(html);
	}
	
	function deleteattach(obj){
		$(obj).prev().remove();
		$(obj).next().remove();
		$(obj).remove();
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
   
   <div class="container">
    <form action="<%=basePath %>article/save.html" method="post" enctype="multipart/form-data">
     <div class="headline-v2">
	   <h2>新增信息</h2>
	 </div>
	   <ul class="list-unstyled list-flow p0_20">
     <li class="col-md-12 p0 mb0">
	   <span class=""><i class="red">＊</i>信息标题：</span>
	   <div class="w70p fl">
        <input class="col-md-12" id="name" name="name" type="text">
         <div class="validate">${ERR_name}</div>
         
       </div>
	 </li>

	 
     <li class="col-md-6  p0 ">
	   <span class=""><i class="red">＊</i>信息类型：</span>
	   <%-- <div class="input-append">
         <input class="span2" id="articleTypeId" name="articleType.id" type="hidden">
		 <input class="span2" id="articleTypeName" name="articleTypeName" type="text">
		 <div class="btn-group ">
          <button class="btn dropdown-toggle add-on" data-toggle="dropdown">
		  <img src="<%=basePath%>public/ZHH/images/down.png" />
          </button>
          <ul class="dropdown-menu list-unstyled">
          	<c:forEach items="${list}" var="list" varStatus="vs">
          		<li class="select_opt">
          			<input type="radio" name="chkItem" value="${list.id }" onclick="cheClick(${list.id },'${list.name }');" class="select_input">${list.name }
          		</li>
		    </c:forEach>
          </ul>
       </div>
       </div> --%>
       <div class="select_common mb10">
       <select name="articleType.id" class="w220">
          	<option></option>
          	<c:forEach items="${list}" var="list" varStatus="vs">
          		<option value="${list.id }" >${list.name }</option>
		    </c:forEach>
          </select>
          <div class="validate">${ERR_typeId}</div>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class=""><i class="red">＊</i>发布范围：</span>
	   <div class="input-append">
        <label class="fl margin-bottom-0"><input type="checkbox" name="ranges" value="0" class="mt0">内网</label>
        <label class="ml10 fl"><input type="checkbox" name="ranges" value="1" class="mt0">外网</label>
        <div class="validate">${ERR_range}</div>
       </div>
	 </li> 
	 <li class="col-md-6  p0 ">
	   <span class=""><i class="red">＊</i>文章来源：</span>
       <div class="input-append">
        <input class="span2" id="source" name="source" type="text">
         <div class="validate">${ERR_source}</div>
       </div>
	 </li> 
	 <li class="col-md-6  p0 ">
	   <span class=""><i class="red">＊</i>链接来源：</span>
       <div class="input-append">
        <input class="span2" id="sourceLink" name="sourceLink" type="text">
       </div>
	 </li>
	 
     <li class="col-md-12 p0">
	   <span class="fl"><i class="red">＊</i>信息正文：</span>
	   <div class="col-md-9 fl p0">
	   <script id="editor" name="content" type="text/plain" class="col-md-12 p0"></script>
       </div>
	 </li> 
	 
	 <li class="col-md-12 p0">
	    <span class="fl">上传附件：</span>
	    <div class="fl" id="uploadAttach" >
	      <input id="pic" type="file" class="toinline" name="attaattach"/>
	      <input class="toinline btn" type="button" value="添加" onclick="addAttach()"/><br/>
	    </div>
	 </li>
  	 </ul> 
	         
	 <div  class="col-md-12">
	   <div class="mt40 tc mb50">
	    <button class="btn btn-windows save" type="submit">保存</button>
	    <input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
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
