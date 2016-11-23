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
		 			<td class="bggrey tr">用户名：</td><td>${user.loginName}</td>
		 			<td class="bggrey tr">真实姓名：</td><td>${user.relName}</td>
		 		</tr>
		 		<tr>
		 			<td class="bggrey tr">性别：</td>
		 			<td>
		 				<c:forEach items="${genders}" var="g" varStatus="vs">
					  		<c:if test="${g.id eq user.gender}">
					  			<c:if test="${'M' eq g.code}">男</c:if>
								<c:if test="${'F' eq g.code}">女</c:if>
					  		</c:if>
			        	</c:forEach>
		 			</td>
		 			<td class="bggrey tr">手机：</td><td>${user.mobile }</td>
		 		</tr>
		 		<tr>
		 			<td class="bggrey tr">邮箱：</td><td>${user.email }</td>
		 			<td class="bggrey tr">职务：</td><td>${user.duties }</td>
		 		</tr>
		 		<tr>
		 			<td class="bggrey tr">类型：</td>
		 			<td>
		 				<c:forEach items="${typeNames}" var="t" varStatus="vs">
					  		<c:if test="${t.id eq user.typeName}">
					  			<c:if test="${'NEED_U' eq t.code}">需求人员</c:if>
								<c:if test="${'PURCHASER_U' eq t.code}">采购人员</c:if>
								<c:if test="${'PUR_MG_U' eq t.code}">采购管理人员</c:if>
								<c:if test="${'OTHER_U' eq t.code}">其他人员</c:if>
								<c:if test="${'SUPPLIER_U' eq t.code}">供应商</c:if>
								<c:if test="${'EXPERT_U' eq t.code}">专家</c:if>
								<c:if test="${'IMP_SUPPLIER_U' eq t.code}">进口供应商</c:if>
								<c:if test="${'IMP_AGENT_U' eq t.code}">进口代理商</c:if>
								<c:if test="${'SUPERVISER_U' eq t.code}">监督人员</c:if>
					  		</c:if>
			        	</c:forEach>
		 			</td>
		 			<td class="bggrey tr">所属机构：</td><td>${user.org.name }</td>
		 		</tr>
		 		<tr>
		 			<td class="bggrey tr">创建日期：</td><td><fmt:formatDate value='${user.createdAt}' pattern='yyyy-MM-dd  HH:mm:ss'/></td>
		 			<td class="bggrey tr">修改日期：</td><td><fmt:formatDate value='${user.updatedAt}' pattern='yyyy-MM-dd  HH:mm:ss'/></td>
		 		</tr>
		 		<tr>
		 			<td class="bggrey tr">联系电话：</td><td>${user.telephone}</td>
		 			<td class="bggrey tr">角色：</td><td colspan="5">${roleName}</td>
		 		</tr>
		 		<tr>
		 			<td class="bggrey tr">详细地址：</td><td colspan="3">${user.address}</td>
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
