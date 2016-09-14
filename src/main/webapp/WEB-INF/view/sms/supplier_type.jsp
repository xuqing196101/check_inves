<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>供应商注册须知</title>

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
		$(".subNav_new").click(function() {
			$(this).toggleClass("currentDd").siblings(".subNav").removeClass("currentDd");
			$(this).toggleClass("currentDt").siblings(".subNav").removeClass("currentDt");

			// 修改数字控制速度， slideUp(500)控制卷起速度
			$(this).next(".navContent").slideToggle(500).siblings(".navContent").slideUp(500);
		});
	});
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
					<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step current fl"><i class="">2</i>
					<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step current fl"><i class="">3</i>
					<div class="line"></div> <span class="step_desc_01">供应商类型</span> </span> <span class="new_step fl"><i class="">4</i>
					<div class="line"></div> <span class="step_desc_02">专业信息</span> </span> <span class="new_step fl"><i class="">5</i>
					<div class="line"></div> <span class="step_desc_01">品目信息</span> </span> <span class="new_step fl"><i class="">6</i>
					<div class="line"></div> <span class="step_desc_02">产品信息</span> </span> <span class="new_step fl"><i class="">7</i>
					<div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span> <span class="new_step fl"><i class="">8</i>
					<div class="line"></div> <span class="step_desc_02">打印申请表</span> </span> <span class="new_step fl"><i class="">9</i><span class="step_desc_01">申请表承诺书上传</span> </span>
				<div class="clear"></div>
			</h2>
		</div>

		<!--详情开始-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<div class="padding-top-20">
							<div class=" margin-bottom-0">
								<div class="tc bgdd subnav_title">供应商类型</div>
								<div class="subNavBoxs">
									<div class="subNav_new currentDd currentDt">
										<input type="checkbox" id="" class="fl" /><span class="ml5">物资</span>
									</div>
									<ul class="navContent " style="display: block;">
										<li><input type="checkbox" id="" class="fl" /><span class="ml5">生产型</span></li>
										<li><input type="checkbox" id="" class="fl" /><span class="ml5">销售型</span></li>
									</ul>
									<div class="subNav_new">
										<input type="checkbox" id="" class="fl" /><span class="ml5">工程</span>
									</div>

									<div class="subNav_new">
										<input type="checkbox" id="" class="fl" /><span class="ml5">服务</span>
									</div>

									<div class="subNav_new ">
										<input type="checkbox" id="" class="fl" /><span class="ml5">电冰箱</span>
									</div>

								</div>
								<div class="mt40 tc mb50">
									<button class="btn padding-left-20 padding-right-20 btn_back margin-15">上一步</button>
									<button class="btn padding-left-20 padding-right-20 btn_back margin-15">下一步</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- footer -->
	<jsp:include page="../../../indexbottom.jsp"></jsp:include>
</body>
</html>
