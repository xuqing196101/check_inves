<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../../common.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title></title>

<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<script type="text/javascript">
const ONE = "1";
const FIVE = "5";
const SIX = "6";
const SEVEN = "7";
const EIGHT = "8";
const NINE = "9";

function addTotal() {
	var allTable = document.getElementsByTagName("table");
	for(var i = 0; i < allTable.length; i++) {
		var totalMoney = 0;
		for(var j = 1; j < allTable[i].rows.length - 1; j++) { //遍历Table的所有Row
			var num = $(allTable[i].rows).eq(j).find("td").eq(FIVE).text();
			var price = $(allTable[i].rows).eq(j).find("td").eq(SIX).find("input").val();
			var reg = /^\d+\.?\d*$/;
			if(!reg.exec(price)) {
				$(allTable[i].rows).eq(j).find("td").eq(SIX).find("input").val('');
				return;
			}
			var total = $(allTable[i].rows).eq(j).find("td").eq(SEVEN).text();
			if(price == "" || price.trim() == "") {
				continue;
			} else {
				$(allTable[i].rows).eq(j).find("td").eq("7").text(parseFloat(price * num).toFixed(2));
				totalMoney += parseFloat(price * num);
				$(allTable[i].rows).eq(allTable[i].rows.length - 1).find("td").eq(ONE).text(parseFloat(totalMoney).toFixed(2));
			};
		};
	};
};

