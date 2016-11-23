<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
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
<!-- 修改订列表开始-->
    <div class="layui-layer-wrap">
	   
   <form action="${pageContext.request.contextPath}/purchaseManage/updateUser.html" method="post" id="formID">
   <div class="drop_window">
    <input class="span2" name="id" id="uId" type="hidden" value="${user.id}">
   	<input class="span2" name="createdAt" type="hidden" value="<fmt:formatDate value='${user.createdAt}' pattern='yyyy-MM-dd  HH:mm:ss'/>">
   	<input class="span2" name="isDeleted" type="hidden" value="${user.isDeleted}">
   	<input class="span2" name="password" type="hidden" value="${user.password}">
   	<input class="span2" name="randomCode" type="hidden" value="${user.randomCode}">
   <ul class="list-unstyled">
	   	<li class="col-sm-6 col-md-6 p0 col-lg-6 col-xs-6">
		   <label class="col-md-12 pl20 col-xs-12">用户名</label>
		    <span class="col-md-12 col-xs-12">
                <input id="citySel" class="title col-md-12" name="loginName" type="text" readonly value="${user.loginName}" />
            </span>
		 </li>
	     <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
		  <label class="col-md-12 pl20 col-xs-12">真实姓名</label>
		  <span class="col-md-12 col-xs-12">
                <input id="citySel" class="title col-md-12" name="relName" type="text"  value="${user.relName}" />
            </span>
		 </li>
		 <li class="col-sm-6 p0 col-md-6 col-lg-6 col-xs-6">
		   <label class="col-md-12 pl20 col-xs-12">性别</label>
		   <span class="col-md-12 col-xs-12">
	        <select name="gender" class="w180 mt5">
	        	<option value="">-请选择-</option> 
	        	<option value="M" <c:if test="${'M' eq user.gender}">selected</c:if> >男</option>
	        	<option value="F" <c:if test="${'F' eq user.gender}">selected</c:if>>女</option>
	        </select>
	        </span>
		 </li>
	     <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
		   <label class="col-md-12 pl20 col-xs-12">手机</label>
		   <span class="col-md-12 col-xs-12">
                <input id="citySel" class="title col-md-12" name="mobile" type="text"  value="${user.mobile}" />
            </span>
		 </li>
	     <li class="col-sm-6 p0 col-md-6 col-lg-6 col-xs-6">
		   <label class="col-md-12 pl20 col-xs-12">邮箱</label>
		    <span class="col-md-12 col-xs-12">
                <input id="citySel" class="title col-md-12" name="email" type="text"  value="${user.email}" />
            </span>
		 </li>
	     <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
		   <label class="col-md-12 pl20 col-xs-12">职务</label>
		   <span class="col-md-12 col-xs-12">
                <input id="citySel" class="title col-md-12" name="duties" type="text"  value="${user.duties}" />
            </span>
		 </li>
		  <li class="col-sm-6 p0 col-md-6 col-lg-6 col-xs-6 hide">
           <label class="col-md-12 pl20 col-xs-12">所属机构</label>
           <span class="col-md-12 col-xs-12">
                <input id="citySel" class="title col-md-12" name="org.id" type="text"  value="${user.org.id }" />
                <input class="title col-md-12" name="typeName" value="${user.typeName }" type="text">
            </span>
         </li>
         <li class="col-sm-6 p0 col-md-6 col-lg-6 col-xs-6">
           <label class="col-md-12 pl20 col-xs-12">联系电话</label>
            <span class="col-md-12 col-xs-12">
                <input id="citySel" class="title col-md-12" name="telephone" type="text"  value="${user.telephone}" />
            </span>
         </li> 
         <li class="col-sm-6  col-md-6 col-lg-6 col-xs-6">
           <label class="col-md-12 pl20 col-xs-12">详细地址</label>
           <span class="col-md-12 col-xs-12">
                <input id="citySel" class="title col-md-12" address="address"  type="text"  value="${user.address}" />
            </span>
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
