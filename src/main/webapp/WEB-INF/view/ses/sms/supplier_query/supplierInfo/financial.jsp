<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
	<!--<![endif]-->

	<head>
		<%@ include file="../../../../common.jsp"%>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<%@ include file="/WEB-INF/view/ses/sms/supplier_query/supplierInfo/common.jsp"%>
		<script type="text/javascript" src="${ pageContext.request.contextPath }/js/ses/ems/expertQuery/common.js"/>
		<script type="text/javascript">
			function download(id, key) {
				var key = 1;
				var form = $("<form>");
				form.attr('style', 'display:none');
				form.attr('method', 'post');
				form.attr('action', globalPath + '/file/download.html?id=' + id + '&key=' + key);
				$('body').append(form);
				form.submit();
			}
		</script>
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
		<div class="container container_box">
			<div class="content height-350">
				<div class="col-md-12 tab-v2 job-content">
					<ul class="nav nav-tabs bgwhite">
						<li class="">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab" class="f18" onclick="tijiao('essential');">基本信息</a>
						</li>
						<li class="active">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('financial');">财务信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('shareholder');">股东信息</a>
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
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('contract');">销售合同</a>
						</li>
						<!-- <li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('chengxin');">诚信记录</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('updateHistory');">历史修改记录</a>
						</li> -->
					</ul>
					<form id="form_id" action="" method="post">
						<input name="supplierId" id="id" value="${suppliers.id }" type="hidden">
						<input name="judge" value="${judge}" type="hidden">
						<input name="sign" value="${sign}" type="hidden">
					</form>
					<form id="form_back" action="" method="post">
						<input name="judge" value="${judge}" type="hidden">
						<c:if test="${sign!=1 and sign!=2 }">
							<input name="address" id="address" value="${suppliers.address}" type="hidden">
						</c:if>
						<input name="sign" value="${sign}" type="hidden">
					</form>
					<c:forEach items="${financial}" var="f" varStatus="vs">
						<h2 class="count_flow"><i>${vs.index + 1}</i>${f.year }年财务（单位：万元）</h2>
						<ul class="ul_list count_flow">
							<table class="table table-bordered  table-condensed table-hover">
								<thead>
									<tr>
										<!-- <th class="info">序号</th> -->
										<th class="info w50">年份</th>
										<th class="info">会计事务所名称</th>
										<th class="info">事务所联系电话</th>
										<th class="info">审计人姓名（2人）</th>
										<!-- <th class="info">指标</th> -->
										<th class="info">资产总额</th>
										<th class="info">负债总额</th>
										<th class="info">净资产总额</th>
										<th class="info">营业收入</th>
									</tr>
								</thead>
								<tr>
									<%-- <td class="tc">${vs.index + 1}</td> --%>
									<td class="tc w50" id="${f.id }">${f.year } </td>
									<td class="tc">${f.name }</td>
									<td class="tc">${f.telephone }</td>
									<td class="tc">${f.auditors }</td>
									<%-- <td class="tc">${f.quota }</td> --%>
									<td class="tc">${f.totalAssets }</td>
									<td class="tc">${f.totalLiabilities }</td>
									<td class="tc">${f.totalNetAssets}</td>
									<td class="tc">${f.taking}</td>
								</tr>
							</table>

							<table class="table table-bordered  table-condensed table-hover">
								<thead>
									<tr>
										<th class="w50 info">年份</th>
										<th class="info">财务利润表</th>
										<th class="info">审计报告的审计意见</th>
										<th class="info">资产负债表</th>
										<th class="info">现金流量表</th>
										<th class="info">所有者权益变动表</th>
									</tr>
								</thead>
								<tbody id="finance_attach_list_tbody_id">
									<tr class="tc">
										<td class="tc w50">${f.year}</td>
										<td class="tc">
											<u:show showId="fina_${vs.index}_pro" delete="false" businessId="${f.id}" typeId="${supplierDictionaryData.supplierProfit}" sysKey="${sysKey}" />
										</td>
										<td class="tc">
											<u:show showId="fina_${vs.index}_audit" delete="false" businessId="${f.id}" typeId="${supplierDictionaryData.supplierAuditOpinion}" sysKey="${sysKey}" />
										</td>
										<td class="tc">
											<u:show showId="fina_${vs.index}_lia" delete="false" businessId="${f.id}" typeId="${supplierDictionaryData.supplierLiabilities}" sysKey="${sysKey}" />
										</td>
										<td class="tc">
											<u:show showId="fina_${vs.index}_cash" delete="false" businessId="${f.id}" typeId="${supplierDictionaryData.supplierCashFlow}" sysKey="${sysKey}" />
										</td>
										<td class="tc">
											<u:show showId="fina_${vs.index}_change" delete="false" businessId="${f.id}" typeId="${supplierDictionaryData.supplierOwnerChange}" sysKey="${sysKey}" />
										</td>
									</tr>
								</tbody>
							</table>
						</ul>
					</c:forEach>
				</div>
			</div>
			<div class="col-md-12 tc">
				<button class="btn btn-windows back" onclick="fanhui()">返回</button>
			</div>
		</div>
	</body>

</html>