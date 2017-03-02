<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>

<%@ include file="/reg_head.jsp"%>
<title>评审专家注册须知</title>
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
	function downNotice(){
		window.location.href="${pageContext.request.contextPath}/expert/downNotice.html";
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
				<div class="tab-v1">
					<!-- <h2 class="tc bbgrey">阅读评审专家须知</h2> -->
				</div>
				<div class="tab-content margin-bottom-20 margin-top-20 lh24">
				${doc}
					<div class="mt40">
						<div class="fl">
							文件下载：<span class="ml10">《军队评审专家入库须知》</span><a onclick='downNotice()' href="javascript:void(0)" class="download"></a>
						</div>
					</div>
					<div class="mt40">
						<div class="fl">
							《军队物资工程服务采购产品分类目录》<a href="${pageContext.request.contextPath}/supplier/download_category.html" class="download"></a>
						</div>
						<div class="clear"></div>
					</div>
					<div class="mt40">
						<input id="registration_input_id" type="checkbox" checked="checked" class="radio_orange"><span class="ml10">我已阅读，并且完全遵守相关规定</span>
					</div>
					<div class="mt40 tc">
						<input id="register_input_id" type="button" class="btn padding-left-20 padding-right-20 btn_back" onclick="location='${pageContext.request.contextPath}/expert/toExpert.html'" value="开始注册">
					    <input id="register_input_id" type="button" class="btn padding-left-20 padding-right-20 btn_back" onclick="location.href='javascript:history.go(-1);'" value="取消">
					</div>
					<div class="mt40 tc" style="color: red;font-size: 16px;font-weight: bolder;">
						推荐使用火狐浏览器（Firefox）、谷歌浏览器（Chrome）以及IE9以上浏览器！
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
