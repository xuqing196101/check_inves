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
	function downOpManuals(){
		window.location.href="${pageContext.request.contextPath}/expert/downOpManuals.html";
	}
	
</script>

</head>

<body>
	<div class="wrapper">
		<div class="container content height-350 job-content ">

			<div class="col-md-12 col-sm-12 col-xs-12 p20 border1 margin-top-20 mb40">
				<div class="tab-content margin-bottom-20 margin-top-20 lh24">
					${doc}
					<div class="mt40 col-md-12 col-sm-12 col-xs-12 p0">
					<div class="lh30 font-20"> <span >文件下载：</span></div>
						<div class="font-20 mt10">
							<span >《${docName }》（第二版）</span><a href="${pageContext.request.contextPath}/browser/supplierDownload.html" class="m_download"></a>
						</div>
						
						<div class="font-20 mt10">
							《军队物资工程服务采购产品分类目录》（第三版）<a href="${pageContext.request.contextPath}/browser/categoryDownload.html" class="m_download"></a>
						</div>
						
						<div class="font-20 mt10">
							《军队物资工程服务供应商入库操作手册》<a href="${pageContext.request.contextPath}/browser/downOpManuals.html" class="m_download"></a>
						</div>
						
						<div class="font-20 mt10">
							《供应商注册常见问题汇总》<a href="${pageContext.request.contextPath}/browser/downQuestion.html" class="m_download"></a>
						</div>
						<div class="clear"></div>
					</div>
					<div class="mt40 col-md-12 col-sm-12 col-xs-12 p0">
						<input id="registration_input_id" type="checkbox" checked="checked" class="radio_orange"><span class="ml10">我已阅读，并且完全遵守相关规定</span>
					</div>
					<div class="mt40 tc  col-md-12 col-sm-12 col-xs-12 p0">
						<input id="register_input_id" type="button" class="btn padding-left-20 padding-right-20 btn_back" onclick="location='${pageContext.request.contextPath}/supplier/register_page.html'" value="开始注册">
						<input type="button" class="btn padding-left-20 padding-right-20 btn_back" onclick="location='${pageContext.request.contextPath}/first.jsp'" value="返回">
					</div>
					<div class="mt40 tc col-md-12 col-sm-12 col-xs-12 p0 red f16 b">
						推荐使用火狐浏览器（Firefox）、谷歌浏览器（Chrome）<!-- 以及IE9以上浏览器 -->！
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="footer_margin">
   		<jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
   </div>
</body>
</html>
