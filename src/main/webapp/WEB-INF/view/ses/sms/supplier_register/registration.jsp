<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/front.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

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
   <jsp:include page="/index_head.jsp" />
	<div class="wrapper">
		<div class="container content height-350 job-content ">

			<div class="col-md-12 p20 border1 margin-top-20 mb40">
				<div class="tab-v1">
					<h2 class="tc bbgrey">阅读军队物资供应商须知</h2>
				</div>
				<div class="tab-content margin-bottom-20 margin-top-20 lh24">
					${doc}
					<div class="mt40">
						<div class="fl">
							文件下载：<span class="ml10">供应商注册须知</span><a href="http://www.plap.cn:80/staticFile/军队物资供应商入库标准（试行）.rar" class="download"></a>
						</div>
						<div class="fl ml20">
							产品分类目录<a href="http://www.plap.cn:80/staticFile/军队物资供应商入库标准（试行）.rar" class="download"></a>
						</div>
						<div class="clear"></div>
					</div>
					<div class="mt40">
						<input id="registration_input_id" type="checkbox" checked="checked" class="radio_orange"><span class="ml10">我已阅读，并且完全遵守相关规定</span>
					</div>
					<div class="mt40 tc">
						<input type="button" class="btn padding-left-20 padding-right-20 btn_back" onclick="location='${pageContext.request.contextPath}/first.jsp'" value="返回"> 
						<input id="register_input_id" type="button" class="btn padding-left-20 padding-right-20 btn_back" onclick="location='${pageContext.request.contextPath}/supplier/register_page.html'" value="开始注册">
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
