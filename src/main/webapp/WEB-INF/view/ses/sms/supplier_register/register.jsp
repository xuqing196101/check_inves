<%@ page language="java" pageEncoding="UTF-8"%>
<%--<%@ include file="../../../front.jsp" %>--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>供应商注册</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/common.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/line-icons.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/application.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/header-v4.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/footer-v2.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/img-hover.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/page_job.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/shop.style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>

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
	<div class="wrapper">
	


		<div class="container clear margin-top-30">
			<div class="col-md-12 margin-top-40">
				<div class="row" style="background-color:#f6f6f6;">
					<div class="mt20 col-md-6 p20">
						<form action="${pageContext.request.contextPath}/supplier/register.html" method="post">
							<div class="login_item col-md-12">
								<label class="col-md-3 p0"> <i class="red mr5">*</i>用户名：</label> <input id="login_input_id" type="text" name="loginName" value="${supplier.loginName}" class="fl">
								<div class="clear"></div>
							</div>
							<div class="login_item margin-top-10 col-md-12">
								<label class="col-md-3 p0"><i class="red mr5">*</i>登录密码：</label> <input type="password" name="password" value="${supplier.password}" class="fl">
								<div class="clear"></div>
							</div>
							<div class="login_item margin-top-10 col-md-12">
								<label class="col-md-3 p0"><i class="red mr5">*</i>确认密码：</label> <input type="password" name="confirmPassword" value="${supplier.confirmPassword}" class="fl">
								<div class="clear"></div>
							</div>
							<div class="login_item margin-top-10 col-md-12">
								<label class="col-md-3 p0"><i class="red mr5">*</i>手机号码：</label> <input type="text" name="mobile" value="${supplier.mobile}" class="fl">
								<button type="button" class="btn ml10">发送验证码</button>
								<div class="clear"></div>
							</div>
							<div class="login_item margin-top-10 col-md-12">
								<label class="col-md-3 p0"><i class="red mr5">*</i>手机验证码：</label> <input type="text" name="mobileCode" value="${supplier.mobileCode}" class="fl">
								<div class="clear"></div>
							</div>
							<div class="login_item margin-top-10 col-md-12">
								<label class="col-md-3 p0"><i class="red mr5">*</i>验证码：</label> <input type="text" name="identifyCode" value="${supplier.identifyCode}" class="fl input-yzm">
								<div class="fl">
									<div class="yzm fl">
										<img id="identity_code_img_id" class="hand" src="${pageContext.request.contextPath}/supplier/get_identity.html" onclick="getIdentityCode()" />
									</div>
									<button class="btn ml10 fl" onclick="getIdentityCode()">换一张</button>
								</div>
								<div class="clear"></div>
							</div>
							<div class="tc mt10 clear col-md-12">
								<button id="submit_button_id" type="submit" class="btn margin-5">注册</button>
								<button type="button" class="btn margin-5" onclick="location='${pageContext.request.contextPath}/supplier/registration_page.html'">返回</button>
							</div>
						</form>
					</div>
					<div class="col-md-6">
						<img src="${pageContext.request.contextPath}/public/ZHQ/images/sup_login.jpg" />
					</div>
				</div>
				
			</div>
			
		</div>
	</div>
</body>
</html>
