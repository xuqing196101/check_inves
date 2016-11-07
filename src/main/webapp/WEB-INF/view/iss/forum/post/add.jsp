<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	<script type="text/javascript" charset="utf-8" src="${ pageContext.request.contextPath }//public/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ pageContext.request.contextPath }//public/ueditor/ueditor.all.min.js"> </script>
	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	<script type="text/javascript" charset="utf-8" src="${ pageContext.request.contextPath }//public/ueditor/lang/zh-cn/zh-cn.js"></script>
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
      var content="${post.content}";
      ue.ready(function(){
          ue.setContent(content);    
      });
	</script>
	<script type="text/javascript">
	$(function(){		  
		    var parkId = "${post.park.id}";
	        $("#park").val(parkId);	        
	        $.ajax({
	            url:"<%=basePath %>topic/getListForSelect.do?parkId="+parkId,   
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
	        ue.setContent("${post.content}");
	        
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
		            	$("#topics").html("");                
		              $.each(topics, function(i, topic) {  
		            	  $("#topics").append("<option  value="+topic.id+">"+topic.name+"</option>");	            	  
		              });  	                          
		            }
		        }
			});
	  }
	  function addAttach(){
	      html="<input id='atta' type='file' class='toinline' name='attaattach'/><a onclick='deleteattach(this)' class='toinline red redhover pointer'>x</a><br/>";
	      $("#uploadAttach").append(html);
	   }
	  
	   function addPic(){
	          html="<input id='pic' type='file' class='toinline' name='picattach'/><a onclick='deleteattach(this)' class='toinline red redhover pointer'>x</a><br/>";
	          $("#uploadPic").append(html);
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
		   <li><a href="#"> 首页</a></li><li><a >论坛管理</a></li><li class="active"><a >帖子管理</a></li><li class="active"><a >增加帖子</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 新增页面开始 -->
     <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
    <form action="<%=basePath %>post/save.html" method="post" >  
    <div>
	    <div class="headline-v2">
	   		<h2>新增帖子</h2>
	   </div>
	   <ul class="list-unstyled list-flow p0_20">
	   
	   		  <li class="col-md-12  p0 ">
			   <span class="fl"><div class="red star_red">*</div>帖子名称：</span>
			   <div class="input-append">
		        <input class="span2"  type="text" name = "name" value='${post.name }'>
		        <div class="validate">${ERR_name}</div>
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 
			 <li class="col-md-6  p0 ">
			   <span class="fl"><div class="red star_red">*</div>所属版块：</span>
			    <select id ="park" name ="parkId" class="select w220" onchange="change(this.options[this.selectedIndex].value)">
					<option></option>
			  	  	<c:forEach items="${parks}" var="park">
			  	  		<option  value="${park.id}">${park.name}</option>
			  	  	</c:forEach> 
	  			</select>
	  			<div class="validate">${ERR_park}</div>
			 </li>
			 <li class="col-md-6  p0 ">
			 
			   <span class="fl"><div class="red star_red">*</div>所属主题：</span>				 	
	        	<select id="topics" name="topicId" class="w220 ">
	        	<option></option>
	  			</select>
	  			<div class="validate">${ERR_topic}</div>
			 </li>
			 
			 <li class="col-md-6  p0 ">
			   <span class="fl">置顶：</span>
			   	<select name="isTop" class="w220 ">
	        	<option value="0" selected="selected">不置顶</option>
	        	<option value="1">置顶</option>
	  			</select>				 	
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">锁定：</span>
			   	<select name="isLocking" class="w220 ">
	        	<option value="0" selected="selected">不锁定</option>
	        	<option value="1">锁定 </option>
	  			</select>	 	
			 </li>

			 
			<li class="col-md-12 p0">
	   			<span class="fl"><div class="red star_red">*</div> 帖子内容：</span>
	  			<div class="col-md-12 pl200 fn mt5 pwr9">
	  				 <script id="editor" name="content" type="text/plain" class="ml125 mt20 w900"></script>
       			</div>
       			<div class="validate">${ERR_content}</div>
			 </li>  
	   		  <li class="col-md-12 p0">
		        <span class="fl">上传附件：</span>
		        <div class="fl" id="uploadAttach" >
		          <input id="atta" type="file" class="toinline" name="attaattach"/>
		          <input class="toinline" type="button" value="添加" onclick="addAttach()"/><br/>
		        </div>
		     </li>
		     <%--  
		       <li class="col-md-12 p0">
                <span class="fl">上传图片：</span>
                <div class="fl" id="uploadPic" >
                  <input id="pic" type="file" class="toinline" name="picattach"/>
                  <input class="toinline" type="button" value="添加" onclick="addPic()"/><br/>
                  <div class="validate">${ERR_pic}</div>
                </div>
             </li> 
	  	    --%>
	  	 </ul>
	</div>  	
	<!-- 底部按钮 -->			          
    <div class="padding-top-10 clear">                
      <div  class="col-md-12 pl185 ">
       <div class="mt40 tc mb50">
    <button class="btn btn-windows save" type="submit">保存</button>
    <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
	</div>
  </div>
  </div>
     </form>
     </div>
     </div>

  </body>
</html>

