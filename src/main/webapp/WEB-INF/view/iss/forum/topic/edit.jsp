<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
		<%@ include file="/WEB-INF/view/common.jsp"%>
  	<script type="text/javascript">
			//返回到主题列表
			function back(){
				window.location.href = "${pageContext.request.contextPath }/topic/backTopic.html";
			}
	</script>
  </head>
  <body>
  
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);">首页</a></li><li><a >论坛管理</a></li><li class="active"><a >主题管理</a></li><li class="active"><a >主题修改</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 新增页面开始 -->
     <div class="container container_box">
    <form action="${pageContext.request.contextPath }/topic/update.html" method="post">  
    <div>
	   <h2 class="list_title">修改主题</h2>

	   <input name ="topicId" type="hidden" value='${topic.id}'>
	   <ul class="ul_list mb20">
	   		  
	   		   <li class="col-md-3 col-sm-6 col-xs-12 pl15">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>主题名称：</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input class="span2"  type="text" name="name" value = '${topic.name}'>
		        <span class="add-on">i</span>
		        <div class="cue">${ERR_name}</div>
		       </div>
			 </li>
			 
			 <li class="col-md-3 col-sm-6 col-xs-12">			 
			   <span class="col-md-12 padding-left-5"> <div class="red star_red">*</div>所属版块：</span>
			   <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
	  			<select  id ="park" name ="parkId" class="col-md-12 col-sm-12 col-xs-12 p0 contract_name" >
					<option></option>
			  	  	<c:forEach items="${parks}" var="park">
			  	  		<c:choose>
			  	  			<c:when test="${parkId==park.id }">
			  	  				<option value="${park.id}" selected="selected">${park.name}</option>
			  	  			</c:when>
			  	  			<c:otherwise>
			  	  				<option value="${park.id}">${park.name}</option>
			  	  			</c:otherwise>
			  	  		</c:choose>
			  	  		
			  	  	</c:forEach> 
	  			</select>
	  			<div class="cue">${ERR_park}</div>
	  			</div>
			 </li>
			<li class="col-md-12 col-sm-12 col-xs-12">	  	 			
				<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"> 主题介绍：</span>
				<div class="col-md-12 col-sm-12 col-xs-12 p0">
					<textarea  class="h130 col-md-12 col-sm-12 col-xs-12 p0" name="content">${topic.content}</textarea>		
				</div>			
	  	 	</li>
	  	 </ul>
	<!-- 底部按钮 -->			          
    <div class="col-md-12 col-sm-12 col-xs-12 tc">         
    	<button class="btn btn-windows save" type="submit">更新</button>
    	<button class="btn btn-windows back" onclick="back()" type="button">返回</button>
  	</div>
  	</div>
    </form>
    </div>
  </body>
</html>

