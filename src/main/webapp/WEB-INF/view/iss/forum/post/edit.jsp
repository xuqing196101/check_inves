<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="${ pageContext.request.contextPath }/public/ZHQ/js/jquery.min.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->	
	<script type="text/javascript" charset="utf-8" src="${ pageContext.request.contextPath }//public/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ pageContext.request.contextPath }//public/ueditor/ueditor.all.min.js"> </script>
	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	<script type="text/javascript" charset="utf-8" src="${ pageContext.request.contextPath }//public/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript">    
	$(function(){ 
		$("#parks").val("${post.park.id}");
		$("#topics").val("${post.topic.id}");
		$("#isTop").val("${post.isTop}");
		$("#isLocking").val("${post.isLocking}");
		
	      var file = "${post.postAttachments}";
	        if(file.length == 2){
	            $("#file").hide();
	        }	
		
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
	  function addAttach(){
		    html="<input id='pic' type='file' class='toinline' name='attaattach'/><a href='#' onclick='deleteattach(this)' class='toinline red redhover'>x</a><br/>";
		    $("#uploadAttach").append(html);
		}

		function deleteattach(obj){
		    $(obj).prev().remove();
		    $(obj).next().remove();
		    $(obj).remove();
		}

		var ids="";
		function deleteAtta(id,obj){
		    ids+=id+",";
		    $("#ids").val(ids);
		    $(obj).prev().remove();
		    $(obj).remove();
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
     <div class="container container_box">
    <form action="${ pageContext.request.contextPath }/post/update.html" method="post" enctype="multipart/form-data">  
      <input type="hidden" id="ids" name="ids"/>
    <div>

	   		<h2 class="count_flow"><i>1</i>修改帖子</h2>

	    <input  name ="postId" type="hidden" value = '${post.id}'>
	   <ul class="ul_list mb20">
	   		  
	   		  <li class="col-md-3 margin-0 padding-0">
			   <span class="col-md-12 padding-left-5"><div class="red fl">*</div>帖子名称：</span>
			   <div class="input-append">
		        <input class="span2 w200"  type="text" name = "name" value='${post.name }'>
		        <span class="add-on">i</span>
		        <div class="validate">${ERR_name}</div>
		       </div>
			 </li>
			 
			 <li class="col-md-3 margin-0 padding-0">
			   <span class="col-md-12 padding-left-5"><div class="red fl">*</div>所属版块：</span>
			   <div class="select_common">
			    <select id ="parks"name ="parkId" class="w220" onchange="change(this.options[this.selectedIndex].value)">
					<option></option>
			  	  	<c:forEach items="${parks}" var="park">
			  	  		<option  value="${park.id}">${park.name}</option>
			  	  	</c:forEach> 
	  			</select>
	  			</div>
	  			<div class="validate">${ERR_park}</div>
			 </li>
			 <li class="col-md-3 margin-0 padding-0">
			   <span class="col-md-12 padding-left-5"><div class="red fl">*</div>所属主题：</span>
			   	<div class="select_common">				 	
	        	<select id="topics" name="topicId" class="w220 ">
	        	<option></option>
	        		<c:forEach items="${topics}" var="topic">
			  	  		<option  value="${topic.id}">${topic.name}</option>
			  	  	</c:forEach> 
	  			</select>
	  			</div>
	  			<div class="validate">${ERR_topic}</div>
			 </li>
			 
			 <li class="col-md-3 margin-0 padding-0">
			   <span class="col-md-12 padding-left-5">置顶：</span>
			    <div class="select_common">	
			   	<select id="isTop" name="isTop" class="w220 ">
	        	<option value="0" selected="selected">不置顶</option>
	        	<option value="1">置顶</option>
	  			</select>				 	
	  			</div>
			 </li>
			 <li class="col-md-3 margin-0 padding-0">
			   <span class="col-md-12 padding-left-5 ">锁定：</span>
			    <div class="select_common">	
			   	<select id="isLocking" name="isLocking" class="w220 ">
	        	<option value="0" selected="selected">不锁定</option>
	        	<option value="1">锁定 </option>
	        	</div>
	  			</select>	 	
			 </li>			 
			<li class="col-md-11 margin-0 padding-0">
	   			<span class="col-md-12 padding-left-5"><div class="red fl">*</div>帖子内容：</span>
	  			<div class="mb5">
	  				 <script id="editor" name="content" type="text/plain" class="ml125 mt20 w900"></script>
       			</div>
       			<div class="validate">${ERR_content}</div>
			 </li>
			 
			 <li class="col-md-12 p0" id="file">
		     <span class="fl">已上传的附件：</span>
		     <div class="fl mt5">
		       <c:forEach items="${post.postAttachments}" var="a">
		        <a class="pointer">${fn:split(a.name, '_')[1]}</a><a onclick="deleteAtta('${a.id}',this)" class="red redhover ml10 pointer">x</a>
		       </c:forEach>
		     </div>
		     </li>
		     <li class="col-md-12 p0">
		        <span class="f14 fl">上传附件：</span>
		        <div class="fl" id="uploadAttach" >
		          <input id="pic" type="file" class="toinline" name="attaattach"/>
		          <input class="toinline btn" type="button" value="添加" onclick="addAttach()"/><br/>
		        </div>
		     </li>
		     </ul>   
	  	 </ul>
	</div>  	
	<!-- 底部按钮 -->			          
    <div class="col-md-12 tc">        
    	<button class="btn btn-windows save" type="submit">更新</button>
    	<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
	</div>
  </div>
     </form>
     </div>
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
        //ue.setDisabled([]);
    });
</script>
  </body>
</html>

