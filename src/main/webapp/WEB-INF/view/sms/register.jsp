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
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>

<script type="text/javascript">
	$(function() {
		document.getElementById("login_input_id").focus();// 用户名自动获取焦点
	});
	function kaptcha() {
		$("#kaptchaImage").hide().attr('src', '${pageContext.request.contextPath}/Kaptcha.jpg').fadeIn();
	}
</script>

</head>

<body>
	<div class="wrapper">
		<!-- header -->
		<jsp:include page="../../../indexhead.jsp"></jsp:include>


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
					<form action="${pageContext.request.contextPath}/supplierInfo/register.html" method="post">
						<div class="login_main mt20">
							<div class="login_item">
								<label class="col-md-3 p0"><i class="red mr5">*</i>用 户 名：</label> <input id="login_input_id" type="text" name="loginName" class="fl"> <span class="fl warning">（用户名由字母、数字、－等字符组成）</span>
								<div class="clear"></div>
							</div>
							<div class="login_item margin-top-10">
								<label class="col-md-3 p0"><i class="red mr5">*</i>登录密码：</label> <input type="password" name="password" class="fl"> <span class="fl warning">（密码由6-20位，由字母、数字组成）</span>
								<div class="clear"></div>
							</div>
							<div class="login_item margin-top-10">
								<label class="col-md-3 p0"><i class="red mr5">*</i>确认密码：</label> <input type="password" name="confirmPassword" class="fl">
								<div class="clear"></div>
							</div>
							<div class="login_item margin-top-10">
								<label class="col-md-3 p0"><i class="red mr5">*</i>手机号码：</label> <input type="text" name="mobile" class="fl">
								<button class="btn padding-left-10 padding-right-10 btn_back ml10">发送验证码</button>
								<div class="clear"></div>
							</div>
							<div class="login_item margin-top-10">
								<label class="col-md-3 p0"><i class="red mr5">*</i>手机验证码：</label> <input type="text" name="mobileCode" class="fl">
								<div class="clear"></div>
							</div>
							<div class="login_item margin-top-10">
								<label class="col-md-3 p0"><i class="red mr5">*</i>验证码：</label> <input type="text" name="identifyCode" class="fl input-yzm">
								<div class="fl">
									<div class="yzm fl">
										<img src="${pageContext.request.contextPath}/Kaptcha.jpg" onclick="kaptcha();" id="kaptchaImage" />
									</div>
									<button class="btn padding-left-10 padding-right-10 btn_back ml10 fl" onclick="kaptcha();">换一张</button>
								</div>
								<div class="clear"></div>
							</div>
							<div class="tc mt20 clear col-md-11">
								<button type="submit" class="btn padding-left-20 padding-right-20 btn_back margin-5">注册</button>
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="location='${pageContext.request.contextPath}/supplierInfo/registration_page.do'">返回</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		
		<!-- footer -->
		<jsp:include page="../../../indexbottom.jsp"></jsp:include>
	</div>
</body>
</html>
