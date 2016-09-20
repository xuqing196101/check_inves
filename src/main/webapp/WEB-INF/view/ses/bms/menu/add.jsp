<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加菜单</title>
    
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
        $("#save").click(function(){
        	
            $.ajax({  
               type: "POST",  
               url: "<%=basePath %>preMenu/save.html",  
               data: $("#form1").serializeArray(),  
               dataType: 'json',  
               success:function(result){
                    if(!result.success){
                        layer.msg(result.msg,{offset: ['150px', '180px']});
                    }else{
                        parent.window.setTimeout(function(){
                            parent.window.location.href = "<%=basePath%>preMenu/list.html";
                        }, 1000);
                        layer.msg(result.msg,{offset: ['150px', '180px']});
                    }
                },
                error: function(result){
                    layer.msg("添加失败",{offset: ['150px', '180px']});
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
		   	   <input type="hidden" name="id" id="pid" value="${pmenu.id }">
			   <ul class="list-unstyled mt10 p0_20">
			     <li class="col-md-6 p0">
				   <span class="fl mt5">上级菜单：</span>
				   <div class="input-append">
			        <input class="span2" name="pname" value="${pmenu.name }" readonly="readonly" type="text">
			        <span class="add-on">i</span>
			       </div>
				 </li>
			     <li class="col-md-6 p0">
				   <span class="fl mt5">&nbsp&nbsp名称：</span>
				   <div class="input-append">
			        <input class="span2" name="name" maxlength="30" type="text">
			        <span class="add-on">i</span>
			       </div>
				 </li>
				 <li class="col-md-6 p0 ">
				 	<span class=""> 菜单类型：</span>
					<select name="type"  >
					 	<option value="">-请选择-</option>
					   	<option value="navigation">导航</option>
					   	<option value="accordion">折叠导航</option>
					   	<option value="menu">菜单</option>
					   	<option value="button">按钮</option>
					</select>
				</li>
				<li class="col-md-6 p0 ">
				 	<span class="">&nbsp&nbsp状态：</span>
					<select  name="status"  >
					 	<option value="">-请选择-</option>
					   	<option value="0">可用</option>
					   	<option value="1">暂停</option>
				    </select>
				</li>
				<li class="col-md-6 p0">
				   <span class="fl mt5">&nbsp&nbsp路径：</span>
				   <div class="input-append">
			        <input class="span2" name="url" maxlength="300" type="text">
			        <span class="add-on">i</span>
			       </div>
				 </li>
			     <li class="col-md-6 p0">
				   <span class="fl mt5">&nbsp&nbsp排序：</span>
				   <div class="input-append">
			        <input class="span2" name="position" maxlength="3" type="text">
			        <span class="add-on">i</span>
			       </div>
				 </li>
				 <li class="col-md-6 p0 ">
				 	<span class=""> 菜单种类：</span>
					<select  name="kind"  >
					 	<option value="">-请选择-</option>
					   	<option value="0">采购管理后台</option>
					   	<option value="1">供应商后台 </option>
					   	<option value="2">专家后台</option>
					   	<option value="1">进口供应商后台 </option>
					</select>
				</li>
			   </ul>
		  </div> 
	   
		  <div  class="col-md-12">
		    <div class="fl padding-10">
			    <button class="btn btn-windows save" id="save" type="button">保存</button>
			    <button class="btn btn-windows git" id="backups" type="button">返回</button>
			</div>
		  </div>
	  </form>
  </div>
 </body>
</html>
