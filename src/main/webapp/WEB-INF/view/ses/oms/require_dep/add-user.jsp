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
<script src="${pageContext.request.contextPath}/public/oms/js/select-tree.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>


<script type="text/javascript">
	function save() {
		var index = parent.layer.getFrameIndex(window.name);
		var pid = parent.$("#parentid").val();
		console.dir(pid);
		$.ajax({
			type : 'post',
			url : "${pageContext.request.contextPath}/purchaseManage/createUser.do?",
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
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">机构管理</a></li><li class="active"><a href="#">增加用户</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   <!-- 修改订列表开始-->
   <div class="container">
   <form action="${pageContext.request.contextPath}/purchaseManage/createUser.html" method="post" id="formID">
   <div>
	   <div class="headline-v2">
	   	 <h2>新增用户</h2>
	   </div>
	   <ul class="list-unstyled list-flow p0_20">
    	 <li class="col-md-6 p0">
		   <span class="">用户名：</span>
		   <div class="input-append">
	        <input class="span2" name="loginName" maxlength="30" type="text">
	        <span class="add-on">i</span>
	        <div class="b f18 ml10 red hand">${loginName_msg}</div>
	       </div>
		 </li>
	     <li class="col-md-6  p0 ">
		   <span class="">真实姓名：</span>
		   <div class="input-append">
	        <input class="span2" name="relName" maxlength="10" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li>
		 <li class="col-md-6  p0 ">
		   <span class="">密码：</span>
		   <div class="input-append">
	        <input class="span2" name="password" maxlength="30" id="password1" type="password">
	        <span class="add-on">i</span>
	        <div class="b f18 ml10 red hand">${password_msg}</div>
	       </div>
		 </li> 
	     <li class="col-md-6  p0 ">
		   <span class="">确认密码：</span>
		   <div class="input-append">
	        <input class="span2" id="password2" maxlength="30" name="password2" type="password">
	        <span class="add-on">i</span>
	        <div class="b f18 ml10 red hand">${password2_msg}</div>
	       </div>
		 </li>
		  <li class="col-md-6 p0">
		   <span class="">性别：</span>
	        <select name="gender">
	        	<option value="">-请选择-</option>
	        	<option value="M">男</option>
	        	<option value="F">女</option>
	        </select>
		 </li>
	     <li class="col-md-6  p0 ">
		   <span class="">手机：</span>
		   <div class="input-append">
	        <input class="span2" name="mobile" maxlength="40" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li>
	     <li class="col-md-6 p0">
		   <span class="">邮箱：</span>
		   <div class="input-append">
	        <input class="span2" name="email" maxlength="100" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li>
	     <li class="col-md-6  p0 ">
		   <span class="">职务：</span>
		   <div class="input-append">
	        <input class="span2" name="duties" maxlength="40" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li>
		 <li class="col-md-6  p0 hide">
		   <span class="">所属机构：</span>
		   <div class="input-append">
	        <input class="span2" name="org.id" value="${orgId }" type="text">
	        <input class="span2" name="typeName" value="${typeName }" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li>
	     <li class="col-md-6  p0 ">
		   <span class="">座机电话：</span>
		   <div class="input-append">
	        <input class="span2" name="telephone" maxlength="40" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li> 
		 <li class="col-md-12 p0">
		   <span class="fl">详细地址：</span>
		   <div class="col-md-12 pl200 fn mt5 pwr9">
	        <textarea class="text_area col-md-12 " name="address" maxlength="400" title="" placeholder=""></textarea>
	       </div>
		 </li>
	   </ul>
    </div> 
   
    <div  class="col-md-12">
      <div class="tc padding-10">
	    <button class="btn btn-windows save" type="button" onclick="save();">保存</button>
	  </div>
    </div>
  </form>
  </div>
</body>
</html>
