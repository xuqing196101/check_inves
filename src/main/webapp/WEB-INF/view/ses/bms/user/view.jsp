<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
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
   <h2 class="list_title">查看用户</h2>
   <div class="tag-box tag-box-v4 col-md-12">
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
   
  <div class="col-md-12 col-sm-12 col-xs-12 tc" >
    	<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
  </div>
  </form>
 </div>
</body>
</html>
