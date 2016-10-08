<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

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
	<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">
	
	<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=path %>/public/ZHH/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/lodop/LodopFuncs.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
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
			       			parent.window.location.href = "<%=basePath%>role/list.html";
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
			  <ul class="list-unstyled mt10">
			     <li class="col-md-6 p0">
				   <span class="fl mt5">名称：</span>
				   <div class="input-append">
			        <input class="w140" name="name" value=${role.name } maxlength="30" type="text">
			        <span class="add-on">i</span>
			       </div>
				 </li>
				 <li class="col-md-6 p0 ">
				 	<span class="fl">状态：</span>
					<select  name="status"  class="w180 ">
					   	<option value="0" <c:if test="${'0' eq role.status}">selected</c:if>>可用</option>
					   	<option value="1" <c:if test="${'1' eq role.status}">selected</c:if>>禁用</option>
				    </select>
				</li>
			     <li class="col-md-12 p0">
				   <span class="fl mt5">描述：</span>
				   <div class="fn mt5">
			        <textarea class="text_area2 " name="description"  maxlength="200" title="" placeholder="">${role.description }</textarea>
			       </div>
				 </li> 
			   </ul>
		  </div> 
	   
		  <div  class="col-md-12">
		    <div class="fl padding-10">
			    <button class="btn btn-windows reset" id="update" type="button">更新</button>
			    <button class="btn btn-windows back" id="backups" type="button">返回</button>
			</div>
		  </div>
	  </form>
  </div>
 </body>
</html>
