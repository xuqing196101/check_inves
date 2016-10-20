<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript">
	/** 保存基本信息 */
	function saveProducts(sign) {
		var action = "${pageContext.request.contextPath}/supplier/";
		if (sign == 1) {
			action += "next_step.html";
		} else if(sign == -1) {
			action += "prev_step.html";
		} else {
			action += "stash_step.html";
		}
		$("#products_form_id").attr("action", action);
		$("#products_form_id").submit();

	}
	
	
	function addProductsMsg(categoryId) {
		var supplierId = $("input[name='id']").val();
		layer.open({
			type : 2,
			title : '添加产品信息',
			// skin : 'layui-layer-rim', //加上边框
			area : [ '600px', '500px' ], //宽高
			offset : '100px',
			scrollbar : false,
			content : '${pageContext.request.contextPath}/supplier_products/add_products.html?categoryId=' + categoryId + '&supplierId=' + supplierId, //url
			closeBtn : 1, //不显示关闭按钮
		});
	}
	
	function checkAll(ele, id) {
		var flag = $(ele).prop("checked");
		$("#" + id).find("input:checkbox").each(function() {
			$(this).prop("checked", flag);
		});

	}
	
	function downloadFile(fileName) {
		$("input[name='fileName']").val(fileName);
		$("#download_form_id").submit();
	}
	
	function addParam(id) {
		var checkbox = $("#" + id).find("input:checkbox:checked");
		if (checkbox.size() != 1) {
			layer.msg("请勾选一条记录 !", {
				offset : '300px',
			});
			return;
		}
		var productsId = checkbox.val();
		var categoryId = checkbox.parents("tr").find("td").eq(1).attr("id");
		
		layer.open({
			type : 2,
			title : '添加技术参数',
			// skin : 'layui-layer-rim', //加上边框
			area : [ '600px', '350px' ], //宽高
			offset : '100px',
			scrollbar : false,
			content : '${pageContext.request.contextPath}/product_param/list.html?productsId=' + productsId + '&categoryId=' + categoryId, //url
			closeBtn : 1, //不显示关闭按钮
		});
	}
	
	function deletePro(id) {
		var checkbox = $("#" + id).find("input:checkbox:checked");
		var size = checkbox.size();
		if (size == 0) {
			layer.msg("请至少勾选一条记录 !", {
				offset : '300px',
			});
			return;
		}
		var ids = "";
		checkbox.each(function() {
			if (ids) {
				ids += ",";
			}
			ids += $(this).val();
		});
		var supplierId = $("input[name='id']").val();
		window.location.href = "${pageContext.request.contextPath}/supplier_products/delete.html?supplierId=" + supplierId + "&proIds=" + ids;
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
			 	<span class="new_step current fl"><i class="">6</i><div class="line"></div> <span class="step_desc_02">产品信息</span> </span>
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
						<form id="products_form_id" method="post" enctype="multipart/form-data">
							<input name="id" value="${currSupplier.id}" type="hidden" /> 
							<input name="sign" value="6" type="hidden" />
							<div class="tab-content padding-top-20">
								<div class="tab-pane fade active in height-300" id="tab-1">
									<div class="margin-bottom-0  categories">
										<c:forEach items="${currSupplier.listSupplierItems}" var="item" varStatus="vs">
											<h2 class="f16 jbxx mt40">
												<i>${vs.index + 1}</i>${item.categoryName}产品信息表
											</h2>
											<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="deletePro('products_tbody_id_${vs.index + 1}')">删除</button>
											<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="addParam('products_tbody_id_${vs.index + 1}')">设置技术参数</button>
											<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="addProductsMsg('${item.categoryId}')">添加产品信息</button>
											<table id="share_table_id" class="table table-bordered table-condensed">
												<thead>
													<tr>
														<th class="info"><input type="checkbox" onchange="checkAll(this, 'products_tbody_id_${vs.index + 1}')" /></th>
														<th class="info">所属类别</th>
														<th class="info">产品名称</th>
														<th class="info">品牌</th>
														<th class="info">规格型号</th>
														<th class="info">尺寸</th>
														<th class="info">生产产地</th>
														<th class="info">保质期</th>
														<th class="info">生产商</th>
														<th class="info">参考价格</th>
														<th class="info">产品图片</th>
														<th class="info">商品二维码</th>
													</tr>
												</thead>
												<tbody id="products_tbody_id_${vs.index + 1}">
													<c:forEach items="${item.listSupplierProducts}" var="products" varStatus="vs">
														<tr>
															<td class="tc"><input name="checkbox" type="checkbox" value="${products.id}" /></td>
															<td id="${products.categoryId}" class="tc">${item.categoryName}</td>
															<td class="tc">${products.name}</td>
															<td class="tc">${products.brand}</td>
															<td class="tc">${products.models}</td>
															<td class="tc">${products.proSize}</td>
															<td class="tc">${products.orgin}</td>
															<td class="tc"><fmt:formatDate value="${products.expirationDate }" pattern="yyyy-MM-dd"/></td>
															<td class="tc">${products.producer}</td>
															<td class="tc">${products.referencePrice}</td>
															<td class="tc">
																<c:if test="${products.productPic != null}">
																	<a class="color7171C6 fz11" href="javascript:void(0)" onclick="downloadFile('${products.productPic}')">下载附件</a>
																</c:if>
																<c:if test="${products.productPic == null}">
																	<span class="fz11">无附件下载</span>
																</c:if>
															</td>
															<td class="tc">
																<c:if test="${products.qrCode != null}">
																	<a class="color7171C6 fz11" href="javascript:void(0)"  onclick="downloadFile('${products.qrCode}')">下载附件</a>
																</c:if>
																<c:if test="${products.qrCode == null}">
																	<span class="fz11">无附件下载</span>
																</c:if>
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:forEach>
									</div>
								</div>
							</div>
							<div class="mt40 tc mb50">
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveProducts(-1)">上一步</button>
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveProducts(0)">暂存</button>
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveProducts(1)">下一步</button>
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
	<!-- footer -->
	<jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
</body>
</html>
