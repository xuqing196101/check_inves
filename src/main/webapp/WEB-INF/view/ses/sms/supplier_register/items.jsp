<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>供应商完品目信息</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/common.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/line-icons.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/application.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/header-v4.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/footer-v2.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/img-hover.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/page_job.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/shop.style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript">

	$(function() {
		$("#page_ul_id").find("li").click(function() {
			var id = $(this).attr("id");
			var page = "tab-" + id.charAt(id.length - 1);
			$("input[name='defaultPage']").val(page);
		});
		var defaultPage = "${defaultPage}";
		if (defaultPage) {
			var num = defaultPage.charAt(defaultPage.length - 1);
			$("#page_ul_id").find("li").each(function(index) {
				var liId = $(this).attr("id");
				var liNum = liId.charAt(liId.length - 1);
				if (liNum == num) {
					$(this).attr("class", "active");
				} else {
					$(this).removeAttr("class");
				}
			});
			$(".tab-pane").each(function() {
				var id = $(this).attr("id");
				if (id == defaultPage) {
					$(this).attr("class", "tab-pane fade height-300 active in");
				} else {
					$(this).attr("class", "tab-pane fade height-300");
				}
			});
		} else {
			$("#page_ul_id").find("li").each(function(index) {
				if (index == 0) {
					$(this).attr("class", "active");
				} else {
					$(this).removeAttr("class");
				}
			});
			$(".tab-pane").each(function(index) {
				if (index == 0) {
					$(this).attr("class", "tab-pane fade height-300 active in");
				} else {
					$(this).attr("class", "tab-pane fade height-300");
				}
			});
		}
	});

	/** 保存品目树信息 */
	function saveItems(sign) {
		var action = "${pageContext.request.contextPath}/supplier/";
		if (sign == 1) {
			action += "next_step.html";
		} else if (sign == -1) {
			action += "prev_step.html";
		} else {
			action += "stash_step.html";
		}
		$("#items_info_form_id").attr("action", action);
		$("#items_info_form_id").submit();

	}
</script>

</head>

<body>
	<div class="wrapper">
		<!-- header -->
		<jsp:include page="../../../../../index_head.jsp"></jsp:include>

		<!-- 项目戳开始 -->
		<div class="container clear margin-top-30">
			<h2 class="padding-20 mt40 ml30">
				<span class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_01">用户名密码</span> </span>
				<span class="new_step current fl"><i class="">2</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span>
				<span class="new_step current fl"><i class="">3</i><div class="line"></div> <span class="step_desc_01">供应商类型</span> </span>
			 	<span class="new_step current fl"><i class="">4</i><div class="line"></div> <span class="step_desc_02">专业信息</span> </span>
			 	<span class="new_step current fl"><i class="">5</i><div class="line"></div> <span class="step_desc_01">品目信息</span></span> 
			 	<span class="new_step fl"><i class="">6</i><div class="line"></div> <span class="step_desc_02">产品信息</span> </span>
			 	<span class="new_step fl"><i class="">7</i><div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span>
			 	<span class="new_step fl"><i class="">8</i><div class="line"></div> <span class="step_desc_02">打印申请表</span> </span>
			 	<span class="new_step fl"><i class="">9</i> <span class="step_desc_01">申请表承诺书上传</span> </span>
				<div class="clear"></div>
			</h2>
		</div>

		<!--基本信息-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<ul id="page_ul_id" class="nav nav-tabs bgdd">
							<c:if test="${fn:contains(currSupplier.supplierTypeNames, '生产型')}">
								<li id="li_id_1" class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">物资-生产型专业信息</a></li>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeNames, '销售型')}">
								<li id="li_id_2" class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="fujian f18">物资-销售型专业信息</a></li>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeNames, '工程')}">
								<li id="li_id_3" class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" class="fujian f18">工程专业信息</a></li>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeNames, '服务')}">
								<li id="li_id_4" class=""><a aria-expanded="false" href="#tab-4" data-toggle="tab" class="fujian f18">服务专业信息</a></li>
							</c:if>
						</ul>
						<form id="items_info_form_id" method="post" enctype="multipart/form-data">
							<input name="id" value="${currSupplier.id}" type="hidden" /> 
							<input name="sign" value="5" type="hidden" />
							<input type="hidden" name="defaultPage" value="${defaultPage}" />
							<div class="tab-content padding-top-20">
								<c:if test="${fn:contains(currSupplier.supplierTypeNames, '生产型')}">
									<!-- 物资生产型 -->
									<div class="tab-pane fade active in height-450" id="tab-1">
										1
									</div>
								</c:if>
								<c:if test="${fn:contains(currSupplier.supplierTypeNames, '销售型')}">
									<!-- 物资销售型 -->
									<div class="tab-pane fade height-450" id="tab-2">
										2
									</div>
								</c:if>
								<c:if test="${fn:contains(currSupplier.supplierTypeNames, '工程')}">
								<!-- 服务 -->
									<div class="tab-pane fade height-200" id="tab-3">
										3
									</div>
								</c:if>
								<c:if test="${fn:contains(currSupplier.supplierTypeNames, '服务')}">
									<!-- 生产 -->
									<div class="tab-pane fade height-200" id="tab-4">
										4多少
									</div>
								</c:if>
							</div>
							<div class="mt40 tc mb50">
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveItems(-1)">上一步</button>
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveItems(0)">暂存</button>
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveItems(1)">下一步</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- footer -->
	<jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
</body>
</html>
