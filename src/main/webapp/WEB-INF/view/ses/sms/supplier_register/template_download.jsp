<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/reg_head.jsp"%>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<c:if test="${currSupplier.status == 2}">
			<%@ include file="/WEB-INF/view/ses/sms/supplier_register/supplier_purchase_dept.jsp"%>
		</c:if>
		<title>供应商注册</title>
		<script type="text/javascript">

			/** 保存基本信息 */
			function otherPage(flag) {
				if(flag == "prev"){
					updateStep(6);
				}
				if(flag == "next"){
					$("#template_download_form_id").submit();
				}
			}

			//下载
			function downloadTable() {
			 	var index = layer.load(1);
				var supplierId = "${currSupplier.id}";
				$.ajax({
					url: "${pageContext.request.contextPath}/supplier/isPass.do",
					data: {
						"supplierId": supplierId
					},
					type: "post",
					success: function(data) {
						if (data == "1") {
							layer.close(index);
							$("#supplierJson").val(supplierId);
							$("#download_form").submit();
						} else {
							layer.msg("近3年加权平均净资产不满足物资销售型供应商的要求！");
							layer.close(index);
						}
					}
				});
			}
			//下载
			/* function downloadNotice() {
				window.location.href = "${pageContext.request.contextPath}/expert/downloadSupplierNotice.html";
			} */
			sessionStorage.locationG = true;
			sessionStorage.index = 7;
		</script>

	</head>

	<body>
		<div class="wrapper">
			<jsp:include page="/WEB-INF/view/ses/sms/supplier_register/common_jump.jsp">
				<jsp:param value="${currSupplier.id}" name="supplierId"/>
				<jsp:param value="${currSupplier.status}" name="supplierSt"/>
				<jsp:param value="7" name="currentStep"/>
			</jsp:include>
			<!-- <div class="container clear margin-top-30">
				<h2 class="step_flow">
					<span id="sp1" class="new_step current fl" onclick="updateStep('1')"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span>
		            <span id="sp2" class="new_step current fl" onclick="updateStep('2')"><i class="">2</i><div class="line"></div> <span class="step_desc_01">供应商类型</span> </span>
		            <span id="ty3" class="new_step current fl" onclick="updateStep('3')"><i class="">3</i><div class="line"></div> <span class="step_desc_02">产品类别</span> </span>
		            <span id="sp4" class="new_step current fl" onclick="updateStep('4')"><i class="">4</i><div class="line"></div> <span class="step_desc_01">资质文件维护</span> </span>
		            <span id="sp5" class="new_step current fl" onclick="updateStep('5')"><i class="">5</i><div class="line"></div> <span class="step_desc_02">销售合同</span> </span>
		            <span id="sp6" class="new_step current fl" onclick="updateStep('6')"><i class="">6</i><div class="line"></div> <span class="step_desc_01">采购机构</span> </span>
		            <span id="sp7" class="new_step current fl"><i class="">7</i><div class="line"></div> <span class="step_desc_02">承诺书和申请表</span> </span>
		            <span id="sp8" class="new_step fl new_step_last"><i class="">8</i> <span class="step_desc_01">提交审核</span> </span>
		            <div class="clear"></div>
				</h2>
			</div> -->

			<!--基本信息-->
			<div class="container content height-350">
				<div class="row magazine-page">
					<div class="col-md-12 tab-v2 job-content">
						<div class="padding-top-10">
							<form action="${pageContext.request.contextPath}/supplier/downloadApplicationForm.html" method="post" id="download_form">
								<input type="hidden" value="" name="supplierJson" id="supplierJson">
							</form>
							<form id="template_download_form_id" action="${pageContext.request.contextPath}/supplier/perfect_download.html" method="post">
								<input name="id" value="${currSupplier.id}" type="hidden" />
								<div class="tab-content padding-top-20">
									<div class="tab-pane fade active in height-300" id="tab-1">
										<div class="margin-bottom-0  categories">
											<h1 class="f16  mt40"> 申请表和承诺书下载 </h1>

											<ul class="list-unstyled f14">
												<li class="col-md-6 col-sm-12 col-xs-12 mb25 pl10">
													<span class="col-md-5 col-sm-12 col-xs-12 padding-left-5">下载《军队供应商承诺书》</span>
													<a class="mt3 color7171C6" href="${pageContext.request.contextPath}/browser/supplierPromise.html"><i class="download mr5"></i></a>
												</li>
											</ul>
											<ul class="list-unstyled f14">
												<li class="col-md-6 col-sm-12 col-xs-12 mb25 pl10">
													<span class="col-md-6 col-sm-12 col-xs-12 padding-left-5">下载 《军队供应商库入库申请表》</span>
													<a class="mt3 color7171C6" href="javascript:downloadTable()"><i class="download mr5"></i></a>
												</li>
											</ul>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="btmfix">
			<div class="mt15 tc">
				<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="otherPage('prev')">上一步</button>
				<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="otherPage('next')">下一步</button>
			</div>
		</div>

			<!-- footer -->
		<div class="footer_margin">
   			<jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
 		</div>
	</body>

</html>