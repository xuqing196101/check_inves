<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>供应商星级规则管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript">
	
</script>
</head>

<body>
	<div class="wrapper">
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li><a href="#"> 首页</a></li>
					<li><a href="#">业务管理</a></li>
					<li><a href="#">供应商星级</a></li>
					<li class="active"><a href="#">添加星级规则</a></li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<form action="${pageContext.request.contextPath}/supplier_stars/save_or_update_supplier_stars.html" method="post">
			<input type="hidden" name="id" value="${supplierStars.id}" />
			<div class="container">
				<div>
					<div class="headline-v2">
						<h2>添加星级</h2>
					</div>
					<ul class="list-unstyled list-flow p0_20">
						<li class="col-md-6 p0"><span class="">一星级所需分数：</span>
							<div class="input-append">
								<input class="span2" name="oneStars" type="text" value="${supplierStars.oneStars}" />
								<span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-6 p0"><span class="">二星级所需分数：</span>
							<div class="input-append">
								<input class="span2" name="twoStars" type="text" value="${supplierStars.twoStars}" />
								<span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-6 p0"><span class="">三星级所需分数：</span>
							<div class="input-append">
								<input class="span2" name="threeStars" type="text" value="${supplierStars.threeStars}" />
								<span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-6 p0"><span class="">四星级所需分数：</span>
							<div class="input-append">
								<input class="span2" name="fourStars" type="text" value="${supplierStars.fourStars}" />
								<span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-6 p0"><span class="">五星级所需分数：</span>
							<div class="input-append">
								<input class="span2" name="fiveStars" type="text" value="${supplierStars.fiveStars}" />
								<span class="add-on">i</span>
							</div>
						</li>
					</ul>
				</div>
				<div class="col-md-12 tc">
					<input class="btn btn-windows save" type="submit" value="保存" />
					<input class="btn btn-windows reset" onclick="history.go(-1)" type="button" value="返回">
				</div>
			</div>
		</form>
	</div>
</body>
</html>
