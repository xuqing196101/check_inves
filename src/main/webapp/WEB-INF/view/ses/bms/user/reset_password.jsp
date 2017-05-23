<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>基本信息</title>
		<!-- Meta -->
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">

		<script type="text/javascript">
			function ajaxOldPassword(){
				var is_error = 0;
				var userId = $("#userId").val();
				var oldPassord = $("#oldPassword").val();
				$.ajax({
		           type: "GET",
		           async: false, 
		           url: "${pageContext.request.contextPath}/user/ajaxOldPassword.do?id="+userId+"&password="+oldPassord,
		           dataType: "json",
		           success: function(data){
		                 if (!data.success) {
		                 	is_error = 1;
							$("#ajaxOldPassword").html(data.msg);
						 } else {
						 	$("#ajaxOldPassword").html("");
						 }
		             }
		       	});
		       	return is_error;
			}
			
			function resetPasswSubmit(){
				var is_error = ajaxOldPassword();
				if (is_error == 1) {
					return false;
				} else {
					$.ajax({   
				            type: "POST",  
				            url: "${pageContext.request.contextPath}/user/resetPwd.html",        
				           	data : $('#form2').serializeArray(),
						    dataType:'json',
						    success:function(result){
						    	if(!result.success){
			                    	layer.msg(result.msg,{offset: ['150px']});
						    	}else{
						    		layer.msg(result.msg,{offset: ['222px']});
						    	}
			                },
			                error: function(result){
			                    layer.msg("重置失败",{offset: ['222px']});
			                }
				     });    
				}
			}
		</script>
	</head>

	<body>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0);">首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">个人中心</a>
					</li>
					<li>
						<a href="javascript:void(0);">修改密码</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container container_box">
			<form method="post" id="form2" >
			<input type="hidden" name="id" id="userId" value="${user.id}">
			<h2 class="list_title">修改密码</h2>
			<ul class="ul_list">
				<li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>原密码</span>
					<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
						<input class="input_group" type="password" id="oldPassword" name="oldPassword" type="text" onblur="ajaxOldPassword()" maxlength="50">
						<span class="add-on">i</span>
						<div id="ajaxOldPassword" class="cue"></div>
					</div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>新密码</span>
					<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
						<input class="input_group" type="password" id="password" name="password" type="password" maxlength="50">
						<span class="add-on">i</span>
						<div id="ajaxPassword" class="cue"></div>
					</div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>确认新密码</span>
					<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
						<input class="input_group" type="password" id="password2" name="password2" maxlength="50">
						<span class="add-on">i</span>
						<div id="ajaxPassword2" class="cue"></div>
					</div>
				</li>
			</ul>
			<div class="tc mt20 clear col-md-11">
				<input class="btn" id="inputb" name="addr" onclick="resetPasswSubmit();" value="确定" type="button"> 
			</div>
		  </form>
		</div>
	</body>

</html>