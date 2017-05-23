<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
	<!--<![endif]-->

	<head>
		<%@ include file="../../../common.jsp"%>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>基本信息</title>
		<!-- Meta -->
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">

		<script type="text/javascript">
			function tijiao() {
				var oldPassword = $("#oldPassword").val();
				var newPassword1 = $("#newPassword1").val();
				var newPassword2 = $("#newPassword2").val();
				if(!newPassword1) {
					layer.tips("请输入新密码.", "#newPassword1");
					return;
				}
				if(!newPassword2) {
					layer.tips("请确认新密码.", "#newPassword2");
					return;
				}
				$.ajax({
					url: "${pageContext.request.contextPath}/supplierInfo/udpate_password.do",
					type: "post",
					data: {
						oldPassword: oldPassword,
						newPassword1: newPassword1,
						newPassword2: newPassword2,
					},
					success: function(result) {
						if(result == "1") {
							layer.tips("旧密码输入错误.", "#oldPassword");
							$("#password").val("");
						}
						if(result == "2") {
							layer.tips("两次输入密码不一致.", "#newPassword1");
							$("#newPassword2").val("");
						}
						if(result == "3") {
							layer.msg("修改成功");
						}
						if(result == "4") {
							layer.tips("两次输入密码都不能为空.", "#newPassword1");
						}
					},
				});
			};

			function qingkong() {
				$("#newPassword1").val('');
				$("#newPassword2").val('');
			}
		</script>
	</head>

	<body>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0);">首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">个人信息</a>
					</li>
					<li>
						<a href="javascript:void(0);">修改密码</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 项目戳开始 -->
		<div class="container">
			<div class="headline-v2">
				<h2>修改密码</h2>
			</div>
			<div class="content table_box">
				<table id="tb1" class="table table-bordered table-condensed table-hover table-striped">
					<tbody>
						<tr>
							<td class="bggrey tr">用户名：</td>
							<td>
								<input class="span2" value="${loginName }" readonly="readonly" type="text">
							</td>
						</tr>
						<tr>
							<td class="bggrey tr">旧密码：</td>
							<td>
								<input class="span2" id="oldPassword" name="oldPassword" type="password">
							</td>
						</tr>
						<tr>
							<td class="bggrey tr">新密码：</td>
							<td>
								<input class="span2" id="newPassword1" name="newPassword1" type="password">
							</td>
						</tr>
						<tr>
							<td class="bggrey tr">确认新密码：</td>
							<td>
								<input class="span2" id="newPassword2" name="newPassword2" type="password">
							</td>
						</tr>
					</tbody>
				</table>
				<div class="tc mt20 clear col-md-11">
					<button class="btn btn-windows git" onclick="tijiao()">立即更改</button>
					<button class="btn btn-windows git" onclick="qingkong()">重新输入</button>
				</div>
			</div>
		</div>
	</body>

</html>