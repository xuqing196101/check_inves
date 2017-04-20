<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<title>销售合同</title>
		<script type="text/javascript">
			$(function() {
				var product = $("#a_id_1").text();
				var sales = $("#a_id_2").text();
				var project = $("#a_id_3").text();
				var service = $("#a_id_4").text();
				//加载默认的页签
				if(product == "物资-生产型合同信息") {
					loadPageOne('tab-1','supplierAudit/ajaxContract.html','PRODUCT');
					return;
				}
		  		if(sales == "物资-销售型合同信息") {
					loadPageTwo('tab-2','supplierAudit/ajaxContract.html','SALES');
					return;
				}
				if(project == "工程合同信息") {
					loadPageThree('tab-3','supplierAudit/ajaxContract.html','PROJECT');
					return;
				}
				if(service == "服务合同信息") {
					loadPageFour('tab-4','supplierAudit/ajaxContract.html','SERVICE');
					return;
				}
			});
			
		
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
		<script type="text/javascript">
			function reason(auditField, firstNode, secondNode, thirdNode, fourthNode) {
				var supplierId = $("#supplierId").val();
				var product = $("#pro_val").val();
				var sales = $("#sal_val").val();
				var project = $("#eng_val").val();
				var service = $("#ser_val").val();
				var str = "";
				if(product != null && str == ""){
					str = "生产";
				}
				if(sales != null && str == ""){
					str = "销售";
				}
				if(project != null && str == ""){
					str = "工程";
				}
				if(service != null && str == ""){
					str = "服务";
				}
				var auditFieldName;
				if(fourthNode != null && fourthNode !=""){
					auditFieldName = fourthNode;
					/* auditContent = fourthNode + "目录信息"; */
				}else if(thirdNode !=null && thirdNode!=""){
					auditFieldName = thirdNode;
					/* auditContent = thirdNode + "目录信息"; */
				}else if(secondNode !=null && secondNode !=""){
					auditFieldName = secondNode;
					/* auditContent = secondNode + "目录信息"; */
				}else{
					auditFieldName = firstNode;
					/* auditContent = firstNode + "目录信息"; */
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
								"auditType": "contract_page",
								"auditFieldName": auditFieldName,
								"auditContent": str + "-附件信息",
								"suggest": text,
								"supplierId": supplierId,
								"auditField": auditField
							},
							dataType: "json",
							success: function(result) {
								result = eval("(" + result + ")");
								if(result.msg == "fail") {
									layer.msg('该条信息已审核过！', {
										shift: 6, //动画类型
										offset: '100px'
									});
								}
							}
						});
						$("#" + auditField + "_hidden").hide();
						$("#" + auditField + "_show").show();
						layer.close(index);
					});
			}

			//下一步
			function nextStep() {
				var action = "${pageContext.request.contextPath}/supplierAudit/applicationForm.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			//上一步
			function lastStep() {
				var action = "${pageContext.request.contextPath}/supplierAudit/aptitude.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			function loadPageOne(id, url, supplierTypeId) {
				index = layer.load(1, {
					shade: [0.1, '#fff'] //0.1透明度的白色背景
				});
				var supplierId = $("#supplierId").val();
				var path = "${pageContext.request.contextPath}/" + url + "?supplierId=" + supplierId + "&supplierTypeId=" + supplierTypeId;
				$("#tab-4").html("");
				$("#tab-2").html("");
				$("#tab-3").html("");
				$("#" + id).load(path);
			}

			function loadPageTwo(id, url, supplierTypeId) {
				index = layer.load(1, {
					shade: [0.1, '#fff'] //0.1透明度的白色背景
				});
				var supplierId = $("#supplierId").val();
				var path = "${pageContext.request.contextPath}/" + url + "?supplierId=" + supplierId + "&supplierTypeId=" + supplierTypeId;
				$("#tab-1").html("");
				$("#tab-4").html("");
				$("#tab-3").html("");
				$("#" + id).load(path);
			}

			function loadPageThree(id, url, supplierTypeId) {
				index = layer.load(1, {
					shade: [0.1, '#fff'] //0.1透明度的白色背景
				});
				var supplierId = $("#supplierId").val();
				var path = "${pageContext.request.contextPath}/" + url + "?supplierId=" + supplierId + "&supplierTypeId=" + supplierTypeId;
				$("#tab-1").html("");
				$("#tab-2").html("");
				$("#tab-4").html("");
				$("#" + id).load(path);
			}

			function loadPageFour(id, url, supplierTypeId) {
				index = layer.load(1, {
					shade: [0.1, '#fff'] //0.1透明度的白色背景
				});
				var supplierId = $("#supplierId").val();
				var path = "${pageContext.request.contextPath}/" + url + "?supplierId=" + supplierId + "&supplierTypeId=" + supplierTypeId;
				$("#tab-1").html("");
				$("#tab-2").html("");
				$("#tab-3").html("");
				$("#" + id).load(path);
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
						<c:if test="${sign == 1}">
							<a href="#">供应商审核</a>
						</c:if>
						<c:if test="${sign == 2}">
							<a href="#">供应商复核</a>
						</c:if>
						<c:if test="${sign == 3}">
							<a href="#">供应商实地考察</a>
						</c:if>
					</li>
				</ul>
			</div>
		</div>
		<div class="container container_box">
			<div class="content ">
				<div class="col-md-12 tab-v2 job-content">
					<ul class="flow_step">
						<li onclick="jump('essential')">
							<a aria-expanded="false">基本信息</a>
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
						<li onclick="jump('supplierType')">
							<a aria-expanded="false">供应商类型</a>
							<i></i>
						</li>
						<!-- <li onclick="jump('items')">
							<a aria-expanded="false">产品类别</a>
							<i></i>
						</li> -->
						<li onclick="jump('aptitude')">
							<a aria-expanded="false">资质文件维护</a>
							<i></i>
						</li>
						<li onclick="jump('contract')" class="active">
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
					<ul class="count_flow ul_list hand">
						<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
							<c:set value="0" var="liCount" />
							<c:if test="${fn:contains(supplierTypeIds, 'PRODUCT')}">
								<c:set value="${liCount+1}" var="liCount" />
								<li id="li_id_1" class="active" onclick="loadPageOne('tab-1','supplierAudit/ajaxContract.html','PRODUCT')">
									<a aria-expanded="true" href="#tab-1" data-toggle="tab" id="a_id_1">物资-生产型合同信息</a>
								</li>
							</c:if>
							<c:if test="${fn:contains(supplierTypeIds, 'SALES')}">
								<li id="li_id_2" class='<c:if test="${liCount == 0}">active</c:if>' onclick="loadPageTwo('tab-2','supplierAudit/ajaxContract.html','SALES')">
									<a aria-expanded="false" href="#tab-2" data-toggle="tab" id="a_id_2">物资-销售型合同信息</a>
								</li>
								<c:set value="${liCount+1}" var="liCount" />
							</c:if>
							<%-- <c:if test="${fn:contains(supplierTypeIds, 'PROJECT')}">
								<li id="li_id_3" class="<c:if test=" ${liCount==0 } ">active</c:if>" onclick="loadPageThree('tab-3','supplierAudit/ajaxContract.html','PROJECT')">
									<a aria-expanded="false" href="#tab-3" data-toggle="tab" id="a_id_3">工程合同信息</a>
								</li>
								<c:set value="${liCount+1}" var="liCount" />
							</c:if> --%>
							<c:if test="${fn:contains(supplierTypeIds, 'SERVICE')}">
								<li id="li_id_4" class="<c:if test=" ${liCount==0 } ">active</c:if>" onclick="loadPageFour('tab-4','supplierAudit/ajaxContract.html','SERVICE')">
									<a aria-expanded="false" href="#tab-4" data-toggle="tab" id="a_id_4">服务合同信息</a>
								</li>
								<c:set value="${liCount+1}" var="liCount" />
							</c:if>
						</ul>

						<div class="count_flow">
							<div class="tab-content padding-top-20" id="tab_content_div_id">
								<c:if test="${fn:contains(supplierTypeIds, 'PRODUCT')}">
									<!-- 物资生产型 -->
									<div class="tab-pane active in fade active in height-300" id="tab-1">
									</div>
								</c:if>
								<c:if test="${fn:contains(supplierTypeIds, 'SALES')}">
									<!-- 物资销售型 -->
									<div class="tab-pane active in fade height-300 " id="tab-2">
									</div>
								</c:if>
								<%-- <c:if test="${fn:contains(supplierTypeIds, 'PROJECT')}">
									<!-- 工程 -->
									<div class="tab-pane active in fade height-200 " id="tab-3">
									</div>
								</c:if> --%>
								<c:if test="${fn:contains(supplierTypeIds, 'SERVICE')}">
									<!-- 服务 -->
									<div class="tab-pane active in fade height-200 " id="tab-4">
									</div>
								</c:if>
							</div>
						</div>
					</ul>
				</div>
			</div>
			<div class="col-md-12 add_regist tc">
				<a class="btn" type="button" onclick="lastStep();">上一步</a>
				<a class="btn" type="button" onclick="nextStep();">下一步</a>
			</div>
		</div>
		<form id="form_id" action="" method="post">
			<input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
			<input name="supplierStatus" value="${supplierStatus}" type="hidden">
			<input type="hidden" name="sign" value="${sign}">
		</form>
	</body>

</html>