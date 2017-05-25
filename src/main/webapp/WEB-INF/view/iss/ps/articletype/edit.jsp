<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
		<script type="text/javascript">    
   		$(function(){ 
       	 $("#oldName").val($("#typeName").val());
    	}); 
    
    	function gotoList(){
    		window.location.href="${pageContext.request.contextPath}/articletype/getAll.html";
    	}
	</script>
  </head>
  <body>
  
  
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		  <ul class="breadcrumb margin-left-0">
			  <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
			  <li><a href="javascript:void(0);">信息服务</a></li>
			  <li><a href="javascript:void(0);">门户管理</a></li>
			  <li><a href="javascript:jumppage('${pageContext.request.contextPath}/articletype/getAll.html');">栏目管理</a></li>
			  <li class="active"><a href="javascript:void(0);">栏目修改</a></li>
		  </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 新增页面开始 -->
     <div class="container container_box">
    <form action="${pageContext.request.contextPath}/articletype/update.html" method="post">  
    <div>
      <h2 class="list_title">修改栏目</h2>
	    <input name ="articletypeId" type="hidden" value = '${articletype.id}'>
	   <ul class="ul_list mb20">
	   		  
	   		   <li class="col-md-3 col-sm-6 col-xs-12 pl15">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red fl">*</div>栏目名称：</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input id="typeName" type="text" name="name" value = '${articletype.name}'>
		        <div class="cue">${ERR_name}</div>
		        <span class="add-on">i</span>
		       </div>
			 </li>
			<li class="col-md-12 col-sm-12 col-xs-12">	  	 			
				<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">栏目介绍：</span>
				<div class="col-md-12 col-sm-12 col-xs-12 p0">
				<textarea  class="h130 col-md-12 col-sm-12 col-xs-12 p0" name="describe">${articletype.describe}</textarea>		
				</div>			
	  	 	</li>
	  	 </ul> 	
	<!-- 底部按钮 -->			          
  <div  class="col-md-12 tc ">
    <button class="btn btn-windows save" type="submit">更新</button>
    <button class="btn btn-windows back" onclick="gotoList()" type="button">返回</button>
  </div>
     </div>
     </form>
     </div>
  </body>
</html>
