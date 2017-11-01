<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp"%>
		<script src="${pageContext.request.contextPath}/public/backend/js/lock_table_head.js"></script>
		<script type="text/javascript">
		</script>
	</head>

	<body>
		<!-- 录入采购计划开始-->
		<div class="container">
			<!-- 项目戳开始 -->
			<div class="content" id="content">
				<c:if test="${lists ne null}">
					<table id="table" class="table table-bordered table-condensed table-hover table_wrap">
						<thead>
							<tr class="space_nowrap">
								<th class="info seq">序号</th>
								<th class="info department">需求部门</th>
								<th class="info goodsname">物资类别<br/>及名称</th>
								<th class="info stand">规格型号</th>
								<th class="info qualitstand">质量技术标准<br/>(技术参数)</th>
								<th class="info item">计量<br/>单位</th>
								<th class="info purchasecount">采购<br/>数量</th>
								<th class="info deliverdate">交货<br/>期限</th>
								<th class="info purchasetype">采购方式</th>
								<th class="info purchasename">供应商名称</th>
								<th class="memo">备注</th>
							</tr>
						</thead>
						<c:forEach items="${lists}" var="obj" varStatus="vs">
							<tr>
								<td>
									<div class="seq">${obj.serialNumber}</div>
								</td>
								<td>
									<div class="department">${obj.department}</div>
								</td>
								<td>
									<div class="goodsname">${obj.goodsName}</div>
								</td>
								<td>
									<div class="stand">${obj.stand}</div>
								</td>
								<td>
									<div class="qualitStand">${obj.qualitStand}</div>
								</td>
								<td>
									<div class="item">${obj.item}</div>
								</td>
								<td>
									<div class="purchaseCount">${obj.purchaseCount}</div>
								</td>
								<td>
									<div class="deliverDate">${obj.deliverDate}</div>
								</td>
								<td>
									<div class="purchaseType tc">${obj.purchaseType}</div>
								</td>
								<td>
									<div class="w100">${obj.supplier}</div>
								</td>
								<td>
									<div class="memo">${obj.memo}</div>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</div>
		</div>
	</body>

</html>