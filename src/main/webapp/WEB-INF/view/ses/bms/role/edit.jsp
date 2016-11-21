<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  <script type="text/javascript">
  	$(function(){
		$("#update").click(function(){
			$.ajax({  
			   type: "POST",  
			   url: "${pageContext.request.contextPath}/role/update.html",  
			   data: $("#form1").serializeArray(),  
			   dataType: 'json',  
			   success:function(result){
	       			if(!result.success){
	       				layer.msg(result.msg,{offset: ['150px', '180px']});
	       			}else{
			       		parent.window.setTimeout(function(){
			       			parent.window.location.href = "${pageContext.request.contextPath}/role/list.html";
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
   
  <div class="layui-layer-wrap" >
	   <form action="" id="form1" method="post">
		   <div class="drop_window">
			   <input name="id" value=${role.id } type="hidden">
			  <ul class="list-unstyled">
			     <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
			       <label class="col-md-12 padding-left-5 col-xs-12"><i class="red">*</i>名称</label>
				   <span class="col-md-12">
			        <input class="title col-md-12" name="name" value=${role.name } maxlength="30" type="text">
			        </span>
				 </li>
				 <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
				 	 <label class="col-md-12 padding-left-5 col-xs-12"><i class="red">*</i>状态</label>
				 	 <span class="col-md-12 col-xs-12">
					<select  name="status"  class="w180 mb10">
					   	<option value="0" <c:if test="${'0' eq role.status}">selected</c:if>>可用</option>
					   	<option value="1" <c:if test="${'1' eq role.status}">selected</c:if>>禁用</option>
				    </select>
				    </span>
				</li>
			     <li class="mt10 col-md-12 p0 col-xs-12">
				   <label class="col-md-12 pl20 col-xs-12">描述</label>
				  <span class="col-md-12 col-xs-12">
			        <textarea class="col-xs-12 h80 mt6 " name="description"  maxlength="400" title="" placeholder="">${role.description }</textarea>
			       </span>
				 </li> 
				 <div class="clear"></div>
			   </ul>
		  </div> 
	   
		  <div class="tc mt10 col-md-12">
			    <button class="btn btn-windows git" id="update" type="button">更新</button>
			    <button class="btn btn-windows back" id="backups" type="button">返回</button>
		  </div>
	  </form>
  </div>
 </body>
</html>
