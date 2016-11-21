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
   <div class="layui-layer-wrap">
   <form action="${pageContext.request.contextPath}/purchaseManage/createUser.html" method="post" id="formID">
            <div class="drop_window">
              <ul class="list-unstyled">
                 <li class="mt10 col-md-12 p0 col-xs-12">
                  <label class="col-md-12 pl20 col-xs-12">编号</label>
                  <span class="col-md-12 col-xs-12">
                   <input maxlength="11" id="planNo" name="planNo" type="text" class="col-xs-12 h80 mt6">
                  </span>
                </li>
                
                <li class="col-sm-6 p0 col-md-6 col-lg-6 col-xs-6">
                  <label class="col-md-12 pl20 col-xs-12">交付时间</label>
                   <span class="col-md-12 col-xs-12">
                   <input maxlength="11" id="givetime" name="givetime" value="" type="text" class="title col-md-12">
                  </span>
                </li>
                <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                  <label class="col-md-12 pl20 col-xs-12">用户名</label>
                  <span class="col-md-12 col-xs-12">
                   <input name="loginName" maxlength="30" class="title col-md-12" type="text">
                  </span>
                  <div class="b f18 ml10 red hand">${loginName_msg}</div>
                </li>
                <li class="col-sm-6 p0 col-md-6 col-lg-6 col-xs-6">
                  <label class="col-md-12 pl20 col-xs-12">真实姓名</label>
                  <span class="col-md-12 col-xs-12">
                   <input class="title col-md-12" name="relName" maxlength="10" type="text">
                  </span>
                </li>
                <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                  <label class="col-md-12 pl20 col-xs-12">密码</label>
                   <span class="col-md-12 col-xs-12">
                   <input class="title col-md-12" name="password" maxlength="30" id="password1" type="password">
                  </span>
                  <div class="b f18 ml10 red hand">${password_msg}</div>
                </li>
               </ul>
            </div>
    </form>
    </div>
   <!-- 修改订列表开始-->
   <%-- <div class="container container_box">
   <form action="${pageContext.request.contextPath}/purchaseManage/createUser.html" method="post" id="formID">
   <div>
        <h2 class="count_flow">新增用户</h2>
	   <ul class="ul_list">
    	 <li class="col-md-3 margin-0 padding-0 ">
		   <span class="col-md-12 padding-left-5">用户名：</span>
		   <div class="input-append">
	        <input class="span5" name="loginName" maxlength="30" type="text">
	        <span class="add-on">i</span>
	        <div class="b f18 ml10 red hand">${loginName_msg}</div>
	       </div>
		 </li>
	     <li class="col-md-3 margin-0 padding-0 ">
		   <span class="col-md-12 padding-left-5">真实姓名：</span>
		   <div class="input-append">
	        <input class="span5" name="relName" maxlength="10" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li>
		 <li class="col-md-3 margin-0 padding-0 ">
		   <span class="col-md-12 padding-left-5">密码：</span>
		   <div class="input-append">
	        <input class="span5" name="password" maxlength="30" id="password1" type="password">
	        <span class="add-on">i</span>
	        <div class="b f18 ml10 red hand">${password_msg}</div>
	       </div>
		 </li> 
	    <li class="col-md-3 margin-0 padding-0 ">
		   <span class="col-md-12 padding-left-5">确认密码：</span>
		   <div class="input-append">
	        <input class="span5" id="password2" maxlength="30" name="password2" type="password">
	        <span class="add-on">i</span>
	        <div class="b f18 ml10 red hand">${password2_msg}</div>
	       </div>
		 </li>
		  <li class="col-md-3 margin-0 padding-0 ">
		   <span class="col-md-12 padding-left-5">性别：</span>
	        <select name="gender">
	        	<option value="">-请选择-</option>
	        	<option value="M">男</option>
	        	<option value="F">女</option>
	        </select>
		 </li>
	     <li class="col-md-3 margin-0 padding-0 ">
		   <span class="col-md-12 padding-left-5">手机：</span>
		   <div class="input-append">
	        <input class="span5" name="mobile" maxlength="40" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li>
	     <li class="col-md-3 margin-0 padding-0 ">
		   <span class="col-md-12 padding-left-5">邮箱：</span>
		   <div class="input-append">
	        <input class="span5" name="email" maxlength="100" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li>
	     <li class="col-md-3 margin-0 padding-0 ">
		   <span class="col-md-12 padding-left-5">职务：</span>
		   <div class="input-append">
	        <input class="span5" name="duties" maxlength="40" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li>
		 <li class="col-md-6  p0 hide">
		   <span class="col-md-12 padding-left-5">所属机构：</span>
		   <div class="input-append">
	        <input class="span5" name="org.id" value="${orgId }" type="text">
	        <input class="span5" name="typeName" value="${typeName }" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li>
	     <li class="col-md-3 margin-0 padding-0 ">
		   <span class="col-md-12 padding-left-5">座机电话：</span>
		   <div class="input-append">
	        <input class="span5" name="telephone" maxlength="40" type="text">
	        <span class="add-on">i</span>
	       </div>
		 </li> 
		 <li class="col-md-3 margin-0 padding-0 ">
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
  </div> --%>
</body>
</html>
