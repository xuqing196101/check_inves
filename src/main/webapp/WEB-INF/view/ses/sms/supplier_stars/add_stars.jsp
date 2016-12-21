<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../../common.jsp"%>

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
				     <div class="headline-v2">
					     <h2>添加星级</h2>
					   </div>   
					<ul class="ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">一星级所需分数</span>
							 <div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" name="oneStars" type="text" value="${supplierStars.oneStars}" />
								<span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">二星级所需分数</span>
							 <div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" name="twoStars" type="text" value="${supplierStars.twoStars}" />
								<span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">三星级所需分数</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" name="threeStars" type="text" value="${supplierStars.threeStars}" />
								<span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">四星级所需分数</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" name="fourStars" type="text" value="${supplierStars.fourStars}" />
								<span class="add-on">i</span>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">五星级所需分数</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" name="fiveStars" type="text" value="${supplierStars.fiveStars}" />
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
