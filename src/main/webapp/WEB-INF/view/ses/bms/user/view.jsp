<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
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
<body>

<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">用户管理</a></li><li class="active"><a href="#">查看用户</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   <div class="container bggrey border1 mt20">
   <form action="" method="post">
   <div>
   <div class="headline-v2 bggrey">
   <h2>查看用户</h2>
   </div>
   <div class="tag-box tag-box-v4 col-md-9">
	 	<table class="table table-bordered">
		 	<tbody>
		 		<tr>
		 			<td class="bggrey">用户名：</td><td>${user.loginName}</td>
		 			<td class="bggrey">真实姓名：</td><td>${user.relName}</td>
		 		</tr>
		 		<tr>
		 			<td class="bggrey">性别：</td>
		 			<td>
		 				<c:forEach items="${genders}" var="g" varStatus="vs">
					  		<c:if test="${g.id eq user.gender}">
					  			${g.name}
					  		</c:if>
			        	</c:forEach>
		 			</td>
		 			<td class="bggrey">手机：</td><td>${user.mobile }</td>
		 		</tr>
		 		<tr>
		 			<td class="bggrey">邮箱：</td><td>${user.email }</td>
		 			<td class="bggrey">职务：</td><td>${user.duties }</td>
		 		</tr>
		 		<%-- <tr>
		 			<td class="bggrey">类型：</td>
		 			<td>
		 				<c:forEach items="${typeNames}" var="t" varStatus="vs">
					  		<c:if test="${t.id eq user.typeName}">
					  			${t.name}
					  		</c:if>
			        	</c:forEach>
		 			</td>
		 			<td class="bggrey">所属机构：</td><td>${user.org.name }</td>
		 		</tr> --%>
		 		<tr>
		 			<td class="bggrey">创建日期：</td><td><fmt:formatDate value='${user.createdAt}' pattern='yyyy-MM-dd  HH:mm:ss'/></td>
		 			<td class="bggrey">修改日期：</td><td><fmt:formatDate value='${user.updatedAt}' pattern='yyyy-MM-dd  HH:mm:ss'/></td>
		 		</tr>
		 		<tr>
		 			<td class="bggrey">联系电话：</td><td>${user.telephone}</td>
		 			<td class="bggrey">角色：</td><td colspan="5">${roleName}</td>
		 		</tr>
		 		<tr>
		 			<td class="bggrey w160">详细地址：</td><td colspan="3">${user.address}</td>
		 		</tr>
		 	</tbody>
	 	</table>
   </div>
  </div> 
   
  <div class="col-md-12 tc mt20" >
    	<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
  </div>
  </form>
 </div>
</body>
</html>
