<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>

<%@ include file="/reg_head.jsp"%>
<title>添加产品信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<script type="text/javascript">
	
	function saveOrBack(sign) {
		var action = "${pageContext.request.contextPath}/supplier_products/";
		if (sign) {
			action += "save_or_update_products.html";
		} else {
			action += "back_to_products.html";
		}
		$("#products_form_id").attr("action", action);
		$("#products_form_id").submit();
	}
	
	function uploadNew(id) {
		$("#" + id).find("div").remove();
		var name = "";
		if (id == "product_li_id") {
			name = "productPicFile";
		} else if (id == "qr_li_id") {
			name = "qrCodeFile";
		}
		var html = "<div class='input-append'>";
		html += "<div class='uploader orange h32 m0 fz8'>";
		html += "<input type='text' class='filename fz8 h32' readonly='readonly'/>";
		html += "<input type='button' name='file' class='button' value='选择...'/>";
		html += "<input name='"+ name +"' type='file' size='30'/>";
		html += "</div>";
		html += "</div>";
		$("#" + id).append(html);
		loadFilePlug();
	}
	
	function downloadFile(fileName) {
		$("input[name='fileName']").val(fileName);
		$("#download_form_id").submit();
	}
</script>

</head>

<body>
<div class="wrapper">

		<!--基本信息-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<form id="products_form_id" method="post" target="_parent">
							<input name="id" value="${supplierProducts.id}" type="hidden" />
							<input name="itemId" value="${supplierProducts.itemId}" type="hidden" />
							<input name="supplierId" value="${supplierProducts.supplierId}" type="hidden" />
							<div class="tab-content padding-top-20">
								<!-- 详细信息 -->
								<div class="tab-pane fade active in height-300" id="tab-1">
									<div class=" margin-bottom-0">
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 col-sm-6 col-xs-12 p0 pl15">
											   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 产品名称：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
													<input class="" type="text" name="name" value="${supplierProducts.name}" />
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 品牌：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
													<input class="" type="text" name="brand" value="${supplierProducts.brand}" />
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 规格型号：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
													<input class="" type="text" name="models" value="${supplierProducts.models}" />
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 尺寸：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
													<input class="" type="text" name="proSize" value="${supplierProducts.proSize}" />
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 生产产地：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
													<input class="" type="text" name="orgin" value="${supplierProducts.orgin}" />
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 保质期：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
													<fmt:formatDate value="${supplierProducts.expirationDate}" pattern="yyyy-MM-dd" var="expirationDate"/>
													<input class="" type="text" name="expirationDate" readonly="readonly" onClick="WdatePicker()" value="${expirationDate}" />
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 生产商：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
													<input class="" type="text" name="producer" value="${supplierProducts.producer}" />
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 参考价格：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
													<input class="" type="text" name="referencePrice" value="${supplierProducts.referencePrice}" />
												</div>
											</li>
											
											<li id="product_li_id" class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
											    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 产品图片：</span>
												<c:if test="${supplierProducts.productPic != null}">
													<div>
														<a class="color7171C6" href="javascript:void(0)" onclick="downloadFile('${supplierProducts.productPic}')">下载附件</a>
														<a title="重新上传" class="ml10 red fz17" href="javascript:void(0)" onclick="uploadNew('product_li_id')">☓</a>
													</div>
												</c:if>
												<c:if test="${supplierProducts.productPic == null}">
													<div class="input-append">
														<div class="uploader orange h32 m0 fz8">
															<input type="text" class="filename h32 fz8" readonly="readonly"/>
															<input type="button" name="file" class="button" value="选择..."/>
															<input name="productPicFile" type="file" size="30"/>
														</div>
													</div>
												</c:if>
											</li>
											<li id="qr_li_id" class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class=""><i class="red">*</i> 商品二维码：</span>
												<c:if test="${supplierProducts.qrCode != null}">
													<div>
														<a class="color7171C6" href="javascript:void(0)" onclick="downloadFile('${supplierProducts.qrCode}')">下载附件</a>
														<a title="重新上传" class="ml10 red fz17" href="javascript:void(0)" onclick="uploadNew('qr_li_id')">☓</a>
													</div>
												</c:if>
												<c:if test="${supplierProducts.qrCode == null}">
													<div class="input-append">
														<div class="uploader orange h32 m0 fz8">
															<input type="text" class="filename h32 fz8" readonly="readonly"/>
															<input type="button" name="file" class="button" value="选择..."/>
															<input name="qrCodeFile" type="file" size="30"/>
														</div>
													</div>
												</c:if>
											</li>
											
											<div class="clear"></div>
										</ul>
									</div>
								</div>
							</div>
							<div class="mt40 tc mb50">
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveOrBack(1)">保存</button>
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveOrBack(0)">返回</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplier/download.html" method="post">
		<input type="hidden" name="fileName" />
	</form>
</body>
</html>
