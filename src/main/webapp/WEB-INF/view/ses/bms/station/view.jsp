<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>

<script type="text/javascript">
	function cheClick(){
		var roleIds="";
		var roleNames="";
		$('input[name="chkItem"]:checked').each(function(){
			var idName=$(this).val();
			var arr=idName.split(";");
			roleIds+=arr[0]+",";
			roleNames+=arr[1]+",";
		});
		$("#roleId").val(roleIds.substr(0,roleIds.length-1));
		$("#roleName").val(roleNames.substr(0,roleNames.length-1));
	}
</script>
</head>
<body>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">支撑环境</a></li><li><a href="javascript:void(0);">后台管理</a></li><li class="active"><a href="javascript:void(0);">用户管理</a></li><li class="active"><a href="javascript:void(0);">增加用户</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
<!-- 修改订列表开始-->
   <div class="container">
   <div>
   <div class="headline-v2">
   <h2>查看站内消息</h2>
   </div>
   <ul class="list-unstyled list-flow p0_20">
   			<input class="span2" name="id" type="hidden" value="${StationMessage.id}">
    		 <li class="col-md-12 p0 " >
			   <span class="">标题：</span>
			   <div class="fl w60p">
		        <input class="col-md-12" disabled="disabled" name="title" type="text" value="${StationMessage.title}">
		       </div>
			 </li>
		     <li class="col-md-12  p0 " >
			   <span class="">内容：</span>
			   <div class="fl w60p">
		        <textarea class="col-md-12 h100"  disabled="disabled"  cols="3" rows="100" name="context" >${StationMessage.content}</textarea>
			   </div>
			 </li> 
   </ul>
  </div> 
  <div  class="col-md-12 tc clear">
  
    	  <c:choose>
				 	<c:when test="${StationMessage.isPublish==0}">
				 		<a class="btn btn-windows apply" href="updateSMIsIssuance.do?id=${StationMessage.id}&&isIssuance=1">发布</a>
				 	</c:when>
				 	<c:otherwise>
				 		<a class="btn btn-windows withdraw" href="updateSMIsIssuance.do?id=${StationMessage.id}&&isIssuance=0">撤回</a>
				 	</c:otherwise>
				 </c:choose>
    	<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
  </div>
 </div>
</body>
</html>
