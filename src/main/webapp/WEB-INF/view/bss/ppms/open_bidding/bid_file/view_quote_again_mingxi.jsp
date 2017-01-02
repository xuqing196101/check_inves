<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>

<html class=" js cssanimations csstransitions" lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
$(function(){
	var allTable = document.getElementsByTagName("table");
	for(var i = 0; i < allTable.length; i++) {
		var totalMoney = 0;
		for(var j = 1; j < allTable[i].rows.length - 1; j++) { //遍历Table的所有Row
			var num = $(allTable[i].rows).eq(j).find("td").eq("5").text();
			var price = $(allTable[i].rows).eq(j).find("td").eq("6").text();
			$(allTable[i].rows).eq(j).find("td").eq("7").text(parseFloat(price * num).toFixed(2));
			totalMoney += parseFloat(price * num);
			$(allTable[i].rows).eq(allTable[i].rows.length - 1).find("td").eq("1").text(parseFloat(totalMoney).toFixed(2));
			};
		};
});

function back() {
	$("#tab-3").load("${pageContext.request.contextPath}/packageExpert/toSupplierQuote.html?projectId=${projectId}&flowDefineId=${flowDefineId}");
}
</script>
</head>
<body>
<!-- 表格开始-->  
       	<div class="clear">
		<c:forEach items="${listPd }" var="listProDel" varStatus="vs">
		<c:set value="${vs.index}" var="index"></c:set>
			   <div>
				 <h2 onclick="ycDiv(this,'${index}')" class="count_flow jbxx hand">包名:<span class="f14 blue">${listPackage[index].name }</span></h2>
               </div>
			<c:forEach items="${listProDel }" var="proDel" varStatus="vs">
				<c:forEach items="${proDel.key }" var="pdkey" varStatus="vs">
						<div class="p0${index}">
						<span class="fl">供应商名称：<span class="f14 blue">${pdkey.supplierName}</span></span>
						<table id="${pdkey.id}"  class="table table-bordered table-condensed mt5">
							<thead>
								<tr>
									<th class="info w50">序号</th>
									<th class="info">物资名称</th>
									<th class="info">规格<br/>型号</th>
									<th class="info">质量技术<br/>标准</th>
									<th class="info">计量<br/>单位</th>
									<th class="info">采购<br/>数量</th>
									<th class="info">单价（元）</th>
									<th class="info">小计</th>
									<th class="info">交货时间</th>
									<th class="info">备注</th>
								</tr>
							</thead>
							<c:forEach items="${listProDel }" var="proDel" varStatus="vs">
								<c:forEach items="${proDel.value }" var="pd" varStatus="vs">
									<c:if test="${pd.supplierId eq pdkey.id }">
										<tr id="${pd.id }" class="hand">
											<td class="tc w50">${pd.serialNumber}</td>
											<td class="tc">${pd.goodsName}</td>
											<td class="tc">${pd.stand}</td>
											<td class="tc">${pd.qualitStand}</td>
											<td class="tc">${pd.item}</td>
											<td class="tc">${pd.purchaseCount}</td>
											<td>${pd.quotePrice}</td>
											<td class="tc">${pd.total}</td>
											<td>${pd.deliveryTime }</td>
											<td class="tc">${pd.remark}</td>
										</tr>
									</c:if>
								</c:forEach>
							</c:forEach>
							<tr>
								<td class="tr" colspan="2"><b>总金额(元):</b></td>
								<td class="tl" colspan="3"></td>
								<td class="tr" colspan="2"><b>是否到场</b></td>
								<td class="tl" colspan="3">
											<c:if test="${pdkey.isturnUp eq '1'}">未到场</c:if>
										<c:if test="${pdkey.isturnUp eq '2'}">已到场</c:if>
								</td>
							</tr>
						</table>
					</div>
				</c:forEach>
			</c:forEach>
		</c:forEach>
		<div class="col-md-12 tc">
			<input class="btn btn-windows reset" value="返回" type="button" onclick="history.go(-1)">
		</div>
	</div>
</body>
</html>
