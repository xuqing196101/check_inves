<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">

	<head>
		<%@ include file="../../../../common.jsp"%>
		<script type="text/javascript">
		function fanhui() {
				var action = "${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html";
				$("#form_back").attr("action", action);
				$("#form_back").submit();
		};

//为只读
$(function() {
	$(":input").each(function() {
		$(this).attr("readonly", "readonly");
	});
});</script>
	</head>

	<body>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0);"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">支撑环境</a>
					</li>
					<li>
						<a href="javascript:void(0);">供应商管理</a>
					</li>
					<li>
						<a href="javascript:void(0);">供应商查看</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="container container_box">
			<div class=" content height-350">
				<div class="col-md-12 tab-v2 job-content">
					<form id="form_back" action="" method="post">
						<input name="sign" value="${sign}" type="hidden">
					</form>
					<h2 class="count_flow"><i>1</i>临时供应商信息</h2>
					<ul class="ul_list">
						<table class="table table-bordered">
							<tbody>
								<tr>
									<td class="bggrey" width="20%">供应商名称：</td>
									<td width="30%">${supplier.supplierName}</td>
									<td class="bggrey" width="20%">统一社会信用代码：</td>
									<td width="30%">${supplier.creditCode } </td>
								</tr>
								<tr>
									<td class="bggrey" width="20%">姓名：</td>
									<td width="30%">${supplier.armyBusinessName } </td>
									<td class="bggrey" width="20%">手机：</td>
									<td width="30%">${supplier.armyBuinessTelephone }</td>
								</tr>
							</tbody>
						</table>
					</ul>
				<div class="col-md-12 tc">
					<button class="btn btn-windows back" onclick="fanhui()">返回</button>
				</div>
			</div>
			</div>
			</div>
	</body>
</html>