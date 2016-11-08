<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${currSupplier.status != 7}"><%@ include file="../../../../../index_head.jsp"%></c:if>
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
<c:if test="${currSupplier.status == 7}">
	<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/> 
	<script>var globalPath = "${contextPath}";</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/public/ZHQ/js/jquery_ujs.js"></script>
	<script src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>
</c:if>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript">
	/** 保存基本信息 */
	function otherPage(jsp) {
		$("input[name='jsp']").val(jsp);
		$("#template_download_form_id").submit();
	}
</script>

</head>

<body>
	<div class="wrapper">

		<!-- 项目戳开始 -->
		<c:if test="${currSupplier.status != 7}">
			<div class="container clear margin-top-30">
				<h2 class="padding-20 mt40 ml30">
					<span class="new_step current fl"><i class="">1</i>
						<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step current fl"><i class="">2</i>
						<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step current fl"><i class="">3</i>
						<div class="line"></div> <span class="step_desc_01">供应商类型</span> </span> <span class="new_step current fl"><i class="">4</i>
						<div class="line"></div> <span class="step_desc_02">专业信息</span> </span> <span class="new_step current fl"><i class="">5</i>
						<div class="line"></div> <span class="step_desc_01">品目信息</span> </span> <span class="new_step current fl"><i class="">6</i>
						<div class="line"></div> <span class="step_desc_02">产品信息</span> </span> <span class="new_step current fl"><i class="">7</i>
						<div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span> <span class="new_step current fl"><i class="">8</i>
						<div class="line"></div> <span class="step_desc_02">打印申请表</span> </span> <span class="new_step fl"><i class="">9</i> 
						<span class="step_desc_01">申请表承诺书上传</span> 
					</span>
					<div class="clear"></div>
				</h2>
			</div>
		</c:if>

		<!--基本信息-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<form id="template_download_form_id" action="${pageContext.request.contextPath}/supplier/perfect_download.html" method="post">
							<input name="id" value="${currSupplier.id}" type="hidden" /> 
							<input name="jsp" type="hidden" />
							<div class="tab-content padding-top-20">
								<!-- 物资生产型 -->
								<div class="tab-pane fade active in height-300" id="tab-1">
									<div class="margin-bottom-0  categories">
										<h2 class="f16 jbxx mt40">
											<i>01</i>申请表和承诺书下载
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class="zzzx w245"> 军队供应商分级方法：</span>
												<div class="input-append">
													<a class="mt3 color7171C6" href="javascript:void(0)">下载附件</a>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx w245">军队供应商承诺书：</span>
												<div class="input-append">
													<div class="input-append">
														<a class="mt3 color7171C6" href="javascript:void(0)">下载附件</a>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx w245">军队供应商入库申请表：</span>
												<div class="input-append">
													<div class="input-append">
														<a class="mt3 color7171C6" href="javascript:void(0)">下载附件</a>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx w245">军队供应商抽取记录表：</span>
												<div class="input-append">
													<div class="input-append">
														<a class="mt3 color7171C6" href="javascript:void(0)">下载附件</a>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx w245">军队供应商实地考察记录表：</span>
												<div class="input-append">
													<div class="input-append">
														<a class="mt3 color7171C6" href="javascript:void(0)">下载附件</a>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx w245">军队供应商实地考察廉政意见函：</span>
												<div class="input-append">
													<div class="input-append">
														<a class="mt3 color7171C6" href="javascript:void(0)">下载附件</a>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx w245">军队供应商注册信息变更申请表：</span>
												<div class="input-append">
													<div class="input-append">
														<a class="mt3 color7171C6" href="javascript:void(0)">下载附件</a>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx w245">军队供应商退库申请表：</span>
												<div class="input-append">
													<div class="input-append">
														<a class="mt3 color7171C6" href="javascript:void(0)">下载附件</a>
													</div>
												</div>
											</li>
											<div class="clear"></div>
										</ul>
									</div>
								</div>
							</div>
							<div class="mt40 tc mb50">
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="otherPage('procurement_dep')">上一步</button>
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="otherPage('template_upload')">下一步</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- footer -->
	<c:if test="${currSupplier.status != 7}"><jsp:include page="../../../../../index_bottom.jsp"></jsp:include></c:if>
</body>
</html>
