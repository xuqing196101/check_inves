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
		<script type="text/javascript">
			var parentId;
			var addressId = "${is.address}";

			$(document).ready(function() {
				for(var i = 0; i < document.getElementById("type").options.length; i++) {
					if(document.getElementById("type").options[i].value == '${ir.type}') {
						document.getElementById("type").options[i].selected = true;
						break;
					}
				}
			});

			$(function() {
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
					},
					error: function(obj) {}
				});
			});

			function fun() {
				var parentId = $("#choose1").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/area/find_area_by_parent_id.do",
					data: {
						"id": parentId
					},
					success: function(obj) {
						var data = eval('(' + obj + ')');
						$("#choose2").empty();
						$("#choose2").append("<option value=''>-请选择-</option>");
						$.each(data, function(i, result) {
							$("#choose2").append("<option value='" + result.id + "'>" + result.name + "</option>");
						});
					},
					error: function(obj) {}
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
						<a href="javascript:void(0);">采购服务</a>
					</li>
					<li>
						<a href="javascript:void(0);">售后服务</a>
					</li>
					<li class="active">
						<a href="javascript:void(0);">用户意见反馈</a>
					</li>
				</ul>
				
		</div>

		<!-- 修改订列表开始-->
		<div class="container container_box ">
			<sf:form action="${pageContext.request.contextPath}/importRecommend/save.html" method="post">
				<div>
			        <h2 class="list_title">用户意见反馈信息</h2> 
					<ul class="ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>用户姓名</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="name" name="name" value="${ir.name }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_name}</div>
							</div>
						</li>

						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>联系方式</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="legalName" name="legalName" value="${ir.legalName }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_legalName}</div>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>供应商名称</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="recommendDep" name="recommendDep" value="${ir.recommendDep }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_recommendDep}</div>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>申请问题处理</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="legalName" name="legalName" value="${ir.legalName }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_legalName}</div>
							</div>
						</li>
					</ul>
				</div>
				<div class="col-md-12 tc mt20">
					<button class="btn btn-windows save" type="submit">保存</button>
					<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
				</div>
			</sf:form>
		</div>
	</body>

</html>