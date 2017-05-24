<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
	<!--<![endif]-->

	<head>
		<%@ include file="../../../common.jsp"%>
		<link href="${pageContext.request.contextPath}/public/webupload/css/webuploader.css" media="screen" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/public/webupload/css/uploadView.css" media="screen" rel="stylesheet" type="text/css">
		<!-- 文件显示 -->
		<link href="${pageContext.request.contextPath}/public/webupload/css/viewer.css" media="screen" rel="stylesheet" type="text/css">
		
		<!-- 文件上传 -->
		<script src="${pageContext.request.contextPath}/public/webupload/js/webuploader.js"></script>
		<script src="${pageContext.request.contextPath}/public/webuploadSBW/upload.js"></script>
		<!-- 文件显示 -->
		<script src="${pageContext.request.contextPath}/public/webupload/js/viewer.js"></script>
		<script src="${pageContext.request.contextPath}/public/webupload/js/display.js"></script>
		
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title></title>
		<!-- Meta -->
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<!-- js -->
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">支撑环境</a>
					</li>
					<%--<li>
						<a href="javascript:void(0);">进口代理商</a>
					</li>
					<li>
						<a href="javascript:void(0);">进口代理商管理</a>
					</li>--%>
					<li>
						<a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/importRecommend/list.html')">进口代理商管理列表</a>
					</li>
					<li class="active">
						<a href="javascript:void(0);">进口代理商激活</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 修改订列表开始-->
		<div class="container">
			<form action="${pageContext.request.contextPath}/importRecommend/jihuo_save.html?id=${id }" method="post">
				<div>
					<div class="headline-v2">
						<h2>进口代理商激活</h2>
					</div>
					<ul class="list-unstyled list-flow p0_20 mt10">
						<li id="bill_li_id" class="col-md-6 p0"><span class="zzzx w245"><i class="red">＊</i> 激活证明：</span>
							<u:upload id="jihuoFile_up" businessId="${id}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
							<u:show showId="jihuoFile_up" businessId="${id}" sysKey="${sysKey}" typeId="${typeId}" />
						</li>
					</ul>
				</div>
				<div class="col-md-12 tc mt20">
					<button class="btn btn-windows save" type="submit">保存</button>
					<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
				</div>
			</form>
		</div>

	</body>

</html>