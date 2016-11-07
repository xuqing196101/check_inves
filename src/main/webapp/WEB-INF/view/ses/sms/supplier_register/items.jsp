<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../../../../index_head.jsp"%>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript">
	var zTreeObj;
	var zNodes;
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
			$("#tab_content_div_id").find(".tab-pane").each(function() {
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
			$("#tab_content_div_id").find(".tab-pane").each(function(index) {
				if (index == 0) {
					$(this).attr("class", "tab-pane fade height-300 active in");
				} else {
					$(this).attr("class", "tab-pane fade height-300");
				}
			});
		}
		// ztree
		$("#tab_content_div_id").find(".tab-pane").each(function(index) {
			var kind = "";
			var id = $(this).attr("id");
			if (id == "tab-1") kind = "1";
			if (id == "tab-2") kind = "2";
			if (id == "tab-3") kind = "3";
			if (id == "tab-4") kind = "4";
			loadZtree(id, kind);
		});
		
	});
	
	function loadZtree(id, kind) {
		var id = "";
		if (kind == "1") id = "tree_ul_id_1";
		if (kind == "2") id = "tree_ul_id_2";
		if (kind == "3") id = "tree_ul_id_3";
		if (kind == "4") id = "tree_ul_id_4";
		var setting = {
			async : {
				enable : true,
				url : "${pageContext.request.contextPath}/category/find_category.do",
				otherParam : {
					supplierId : "${currSupplier.id}",
					kind : kind
				},
				dataType : "json",
				type : "post",
			},
			check : {
				enable : true,
				chkboxType : {
					"Y" : "s",
					"N" : "s"
				}
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parentId"
				}
			},
			callback: {
				onCheck: onCheck
			}
		};
		zTreeObj = $.fn.zTree.init($("#" + id), setting, zNodes);
	}
	
	function onCheck(e, treeId, treeNode) {
		var ids = "";
		var flag = treeNode.checked;
		var result = checkType();
		var tree = $.fn.zTree.getZTreeObj(result.id);
		var nodes = tree.getChangeCheckedNodes();
		for (var i = 0; i < nodes.length; i++) {
			if (!nodes[i].isParent) {
				if (ids) {
					ids += ",";
				}
				ids += nodes[i].id;
			}
		}
		
		if (ids) {
			$.ajax({
				url : "${pageContext.request.contextPath}/supplier_level/find_credit_ctnt_by_credit_id.do",
				type : "post",
				data : {
					ids : ids,
					flag : flag,
					type : result.type
				},
				dataType : "json",
				success : function(result) {
				},
			});
		}
		
		/**for (var i = 0; i < nodes.length; i++) {
			nodes[i].checkedOld = nodes[i].checked;
		}*/
	}
	

	/** 保存品目树信息 */
	function saveItems(jsp) {
		var result = getIds("tree_ul_id_1");
		var addProCategoryIds = result.addIds;
		var deleteProCategoryIds = result.deleteIds;
		
		result = getIds("tree_ul_id_2");
		var addSellCategoryIds = result.addIds;
		var deleteSellCategoryIds = result.deleteIds;
		
		result = getIds("tree_ul_id_3");
		var addEngCategoryIds = result.addIds;
		var deleteEngCategoryIds = result.deleteIds;
		
		result = getIds("tree_ul_id_4");
		var addServeCategoryIds = result.addIds;
		var deleteServeCategoryIds = result.deleteIds;
		
		$("input[name='jsp']").val(jsp);
		
		$("input[name='addProCategoryIds']").val(addProCategoryIds);
		$("input[name='deleteProCategoryIds']").val(deleteProCategoryIds);
		
		$("input[name='addSellCategoryIds']").val(addSellCategoryIds);
		$("input[name='deleteSellCategoryIds']").val(deleteSellCategoryIds);
		
		$("input[name='addEngCategoryIds']").val(addEngCategoryIds);
		$("input[name='deleteEngCategoryIds']").val(deleteEngCategoryIds);
		
		$("input[name='addServeCategoryIds']").val(addServeCategoryIds);
		$("input[name='deleteServeCategoryIds']").val(deleteServeCategoryIds);
		
		$("#items_info_form_id").submit();
	}
	
	function getIds(id) {
		var treeObj = $.fn.zTree.getZTreeObj(id);
		var addIds = "";
		var deleteIds = "";
		if (treeObj) {
			var nodes = treeObj.getChangeCheckedNodes();
			for ( var i = 0; i < nodes.length; i++) {
				if (!nodes[i].isParent) {
					if (nodes[i].checked) {
						if (addIds) {
							addIds += ",";
						}
						addIds += nodes[i].id;
					} else {
						if (deleteIds) {
							deleteIds += ",";
						}
						deleteIds += nodes[i].id;
					}
				}
			}
		}
		var result = {
			addIds : addIds,
			deleteIds : deleteIds
		};
		return result;
	}
	
