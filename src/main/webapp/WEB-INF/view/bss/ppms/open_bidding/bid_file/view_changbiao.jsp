<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>

<html class=" js cssanimations csstransitions" lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
	function back() {
		$("#tab-3").load("${pageContext.request.contextPath}/packageExpert/toSupplierQuote.html?projectId=${projectId}&flowDefineId=${flowDefineId}");
	}

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

	$(function(){
		for (var i = 1; i < 20; i++) {
			$(".p0" + i).addClass("hide");
		};
	});

	function ycDiv(obj, index){
		if ($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
			$(obj).removeClass("spread");
			$(obj).addClass("shrink");
		} else {
			if ($(obj).hasClass("shrink") && !$(obj).hasClass("spread")) {
				$(obj).removeClass("shrink");
				$(obj).addClass("spread");
			}
		}
		
		var divObj = new Array();
		divObj = $(".p0" + index);
		for (var i =0; i < divObj.length; i++) {
	    	if ($(divObj[i]).hasClass("p0"+index) && $(divObj[i]).hasClass("hide")) {
	    		$(divObj[i]).removeClass("hide");
	    	} else {
	    		if ($(divObj[i]).hasClass("p0"+index)) {
	    			$(divObj[i]).addClass("hide");
	    		}
	    	}
		};
	}
	
	 function openMax(){
	 	window.open("${pageContext.request.contextPath}/open_bidding/viewMingxi.html?projectId=${projectId}");
	 }; 
	 
	 function printCon(projectId, packageId){
	 	 window.location.href="${pageContext.request.contextPath}/open_bidding/changmingxiWord.html?projectId="+projectId+"&packId="+packageId;
	 }
</script>
</head>
<body onload="addTotal()">
<!-- 表格开始-->  
	   <c:if test="${fn:length(listPackage) != 1 }">
	   		<div class="tr mt10">
	   			<button class="btn" onclick="openMax()">全屏</button>
	   		</div>
	   </c:if>
       <div class="clear">
		<input id="projectId" name="projectId" value="${project.id}" type="hidden" />
		<c:forEach items="${listPackage}" var="listPackage" varStatus="vs">
		<c:set value="${vs.index}" var="index"></c:set>
			   <div class="col-md-12 col-sm-12 col-xs-12 p0">
				 <c:if test="${vs.index == 0 }">
				 	<h2 onclick="ycDiv(this,'${index}')" class="count_flow spread hand fl">包名:<span class="f14 blue">${listPackage.name }</span>
				 		<span>项目预算报价(万元)：${listPackage.projectBudget}</span>
				 	</h2>
				 	<div class="fl mt20 ml10">
					 	<button class="btn" onclick="printCon('${projectId}','${listPackage.id}')">投标报价一览表</button>
				 	</div>
				 </c:if>
				  <c:if test="${vs.index != 0 }">
				 	<h2 onclick="ycDiv(this,'${index}')" class="count_flow shrink hand fl clear">包名:<span class="f14 blue">${listPackage.name }</span>
				 		<span>项目预算报价(万元)：${listPackage.projectBudget}</span>
				 	</h2>
				 	<div class="fl mt20 ml10">
					 	<button class="btn" onclick="printCon('${projectId}','${listPackage.id}')">投标报价一览表</button>
				 	</div>
				 </c:if>
               </div>
			<c:forEach items="${listPackage.suList}" var="suList" varStatus="vs">
				<div class="p0${index} clear w100p ">
					<span class="clear fl">供应商名称：<span class="f14 blue">${suList.supplierName}</span></span>
					<table id="${suList.id}"  class="table table-bordered table-condensed mt5">
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
						<c:forEach items="${suList.quoteList}" var="quoteList" varStatus="vs">
							<tr id="${quoteList.id }" class="hand">
								<td class="tc w50">${vs.index + 1}</td>
								<td class="tl">${quoteList.projectDetail.goodsName}</td>
								<td class="tl">${quoteList.projectDetail.stand}</td>
								<td class="tl w200">${quoteList.projectDetail.qualitStand}</td>
								<td class="tc w50">${quoteList.projectDetail.item}</td>
								<td class="tc w50">${quoteList.projectDetail.purchaseCount}</td>
								<td class="tr w50">${quoteList.quotePrice}</td>
								<td class="tr w50">${quoteList.total}</td>
								<td class="tc w80">${quoteList.deliveryTime }</td>
								<td class="tc">${quoteList.remark}</td>
							</tr>
						</c:forEach>
						<tr>
							<td class="tr" colspan="2"><b>总金额(元):</b></td>
							<td class="tl" colspan="8"></td>
						</tr>
					</table>
				</div>
				</c:forEach>
		</c:forEach>
	</div>
		<div class="col-md-12 tc">
		<c:if test="${fn:length(listPackage) == 1 }">
			<input class="btn btn-windows reset" value="返回" type="button" onclick="back()">
		</c:if>
		</div>
</body>
</html>
