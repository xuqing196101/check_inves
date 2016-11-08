<%@ page language="java" pageEncoding="UTF-8"%>

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
		<!-- header -->
		<jsp:include page="../../../../../index_head.jsp"></jsp:include>


		<!-- 项目戳开始 -->
		<div class="container clear margin-top-30">
			<h2 class="padding-20 mt40 ml30">
				<span class="new_step current fl"><i class="">1</i>
					<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step fl"><i class="">2</i>
					<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step fl"><i class="">3</i>
					<div class="line"></div> <span class="step_desc_01">供应商类型</span> </span> <span class="new_step fl"><i class="">4</i>
					<div class="line"></div> <span class="step_desc_02">专业信息</span> </span> <span class="new_step fl"><i class="">5</i>
					<div class="line"></div> <span class="step_desc_01">品目信息</span> </span> <span class="new_step fl"><i class="">6</i>
					<div class="line"></div> <span class="step_desc_02">产品信息</span> </span> <span class="new_step fl"><i class="">7</i>
					<div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span> <span class="new_step fl"><i class="">8</i>
					<div class="line"></div> <span class="step_desc_02">打印申请表</span> </span> <span class="new_step fl"><i class="">9</i><span class="step_desc_01">申请表承诺书上传</span> </span>
				<div class="clear"></div>
			</h2>

			<div class="col-md-12 margin-top-40">
				<div class="row">
					<form action="${pageContext.request.contextPath}/supplier/register.html" method="post">
						<div class="login_main mt20">
							<div class="login_item">
								<label class="col-md-3 p0" onclick="show()"><i class="red mr5">*</i>用 户 名：</label> 
								<input id="login_input_id" type="text" name="loginName" class="fl" placeholder="由6-20位字母、数字和下划线组成" value="${supplier.loginName}"> 
								<span id="dsds" class="fl mt5 ml10 span-err-msg">${err_msg_loginName}</span>
								<div class="clear"></div>
							</div>
							<div class="login_item margin-top-10">
								<label class="col-md-3 p0"><i class="red mr5">*</i>登录密码：</label> 
								<input type="password" name="password" class="fl" value="${supplier.password}" placeholder="由6-20位字母、数字和下划线组成"> 
								<span class="fl mt5 ml10 span-err-msg">${err_msg_password}</span>
								<div class="clear"></div>
							</div>
							<div class="login_item margin-top-10">
								<label class="col-md-3 p0"><i class="red mr5">*</i>确认密码：</label> 
								<input type="password" name="confirmPassword" class="fl" value="${supplier.confirmPassword}">
								<span class="fl mt5 ml10 span-err-msg">${err_msg_ConfirmPassword}</span>
								<div class="clear"></div>
							</div>
							<div class="login_item margin-top-10">
								<label class="col-md-3 p0"><i class="red mr5">*</i>手机号码：</label> 
								<input type="text" name="mobile" class="fl" value="${supplier.mobile}">
								<button type="button" class="fl btn padding-left-10 padding-right-10 btn_back ml10">发送验证码</button>
								<span class="fl mt5 ml10 span-err-msg">${err_msg_mobile}</span>
								<div class="clear"></div>
							</div>
							<div class="login_item margin-top-10">
								<label class="col-md-3 p0"><i class="red mr5">*</i>手机验证码：</label> 
								<input type="text" name="mobileCode" class="fl" value="${supplier.mobileCode}">
								<span class="fl mt5 ml10 span-err-msg">${err_msg_mobileCode}</span>
								<div class="clear"></div>
							</div>
							<div class="login_item margin-top-10">
								<label class="col-md-3 p0"><i class="red mr5">*</i>验证码：</label> 
								<input type="text" name="identifyCode" class="fl input-yzm" value="${supplier.identifyCode}">
								<div class="fl">
									<div class="yzm fl h28">
										<img id="identity_code_img_id" class="hand" src="${pageContext.request.contextPath}/supplier/get_identity.html" onclick="getIdentityCode()"/>
									</div>
									<button type="button" class="btn padding-left-10 padding-right-10 btn_back ml10 fl" onclick="getIdentityCode()">换一张</button>
								</div>
								<span class="fl mt5 ml10 span-err-msg">${err_msg_code}</span>
								<div class="clear"></div>
							</div>
							<div class="tc mt20 clear col-md-11">
								<button id="submit_button_id" type="submit" class="btn padding-left-20 padding-right-20 btn_back margin-5">注册</button>
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="location='${pageContext.request.contextPath}/supplier/registration_page.html'">返回</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		
		<!-- footer -->
		<jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
	</div>
</body>
</html>
