<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<title>销售合同</title>
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
			
			/* function fanhui() {
				if('${judge}' == 2) {
					window.location.href = "${pageContext.request.contextPath}/supplierQuery/selectByCategory.html";
				} else {
					window.location.href = "${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?address=" + encodeURI(encodeURI('${suppliers.address}')) + "&judge=${judge}";
				}
			} */
			
			function fanhui() {
				if('${judge}' == 2) {
					window.location.href = "${pageContext.request.contextPath}/supplierQuery/selectByCategory.html";
				} else {
					var action = "${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html";
					$("#form_back").attr("action", action);
					$("#form_back").submit();
				};
			};
		</script>
		<script type="text/javascript">
			$(function() {
				var product = $("#a_id_1").text();
				var sales = $("#a_id_2").text();
				var project = $("#a_id_3").text();
				var service = $("#a_id_4").text();
				//加载默认的页签
				if(product == "物资-生产型合同信息") {
					loadPageOne('tab-1','supplierQuery/ajaxContract.html','PRODUCT');
					return;
				}
		  		if(sales == "物资-销售型合同信息") {
					loadPageTwo('tab-2','supplierQuery/ajaxContract.html','SALES');
					return;
				}
				if(project == "工程合同信息") {
					loadPageThree('tab-3','supplierQuery/ajaxContract.html','PROJECT');
					return;
				}
				if(service == "服务合同信息") {
					loadPageFour('tab-4','supplierQuery/ajaxContract.html','SERVICE');
					return;
				}
			});

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
						<a href="javascript:void(0);"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">支撑环境</a>
					</li>
					<li>
						<a href="javascript:void(0);">供应商管理</a>
					</li>
					<li>
						<a href="javascript:void(0);">供应商查看</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="container container_box">
			<div class="content">
				<div class="col-md-12 tab-v2 job-content">
					<ul class="nav nav-tabs bgwhite">
						<li class="">
							<a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18" onclick="tijiao('essential');">基本信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('financial');">财务信息</a>
						</li>
						<li class="">
							<a aria-expanded="fale" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('shareholder');">股东信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('supplierType');">供应商类型</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('item');">产品类别</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('zizhi');">资质文件</a>
						</li>
						<li class="active">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('contract');">销售合同</a>
						</li>
						<!-- <li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('chengxin');">诚信记录</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('updateHistory');">历史修改记录</a>
						</li> -->
					</ul>
					<ul class="count_flow ul_list hand">
						<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
							<c:set value="0" var="liCount" />
							<c:if test="${fn:contains(supplierTypeIds, 'PRODUCT')}">
								<c:set value="${liCount+1}" var="liCount" />
								<li id="li_id_1" class="active" onclick="loadPageOne('tab-1','supplierQuery/ajaxContract.html','PRODUCT')">
									<a aria-expanded="true" href="#tab-1" data-toggle="tab" id="a_id_1">物资-生产型合同信息</a>
								</li>
							</c:if>
							<c:if test="${fn:contains(supplierTypeIds, 'SALES')}">
								<li id="li_id_2" class='<c:if test="${liCount == 0}">active</c:if>' onclick="loadPageTwo('tab-2','supplierQuery/ajaxContract.html','SALES')">
									<a aria-expanded="false" href="#tab-2" data-toggle="tab" id="a_id_2">物资-销售型合同信息</a>
								</li>
								<c:set value="${liCount+1}" var="liCount" />
							</c:if>
							<%-- <c:if test="${fn:contains(supplierTypeIds, 'PROJECT')}">
								<li id="li_id_3" class="<c:if test=" ${liCount==0 } ">active</c:if>" onclick="loadPageThree('tab-3','supplierQuery/ajaxContract.html','PROJECT')">
									<a aria-expanded="false" href="#tab-3" data-toggle="tab" id="a_id_3">工程合同信息</a>
								</li>
								<c:set value="${liCount+1}" var="liCount" />
							</c:if> --%>
							<c:if test="${fn:contains(supplierTypeIds, 'SERVICE')}">
								<li id="li_id_4" class="<c:if test=" ${liCount==0 } ">active</c:if>" onclick="loadPageFour('tab-4','supplierQuery/ajaxContract.html','SERVICE')">
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
					<div class="col-md-12 tc">
			    	<button class="btn btn-windows back" onclick="fanhui()">返回</button> 
			   	</div>
				</div>
			</div>
		</div>
		<form id="form_id" action="" method="post">
			<input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
			<input name="judge" value="${judge}" type="hidden">
			<input name="sign" value="${sign}" type="hidden">
		</form>
		
		<form id="form_back" action="" method="post">
			<input name="judge" value="${judge}" type="hidden">
			<c:if test="${sign!=1 and sign!=2 }">
				<input name="address" value="${suppliers.address}" type="hidden">
			</c:if>
			<input name="sign" value="${sign}" type="hidden">
		</form>
	</body>

</html>