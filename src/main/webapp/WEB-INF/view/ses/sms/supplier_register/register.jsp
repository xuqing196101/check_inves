<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@include file="/WEB-INF/view/front.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<script type="text/javascript">
	$(function() {
		document.getElementById("login_input_id").focus();// 用户名自动获取焦点

		/** ajax 校验用户名是否存在 */
		$("#login_input_id").blur(function() {
			var loginName = $(this).val();
			$.ajax({
				url : "${pageContext.request.contextPath}/supplier/check_login_name.do",
				type : "post",
				data : {
					loginName : loginName
				},
				dataType : "json",
				success : function(result) {
					result = eval("(" + result + ")");
					if (result.msg == "fail") {
						$("#login_input_id").next().text("用户名重复 !");
						$("#submit_button_id").prop("disabled", true);
					} else {
						$("#submit_button_id").prop("disabled", false);
					}
				},
			});
		});
	});
	var count = 0;
	function getIdentityCode() {
		var random = Math.random();
		$("#identity_code_img_id").hide().attr("src", "${pageContext.request.contextPath}/supplier/get_identity.html?random" + random).fadeIn();
	}
</script>

</head>

<body>
<jsp:include page="/index_head.jsp"></jsp:include>
	<div class="wrapper">
		<div class="container clear margin-top-30">
			<div class="col-md-12 col-sm-12 col-xs-12 margin-top-40">
				<div class="row" style="background-color:#f6f6f6;">
					<div class="mt20 col-md-6 col-sm-6 col-xs-12 p20">
						<form action="${pageContext.request.contextPath}/supplier/register.html" method="post">
							<div class="login_item col-md-12  col-sm-12 col-xs-12">
								<label class="col-md-3 col-sm-12 col-xs-12 p0"> <i class="red mr5">*</i>用户名：</label>
								<div class="col-md-7 col-xs-12 col-sm-12 p0">
							       <input id="login_input_id" type="text" name="loginName" value="${supplier.loginName}"  class="col-md-12 col-sm-12 col-xs-12">
								   <span class="red clear col-md-12 col-xs-12 col-sm-12 p0">${err_msg_loginName }</span> 
								</div>
							</div>
							<div class="login_item margin-top-10 col-md-12  col-sm-12 col-xs-12 ">
								<label class="col-md-3 col-sm-12 col-xs-12  p0"><i class="red mr5">*</i>登录密码：</label> 
								<div class="col-md-7 col-xs-12 col-sm-12 p0">
								  <input type="password" name="password" value="${supplier.password}" class="col-md-12 col-sm-12 col-xs-12">
								  <span class="red clear col-md-12 col-xs-12 col-sm-12 p0" >${err_msg_loginName }</span> 
								</div>
							</div>
							<div class="login_item margin-top-10 col-md-12 col-sm-12 col-xs-12 ">
								<label class="col-md-3 col-sm-12 col-xs-12 p0"><i class="red mr5">*</i>确认密码：</label> 
								<div class="col-md-7 col-xs-12 col-sm-12 p0">
								   <input type="password" name="confirmPassword" value="${supplier.confirmPassword}" class="col-md-12 col-sm-12 col-xs-12">
								   <span class="red clear col-md-12 col-xs-12 col-sm-12 p0">${err_msg_ConfirmPassword }</span> 
								</div>
							</div>
							<div class="login_item margin-top-10 col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-3 col-sm-12 col-xs-12 p0"><i class="red mr5">*</i>手机号码：</label> 
								<div class="col-md-7 col-xs-12 col-sm-12 p0">
								   <input type="text" name="mobile" value="${supplier.mobile}" class="col-md-6 col-sm-4 col-xs-6">
								   <button type="button" class="btn ml10">发送验证码</button>
								   <span class="red clear col-md-12 col-xs-12 col-sm-12 p0">${err_msg_mobile }</span> 
								</div>
							</div>
							<div class="login_item margin-top-10 col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-3 col-sm-12 col-xs-12 p0"><i class="red mr5">*</i>手机验证码：</label> 
								<div class="col-md-7 col-xs-12 col-sm-12 p0">
								    <input type="text" name="mobileCode" value="${supplier.mobileCode}" class="col-md-12 col-sm-12 col-xs-12">
							        <span class="red clear col-md-12 col-xs-12 col-sm-12 p0">${err_msg_mobileCode }</span> 
								</div>
							</div>
							<div class="login_item margin-top-10 col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-3 col-sm-12 col-xs-12 p0"><i class="red mr5">*</i>验证码：</label> 
								<div class="col-md-7 col-xs-12 col-sm-12 p0">
								    <input type="text" name="identifyCode" value="${supplier.identifyCode}" class="col-md-4 col-sm-3 col-xs-4">
								    <div class="fl">
								       <div class="yzm fl">
										<img id="identity_code_img_id" class="hand" src="${pageContext.request.contextPath}/supplier/get_identity.html" onclick="getIdentityCode()" />
									   </div>
									   <button class="btn ml10 fl" onclick="getIdentityCode()">换一张</button>
								    </div>
								 <span class="red clear col-md-12 col-xs-12 col-sm-12 p0">${err_msg_code }</span> 
								</div>
							</div>
							<input type="hidden" name="id" value="${id }">
							<div class="tc mt10 clear col-md-12 col-sm-12 col-xs-12">
								<button id="submit_button_id" type="submit" class="btn margin-5">注册</button>
								<button type="button" class="btn margin-5" onclick="location='${pageContext.request.contextPath}/supplier/registration_page.html'">返回</button>
							</div>
						</form>
					</div>
					<div class="col-md-6 col-sm-6 col-xs-12">
						<img src="${pageContext.request.contextPath}/public/front/images/sup_login.jpg" width="100%" />
					</div>
				</div>
				
			</div>
			
		</div>
	</div>
</body>
</html>

			
