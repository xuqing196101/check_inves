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
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title></title>

		<!-- Meta -->
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<script type="text/javascript">
			$(document).ready(function() {
				for(var i = 0; i < document.getElementById("type").options.length; i++) {
					if(document.getElementById("type").options[i].value == '${ir.type}') {
						document.getElementById("type").options[i].selected = true;
						break;
					}
				}
			});

			var parentId;
			var addressId = "${ir.address}";
			$(function() {
				var parentId;
				//地区回显和数据显示
				$.ajax({
					url: "${pageContext.request.contextPath}/area/find_by_id.do",
					data: {
						"id": addressId
					},
					success: function(obj) {
						$.each(obj, function(i, result) {
							if(addressId == result.id) {
								parentId = result.parentId;
								$("#choose2").append("<option selected='true' value='" + result.id + "'>" + result.name + "</option>");
							} else {
								$("#choose2").append("<option value='" + result.id + "'>" + result.name + "</option>");
							}

						});
					}
				});

				$.ajax({
					url: "${pageContext.request.contextPath}/area/listByOne.do",
					success: function(obj) {
						$.each(obj, function(i, result) {
							if(parentId == result.id) {
								$("#choose1").append("<option selected='true' value='" + result.id + "'>" + result.name + "</option>");
							} else {
								$("#choose1").append("<option value='" + result.id + "'>" + result.name + "</option>");
							}
						});
					}

				});
			});

			function fun() {
				var parentId = $("#choose1").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/area/find_by_parent_id.do",
					data: {
						"id": parentId
					},
					success: function(obj) {
						$("#choose2").empty();
						$("#choose2").append("<option value=''>-请选择-</option>");
						$.each(obj, function(i, result) {

							$("#choose2").append("<option value='" + result.id + "'>" + result.name + "</option>");
						});
					},
					error: function(obj) {

					}

				});
			}
		</script>
	</head>

	<body>

		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0);"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">进口代理商</a>
					</li>
					<li>
						<a href="javascript:void(0);">进口代理商管理</a>
					</li>
					<li class="active">
						<a href="javascript:void(0);">进口代理商修改</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>

		<!-- 修改订列表开始-->
		<div class="container container_box">
			<form action="${pageContext.request.contextPath}/importRecommend/update.html" method="post">
				<div>
				  <h2 class="list_title">进口代理商修改</h2>
					<ul class="ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>登录名：</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input type="hidden" name="id" value="${ir.id }" />
								<input class="input_group" id="loginName" readonly name="loginName" value="${ir.loginName }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_loginName}</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>登录密码：</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="password" name="password" value="${ir.password }" type="password">
								<span class="add-on">i</span>
								<div class="cue">${ERR_password}</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>企业名称：</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="name" name="name" value="${ir.name }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_name}</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>法定代表人：</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="legalName" name="legalName" value="${ir.legalName }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_legalName}</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>企业地址：</span>
							<div class="select_common">
								<select id="choose1" class="w100" onchange="fun();">
									<option class="w100">-请选择-</option>
								</select>
								<select name="address" class="w100 ml40" id="choose2">
									<option class="w100">-请选择-</option>
								</select>
								<div class="cue">${ERR_address}</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>进口代理商类型：</span>
							<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
								<select id="type" name="type">
									<option value="1">正式代理商</option>
									<option value="2">临时代理商</option>
								</select>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>推荐单位：</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="recommendDep" name="recommendDep" value="${ir.recommendDep }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_recommendDep}</div>
							</div>
						</li>
					</ul>
				</div>
				<div class="col-md-12 tc mt20">
					<button class="btn btn-windows save" type="submit">更新</button>
					<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
				</div>
			</form>
		</div>
	</body>

</html>