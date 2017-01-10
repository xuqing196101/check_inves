<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html class=" js cssanimations csstransitions" lang="en">
<head>

<%@ include file="/reg_head.jsp"%>
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
	function downSupplierNotice(){
		window.location.href="${pageContext.request.contextPath}/expert/downSupplierNotice.html";
	}
	function downCategory(){
		window.location.href="${pageContext.request.contextPath}/expert/downCategory.html";
	}
</script>

</head>

<body>
	<div class="wrapper">
		<div class="container content height-350 job-content ">

			<div class="col-md-12 p20 border1 margin-top-20 mb40">
				<div class="tab-content margin-bottom-20 margin-top-20 lh24">
					${doc}
					<div class="mt40">
						<div class="fl">
							文件下载：<span class="ml10">供应商注册须知</span><a href="javascript:downSupplierNotice();" class="download"></a>
						</div>
						<div class="fl ml20">
							产品分类目录<a href="${pageContext.request.contextPath}/supplier/download_category.html" class="download"></a>
						</div>
						<div class="clear"></div>
					</div>
					<div class="mt40">
						<input id="registration_input_id" type="checkbox" checked="checked" class="radio_orange"><span class="ml10">我已阅读，并且完全遵守相关规定</span>
					</div>
					<div class="mt40 tc">
						<input id="register_input_id" type="button" class="btn padding-left-20 padding-right-20 btn_back" onclick="location='${pageContext.request.contextPath}/supplier/register_page.html'" value="开始注册">
						<input type="button" class="btn padding-left-20 padding-right-20 btn_back" onclick="location='${pageContext.request.contextPath}/first.jsp'" value="返回">
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
