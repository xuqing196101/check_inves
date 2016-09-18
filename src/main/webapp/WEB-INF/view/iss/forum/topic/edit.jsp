<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">  
    <title></title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=basePath%>public/ZHQ/js/jquery.min.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	--><%--	
	<script type="text/javascript">    
	$(function(){ 
		$("#parkName").val("${topic.park.name}");
		});  
	function cheClick(){
		var parkId =$('input:radio[name="item"]:checked').val();
		var parkName=$('input:radio[name="item"]:checked').next().html();
		$("#parkId").val(parkId);
		$("#parkName").val(parkName);
	}
	</script>
	
  --%>
  	<script type="text/javascript">    
	$(function(){ 
		$("#park").val("${topic.park.id}");
		});  
	</script>
  </head>
  <body>
  
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a href="#">论坛管理</a></li><li class="active"><a href="#">主题管理</a></li><li class="active"><a href="#">主题修改</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 新增页面开始 -->
     <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
    <form action="<%=basePath %>topic/update.html" method="post">  
    <div>
	    <div class="headline-v2">
	   		<h2>修改主题</h2>
	   </div>
	    <input  name ="topicId" type="hidden" value = '${topic.id}'>
	   <ul class="list-unstyled list-flow p0_20">
	   		  
	   		   <li class="col-md-6  p0 ">
			   <span class="fl">主题名称：</span>
			   <div class="input-append">
		        <input class="span2"  type="text" name="name" value = '${topic.name}'>
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 
			 <li class="col-md-6  p0 ">
			   <span class="fl">所属版块：</span>
			   <%--				 
			 	 <div class="input-append">
	  			   <input class="span2" name ="parkId" id="parkId" type="hidden">
                   <input class="span2" name ="parkName" id="parkName" type="text">
		           <div class="btn-group">
                    <button aria-expanded="false" class="btn dropdown-toggle add-on" data-toggle="dropdown">
		              <img src="<%=basePath%>public/ZHH/images/down.png" >
                    </button>
                    <ul class="dropdown-menu list-unstyled" >
				  	  	<c:forEach items="${parks}" var="park">
							<li class="select_opt">
		          				<input type="radio" name="item" class="fl mt10" value="${park.id }" onclick="cheClick();" ><div  class="ml10 fl">${park.name}</div>		          				
		          			</li>
				  	  	</c:forEach> 
                    </ul>
                   </div>
                 </div>--%>
	  			<select name ="parkId" class="w230" >
					<option></option>
			  	  	<c:forEach items="${parks}" var="park">
			  	  		<option  value="${park.id}">${park.name}</option>
			  	  	</c:forEach> 
	  			</select>
			 </li>
			<li class="col-md-12  p0 ">	  	 			
				<span class="fl"> 主题介绍：</span>
				<div class="col-md-12 mt5 fn pl200 pwr9">
				<textarea  class="text_area col-md-12" name="content">${topic.content}</textarea>		
				</div>			
	  	 	</li>
	  	 </ul>
	</div>  	
	<!-- 底部按钮 -->			          
  <div  class="col-md-12 ml185">
   <div class="fl padding-10">
    <button class="btn btn-windows save" type="submit">更新</button>
    <button class="btn btn-windows reset" onclick="history.go(-1)" type="button">返回</button>
	</div>
  </div>
     </form>
     </div>
     </div>

  </body>
</html>

