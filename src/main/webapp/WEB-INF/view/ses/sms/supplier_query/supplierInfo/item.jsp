<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
	<!--<![endif]-->

	<head>
		<%@ include file="../../../../common.jsp"%>
		<script type="text/javascript">
			function tijiao(str) {
				var action;
				if(str == "essential") {
					action = "${pageContext.request.contextPath}/supplierQuery/essential.html";
				}
				if(str == "financial") {
					action = "${pageContext.request.contextPath}/supplierQuery/financial.html";
				}
				if(str == "shareholder") {
					action = "${pageContext.request.contextPath}/supplierQuery/shareholder.html";
				}
				if(str == "chengxin") {
					action = "${pageContext.request.contextPath}/supplierQuery/list.html";
				}
				if(str == "item") {
					action = "${pageContext.request.contextPath}/supplierQuery/item.html";
				}
				if(str == "product") {
					action = "${pageContext.request.contextPath}/supplierQuery/product.html";
				}
				if(str == "updateHistory") {
					action = "${pageContext.request.contextPath}/supplierQuery/showUpdateHistory.html";
				}
				if(str == "zizhi") {
					action = "${pageContext.request.contextPath}/supplierQuery/aptitude.html";
				}
				if(str == "contract") {
					action = "${pageContext.request.contextPath}/supplierQuery/contract.html";
				}
				if(str == "supplierType") {
					action = "${pageContext.request.contextPath}/supplierQuery/supplierType.html";
				}
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			function downloadFile(fileName) {
				fileName = encodeURI(fileName);
				fileName = encodeURI(fileName);
				window.location.href = "${pageContext.request.contextPath}/supplierQuery/downLoadFile.html?fileName=" + fileName;
			}

			function fanhui() {
				window.location.href = "${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?address=" + encodeURI(encodeURI('${suppliers.address}')) + "&status=${status}";
			}
			var zTreeObj;
			var zNodes;
			$(function() {
				$("#page_ul_id").find("li").click(function() {
					var id = $(this).attr("id");
					var page = "tab-" + id.charAt(id.length - 1);
					$("input[name='defaultPage']").val(page);
				});
				var defaultPage = "${defaultPage}";
				if(defaultPage) {
					var num = defaultPage.charAt(defaultPage.length - 1);
					$("#page_ul_id").find("li").each(function(index) {
						var liId = $(this).attr("id");
						var liNum = liId.charAt(liId.length - 1);
						if(liNum == num) {
							$(this).attr("class", "active");
						} else {
							$(this).removeAttr("class");
						}
					});
					$("#tab_content_div_id").find(".tab-pane").each(function() {
						var id = $(this).attr("id");
						if(id == defaultPage) {
							$(this).attr("class", "tab-pane fade height-300 active in");
						} else {
							$(this).attr("class", "tab-pane fade height-300");
						}
					});
				} else {
					$("#page_ul_id").find("li").each(function(index) {
						if(index == 0) {
							var id = $(this).attr("id");
							defaultLoadTab(id);
							$(this).attr("class", "active");
						} else {
							$(this).removeAttr("class");
						}
					});
					$("#tab_content_div_id").find(".tab-pane").each(function(index) {
						if(index == 0) {
							$(this).attr("class", "tab-pane fade height-300 active in");
						} else {
							$(this).attr("class", "tab-pane fade height-300");
						}
					});
				}

			});

			//加载默认的页签
			function defaultLoadTab(id) {
				if(id = "tree_ul_id_1") {
					loadTab('PRODUCT', 'tree_ul_id_1', 1);
				}
				if(id = "tree_ul_id_2") {
					loadTab('SALES', 'tree_ul_id_2', 2);
				}
				if(id = "tree_ul_id_3") {
					loadTab('PROJECT', 'tree_ul_id_3', null);
				}
				if(id = "tree_ul_id_4") {
					loadTab('SERVICE', 'tree_ul_id_4', null);
				}
			}

			//加载对应的节点数据
			function loadZtree(code, kind, status) {
				var supplierId = $("#supplierId").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/supplierAudit/getTree.do",
					data: {
						"supplierId": supplierId,
						"code": code
					},
					async: false,
					dataType: "json",
					success: function(response) {
						zNodes = response;
					}
				});
				var setting = {
					data: {
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "parentId",
						}
					},
					view: {
						showLine: true
					}

				};
				var ztreeObj = $.fn.zTree.init($("#" + kind), setting, zNodes);
				ztreeObj.expandAll(true); //全部展开
			}

			//加载tab页签
			function loadTab(code, kind, status) {
				loadZtree(code, kind, status);
			}

			function getCategoryId() {
				var ids = [];
				for(var i = 1; i < 5; i++) {
					var id = "tree_ul_id_" + i;
					var tree = $.fn.zTree.getZTreeObj(id);
					if(tree != null) {
						nodes = tree.getCheckedNodes(true);
						for(var j = 0; j < nodes.length; j++) {
							alert(nodes[j].name);
							alert(nodes[j].id);
							ids.push(nodes[j].id);

						}
					}
				}
				alert(ids);
				$("#categoryId").val(ids);
				return ids;
			}

			function supCategory() {
				var flag = true;
				var supplierId = "${currSupplier.id}";
				$.ajax({
					url: "${pageContext.request.contextPath}/supplier_item/getSupplierCate.do",
					type: "post",
					data: {
						supplierId: supplierId,

					},
					dataType: "json",
					success: function(result) {
						if(result == "0") {
							flag = false;

						}
					}
				});
				return flag;
			}
		</script>

		<style type="text/css">
			.line {
				border: 4px solid #fff0;
			}
		</style>
	</head>

	<body>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="#"> 首页</a>
					</li>
					<li>
						<a href="#">支撑系统</a>
					</li>
					<li>
						<a href="#">供应商查看</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container container_box">
			<div class=" content height-350">
				<div class="col-md-12 tab-v2 job-content">
					<ul class="nav nav-tabs bgwhite">
						<li class="">
							<a aria-expanded="true" class="f18" href="#tab-1" data-toggle="tab" onclick="tijiao('essential');">基本信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" class="f18" href="#tab-2" data-toggle="tab" onclick="tijiao('financial');">财务信息</a>
						</li>
						<li class="">
							<a aria-expanded="fale" class="f18" href="#tab-3" data-toggle="tab" onclick="tijiao('shareholder');">股东信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('supplierType');">供应商类型</a>
						</li>
						<li class="active">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('item');">产品类别</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('zizhi');">资质文件</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('contract');">销售合同</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" class="f18" data-toggle="tab" onclick="tijiao('chengxin');">诚信记录</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" class="f18" data-toggle="tab" onclick="tijiao('updateHistory');">历史修改记录</a>
						</li>
					</ul>
					<div class="content ">
						<div class="col-md-12 tab-v2 job-content">
							<div class="tab-v2">
								<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
									<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PRODUCT')}">
										<li id="li_id_1" class="active" onclick="loadTab('PRODUCT','tree_ul_id_1',1);">
											<a aria-expanded="true" href="#tab-1" data-toggle="tab">物资-生产型品目信息</a>
											<input type="hidden" id="tree_ul_id_1_name" value="mat_serve_page">
										</li>
									</c:if>
									<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SALES')}">
										<li id="li_id_2" class="" onclick="loadTab('SALES','tree_ul_id_2',2);">
											<a aria-expanded="false" href="#tab-2" data-toggle="tab">物资-销售型品目信息</a>
											<input type="hidden" id="tree_ul_id_2_name" value="item_sell_page">
										</li>
									</c:if>
									<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PROJECT')}">
										<li id="li_id_3" class="" onclick="loadTab('PROJECT','tree_ul_id_3',null);">
											<a aria-expanded="false" href="#tab-3" data-toggle="tab">工程品目信息</a>
											<input type="hidden" id="tree_ul_id_3_name" value="item_eng_page">
										</li>
									</c:if>
									<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SERVICE')}">
										<li id="li_id_4" class="" onclick="loadTab('SERVICE','tree_ul_id_4',null);">
											<a aria-expanded="false" href="#tab-4" data-toggle="tab">服务品目信息</a>
											<input type="hidden" id="tree_ul_id_4_name" value="item_serve_page">
										</li>
									</c:if>
								</ul>
							</div>
							<form id="form_id" action="" method="post">
								<input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
							</form>
							<div class="tab-content padding-top-20" id="tab_content_div_id">
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PRODUCT')}">
									<div class="tab-pane fade active in height-300" id="tab-1">
										<div class="lr0_tbauto">
											<ul id="tree_ul_id_1" class="ztree_supplier mt30"></ul>
										</div>
									</div>
								</c:if>
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SALES')}">
									<div class="tab-pane fade height-300" id="tab-2">
										<div class="lr0_tbauto ">
											<ul id="tree_ul_id_2" class="ztree_supplier mt30"></ul>
										</div>
									</div>
								</c:if>
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PROJECT')}">
									<div class="tab-pane fade height-200" id="tab-3">
										<div class="lr0_tbauto ">
											<ul id="tree_ul_id_3" class="ztree_supplier mt30"></ul>
										</div>
									</div>
								</c:if>
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SERVICE')}">
									<div class="tab-pane fade height-200" id="tab-4">
										<div class="lr0_tbauto ">
											<ul id="tree_ul_id_4" class="ztree_supplier mt30"></ul>
										</div>
									</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>

</html>