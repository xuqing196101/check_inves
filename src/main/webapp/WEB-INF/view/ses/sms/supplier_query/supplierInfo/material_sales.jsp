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
				if(str == "materialProduction") {
					action = "${pageContext.request.contextPath}/supplierQuery/materialProduction.html";
				}
				if(str == "materialSales") {
					action = "${pageContext.request.contextPath}/supplierQuery/materialSales.html";
				}
				if(str == "engineering") {
					action = "${pageContext.request.contextPath}/supplierQuery/engineering.html";
				}
				if(str == "service") {
					action = "${pageContext.request.contextPath}/supplierQuery/serviceInformation.html";
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
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}
		</script>
		<style type="text/css">

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
		<!-- 项目戳开始 -->
		<div class="container clear margin-top-30">
			<!-- <div class="container">
   <div class="col-md-12">
    <button class="btn btn-windows back" onclick="fanhui()">返回</button> 
    </div>
    </div> -->
			<!--详情开始-->
			<div class="container content pt0">
				<div class="tab-v2">
					<ul class="nav nav-tabs bgwhite">
						<li class="">
							<a aria-expanded="fale" href="#tab-1" class="f18" data-toggle="tab" onclick="tijiao('essential');">基本信息</a>
						</li>
						<li class="">
							<a aria-expanded="fale" href="#tab-2" class="f18" data-toggle="tab" onclick="tijiao('financial');">财务信息</a>
						</li>
						<li class="">
							<a aria-expanded="fale" href="#tab-3" class="f18" data-toggle="tab" onclick="tijiao('shareholder');">股东信息</a>
						</li>
						<c:if test="${fn:contains(suppliers.supplierType, '生产')}">
							<li class="">
								<a aria-expanded="fale" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('materialProduction');">物资-生产型专业信息</a>
							</li>
						</c:if>
						<c:if test="${fn:contains(suppliers.supplierType, '销售')}">
							<li class="active">
								<a aria-expanded="fale" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('materialSales');">物资-销售型专业信息</a>
							</li>
						</c:if>
						<c:if test="${fn:contains(suppliers.supplierType, '工程')}">
							<li class="">
								<a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('engineering');">工程-专业信息</a>
							</li>
						</c:if>
						<c:if test="${fn:contains(suppliers.supplierType, '服务')}">
							<li class="">
								<a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('service');">服务-专业信息</a>
							</li>
						</c:if>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('item');">品目信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('product');">产品信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('chengxin');">诚信记录</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('updateHistory');">历史修改记录</a>
						</li>
					</ul>
					<div class="tab-content padding-top-20">
						<div class="tab-pane fade active in">
							<form id="form_id" action="" method="post">
								<input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
							</form>
							<h2 class="count_flow jbxx">供应商资质证书</h2>
							<table class="table table-bordered table-condensed table-hover">
								<thead>
									<tr>
										<th class="info">资质证书名称</th>
										<th class="info">资质等级</th>
										<th class="info">发证机关</th>
										<th class="info">有效期(起止时间)</th>
										<th class="info">是否年检</th>
										<th class="info">附件上传</th>
									</tr>
								</thead>
								<c:forEach items="${supplierCertSell}" var="s">
									<tr>
										<td class="tc" id="${s.id}">${s.name }</td>
										<td class="tc">${s.levelCert}</td>
										<td class="tc">${s.licenceAuthorith }</td>
										<td class="tc">
											<fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd' /> 至
											<fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd' />
										</td>
										<td class="tc">
											<c:if test="${s.mot==0 }">否</c:if>
											<c:if test="${s.mot==1 }">是</c:if>
										</td>
										<td class="tc">${s.attach }</td>
									</tr>
								</c:forEach>
							</table>
						</div>
						<div class="tab-pane fade active in">
							<h2 class="count_flow jbxx">供应商组织结构和人员</h2>
							<table class="table table-bordered table-condensed table-hover">
								<tbody>
									<tr>
										<td class="bggrey">组织机构：</td>
										<td onmouseover="out('${supplierMatSells.orgName}')">${supplierMatSells.orgName}</td>
										<td class="bggrey">人员总数：</td>
										<td>${supplierMatSells.totalPerson }</td>
										<td class="bggrey">管理人员：</td>
										<td>${supplierMatSells.totalMange }</td>
									</tr>
									<tr>
										<td class="bggrey">技术人员：</td>
										<td onmouseover="out('${supplierMatSells.totalTech}')">${supplierMatSells.totalTech}</td>
										<td class="bggrey">工人(职员)：</td>
										<td colspan="3">${supplierMatSells.totalWorker }</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>

</html>