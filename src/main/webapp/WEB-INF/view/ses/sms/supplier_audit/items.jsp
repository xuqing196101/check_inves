<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>

		<script type="text/javascript">
			function tijiao(status) {
				$("#status").val(status);
				form1.submit();
			}

			function nextStep() {
				var action = "${pageContext.request.contextPath}/supplierAudit/product.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			//填写原因
			function reason(id, auditType) {
				var offset = "";
				if(window.event) {
					e = event || window.event;
					var x = "";
					var y = "";
					x = e.clientX + 20 + "px";
					y = e.clientY + 20 + "px";
					offset = [y, x];
				} else {
					offset = "200px";
				}
				var supplierId = $("#supplierId").val();
				var id1 = id + "1";
				var id2 = id + "2";
				var auditFieldName = $("#" + id2 + "").text().replace("：", ""); //审批的字段名字
				var index = layer.prompt({
						title: '请填写不通过的理由：',
						formType: 2,
						offset: offset
					},
					function(text) {
						$.ajax({
							url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
							type: "post",
							data: "auditType=" + auditType + "&auditFieldName=" + auditFieldName + "&suggest=" + text + "&supplierId=" + supplierId + "&auditField=品目树" + "&auditContent=品目树",
							dataType: "json",
							success: function(result) {
								result = eval("(" + result + ")");
								if(result.msg == "fail") {
									layer.msg('该条信息已审核过！', {
										shift: 6, //动画类型
										offset: '300px'
									});
								}
							}
						});
						$("#" + id1).show();
						layer.close(index);
					});
			}
		</script>
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
				// ztree
				$("#tab_content_div_id").find(".tab-pane").each(function(index) {
					var kind = "";
					var id = $(this).attr("id");
					if(id == "tab-1") {
						id = "PRODUCT";
						kind = "tree_ul_id_1";
					}
					if(id == "tab-2") {
						id = "SALES";
						kind = "tree_ul_id_2";
					}
					if(id == "tab-3") {
						id = "PROJECT";
						kind = "tree_ul_id_3";
					}
					if(id == "tab-4") {
						id = "SERVICE";
						kind = "tree_ul_id_4";
					}
					loadZtree(id, kind);
				});

				if("${currSupplier.status}" == 7) {
					showReason();
				}

			});

			/* 	alert(kind); */
			/* 	var id = "";
				if (kind == "1") id = "tree_ul_id_1";
				if (kind == "2") id = "tree_ul_id_2";
				if (kind == "3") id = "tree_ul_id_3";
				if (kind == "4") id = "tree_ul_id_4"; */

			var setting = {
				/* 			async : {
								autoParam: ["id"],
								enable : true,
								url : "${pageContext.request.contextPath}/supplier/category_type.html",
								otherParam : {
									"code":code,
									"supplierId": "${currSupplier.id}",
									 kind : kind  
								},
								dataType : "json",
								type : "post",
							}, */
				check: {
					enable: true,
					chkStyle: "checkbox",
					chkboxType: {
						"Y": "ps",
						"N": "ps"
					}, //勾选checkbox对于父子节点的关联关系  
				},
				data: {
					simpleData: {
						enable: true,
						idKey: "id",
						pIdKey: "parentId",
					}
				},
				callback: {
					onClick: zTreeOnClick
				}
			};

			function loadZtree(code, kind) {
				var supplierId = $("#supplierId").val();
				$.ajax({
					type: "GET",
					async: false,
					url: "${pageContext.request.contextPath}/supplier/category_type.html?code=" + code + "&supplierId=" + supplierId,
					dataType: "json",
					success: function(zNodes) {
						zTreeObj = $.fn.zTree.init($("#" + kind), setting, zNodes);
						zTreeObj.expandAll(true);
					}
				});
			}

			function zTreeOnClick(event, treeId, treeNode) {
				var categoryId = treeNode.id;

				layer.open({
					type: 2,
					title: '品目文件上传',
					// skin : 'layui-layer-rim', //加上边框
					area: ['300px', '280px'], //宽高
					offset: '100px',
					scrollbar: false,
					content: '${pageContext.request.contextPath}/supplier_item/getCategory.html?categoryId=' + categoryId, //url
					closeBtn: 1, //不显示关闭按钮
				});

			};
			/* 	function onCheck(e, treeId, treeNode) {
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
				} */

			/**for (var i = 0; i < nodes.length; i++) {
				nodes[i].checkedOld = nodes[i].checked;
			}*/
			/* 	} */



			function saveItems(flag) {
				getCategoryId();
				$("#flag").val(flag);
				$("#items_info_form_id").submit();
			}

			function next(flag) {
				getCategoryId();
				$("#flag").val(flag);
				$("#items_info_form_id").submit();
			}

			function prev(flag) {
				getCategoryId();
				$("#flag").val(flag);
				$("#items_info_form_id").submit();
			}

			function getCategoryId() {
				var ids = [];
				for(var i = 1; i < 5; i++) {
					var id = "tree_ul_id_" + i;
					var tree = $.fn.zTree.getZTreeObj(id);
					if(tree != null) {
						nodes = tree.getCheckedNodes(true);
						for(var j = 0; j < nodes.length; j++) {
							//	if (!nodes[j].isParent) {
							//alert(nodes[j].id);
							ids.push(nodes[j].id);
							//}
						}
					}
				}
				$("#categoryId").val(ids);
				//return ids;
			}
		</script>
		<script type="text/javascript">
			function jump(str) {
				var action;
				if(str == "essential") {
					action = "${pageContext.request.contextPath}/supplierAudit/essential.html";
				}
				if(str == "financial") {
					action = "${pageContext.request.contextPath}/supplierAudit/financial.html";
				}
				if(str == "shareholder") {
					action = "${pageContext.request.contextPath}/supplierAudit/shareholder.html";
				}
				if(str == "materialProduction") {
					action = "${pageContext.request.contextPath}/supplierAudit/materialProduction.html";
				}
				if(str == "materialSales") {
					action = "${pageContext.request.contextPath}/supplierAudit/materialSales.html";
				}
				if(str == "engineering") {
					action = "${pageContext.request.contextPath}/supplierAudit/engineering.html";
				}
				if(str == "serviceInformation") {
					action = "${pageContext.request.contextPath}/supplierAudit/serviceInformation.html";
				}
				if(str == "items") {
					action = "${pageContext.request.contextPath}/supplierAudit/items.html";
				}
				if(str == "product") {
					action = "${pageContext.request.contextPath}/supplierAudit/product.html";
				}
				if(str == "applicationForm") {
					action = "${pageContext.request.contextPath}/supplierAudit/applicationForm.html";
				}
				if(str == "reasonsList") {
					action = "${pageContext.request.contextPath}/supplierAudit/reasonsList.html";
				}
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="#"> 首页</a>
					</li>
					<li>
						<a href="#">供应商管理</a>
					</li>
					<li>
						<a href="#">供应商审核</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="container container_box">
			<div class="content ">
				<div class="col-md-12 tab-v2 job-content">
					<ul class="nav nav-tabs bgdd">
						<li onclick="jump('essential')">
							<a aria-expanded="false" href="#tab-1">详细信息</a>
							<i></i>
						</li>
						<li onclick="jump('financial')">
							<a aria-expanded="true" href="#tab-2">财务信息</a>
							<i></i>
						</li>
						<li onclick="jump('shareholder')">
							<a aria-expanded="false" href="#tab-3">股东信息</a>
							<i></i>
						</li>
						<c:if test="${fn:contains(supplierTypeNames, '生产')}">
							<li onclick="jump('materialProduction')">
								<a aria-expanded="false" href="#tab-4">生产信息</a>
								<i></i>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '销售')}">
							<li onclick="jump('materialSales')">
								<a aria-expanded="false" href="#tab-4">销售信息</a>
								<i></i>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '工程')}">
							<li onclick="jump('engineering')">
								<a aria-expanded="false" href="#tab-4">工程信息</a>
								<i></i>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '服务')}">
							<li onclick="jump('serviceInformation')">
								<a aria-expanded="false" href="#tab-4" data-toggle="tab">服务信息</a>
								<i></i>
							</li>
						</c:if>
						<li onclick="jump('items')" class="active">
							<a aria-expanded="false" href="#tab-4">品目信息</a>
							<i></i>
						</li>
						<!-- <li onclick="jump('product')">
							<a aria-expanded="false" href="#tab-4">产品信息</a>
							<i></i>
						</li> -->
						<li onclick="jump('applicationForm')">
							<a aria-expanded="false" href="#tab-4">申请表</a>
							<i></i>
						</li>
						<li onclick="jump('reasonsList')">
							<a aria-expanded="false" href="#tab-4">审核汇总</a>
						</li>
					</ul>
					<ul class="count_flow ul_list">
						<div class="tab-v2">
							<ul id="page_ul_id" class="nav nav-tabs bgwhite">
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PRODUCT')}">
									<li id="li_id_1" class="active">
										<a aria-expanded="true" href="#tab-1" data-toggle="tab" id="production2">物资-生产型品目信息</a>
									</li>
								</c:if>
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SALES')}">
									<li id="li_id_2" class="">
										<a aria-expanded="false" href="#tab-2" data-toggle="tab" id="sale2">物资-销售型品目信息</a>
									</li>
								</c:if>
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PROJECT')}">
									<li id="li_id_3" class="">
										<a aria-expanded="false" href="#tab-3" data-toggle="tab" id="engineering2">工程品目信息</a>
									</li>
								</c:if>
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SERVICE')}">
									<li id="li_id_4" class="">
										<a aria-expanded="false" href="#tab-4" data-toggle="tab" id="service2">服务品目信息</a>
									</li>
								</c:if>
							</ul>
						</div>
						<form id="form_id" action="" method="post">
							<input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
						</form>
						<div class="tab-content padding-top-20" id="tab_content_div_id">
							<c:if test="${fn:contains(supplierTypeNames, '生产型')}">
								<!-- 物资生产型 -->
								<div class="tab-pane fade active in height-300" id="tab-1">
									<div class="lr0_tbauto w200" onclick="reason(this.id,'item_pro_page')" id="production">
										<ul id="tree_ul_id_1" class="ztree mt30"></ul>
										<div id="production1" class="b f18 fl ml10 hand red" style="display: none">×</div>
									</div>
								</div>
							</c:if>
							<c:if test="${fn:contains(supplierTypeNames, '销售型')}">
								<!-- 物资销售型 -->
								<div class="tab-pane fade height-300" id="tab-2">
									<div class="lr0_tbauto w200" onclick="reason(this.id,'item_sell_page')" id="sale">
										<ul id="tree_ul_id_2" class="ztree mt30"></ul>
										<div id="sale1" class="b f18 fl ml10 hand red" style="display: none">×</div>
									</div>
								</div>
							</c:if>
							<c:if test="${fn:contains(supplierTypeNames, '工程')}">
								<!-- 服务 -->
								<div class="tab-pane fade height-200" id="tab-3">
									<div class="lr0_tbauto w200" onclick="reason(this.id,'item_eng_page')" id="engineering">
										<ul id="tree_ul_id_3" class="ztree mt30"></ul>
										<div id="engineering1" class="b f18 fl ml10 hand red" style="display: none">×</div>
									</div>
								</div>
							</c:if>
							<c:if test="${fn:contains(supplierTypeNames, '服务')}">
								<!-- 生产 -->
								<div class="tab-pane fade height-200" id="tab-4">
									<div class="lr0_tbauto w200" onclick="reason(this.id,'item_serve_page')" id="service">
										<ul id="tree_ul_id_4" class="ztree mt30"></ul>
										<div id="service1" class="b f18 fl ml10 hand red" style="display: none">×</div>
									</div>
								</div>
							</c:if>
						</div>
					</ul>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 add_regist tc">
					<!-- <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a> -->
					<input class="btn btn-windows" type="button" onclick="nextStep();" value="下一步">
				</div>
			</div>
		</div>
	</body>

</html>