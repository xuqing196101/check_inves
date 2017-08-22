<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
	<!--<![endif]-->

	<head>
		<%@ include file="../../../../common.jsp"%>
		<%@ include file="/WEB-INF/view/ses/sms/supplier_query/supplierInfo/common.jsp"%>
		<script type="text/javascript" src="${ pageContext.request.contextPath }/js/ses/ems/expertQuery/common.js"></script>
		<script type="text/javascript">
			/*var zTreeObj;
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
			} */
		</script>
		<script type="text/javascript">
			//加载默认的页签
            var supplier_status;
				$(function() {
					var supplierId = $("#supplierId").val();
					var PRODUCT = $("#a_id_1").text();
					var SALES = $("#a_id_2").text();
					var PROJECT = $("#a_id_3").text();
					var SERVICE = $("#a_id_4").text();
                    supplier_status = $("input[name='supplier_status']").val();
					//加载默认的页签
					if(PRODUCT == "物资-生产型产品类别信息") {
						// 加载已选品目列表
						loading = layer.load(1);
						var path = "${pageContext.request.contextPath}/supplierQuery/getCategories.html?supplierId=" + supplierId + "&supplierTypeRelateId=PRODUCT&status="+supplier_status;
						$("#tbody_category").load(path);
						return;
					}
				 	if(SALES == "物资-销售型产品类别信息") {
					 	// 加载已选品目列表
						loading = layer.load(1);
						var path = "${pageContext.request.contextPath}/supplierQuery/getCategories.html?supplierId=" + supplierId + "&supplierTypeRelateId=SALES&status="+supplier_status;
						$("#tbody_category").load(path);
						return;
					}
					if(PROJECT == "工程产品类别信息") {
						// 加载已选品目列表
					  loading = layer.load(1);
						var path = "${pageContext.request.contextPath}/supplierQuery/getCategories.html?supplierId=" + supplierId + "&supplierTypeRelateId=PROJECT&status="+supplier_status;
						$("#tbody_category").load(path);
						return;
					}
					if(SERVICE == "服务产品类别信息") {
						// 加载已选品目列表
						loading = layer.load(1);
						var path = "${pageContext.request.contextPath}/supplierQuery/getCategories.html?supplierId=" + supplierId + "&supplierTypeRelateId=SERVICE&status="+supplier_status;
						$("#tbody_category").load(path);
						return;
					}
				});
		
			function loadTab(code, kind, status) {
					// 加载已选品目列表
				  loading = layer.load(1);
					var supplierId = $("#supplierId").val();
					var path = "${pageContext.request.contextPath}/supplierQuery/getCategories.html?supplierId=" + supplierId + "&supplierTypeRelateId=" + code + "&status="+supplier_status;
					$("#tbody_category").load(path);
			};
		
		</script>
		<!-- <style type="text/css">
			.line {
				border: 4px solid #fff0;
			}
		</style> -->
	</head>

	<body>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<c:choose>
						<c:when test="${person == 1 }">
							<li>
								<a href="javascript:void(0);">个人中心</a>
							</li>
							<li>
								<a href="javascript:void(0);">个人信息</a>
							</li>
						</c:when>
						<c:otherwise>
							<li>
								<a href="javascript:void(0);">支撑环境</a>
							</li>
							<li>
								<a href="javascript:void(0);">供应商管理</a>
							</li>
							<li>
								<a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?sign=1')">供应商列表</a>
							</li>
							<li>
								<a href="javascript:void(0);">供应商查看</a>
							</li>
						</c:otherwise>
					</c:choose>
				</ul>
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
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('table');">承诺书/申请表</a>
						</li>
						<li class="">
              <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('audit');">审核信息</a>
            </li>
					</ul>
					<div class="content ">
						<div class="col-md-12 tab-v2 job-content">
							<div class="tab-v2">
								<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
									<c:set value="0" var="liCount"/>
									<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PRODUCT')}">
										<c:set value="${liCount+1}" var="liCount"/>
										<li id="li_id_1" class="active" onclick="loadTab('PRODUCT','tree_ul_id_1',1);">
											<a aria-expanded="true" href="#tab-1" data-toggle="tab" id="a_id_1">物资-生产型产品类别信息</a>
											<input type="hidden" id="tree_ul_id_1_name" value="mat_serve_page">
										</li>
									</c:if>
									<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SALES')}">
										<li id="li_id_2" class='<c:if test="${liCount == 0}">active</c:if>' onclick="loadTab('SALES','tree_ul_id_2',2);">
											<a aria-expanded="false" href="#tab-2" data-toggle="tab" id="a_id_2">物资-销售型产品类别信息</a>
											<input type="hidden" id="tree_ul_id_2_name" value="item_sell_page">
										</li>
										<c:set value="${liCount+1}" var="liCount"/>
									</c:if>
									<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PROJECT')}">
										<li id="li_id_3" class='<c:if test="${liCount == 0}">active</c:if>' onclick="loadTab('PROJECT','tree_ul_id_3',null);">
											<a aria-expanded="false" href="#tab-3" data-toggle="tab" id="a_id_3">工程产品类别信息</a>
											<input type="hidden" id="tree_ul_id_3_name" value="item_eng_page">
										</li>
										<c:set value="${liCount+1}" var="liCount"/>
									</c:if>
									<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SERVICE')}">
										<li id="li_id_4" class='<c:if test="${liCount == 0}">active</c:if>' onclick="loadTab('SERVICE','tree_ul_id_4',null);">
											<a aria-expanded="false" href="#tab-4" data-toggle="tab" id="a_id_4">服务产品类别信息</a>
											<input type="hidden" id="tree_ul_id_4_name" value="item_serve_page">
										</li>
										<c:set value="${liCount+1}" var="liCount"/>
									</c:if>
								</ul>
							</div>
							
							<form id="form_id" action="" method="post">
								<input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
								<input name="judge" value="${judge}" type="hidden">
								<input name="sign" value="${sign}" type="hidden">
								<input name="person" value="${person}" type="hidden">
							</form>
							<form id="form_back" action="" method="post">
								<input name="judge" value="${judge}" type="hidden">
								<c:if test="${sign!=1 and sign!=2 }">
									<input name="address" id="address" value="${suppliers.address}" type="hidden">
								</c:if>
								<input name="sign" value="${sign}" type="hidden">
							</form>
							
							<div class="mt20" id="tbody_category"></div>
							<div id="pagediv" align="right" class="mb50"></div>
							<%-- <div class="tab-content padding-top-20" id="tab_content_div_id">
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
							</div> --%>
							<input type="hidden" name="supplier_status" value="${supplier_status}">
							<div class="col-md-12 tc">
								<c:choose>
									<c:when test="${person == 1 }">
										<button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
									</c:when>
									<c:otherwise>
										<button class="btn btn-windows back" onclick="fanhui()">返回</button>
									</c:otherwise>
								</c:choose>
			   			</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>

</html>