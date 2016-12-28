<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<title>资质文件</title>
		<script type="text/javascript">
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
						<li onclick="jump('items')">
							<a aria-expanded="false" href="#tab-4">品目信息</a>
							<i></i>
						</li>
						<li onclick="jump('aptitude')" class="active">
							<a aria-expanded="false" href="#tab-4">资质文件</a>
							<i></i>
						</li>
						<li onclick="jump('contract')">
							<a aria-expanded="false" href="#tab-4">品目合同</a>
						</li>
						<li onclick="jump('applicationForm')">
							<a aria-expanded="false" href="#tab-4">申请表</a>
							<i></i>
						</li>
						<li onclick="jump('reasonsList')">
							<a aria-expanded="false" href="#tab-4">审核汇总</a>
						</li>
					</ul>

					<ul class="count_flow ul_list">
						<table class="table table-bordered  table-condensed table-hover">
							<tr>
								<td class="info tc"> 品目名称</td>
								<td class="info tc">需要上传的文件</td>
							</tr>
							<c:forEach items="${cateList }" var="obj">
								<tr>
									<td class="info">${obj.categoryName }</td>
									<td class="info">
										<c:forEach items="${obj.list }" var="qua" varStatus="vs">
											<div class="col-md-12 col-sm-12 col-xs-12 p0" id="breach_li_id">
												${qua.name }
												<u:show showId="pShow${len-(vs.index+1)}" groups="${sbShow}" businessId="${qua.id}" sysKey="1" typeId="1" />
											</div>
										</c:forEach>
									</td>
								</tr>
							</c:forEach>
						</table>
					</ul>
				</div>
			</div>
		</div>
	</body>

</html>