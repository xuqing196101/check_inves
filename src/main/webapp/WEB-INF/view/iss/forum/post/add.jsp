<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>My JSP 'add.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	 <script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
      var option ={
        toolbars: [[
                'undo', 'redo', '|',
                'bold', 'italic', 'underline',  'formatmatch', 'autotypeset', '|', 'forecolor', 'backcolor',                
                 'fontfamily', 'fontsize', '|',
                 'indent', '|',
                'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|','emotion',
                 'insertimage', 

            ]]

	    }
        var ue = UE.getEditor('editor',option);  
        var content='${post.content}';
        ue.ready(function(){
        ue.setContent(content);    
      });
	</script>
	<script type="text/javascript">
	$(function(){		  
		    var parkId = "${post.park.id}";
	        $("#park").val(parkId);	        
	        $.ajax({
	            url:"${ pageContext.request.contextPath }/topic/getListForSelect.do?parkId="+parkId,   
	            contentType: "application/json;charset=UTF-8", 
	            dataType:"json",   //返回格式为json
	            type:"POST",   //请求方式           
	            success : function(topics) {     
	                if (topics) {           
	                  $("#topics").html("<option></option>");                
	                  $.each(topics, function(i, topic) {  
	                      $("#topics").append("<option  value="+topic.id+">"+topic.name+"</option>");                     
	                  });  
	                  $("#topics").val("${post.topic.id}"); 
	                }
	            }
	        });
	        $("#isTop").val("${post.isTop}");
	        $("#isLocking").val("${post.isLocking}");
	        
	        ue.ready(function(){
	        	ue.setContent("${post.content}");   
	        });
	        
	});
	  //2级联动
	  function change(id){
			$.ajax({
			    url:"${ pageContext.request.contextPath }/topic/getListForSelect.do?parkId="+id,   
			    contentType: "application/json;charset=UTF-8", 
			    dataType:"json",   //返回格式为json
			    type:"POST",   //请求方式		    
		        success : function(topics) {     
		            if (topics) {          	
		            	$("#topics").html("");                
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
		   <li><a href="#"> 首页</a></li><li><a >论坛管理</a></li><li class="active"><a >帖子管理</a></li><li class="active"><a >增加帖子</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 新增页面开始 -->
     <div class="container container_box">
    <form action="${ pageContext.request.contextPath }/post/save.html" method="post" >  
    <div>
            <div class="headline-v2">
	   		  <h2 class="count_flow">新增帖子</h2>
	   		</div>
	   <ul class="ul_list mb20">
	   
	   		  <li class="col-md-3 margin-0 padding-0 ">
			   <span class="col-md-12 padding-left-5"><div class="red fl">*</div>帖子名称：</span>
			   <div class="input-append">
		        <input class="span2 w200"  type="text" name = "name" value='${post.name }'>
		        <span class="add-on">i</span>
		         <div class="cue">${ERR_name}</div>


			 
			 <li class="col-md-3 margin-0 padding-0">
			
			   <span class="col-md-12 padding-left-5"><div class="red fl">*</div>所属版块：</span>
			    <div class="select_common">
			    <select id ="park" name ="parkId" class="select w220" onchange="change(this.options[this.selectedIndex].value)">
					<option></option>
			  	  	<c:forEach items="${parks}" var="park">
			  	  		<option  value="${park.id}">${park.name}</option>
			  	  	</c:forEach> 
	  			</select>
	  			<div class="cue">${ERR_park}</div>
	  			</div>
			 </li>
			 <li class="col-md-3 margin-0 padding-0">
			   <span class="col-md-12 padding-left-5"><div class="red fl">*</div>所属主题：</span>
			    <div class="select_common">			 	
	        	<select id="topics" name="topicId" class="w220 ">
	        	<option></option>
	  			</select>
	  			<div class="cue">${ERR_topic}</div>
	  			</div>
			 </li>
			 
			 <li class="col-md-3 margin-0 padding-0">
			   <span class="col-md-12 padding-left-5 ">置顶：</span>
		          <div class="select_common">
			   	<select name="isTop" class="w220 ">
	        	<option value="0" selected="selected">不置顶</option>
	        	<option value="1">置顶</option>
	  			</select>	
	  				</div>		 	
			 </li>
			 <li class="col-md-3 margin-0 padding-0">
			   <span class="col-md-12 padding-left-5 ">锁定：</span>
			   <div class="select_common">
			   	<select name="isLocking" class="w220 ">
	        	<option value="0" selected="selected">不锁定</option>
	        	<option value="1">锁定 </option>
	  			</select>
	  			</div>	 	
			 </li>		
			 <input type="hidden" name="id" value='${id}'></input>	 
			<li class="col-md-11 margin-0 padding-0">
	   			<span class="col-md-12 padding-left-5"><div class="red fl">*</div> 帖子内容：</span>
	  			<div class="mb5">
	  				 <script id="editor" name="content" type="text/plain" class="ml125 mt20 w900"></script>
       			</div>
       			<div class="cue">${ERR_content}</div>
			 </li>  
			 <input type="hidden" name="id" value='${id}'></input>
	   		  <li class="col-md-12 p0">
		       <span class="zzzx w245">上传附件：</span>
		          <up:upload id="post_attach_up"  multiple="true" businessId="${id}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
                  <up:show showId="post_attach_show" businessId="${id}" sysKey="${sysKey}" typeId="${typeId}"/>
              </li>



	  	 </ul>
	<!-- 底部按钮 -->			       
    <div class="col-md-12 tc">
    	<button class="btn btn-windows save" type="submit">保存</button>
    	<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
	</div>
  </div>
     </form>
     </div>

  </body>
</html>

