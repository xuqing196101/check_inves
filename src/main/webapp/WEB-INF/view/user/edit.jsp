<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../common.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
</head>
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
	//初始化选择角色
	$(function(){
		var initRid=$("#roleId").val().split(",");
		$('input[name="chkItem"]').each(function(){
			var idName=$(this).val();
			var arr=idName.split(";");
			for ( var int = 0; int < initRid.length; int++) {
				if(initRid[int]==arr[0]){
					$(this).attr("checked",'true');
				}
			}
		});
	});
</script>
<body>

<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">用户管理</a></li><li class="active"><a href="#">修改用户</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 修改订列表开始-->
   <div class="container">
   <form action="<%=basePath %>user/update.html" method="post">
   <div>
   <div class="headline-v2">
   <h2>修改用户</h2>
   </div>
   <ul class="list-unstyled list-flow p0_20">
   	<input class="span2" name="id" type="hidden" value="${user.id}">
     <li class="col-md-6 p0">
			   <span class="">用户名：</span>
			   <div class="input-append">
		        <input class="span2" name="loginName" type="text" value="${user.loginName}">
		        <span class="add-on">i</span>
		       </div>
			 </li>
		     <li class="col-md-6  p0 ">
			   <span class="">真实姓名：</span>
			   <div class="input-append">
		        <input class="span2" name="relName" type="text" value="${user.relName}">
		        <span class="add-on">i</span>
		       </div>
			 </li>
		     <li class="col-md-6  p0 ">
			   <span class="">联系电话：</span>
			   <div class="input-append">
		        <input class="span2" name="phone" type="text" value="${user.phone}">
		        <span class="add-on">i</span>
		       </div>
			 </li> 
	 		<li class="col-md-6 p0">
			   <span class="">角色：</span>
			   <div class="input-append">
			   	 <input class="span2" id="roleId" name="roleId" type="hidden" value="${roleId }">
		         <input class="span2" id="roleName" name="roleName" type="text" value="${roleName }">
				 <div class="btn-group ">
		          <button class="btn dropdown-toggle add-on" data-toggle="dropdown">
				  <img src="<%=basePath%>public/ZHH/images/down.png" />
		          </button>
		          <ul class="dropdown-menu list-unstyled">
		          	<c:forEach items="${roles}" var="role" varStatus="vs">
		          		<li>
	          				<input type="checkbox" name="chkItem" value="${role.id };${role.name }" onclick="cheClick();">${role.name }
		          		</li>
		          	</c:forEach>
		          </ul>
		       </div>
		      </div>
			 </li>
			 
   </ul>
  </div> 
   
  <div  class="col-md-12">
   	<div class="fl padding-10">
    	<button class="btn btn-windows reset" type="submit">更新</button>
    	<button class="btn btn-windows git" onclick="history.go(-1)" type="button">返回</button>
	</div>
  </div>
  </form>
 </div>
</body>
</html>
