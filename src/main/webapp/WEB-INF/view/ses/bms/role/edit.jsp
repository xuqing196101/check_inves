<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
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
  </head>
 <body>
   
  <div class="layui-layer-wrap" >
	   <form action="" id="form1" method="post">
		   <div class="drop_window">
			   <input name="id" value=${role.id } type="hidden">
			  <ul class="list-unstyled">
				 <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6 pl15">
                   <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>名称</label>
                   <div class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
                   <input class="" name="name" maxlength="30" value="${role.name }" type="text">
                  </div>
                </li>
                <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                   <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>唯一编码</label>
                   <div class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
                     <input class="" name="code" maxlength="30" value="${role.code }" readonly type="text">
                   </div>
                </li>
				 <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
				 	 <label class="col-md-12 col-sm-12 col-xs-12 col-lg-12  padding-left-5 padding-left-5"><div class="star_red">*</div>状态</label>
				 	 <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 select_common p0">
					   <select  name="status"  class="">
					   	<option value="0" <c:if test="${'0' eq role.status}">selected</c:if>>可用</option>
					   	<option value="1" <c:if test="${'1' eq role.status}">selected</c:if>>禁用</option>
				       </select>
				    </div>
				</li>
				<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                   <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>角色所属后台</label>
                  <div class="col-md-12 col-sm-12 col-xs-12 select_common p0">
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
                  </div>
                </li>
			     <li class="col-md-12 col-sm-12 col-xs-12 col-lg-12">
				   <label class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">描述</label>
				   <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 p0">
			          <textarea class="col-md-12 col-sm-12 col-xs-12 col-lg-12 h80" name="description"  maxlength="400" title="" placeholder="">${role.description }</textarea>
			       </div>
				 </li> 
				 <div class="clear"></div>
			   </ul>
		  </div> 
	   
		  <div class="tc mt10 col-md-12 col-sm-12 col-xs-12 col-lg-12 ">
			    <button class="btn btn-windows save" id="update" type="button">更新</button>
			    <button class="btn btn-windows back" id="backups" type="button">返回</button>
		  </div>
	  </form>
  </div>
 </body>
</html>
