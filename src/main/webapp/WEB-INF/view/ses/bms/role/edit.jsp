<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改角色</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="<%=basePath%>public/layer/layer.js"></script>
  </head>
  <script type="text/javascript">
  	$(function(){
		$("#update").click(function(){
			$.ajax({  
			   type: "POST",  
			   url: "<%=basePath %>role/update.html",  
			   data: $("#form1").serializeArray(),  
			   dataType: 'json',  
			   success:function(result){
	       			if(!result.success){
	       				layer.msg(result.msg,{offset: ['150px', '180px']});
	       			}else{
			       		parent.window.setTimeout(function(){
			       			parent.window.location.href = "<%=basePath%>role/getAll.html";
			       		}, 1000);
			       		layer.msg(result.msg,{offset: ['150px', '180px']});
	       			}
	       		},
	       		error: function(result){
					layer.msg("更新失败",{offset: ['150px', '180px']});
				}
			});
			
		});
		$("#backups").click(function(){
			var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			parent.layer.close(index); 
		});
   	});
  </script>
 <body>
   
   <div class="container">
	   <form action="" id="form1" method="post">
		   <div>
			   <input name="id" value=${role.id } type="hidden">
			   <ul class="list-unstyled list-flow p0_20">
			     <li class="col-md-6 p0">
				   <span class="">名称：</span>
				   <div class="input-append">
			        <input class="span2" name="name" value=${role.name } maxlength="30" type="text">
			        <span class="add-on">i</span>
			       </div>
				 </li>
			     <li class="col-md-12 p0">
				   <span class="fl">描述：</span>
				   <div class="col-md-12 pl200 fn mt5 pwr9">
			        <textarea class="text_area col-md-12 " name="describe"  maxlength="200" title="" placeholder="">${role.describe }</textarea>
			       </div>
				 </li> 
			   </ul>
		  </div> 
	   
		  <div  class="col-md-12">
		    <div class="fl padding-10">
			    <button class="btn btn-windows save" id="update" type="button">更新</button>
			    <button class="btn btn-windows git" id="backups" type="button">返回</button>
			</div>
		  </div>
	  </form>
  </div>
 </body>
</html>
