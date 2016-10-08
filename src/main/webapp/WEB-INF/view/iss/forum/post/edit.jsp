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
	-->	
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.all.min.js"> </script>
	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript">    
	$(function(){ 
		$("#parks").val("${post.park.id}");
		$("#topics").val("${post.topic.id}");
		$("#isTop").val("${post.isTop}");
		$("#isLocking").val("${post.isLocking}");
		});
	//2级联动
	  function change(id){
			$.ajax({
			    url:"<%=basePath %>topic/getListForSelect.do?parkId="+id,   
			    contentType: "application/json;charset=UTF-8", 
			    dataType:"json",   //返回格式为json
			    type:"POST",   //请求方式		    
		        success : function(topics) {     
		            if (topics) {          	
		            	$("#topicId").html("<option></option>");              
		              $.each(topics, function(i, topic) {  
		            	  $("#topics").append("<option  value="+topic.id+">"+topic.name+"</option>");	            	  
		              });  	                          
		            }
		        }
			});
	  }
	</script>
  </head>
  <body>
  
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a >论坛管理</a></li><li class="active"><a >帖子管理</a></li><li class="active"><a >帖子修改</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 新增页面开始 -->
     <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
    <form action="<%=basePath %>post/update.html" method="post">  
    <div>
	    <div class="headline-v2">
	   		<h2>修改帖子</h2>
	   </div>
	    <input  name ="postId" type="hidden" value = '${post.id}'>
	   <ul class="list-unstyled list-flow p0_20">
	   		  
	   		  <li class="col-md-12  p0 ">
			   <span class="fl">帖子名称：</span>
			   <div class="input-append">
		        <input class="span2 w775"  type="text" name = "name" value='${post.name }'>
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 
			 <li class="col-md-6  p0 ">
			   <span class="fl">所属版块：</span>
			    <select id ="parks"name ="parkId" class="w220" onchange="change(this.options[this.selectedIndex].value)">
					<option></option>
			  	  	<c:forEach items="${parks}" var="park">
			  	  		<option  value="${park.id}">${park.name}</option>
			  	  	</c:forEach> 
	  			</select>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">所属主题：</span>				 	
	        	<select id="topics" name="topicId" class="w220 ">
	        	<option></option>
	        		<c:forEach items="${topics}" var="topic">
			  	  		<option  value="${topic.id}">${topic.name}</option>
			  	  	</c:forEach> 
	  			</select>
			 </li>
			 
			 <li class="col-md-6  p0 ">
			   <span class="fl">置顶：</span>
			   	<select id="isTop" name="isTop" class="w220 ">
			   	<option></option>
	        	<option value="0">不置顶</option>
	        	<option value="1">置顶</option>
	  			</select>				 	
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">锁定：</span>
			   	<select id="isLocking" name="isLocking" class="w220 ">
			   	<option></option>
	        	<option value="0">不锁定</option>
	        	<option value="1">锁定 </option>
	  			</select>	 	
			 </li>

			 
			<li class="col-md-12 p0">
	   			<span class="fl">帖子内容：</span>
	  			<div class="col-md-12 pl200 fn mt5 pwr9">
	  				 <script id="editor" name="content" type="text/plain" class="ml125 mt20 w900"></script>
       			</div>
			 </li>
			 

	  	 </ul>
	</div>  	
	<!-- 底部按钮 -->			          
    <div class="padding-top-10 clear">                
      <div  class="col-md-12 pl185 ">
       <div class="mt40 tc mb50">
    <button class="btn btn-windows save" type="submit">更新</button>
    <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
	</div>
  </div>
  </div>
     </form>
     </div>
     </div>
 <script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
    var content="${post.content}";
	ue.ready(function(){
  		ue.setContent(content);    
  		//ue.setDisabled([]);
	});
    
</script>
  </body>
</html>

