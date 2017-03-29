<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<title>产品目录</title>
		<script type="text/javascript">
			//下一步
			function nextStep() {
				var action = "${pageContext.request.contextPath}/supplierAudit/aptitude.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			//上一步
			function lastStep() {
				/*$("#form_id").attr("action", lastUrl);*/
				var action = "${pageContext.request.contextPath}/supplierAudit/supplierType.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			//填写原因
			/* function reason(str, id, type) {
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
			} */
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
					data: {"supplierId": supplierId,"code": code},
					async: false,
					dataType: "json",
					success: function(response){
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
					callback: {
						onClick: zTreeOnClick
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
			
			function zTreeOnClick(event, treeId, treeNode) {
				if (!treeNode.isParent){
									reason(treeNode.name,treeNode.id,treeId);
						} else {
							layer.msg("请选择末级节点进行审核");
						}
					}
		</script> -->
		<script type="text/javascript">
				
							
				//加载默认的页签
				$(function() {
				var supplierId = $("#supplierId").val();
					var PRODUCT = $("#a_id_1").text();
					var SALES = $("#a_id_2").text();
					var PROJECT = $("#a_id_3").text();
					var SERVICE = $("#a_id_4").text();
					//加载默认的页签
					if(PRODUCT == "物资-生产型品目信息") {
						// 加载已选品目列表
						loading = layer.load(1);
						var path = "${pageContext.request.contextPath}/supplierAudit/getCategories.html?supplierId=" + supplierId + "&supplierTypeRelateId=PRODUCT";
						$("#tbody_category").load(path);
						return;
					}
				 	if(SALES == "物资-销售型品目信息") {
					 	// 加载已选品目列表
						loading = layer.load(1);
						var path = "${pageContext.request.contextPath}/supplierAudit/getCategories.html?supplierId=" + supplierId + "&supplierTypeRelateId=SALES";
						$("#tbody_category").load(path);
						return;
					}
					if(PROJECT == "工程品目信息") {
						// 加载已选品目列表
						loading = layer.load(1);
						var path = "${pageContext.request.contextPath}/supplierAudit/getCategories.html?supplierId=" + supplierId + "&supplierTypeRelateId=PROJECT";
						$("#tbody_category").load(path);
						return;
					}
					if(SERVICE == "服务品目信息") {
						// 加载已选品目列表
						loading = layer.load(1);
						var path = "${pageContext.request.contextPath}/supplierAudit/getCategories.html?supplierId=" + supplierId + "&supplierTypeRelateId=SERVICE";
						$("#tbody_category").load(path);
						return;
					}
				});
		
			function loadTab(code, kind, status) {
					// 加载已选品目列表
					loading = layer.load(1);
					var supplierId = $("#supplierId").val();
					var path = "${pageContext.request.contextPath}/supplierAudit/getCategories.html?supplierId=" + supplierId + "&supplierTypeRelateId=" + code;
					$("#tbody_category").load(path);
			};
			
			function reason(firstNode, secondNode, thirdNode, fourthNode, id) {
				var auditContent;;
				var auditFieldName;
				var supplierId = $("#supplierId").val();
				if(fourthNode != null && fourthNode !=""){
					auditFieldName = fourthNode;
					auditContent = fourthNode + "目录信息";
				}else if(thirdNode !=null && thirdNode!=""){
					auditFieldName = thirdNode;
					auditContent = thirdNode + "目录信息";
				}else if(secondNode !=null && secondNode !=""){
					auditFieldName = secondNode;
					auditContent = secondNode + "目录信息";
				}else{
					auditFieldName = firstNode;
					auditContent = firstNode + "目录信息";
				}
				
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
								"auditType": "items_page",
								"auditFieldName": auditFieldName,
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
						$("#" + id + "_hidden").hide();
						$("#" + id + "_show").show();
						
						layer.close(index);
					});
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
				/*if(str == "materialProduction") {
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
				}*/
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
				if(str == "supplierType") {
					action = "${pageContext.request.contextPath}/supplierAudit/supplierType.html";
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
						<a href="#">支撑环境</a>
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
					<ul class="flow_step">
						<li onclick="jump('essential')">
							<a aria-expanded="false">详细信息</a>
							<i></i>
						</li>
						<li onclick="jump('financial')">
							<a aria-expanded="true">财务信息</a>
							<i></i>
						</li>
						<li onclick="jump('shareholder')">
							<a aria-expanded="false">股东信息</a>
							<i></i>
						</li>
						<%--<c:if test="${fn:contains(supplierTypeNames, '生产')}">
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
						--%>
						<li onclick = "jump('supplierType')">
           	  <a aria-expanded="false">供应商类型</a>
            	<i></i>
	          </li>
						<li onclick="jump('items')" class="active">
							<a aria-expanded="false">产品类别</a>
							<i></i>
						</li>
						<li onclick="jump('aptitude')">
							<a aria-expanded="false">资质文件维护</a>
							<i></i>
						</li>
						<li onclick="jump('contract')">
							<a aria-expanded="false">销售合同</a>
							<i></i>
						</li>
						<li onclick="jump('applicationForm')">
							<a aria-expanded="false">承诺书和申请表</a>
							<i></i>
						</li>
						<li onclick="jump('reasonsList')">
							<a aria-expanded="false">审核汇总</a>
						</li>
					</ul>
					<ul class="count_flow ul_list">
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
							<input name="supplierStatus" value="${supplierStatus}" type="hidden">
						</form>
						<div class="tab-content padding-top-20" id="tab_content_div_id">
							<%-- <c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PRODUCT')}">
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
							</c:if> --%>
							
							
							<div class="mt20" id="tbody_category"></div>
							<div id="pagediv" align="right" class="mb50"></div>
							
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