<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<script type="text/javascript">
	
</script>
</head>

<body>
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
		 <div class="container container_box">
		<form action="${pageContext.request.contextPath}/supplier_stars/save_or_update_supplier_stars.html" method="post">
			<input type="hidden" name="id" value="${supplierStars.id}" />
				<div>
				    <h2 class="count_flow"><i>1</i>添加星级</h2>
					<ul class="ul_list">
						<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5">一星级所需分数</span>
							<div class="input-append">
								<input class="span5" name="oneStars" type="text" value="${supplierStars.oneStars}" />
								<span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5">二星级所需分数</span>
							<div class="input-append">
								<input class="span5" name="twoStars" type="text" value="${supplierStars.twoStars}" />
								<span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5">三星级所需分数</span>
							<div class="input-append">
								<input class="span5" name="threeStars" type="text" value="${supplierStars.threeStars}" />
								<span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5">四星级所需分数</span>
							<div class="input-append">
								<input class="span5" name="fourStars" type="text" value="${supplierStars.fourStars}" />
								<span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5">五星级所需分数</span>
							<div class="input-append">
								<input class="span5" name="fiveStars" type="text" value="${supplierStars.fiveStars}" />
								<span class="add-on">i</span>
							</div>
						</li>
					</ul>
				</div>
				<div class="col-md-12 tc">
					<input class="btn btn-windows save" type="submit" value="保存" />
					<input class="btn btn-windows back" onclick="history.go(-1)" type="button" value="返回">
				</div>
		</form>
	</div>
</body>
</html>
