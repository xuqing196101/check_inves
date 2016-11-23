<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/view/front.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>评审专家注册须知</title>
<jsp:include page="/WEB-INF/view/ses/ems/expert/common/common.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		// 注册须知
		$("#registration_input_id").change(function() {
			var flag = $(this).prop("checked");
			if (!flag) {
				$("#register_input_id").attr("disabled", "disabled");
			} else {
				$("#register_input_id").removeAttr("disabled", "disabled");
			}
		});
	});
</script>

</head>

<body>
	<div class="wrapper">
	<jsp:include page="/index_head.jsp"></jsp:include>
		<div class="container content height-350 job-content ">
			<div class="col-md-12 p20 border1 margin-top-20 mb40">
				<div class="tab-v1">
					<h2 class="tc bbgrey">阅读评审专家须知</h2>
				</div>
				<div class="tab-content margin-bottom-20 margin-top-20 lh24">
				${doc }
					<div class="mt40">
						<input id="registration_input_id" type="checkbox" checked="checked" class="radio_orange"><span class="ml10">我已阅读，并且完全遵守相关规定</span>
					</div>
					<div class="mt40 tc">
					    <a class="btn btn-windows reset"  onclick="location.href='javascript:history.go(-1);'">返回</a>
						<input id="register_input_id" type="button" class="btn padding-left-20 padding-right-20 btn_back" onclick="location='${pageContext.request.contextPath}/expert/toExpert.html'" value="开始注册">
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
