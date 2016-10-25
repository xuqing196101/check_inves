<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>查看须知文档</title>
    
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.all.min.js"> </script>
	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/lang/zh-cn/zh-cn.js"></script>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
  
  <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">论坛管理</a></li><li class="active"><a href="#">版块管理</a></li><li class="active"><a href="#">版块详情</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
  <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
    	<div>
	    	<div class="headline-v2">
	   			<h2>须知文档详情</h2>
	   		</div>
	   		<ul class="list-unstyled list-flow p0_20">
	   		   <li class="col-md-6  p0 ">
			   <span class="fl">须知文档名称：</span>
			   <div class="input-append">
		        <input class="span2"  type="text" value = '${noticeDocument.name}' readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 
			 <li class="col-md-6  p0 ">
			   <span class="fl">须知文档类型：</span>				 	
	  			<div class="input-append">
		        <input class="span2"  type="text" value = '${noticeDocument.docType}' readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">创建时间：</span>				 	
	  			<div class="input-append">
		        <input class="span2"  type="text"  value="<fmt:formatDate value='${noticeDocument.createdAt}' pattern='yyyy-MM-dd'/>" readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">修改时间：</span>				 	
	  			<div class="input-append">
		        <input class="span2"  type="text" value="<fmt:formatDate value='${noticeDocument.updatedAt}' pattern='yyyy-MM-dd'/>" readonly="readonly">
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 
			<li class="col-md-12 p0">
	   			<span class="fl">须知文档内容：</span>
	  			<div class="col-md-12 pl200 fn mt5 pwr9">
	  				 <script id="editor" name="content" type="text/plain" class="ml125 mt20 w900"></script>
        			<!-- <textarea class="text_area col-md-12 " title="不超过800个字" placeholder="不超过800个字"></textarea> -->
       			</div>
			 </li> 
	  	 </ul>
	<!-- 底部按钮 -->			          
  <div  class="col-md-12 ml185">
   <div class="fl padding-10">
    <button class="btn btn-windows reset" onclick="history.go(-1)" type="button">返回</button>
	</div>
  </div>
	  	 
	</div>  	
     
     </div>
     </div>
     <script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
    var content="${noticeDocument.content}";
	ue.ready(function(){
  		ue.setContent(content);    
  		ue.setDisabled([]);
	});
    
</script>
  </body>
</html>