</script>

</head>

<body>
	<div class="wrapper">

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
		<div class="container content height-300">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<ul id="page_ul_id" class="nav nav-tabs bgdd">
							<c:if test="${fn:contains(currSupplier.supplierTypeNames, '生产型')}">
								<li id="li_id_1" class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">物资-生产型品目信息</a></li>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeNames, '销售型')}">
								<li id="li_id_2" class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="fujian f18">物资-销售型品目信息</a></li>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeNames, '工程')}">
								<li id="li_id_3" class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" class="fujian f18">工程品目信息</a></li>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeNames, '服务')}">
								<li id="li_id_4" class=""><a aria-expanded="false" href="#tab-4" data-toggle="tab" class="fujian f18">服务品目信息</a></li>
							</c:if>
						</ul>
						<div class="tab-content padding-top-20" id="tab_content_div_id">
							<c:if test="${fn:contains(currSupplier.supplierTypeNames, '生产型')}">
								<!-- 物资生产型 -->
								<div class="tab-pane fade active in height-300" id="tab-1">
									<h2 class="f16 jbxx">
										<i>01</i>勾选物资生产型品目信息
									</h2>
									<div class="lr0_tbauto w200">
										<ul id="tree_ul_id_1" class="ztree mt30"></ul>
									</div>
								</div>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeNames, '销售型')}">
								<!-- 物资销售型 -->
								<div class="tab-pane fade height-300" id="tab-2">
									<h2 class="f16 jbxx">
										<i>01</i>勾选物资销售型品目信息
									</h2>
									<div class="lr0_tbauto w200">
										<ul id="tree_ul_id_2" class="ztree mt30"></ul>
									</div>
								</div>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeNames, '工程')}">
							<!-- 服务 -->
								<div class="tab-pane fade height-200" id="tab-3">
									<h2 class="f16 jbxx">
										<i>01</i>勾选工程品目信息
									</h2>
									<div class="lr0_tbauto w200">
										<ul id="tree_ul_id_3" class="ztree mt30"></ul>
									</div>
								</div>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeNames, '服务')}">
								<!-- 生产 -->
								<div class="tab-pane fade height-200" id="tab-4">
									<h2 class="f16 jbxx">
										<i>01</i>勾选服务品目信息
									</h2>
									<div class="lr0_tbauto w200">
										<ul id="tree_ul_id_4" class="ztree mt30"></ul>
									</div>
								</div>
							</c:if>
						</div>
						<div class="mt40 tc mb50">
							<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveItems('professional_info')">上一步</button>
							<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveItems('items')">暂存</button>
							<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveItems('products')">下一步</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<form id="items_info_form_id" action="${pageContext.request.contextPath}/supplier_item/save_or_update.html" method="post">
		<input name="supplierId" value="${currSupplier.id}" type="hidden" /> 
		<input name="jsp" type="hidden" />
		<input type="hidden" name="defaultPage" value="${defaultPage}" />
		
		<input type="hidden" name="addProCategoryIds" />
		<input type="hidden" name="deleteProCategoryIds" />
		
		<input type="hidden" name="addSellCategoryIds" />
		<input type="hidden" name="deleteSellCategoryIds" />
		
		<input type="hidden" name="addEngCategoryIds" />
		<input type="hidden" name="deleteEngCategoryIds" />
		
		<input type="hidden" name="addServeCategoryIds" />
		<input type="hidden" name="deleteServeCategoryIds" />
	</form>
	<!-- footer -->
	<jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
</body>
</html>
