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
				 <li class="mt10 col-md-12 p0 col-xs-12">
				   <label class="col-md-12 pl20 col-xs-12"><div class="star_red">*</div>名称</label>
				     <span class="col-md-12 col-xs-12">
                        <input class="col-xs-12 h80 mt6" name="name" value=${role.name } maxlength="30" type="text">
                    </span>
				 </li>
				 <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
				 	 <label class="col-md-12 col-sm-12 col-xs-12 col-lg-12  padding-left-5 padding-left-5"><div class="star_red">*</div>状态</label>
				 	 <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 select_common p0">
					<select  name="status"  class="">
					   	<option value="0" <c:if test="${'0' eq role.status}">selected</c:if>>可用</option>
					   	<option value="1" <c:if test="${'1' eq role.status}">selected</c:if>>禁用</option>
				    </select>
				    </span>
				</li>
				<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                   <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>角色所属后台</label>
                  <span class="col-md-12 col-sm-12 col-xs-12 select_common p0">
                   <select name="kind" class="">
                   		<c:forEach items="${dds}" var="dd" varStatus="vs">
                   			<option value="${dd.id}" <c:if test="${dd.id eq role.kind}">selected</c:if>>
	                   			<c:if test="${'PURCHASE_BACK' eq dd.code}">采购后台</c:if>
	                   			<c:if test="${'EXPERT_BACK' eq dd.code}">专家后台</c:if>
	                   			<c:if test="${'SUPPLIER_BACK' eq dd.code}">供应商后台</c:if>
	                   			<c:if test="${'IMPORT_SUPPLIER_BACK' eq dd.code}">进口供应商后台</c:if>
	                   			<c:if test="${'IMPORT_AGENT_BACK' eq dd.code}">进口代理商后台</c:if>
	                   		</option>
                   		</c:forEach>
                    </select>
                  </span>
                </li>
			     <li class="mt10 col-md-12 col-sm-12 col-xs-12 col-lg-12">
				   <label class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">描述</label>
				  <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 p0">
			        <textarea class="col-md-12 col-sm-12 col-xs-12 col-lg-12 h80" name="description"  maxlength="400" title="" placeholder="">${role.description }</textarea>
			       </span>
				 </li> 
				 <div class="clear"></div>
			   </ul>
		  </div> 
	   
		  <div class="tc mt10 col-md-12 col-sm-12 col-xs-12 col-lg-12 ">
			    <button class="btn btn-windows git" id="update" type="button">更新</button>
			    <button class="btn btn-windows back" id="backups" type="button">返回</button>
		  </div>
	  </form>
  </div>
 </body>
</html>