function eachTable() {
	var allTable = document.getElementsByTagName("table");
	var priceStr = "";
	var error = 0;
	for(var i = 0; i < allTable.length; i++) {
	alert(i);
		for(var j = 1; j < allTable[i].rows.length - 1; j++) { //遍历Table的所有Row
		    var supplierId = $(allTable[i]).attr('id');
		    var productId = $(allTable[i].rows).eq(j).attr('id');
			var num = $(allTable[i].rows).eq(j).find("td").eq(FIVE).text();
			var price = $(allTable[i].rows).eq(j).find("td").eq(SIX).find("input").val();
			var total = $(allTable[i].rows).eq(j).find("td").eq(SEVEN).text();
			var deliveryTime = $(allTable[i].rows).eq(j).find("td").eq(EIGHT).find("input").val();
			var remark = $(allTable[i].rows).eq(j).find("td").eq(NINE).find("input").val();
			if(remark == "" || remark.trim() == "") {
				remark = null;
			}
			if(deliveryTime == "") {
				layer.msg("页签" + (i + 1) + ",表格第" + (j + 1) + "行,交货时间未填写");
				return;
			}
			if(price == "" || price.trim() == "") {
				layer.msg("页签" + (i + 1) + ",表格第" + (j + 1) + "行,未报价");
				return;
				error++;
			} else {
				priceStr += price + "," + total + "," + deliveryTime + "," + remark + "," + supplierId + "," + productId + ",";
			};
		};
	}
	if(error == 0) {
		$("#priceStr").val(priceStr);
		form.submit();
	};
}
</script>
</head>
<body>
<!-- 表格开始-->  
       <!--  <button class="btn btn-windows git tr" onclick="show()">唱标</button> -->
       <c:if test="${flag == false}">
       	 <c:forEach items="${listQuoteList }" var="listQuote" varStatus="vs">
        <h3 class="tc">
            <c:choose>
            	<c:when test="${project.dictionary.name == '公开招标' }">开标一览表</c:when>
            	<c:otherwise>报价一览表</c:otherwise>
            </c:choose>
        	</h3>
        <h2 class="list_title">投标人全称：${listQuote[0].supplier.supplierName }</h2>
        <table class="table table-striped table-bordered table-hover tc">
		<thead>
		<tr>
		  <th class="w50 info">序号</th>
		  <th class="info">包名</th>
		  <th class="info">货物名称</th>
		  <th class="info">品牌</th>
		  <th class="info">规格型号</th>
		  <th class="info">计量单位</th>
		  <th class="info">数量</th>
		  <th class="info">单价(含税)</th>
		  <th class="info">金额(含税)</th>
		  <th class="info">交货时间</th>
		  <th class="info">备注</th>
		</tr>
		</thead>
		<c:forEach items="${listQuote }" var="lq" varStatus="vs">
			<tr>
			    <td>${vs.index+1 }</td>
			    <td>${lq.packages.name}</td>
				<td>${lq.projectDetail.goodsName }</td>
				<td>${lq.projectDetail.brand }</td>
				<td>${lq.projectDetail.stand }</td>
				<td>${lq.projectDetail.item }</td>
				<td>${lq.projectDetail.purchaseCount }</td>
				<td>${lq.quotePrice }</td>
				<td>${lq.total }</td>
				<td><fmt:formatDate value="${lq.deliveryTime }" pattern="YYYY-MM-dd" /></td>
				<td>${lq.remark }</td>
			</tr>
		</c:forEach> 
		   <tr>
				<td colspan="2"><b>合计</b></td>
				<td colspan="2">投标总价</td>
				<td colspan="3">${listQuote[0].totalMoneyNames }</td>
				<td colspan="2">(小写)¥：</td>
				<td colspan="2">${listQuote[0].totalMoney }</td>
			</tr>
        </table>
        </c:forEach>
       </c:if>
       <c:if test="${flag == true }">
       <div class="container clear">
       <form id="form" action="${pageContext.request.contextPath}/open_bidding/save.html" method="post">
				<input id="priceStr" name="priceStr" type="hidden" />
				<input id="projectId" name="projectId" value="${projectId }" type="hidden" />
				<div class="row magazine-page">
					<div class="col-md-12 tab-v2 job-content">
						<div class="padding-top-10">
							<ul class="nav nav-tabs bgdd">
								<c:forEach items="${listPackage }" var="obj" varStatus="vs">
									<c:if test="${vs.index==0 }">
										<li class="active">
											<a aria-expanded="true" href="#tab-${vs.index+1 }" data-toggle="tab" title="${obj.name }">
												<c:choose>
													<c:when test="${fn:length(obj.name)>3}">${fn:substring(obj.name, 0, 3)}...</c:when>
													<c:otherwise>${obj.name}</c:otherwise>
												</c:choose>
											</a>
										</li>
									</c:if>
									<c:if test="${vs.index>0 }">
										<li class="">
											<a aria-expanded="true" href="#tab-${vs.index+1 }" data-toggle="tab" title="${obj.name }">
												<c:choose>
													<c:when test="${fn:length(obj.name)>3}">${fn:substring(obj.name, 0, 3)}...</c:when>
													<c:otherwise>${obj.name}</c:otherwise>
												</c:choose>
											</a>
										</li>
									</c:if>
								</c:forEach>
							</ul>
							<div class="tab-content">
								<c:forEach items="${listPd }" var="listProDel" varStatus="vs">
									<c:choose>
										<c:when test="${vs.index==0 }">
											<div class="tab-pane fade active in height-450 w800" id="tab-${vs.index+1 }">
												<c:forEach items="${listProDel }" var="proDel" varStatus="vs">
													<c:forEach items="${proDel.key }" var="pd" varStatus="vs">
													<span>${pd.supplierName}</span>
												<table id="${pd.id}"  class="table table-bordered table-condensed mt5">
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
															<tr id="${pd.id }" class="hand">
																<td class="tc w50">${pd.serialNumber}</td>
																<td class="tc">${pd.goodsName}</td>
																<td class="tc">${pd.stand}</td>
																<td class="tc">${pd.qualitStand}</td>
																<td class="tc">${pd.item}</td>
																<td class="tc">${pd.purchaseCount}</td>
																<td class="tc"><input class="w60" maxlength="16" onblur="addTotal()" /></td>
																<td class="tc"></td>
																<td class="tc"><input class="w90" readonly="readonly" onClick="WdatePicker()" /></td>
																<td class="tc"><input class="w60" /></td>
															</tr>
														</c:forEach>
													</c:forEach>
													<tr>
														<td class="tr" colspan="2"><b>总金额(元):</b></td>
														<td class="tl" colspan="7"></td>
													</tr>
												</table>
												</c:forEach>
												</c:forEach>
											</div>
										</c:when>
										<c:otherwise>
											<div class="tab-pane fade in height-450" id="tab-${vs.index+1 }">
											<c:forEach items="${listProDel }" var="proDel" varStatus="vs">
											   <c:forEach items="${proDel.key }" var="pd" varStatus="vs">
												<span>${pd.supplierName}</span>
												<table id="${pd.id}" class="table table-bordered table-condensed mt5">
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
															<tr id="${pd.id }" class="hand">
																<td class="tc w50">${pd.serialNumber}</td>
																<td class="tc">${pd.goodsName}</td>
																<td class="tc">${pd.stand}</td>
																<td class="tc">${pd.qualitStand}</td>
																<td class="tc">${pd.item}</td>
																<td class="tc">${pd.purchaseCount}</td>
																<td class="tc"><input class="w60" maxlength="16" onblur="addTotal()" /></td>
																<td class="tc"></td>
																<td class="tc"><input class="w90" readonly="readonly" onClick="WdatePicker()" /></td>
																<td class="tc"><input class="w60" /></td>
															</tr>
														</c:forEach>
													</c:forEach>
													<tr>
														<td class="tr" colspan="2"><b>总金额(元):</b></td>
														<td class="tl" colspan="7"></td>
													</tr>
												</table>
												</c:forEach>
												</c:forEach>
											</div>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</div>
						</div>
						<div class="col-md-12 tc">
							<input class="btn btn-windows save" value="保存" type="button" onclick="eachTable()">
						</div>
					</div>
				</div>
			</form>
		</div>
       </c:if>
</body>
</html>
