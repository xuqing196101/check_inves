<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../../../common.jsp"%>
	<script src="${pageContext.request.contextPath}/js/ses/sms/supplier_stars/add_stars.js"></script>
</head>

<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
					<li><a href="javascript:void(0);">支撑系统</a></li>
					<li><a href="javascript:void(0);">供应商管理</a></li>
					<li><a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/supplier_stars/list.html')">供应商星级规则</a></li>
					<c:if test="${supplierStars.id==null}">
						<li class="active"><a href="javascript:;">添加星级规则</a></li>
					</c:if>
					<c:if test="${supplierStars.id!=null}">
						<li class="active"><a href="javascript:;">修改星级规则</a></li>
					</c:if>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		 <div class="container container_box">
		<form action="" id="starForm" method="post">
			<input type="hidden" name="id" value="${supplierStars.id}" />
				<div>
				     <div class="headline-v2">
					     <h2>添加星级</h2>
					   </div>   
					<ul class="ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>一星级所需分数</span>
							 <div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" name="oneStars" id="oneStars" type="text" value="${supplierStars.oneStars}" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
								<span class="add-on">i</span>
								<div class="cue"><span><font id="oneStarsErr"></font></span></div>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>二星级所需分数</span>
							 <div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" name="twoStars" id="twoStars" type="text" value="${supplierStars.twoStars}" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
								<span class="add-on">i</span>
								<div class="cue"><span><font id="twoStarsErr"></font></span></div>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>三星级所需分数</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" name="threeStars" id="threeStars" type="text" value="${supplierStars.threeStars}" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
								<span class="add-on">i</span>
								<div class="cue"><span><font id="threeStarsErr"></font></span></div>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>四星级所需分数</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" name="fourStars" id="fourStars" type="text" value="${supplierStars.fourStars}" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
								<span class="add-on">i</span>
								<div class="cue"><span><font id="fourStarsErr"></font></span></div>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>五星级所需分数</span>
							<div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" name="fiveStars" id="fiveStars" type="text" value="${supplierStars.fiveStars}" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
								<span class="add-on">i</span>
								<div class="cue"><span><font id="fiveStarsErr"></font></span></div>
							</div>
						</li>
					</ul>
				</div>
				<div class="col-md-12 tc">
					<input class="btn btn-windows save" onclick="submitForm()" type="button" value="保存" />
					<input class="btn btn-windows back" onclick="history.go(-1)" type="button" value="返回">
				</div>
		</form>
	</div>
</body>
</html>
