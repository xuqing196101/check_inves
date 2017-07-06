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

			$(document).ready(function() {
				for(var i = 0; i < document.getElementById("type").options.length; i++) {
					if(document.getElementById("type").options[i].value == '${ir.type}') {
						document.getElementById("type").options[i].selected = true;
						break;
					}
				}
			});
		</script>
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
						<a href="javascript:void(0);">进口代理商查看</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>

		<!-- 修改订列表开始-->
    <div class="container mt20">
      <div class="tab-content">
        <div class="tab-v2">
          <ul class="nav nav-tabs bgwhite">
            <li id="li_id_1" class="active">
              <a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18">详细信息</a>
            </li>
          </ul>
            <div class="tab-content padding-top-20">
              <div class="tab-pane fade active in" id="tab-1">
                <h2 class="count_flow jbxx">基本信息</h2>
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="bggrey">登录名：</td>
							<td>${ir.loginName }</td>
							<td class="bggrey ">密码：</td>
							<td>******</td>
							<td class="bggrey ">企业名称：</td>
							<td>${ir.name }</td>
						</tr>
						<tr>
							<td class="bggrey ">企业地址：</td>
							<td>
								<select id="choose1" class="w100" disabled onchange="fun();">
									<option class="w100">-请选择-</option>
								</select>
								<select name="address" disabled class="w100" id="choose2">
									<option class="w100">-请选择-</option>
								</select>
							</td>
							<td class="bggrey ">法定代表人：</td>
							<td>${ir.legalName }</td>
							<td class="bggrey ">推荐单位：</td>
							<td>${ir.recommendDep }</td>
						</tr>
						<tr>
							<td class="bggrey ">进口代理商类型：</td>
							<td>
								<c:if test="${ir.type==1 }">正式代理商</c:if>
								<c:if test="${ir.type==2 }">临时代理商</c:if>
							</td>
							<td class="bggrey ">状态：</td>
							<td colspan="3">
								<c:if test="${ir.status==0 }">未激活</c:if>
								<c:if test="${ir.status==1 }">已激活</c:if>
								<c:if test="${ir.status==2 }">暂停</c:if>
								<c:if test="${ir.status==3 }">启用</c:if>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="col-md-12 tc mt20">
					<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
				</div>
			</div>
		</div>
		</form>
		</div>
	</body>

</html>