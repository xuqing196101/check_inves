<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<title>产品目录</title>
		<script type="text/javascript">
			//下一步
			function nextStep() {
				var action = "${pageContext.request.contextPath}/supplierAudit/applicationForm.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			//上一步
			function lastStep(lastUrl) {
				$("#form_id").attr("action", lastUrl);
				$("#form_id").submit();
			}

			//填写原因
			function reason(str, id, type) {
				var supplierId = $("#supplierId").val();
				var type = type + "_name";
				var auditType = $("#" + type + "").val();
				var auditContent = str + "目录信息";
				var index = layer.prompt({
						title: '请填写不通过的理由：',
						formType: 2,
						offset: '100px'
					},
					function(text) {
						$.ajax({
							url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
							type: "post",
							data: {
								"auditType": auditType,
								"auditFieldName": str,
								"suggest": text,
								"supplierId": supplierId,
								"auditField": id,
								"auditContent": auditContent
							},
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
						layer.close(index);
					});
			}
		</script>
		<!-- <script type="text/javascript">
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
					chkboxType:{"Y" : "ps", "N" : "ps"},//勾选checkbox对于父子节点的关联关系  
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
				if (!treeNode.isParent){
									reason(treeNode.name,treeNode.id,treeId);
								} else {
									layer.msg("请选择末级节点进行审核");
								}
							}

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
		</script> -->
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
				var setting = {
					async: {
						autoParam: ["id","code"],
						enable: true,
						url: "${pageContext.request.contextPath}/supplier/category_type.do?shenhe=true",
						otherParam: {
							"code": code,
							"supplierId": supplierId,
							"status": status
						},
						dataType: "json",
						type: "post",
					},
					/* check: {
						enable: true,
						chkStyle: "checkbox",
						chkboxType: {
							"Y": "ps",
							"N": "ps"
						}, //勾选checkbox对于父子节点的关联关系  
					}, */
					data: {
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "parentId",
						}
					},
					callback: {
						onClick: zTreeOnClick
						},
					view: {
						showLine: false
					}

				};
				$.fn.zTree.init($("#" + kind), setting, zNodes);
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
			
			function zTreeOnClick(event, treeId, treeNode) {
				if (!treeNode.isParent){
									reason(treeNode.name,treeNode.id,treeId);
						} else {
							layer.msg("请选择末级节点进行审核");
						}
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
				if(str == "aptitude") {
					action = "${pageContext.request.contextPath}/supplierAudit/aptitude.html";
				}
				if(str == "contract") {
					action = "${pageContext.request.contextPath}/supplierAudit/contract.html";
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
						</li>
						<li onclick="jump('financial')">
							<a aria-expanded="true" href="#tab-2">财务信息</a>
						</li>
						<li onclick="jump('shareholder')">
							<a aria-expanded="false" href="#tab-3">股东信息</a>
						</li>
						<c:if test="${fn:contains(supplierTypeNames, '生产')}">
							<li onclick="jump('materialProduction')">
								<a aria-expanded="false" href="#tab-4">生产信息</a>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '销售')}">
							<li onclick="jump('materialSales')">
								<a aria-expanded="false" href="#tab-4">销售信息</a>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '工程')}">
							<li onclick="jump('engineering')">
								<a aria-expanded="false" href="#tab-4">工程信息</a>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '服务')}">
							<li onclick="jump('serviceInformation')">
								<a aria-expanded="false" href="#tab-4" data-toggle="tab">服务信息</a>
							</li>
						</c:if>
						<li onclick="jump('items')" class="active">
							<a aria-expanded="false" href="#tab-4">品目信息</a>
						</li>
						<!-- <li onclick="jump('aptitude')">
							<a aria-expanded="false" href="#tab-4">资质文件</a>
						</li> -->
						<li onclick="jump('contract')">
							<a aria-expanded="false" href="#tab-4">品目合同</a>
						</li>
						<li onclick="jump('applicationForm')">
							<a aria-expanded="false" href="#tab-4">申请表</a>
						</li>
						<li onclick="jump('reasonsList')">
							<a aria-expanded="false" href="#tab-4">审核汇总</a>
						</li>
					</ul>
					<ul class="count_flow ul_list">
						<div class="tab-v2">
							<ul id="page_ul_id" class="nav nav-tabs bgwhite">
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
							<c:if test="${fn:contains(supplierTypeNames, '生产型')}">
								<!-- 物资生产型 -->
								<div class="tab-pane fade active in height-300" id="tab-1">
									<div class="lr0_tbauto w200" onclick="reason(this.id,'item_pro_page')">
										<ul id="tree_ul_id_1" class="ztree mt30"></ul>
									</div>
								</div>
							</c:if>
							<c:if test="${fn:contains(supplierTypeNames, '销售型')}">
								<!-- 物资销售型 -->
								<div class="tab-pane fade height-300" id="tab-2">
									<div class="lr0_tbauto w200">
										<ul id="tree_ul_id_2" class="ztree mt30"></ul>
									</div>
								</div>
							</c:if>
							<c:if test="${fn:contains(supplierTypeNames, '工程')}">
								<!-- 服务 -->
								<div class="tab-pane fade height-200" id="tab-3">
									<div class="lr0_tbauto w200">
										<ul id="tree_ul_id_3" class="ztree mt30"></ul>
									</div>
								</div>
							</c:if>
							<c:if test="${fn:contains(supplierTypeNames, '服务')}">
								<!-- 生产 -->
								<div class="tab-pane fade height-200" id="tab-4">
									<div class="lr0_tbauto w200">
										<ul id="tree_ul_id_4" class="ztree mt30"></ul>
									</div>
								</div>
							</c:if>
						</div>
					</ul>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 add_regist tc">
					<!-- <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a> -->
					<a class="btn" type="button" onclick="lastStep('${lastUrl}');">上一步</a>
					<a class="btn" type="button" onclick="nextStep();">下一步</a>
				</div>
			</div>
		</div>
	</body>

</html>