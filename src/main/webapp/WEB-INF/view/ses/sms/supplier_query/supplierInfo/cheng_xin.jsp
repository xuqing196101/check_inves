<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
	<!--<![endif]-->

	<head>
		<%@ include file="../../../../common.jsp"%>
		<%@ include file="/WEB-INF/view/ses/sms/supplier_query/supplierInfo/common.jsp"%>
		<script type="text/javascript" src="${ pageContext.request.contextPath }/js/ses/ems/expertQuery/common.js"></script>
		<style type="text/css">

		</style>
	</head>

	<body>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
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
				</ul>
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
							<a aria-expanded="fale" class="f18" href="#tab-1" data-toggle="tab" onclick="tijiao('essential');">基本信息</a>
						</li>
						<li class="">
							<a aria-expanded="true" class="f18" href="#tab-2" data-toggle="tab" onclick="tijiao('financial');">财务信息</a>
						</li>
						<li class="">
							<a aria-expanded="fale" class="f18" href="#tab-3" data-toggle="tab" onclick="tijiao('shareholder');">股东信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('supplierType');">供应商类型</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" class="f18" data-toggle="tab" onclick="tijiao('item');">产品类别</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('zizhi');">资质文件</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('contract');">销售合同</a>
						</li>
						<li class="active">
							<a aria-expanded="false" href="#tab-2" class="f18" data-toggle="tab" onclick="tijiao('chengxin');">诚信记录</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" class="f18" data-toggle="tab" onclick="tijiao('updateHistory');">历史修改记录</a>
						</li>
					</ul>
					<div class="tab-content padding-top-20">
						<div class="tab-pane fade active in">
							<form id="form_id" action="" method="post">
								<input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
								<input name="judge" value="${judge}" type="hidden">
							</form>
							<table class="table table-bordered table-condensed table-hover">
								<thead>
									<tr>
										<th class="info w50">序号</th>
										<th class="info">供应商名称</th>
										<th class="info">企业等级</th>
										<th class="info">分数</th>
										<th class="info">企业类型</th>
										<th class="info">企业性质</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${listSuppliers.list}" var="supplier" varStatus="vs">
										<tr>
											<td class="tc">${vs.index + 1}</td>
											<td class="tc">${supplier.supplierName}</td>
											<td class="tc">${supplier.level}</td>
											<td class="tc">${supplier.score}</td>
											<td class="tc">
												<c:forEach items="${supplier.listSupplierTypeRelates}" var="relate">
													${relate.supplierTypeName}
												</c:forEach>
											</td>
											<td class="tc">${supplier.businessType}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<div class="col-md-12 tc">
			    	<button class="btn btn-windows back" onclick="fanhui()">返回</button> 
			   	</div>
				</div>
			</div>
		</div>
	</body>

</html>