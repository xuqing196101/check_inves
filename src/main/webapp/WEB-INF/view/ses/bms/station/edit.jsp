<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<script type="text/javascript">
	function cheClick() {
		var roleIds = "";
		var roleNames = "";
		$('input[name="chkItem"]:checked').each(function() {
			var idName = $(this).val();
			var arr = idName.split(";");
			roleIds += arr[0] + ",";
			roleNames += arr[1] + ",";
		});
		$("#roleId").val(roleIds.substr(0, roleIds.length - 1));
		$("#roleName").val(roleNames.substr(0, roleNames.length - 1));
	}
	//初始化选择角色
	$(function() {
		var initRid = $("#roleId").val().split(",");
		$('input[name="chkItem"]').each(function() {
			var idName = $(this).val();
			var arr = idName.split(";");
			for (var int = 0; int < initRid.length; int++) {
				if (initRid[int] == arr[0]) {
					$(this).attr("checked", 'true');
				}
			}
		});
	});
</script>
<body>

	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="${pageContext.request.contextPath}"> 首页</a></li>
				<li><a href="javascript:void(0);">业务管理</a></li>
				<li><a href="javascript:void(0);">订单中心</a></li>
				<li class="active"><a href="javascript:void(0);">修改订单</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<!-- 修改订列表开始-->
	<div class="container">
		<form action="${pageContext.request.contextPath}/StationMessage/updateStationMessage.do"
			method="post">
			<div>
				<div class="headline-v2">
					<h2>修改站内消息</h2>
				</div>
				<ul class="list-unstyled list-flow p0_20">
					<input class="span2" name="id" type="hidden" 
						value="${StationMessage.id}">
					<li class="col-md-6 p0 "><span class="">标题：</span>
						<div class="input-append">
							<input class="span2 w350 " maxlength="40"  name="title" type="text"
								value="${StationMessage.title}">
						</div></li>
					<li class="col-md-12  p0 "><span class="">内容：</span> <textarea
							class="w350 h100" cols="3" maxlength="100"  rows="100" name="content">${StationMessage.content}</textarea>
					</li>
				</ul>
			</div>
			<div class="col-md-12">
				<div class="fl padding-10">
					<c:if test="${operation==1}">
						<button class="btn btn-windows save" type="submit">保存</button>
					</c:if>
					<c:if test="${operation==2}">
						<button class="btn btn-windows edit" type="submit">修改</button>
					</c:if>
					<button class="btn btn-windows git" onclick="history.go(-1)"
						type="button">返回</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
