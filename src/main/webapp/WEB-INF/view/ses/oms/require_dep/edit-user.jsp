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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ztree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.excheck.js"></script>
	<script src="${pageContext.request.contextPath}/public/oms/js/select-tree.js"></script>
	<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
	
	<script type="text/javascript">
		function updatePurchase(){
			var index = parent.layer.getFrameIndex(window.name);
			$.ajax({
				type : 'post',
				url : "${pageContext.request.contextPath}/purchaseManage/updateUser.do?",
				data :  $('#formID').serialize(),
				//data: {'pid':pid,$("#formID").serialize()},
				success : function(data) {
					truealert(data.message, data.success == false ? 5 : 1);
				}
			});
		}
		function truealert(text, iconindex) {
			layer.open({
				content : text,
				icon : iconindex,
				shade : [ 0.3, '#000' ],
				yes : function(index) {
					//do something
					parent.location.reload();
					layer.closeAll();
					parent.layer.close(index); //执行关闭
					//parent.location.href="${pageContext.request.contextPath}/purchaseManage/list.do";
				}
			});
		}
	</script>
</head>
<body>

<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">机构管理</a></li><li class="active"><a href="#">修改用户</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 修改订列表开始-->
   <div class="container">
	   
   <form action="${pageContext.request.contextPath}/purchaseManage/updateUser.html" method="post" id="formID">
   <div>
   <div class="headline-v2">
   <h2>修改用户</h2>
   </div>
    <input class="span2" name="id" id="uId" type="hidden" value="${user.id}">
   	<input class="span2" name="createdAt" type="hidden" value="<fmt:formatDate value='${user.createdAt}' pattern='yyyy-MM-dd  HH:mm:ss'/>">
   	<input class="span2" name="isDeleted" type="hidden" value="${user.isDeleted}">
   	<input class="span2" name="password" type="hidden" value="${user.password}">
   	<input class="span2" name="randomCode" type="hidden" value="${user.randomCode}">
   <ul class="list-unstyled list-flow p0_20">
	   	<li class="col-md-6 p0">
		   <span class="">用户名：</span>
		   <div class="input-append">
	        <input class="span2" name="loginName" type="text" readonly="readonly" value="${user.loginName}">
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
		 <li class="col-md-6 p0">
		   <span class="">性别：</span>
	        <select name="gender">
	        	<option value="">-请选择-</option> 
	        	<option value="M" <c:if test="${'M' eq user.gender}">selected</c:if> >男</option>
	        	<option value="F" <c:if test="${'F' eq user.gender}">selected</c:if>>女</option>
	        </select>
		 </li>
	     <li class="col-md-6  p0 ">
		   <span class="">手机：</span>
		   <div class="input-append">
	        <input class="span2" name="mobile" value="${user.mobile }" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li>
	     <li class="col-md-6 p0">
		   <span class="">邮箱：</span>
		   <div class="input-append">
	        <input class="span2" name="email" value="${user.email }" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li>
	     <li class="col-md-6  p0 ">
		   <span class="">职务：</span>
		   <div class="input-append">
	        <input class="span2" name="duties" value="${user.duties }" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li>
		 <li class="col-md-6  p0 hide">
		   <span class="">所属机构：</span>
		   <div class="input-append">
	        <input class="span2" name="org.id" value="${user.org.id }" type="text">
	        <input class="span2" name="typeName" value="${user.typeName }" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li>
	     <li class="col-md-6  p0 ">
		   <span class="">联系电话：</span>
		   <div class="input-append">
	        <input class="span2" name="telephone" type="text" value="${user.telephone}">
	        <span class="add-on">i</span>
	       </div>
		 </li> 
		 <li class="col-md-12 p0">
		   <span class="fl">详细地址：</span>
		   <div class="col-md-12 pl200 fn mt5 pwr9">
	        <textarea class="text_area col-md-12 " address="address" maxlength="200" title="" placeholder="">${user.address}</textarea>
	       </div>
		 </li>
			 
   </ul>
  </div> 
   
  <div  class="col-md-12">
   	<div class="tc padding-10">
    	<button class="btn btn-windows reset" type="button" onclick="updatePurchase();">更新</button>
	</div>
  </div>
  </form>
 </div>
</body>
</html>
