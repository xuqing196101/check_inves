<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
	<!--<![endif]-->

	<head>
		<%@ include file="../../../../common.jsp"%>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<%@ include file="/WEB-INF/view/ses/sms/supplier_query/supplierInfo/common.jsp"%>
		<script type="text/javascript" src="${ pageContext.request.contextPath }/js/ses/ems/expertQuery/common.js"></script>
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
		<!-- 项目戳开始 -->
		<div class="container container_box">
			<div class="content height-350">
				<div class="col-md-12 tab-v2 job-content">
					<jsp:include page="/WEB-INF/view/ses/sms/supplier_query/supplierInfo/nav.jsp">
						<jsp:param name="nav_flag" value="2"></jsp:param>
						<jsp:param name="supplierStatus" value="${suppliers.status}"></jsp:param>
					</jsp:include>
					<form id="form_id" action="" method="post">
						<input name="supplierId" id="id" value="${suppliers.id }" type="hidden">
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
					<c:forEach items="${financial}" var="f" varStatus="vs">
						<h2 class="count_flow"><i>${vs.index + 1}</i>${f.year }年财务（单位：万元）</h2>
						<div class="ul_list">
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
										<th class="info">审计报告书中的审计报告</th>
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
						</div>
						<div class="clear"></div>
					</c:forEach>
				</div>
			</div>
			<div class="col-md-12 tc mt20">
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
	</body>

</html>