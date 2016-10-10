<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title></title>

<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link href="<%=basePath%>public/ZHH/css/common.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css"
	media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/animate.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/ui-dialog.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/dialog-select.css"
	media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/font-awesome.min.css"
	media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/jquery.fileupload-ui.css"
	media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/zTreeStyle.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/sky-forms.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/custom-sky-forms.css"
	media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/jquery.fancybox.css"
	media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/owl.carousel.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/owl.theme.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/style-switcher.css"
	media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/shortcode_timeline2.css"
	media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/blocks.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/datepicker.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/WdatePicker.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/select2.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css"
	media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/brand-buttons-inversed.css"
	media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/blog_magazine.css"
	media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/page_log_reg_v1.css"
	media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/footer-v4.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/style(1).css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/masterslider.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/james.css" media="screen"
	rel="stylesheet">
<link href="<%=basePath%>public/supplier/css/supplieragents.css"
	media="screen" rel="stylesheet">
<script src="<%=basePath%>public/ZHH/js/hm.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery-migrate-1.2.1.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/back-to-top.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.query.js"></script>
<script src="<%=basePath%>public/ZHH/js/dialog-plus-min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.fancybox.pack.js"></script>
<script src="<%=basePath%>public/ZHH/js/smoothScroll.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.parallax.js"></script>
<script src="<%=basePath%>public/ZHH/js/app.js"></script>
<script src="<%=basePath%>public/ZHH/js/common.js"></script>
<script src="<%=basePath%>public/ZHH/js/dota.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.dragsort-0.5.2.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/fancy-box.js"></script>
<script src="<%=basePath%>public/ZHH/js/style-switcher.js"></script>
<script src="<%=basePath%>public/ZHH/js/owl.carousel.js"></script>
<script src="<%=basePath%>public/ZHH/js/owl-carousel.js"></script>
<script src="<%=basePath%>public/ZHH/js/owl-recent-works.js"></script>
<script
	src="<%=basePath%>public/ZHH/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/WdatePicker.js"></script>
<link href="<%=basePath%>public/ZHH/css/WdatePicker(1).css"
	rel="stylesheet" type="text/css">
<script src="<%=basePath%>public/ZHH/js/jquery.form.min.js"></script>



<script src="<%=basePath%>public/ZHH/js/jquery.validate.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.maskedinput.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery-ui.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/masking.js"></script>
<script src="<%=basePath%>public/ZHH/js/datepicker.js"></script>
<script src="<%=basePath%>public/ZHH/js/timepicker.js"></script>
<script src="<%=basePath%>public/ZHH/js/dialog-select.js"></script>
<script src="<%=basePath%>public/ZHH/js/locale.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.ui.widget.js"></script>
<script src="<%=basePath%>public/ZHH/js/load-image.js"></script>
<script src="<%=basePath%>public/ZHH/js/canvas-to-blob.js"></script>
<script src="<%=basePath%>public/ZHH/js/tmpl.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.iframe-transport.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.fileupload.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.fileupload-fp.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.fileupload-ui.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery-fileupload.js"></script>
<script src="<%=basePath%>public/ZHH/js/form.js"></script>
<script src="<%=basePath%>public/ZHH/js/select2.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/select2_locale_zh-CN.js"></script>
<script src="<%=basePath%>public/ZHH/js/application.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.counterup.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/modernizr.js"></script>
<script src="<%=basePath%>public/ZHH/js/touch.js"></script>
<script src="<%=basePath%>public/ZHH/js/product-quantity.js"></script>
<script src="<%=basePath%>public/ZHH/js/master-slider.js"></script>
<script src="<%=basePath%>public/ZHH/js/shop.app.js"></script>
<script src="<%=basePath%>public/ZHH/js/masterslider.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.easing.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/james.js"></script>
</head>
<script type="text/javascript">
	function cheClick() {
		var roleIds = "";
		var roleNames = "";
		$('input[name="chkItem"]:checked').each(function() {
			var idName = $(this).val();
			var arr = idName.split(";");
			roleIds += arr[0] + ",";
			roleNames += arr[1] + ",";
		});
		$("#roleId").val(roleIds.substr(0, roleIds.length - 1));
		$("#roleName").val(roleNames.substr(0, roleNames.length - 1));
	}
	//初始化选择角色
	$(function() {
		var initRid = $("#roleId").val().split(",");
		$('input[name="chkItem"]').each(function() {
			var idName = $(this).val();
			var arr = idName.split(";");
			for (var int = 0; int < initRid.length; int++) {
				if (initRid[int] == arr[0]) {
					$(this).attr("checked", 'true');
				}
			}
		});
	});
</script>
<body>

	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a></li>
				<li><a href="#">业务管理</a></li>
				<li><a href="#">订单中心</a></li>
				<li class="active"><a href="#">修改订单</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<!-- 修改订列表开始-->
	<div class="container">
		<form action="<%=basePath%>StationMessage/updateStationMessage.do"
			method="post">
			<div>
				<div class="headline-v2">
					<h2>修改站内消息</h2>
				</div>
				<ul class="list-unstyled list-flow p0_20">
					<input class="span2" name="id" type="hidden" 
						value="${StationMessage.id}">
					<li class="col-md-6 p0 "><span class="">标题：</span>
						<div class="input-append">
							<input class="span2 w350 " maxlength="40"  name="title" type="text"
								value="${StationMessage.title}">
						</div></li>
					<li class="col-md-12  p0 "><span class="">内容：</span> <textarea
							class="w350 h100" cols="3" maxlength="100"  rows="100" name="content">${StationMessage.content}</textarea>
					</li>
				</ul>
			</div>
			<div class="col-md-12">
				<div class="fl padding-10">
					<c:if test="${operation==1}">
						<button class="btn btn-windows save" type="submit">保存</button>
					</c:if>
					<c:if test="${operation==2}">
						<button class="btn btn-windows edit" type="submit">修改</button>
					</c:if>
					<button class="btn btn-windows git" onclick="history.go(-1)"
						type="button">返回</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
