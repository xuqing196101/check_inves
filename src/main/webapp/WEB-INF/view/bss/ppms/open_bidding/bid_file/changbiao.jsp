<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>

<html class=" js cssanimations csstransitions" lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
const ONE = "1";
const FIVE = "5";
const SIX = "6";
const SEVEN = "7";
const EIGHT = "8";
const NINE = "9";

function addTotal() {
	var allTable = document.getElementsByTagName("table");
	for(var i = 1; i < allTable.length; i++) {
		var totalMoney = 0;
		for(var j = 1; j < allTable[i].rows.length - 1; j++) { //遍历Table的所有Row
			var num = $(allTable[i].rows).eq(j).find("td").eq("5").text();
			var price = $(allTable[i].rows).eq(j).find("td").eq("6").find("input").val();
			var reg = /^\d+\.?\d*$/;
			var flag = false;
			if(!reg.exec(price)) {
				$(allTable[i].rows).eq(j).find("td").eq("6").find("input").val('');
				flag = true;
			}
			var total = $(allTable[i].rows).eq(j).find("td").eq("7").text();
			if(price == "" || price.trim() == "") {
				continue;
			} else {
				if (flag == true) {
					price = 0;
				}
				$(allTable[i].rows).eq(j).find("td").eq("7").text(parseFloat(price * num).toFixed(2));
				totalMoney += parseFloat(price * num);
				$(allTable[i].rows).eq(allTable[i].rows.length - 1).find("td").eq("1").text(parseFloat(totalMoney).toFixed(2));
			};
		};
	};
};

$(function(){
	var allTable = document.getElementsByTagName("table");
	for(var i = 1; i < allTable.length; i++) {
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

function eachTable(obj) {
    //根据保存按钮显示提示信息
 	var x,y;  
    oRect = obj.getBoundingClientRect();  
    x=oRect.left - 150;  
    y=oRect.top - 150;  
	var allTable = document.getElementsByTagName("table");
	var priceStr = "";
	var error = 0;
	for(var i = 1; i < allTable.length; i++) {
		var isTurnUp = $(allTable[i]).find("tr:last").find("td").eq("3").find("option:selected").text();
		if (isTurnUp == '未到场') {
			isTurnUp = 1;
		} else {
			isTurnUp = 2;
		}
		for(var j = 1; j < allTable[i].rows.length - 1; j++) { //遍历Table的所有Row
		    var supplierId = $(allTable[i]).attr('id');
		    var productId = $(allTable[i].rows).eq(j).attr('id');
			var num = $(allTable[i].rows).eq(j).find("td").eq("5").text();
			var price = $(allTable[i].rows).eq(j).find("td").eq("6").find("input").val();
			var total = $(allTable[i].rows).eq(j).find("td").eq("7").text();
			var deliveryTime = $(allTable[i].rows).eq(j).find("td").eq("8").find("input").val();
			deliveryTime = encodeURI(deliveryTime);
			deliveryTime = encodeURI(deliveryTime);
			var remark = $(allTable[i].rows).eq(j).find("td").eq("9").find("input").val();
			remark = encodeURI(remark);
			remark = encodeURI(remark);
			if(remark == "" || remark.trim() == "") {
				remark = null;
			}
			if(deliveryTime == "") {
				 //layer.msg("第" + (i + 1) + "包,表格第" + (j + 1) + "行,交货时间未填写"); 
				 layer.msg("表单未填写完整,单价和交货时间必须填写,请检查表单",{offset: [y, x]});
				return;
			}
			if(price == "" || price.trim() == "") {
				 //layer.msg("第" + (i + 1) + "包,表格第" + (j + 1) + "行,未报价"); 
				layer.msg("表单未填写完整,单价和交货时间必须填写,请检查表单",{offset: [y, x]});
				return;
				error++;
			} else {
				priceStr += price + "," + total + "," + deliveryTime + "," + remark + "," + supplierId + "," + productId + "," + isTurnUp + ",";
			};
		};
	}
	if(error == 0) {
		$("#priceStr").val(priceStr);
		var priceStr = $("#priceStr").val();
		var projectId = $("#projectId").val();
		$.ajax({
			url:"${pageContext.request.contextPath}/open_bidding/savemingxi.html?priceStr=" + priceStr + "&projectId="+ projectId,
			success:function(data){
				layer.alert("暂存成功",{offset: [y, x], shade:0.01});
				window.location.reload();
			}
		});
		//form.submit();
	};
}

function ycDiv(obj, index){
	if ($(obj).hasClass("jbxx") && !$(obj).hasClass("zhxx")) {
		$(obj).removeClass("jbxx");
		$(obj).addClass("zhxx");
	} else {
		if ($(obj).hasClass("zhxx") && !$(obj).hasClass("jbxx")) {
			$(obj).removeClass("zhxx");
			$(obj).addClass("jbxx");
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
</script>
</head>
<body onload="addTotal()">
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
				<td>${lq.deliveryTime }</td>
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
       <div class="clear">
       <form id="form" action="${pageContext.request.contextPath}/open_bidding/savemingxi.html" method="post">
		<input id="priceStr" name="priceStr" type="hidden" />
		<input id="projectId" name="projectId" value="${projectId }" type="hidden" />
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
											<%-- <td class="tc"><input class="w60" value="${pd.quotePrice}" maxlength="16" onblur="addTotal()" /></td> --%>
											<td>${pd.quotePrice}</td>
											<td class="tc">${pd.total}</td>
											<%-- <td class="tc"><input class="w90" value="<fmt:formatDate value="${pd.deliveryTime }" pattern="YYYY-MM-dd" />" readonly="readonly" onClick="WdatePicker()" /></td> --%>
											<td>${pd.deliveryTime }</td>
											<%-- <td class="tc"><input class="w60" />${pd.remark}</td> --%>
											<td class="tc">${pd.remark}</td>
										</tr>
									</c:if>
									<c:if test="${empty pd.supplierId}">
										<tr id="${pd.id }" class="hand">
											<td class="tc w50">${pd.serialNumber}</td>
											<td class="tc">${pd.goodsName}</td>
											<td class="tc">${pd.stand}</td>
											<td class="tc">${pd.qualitStand}</td>
											<td class="tc">${pd.item}</td>
											<td class="tc">${pd.purchaseCount}</td>
											<td class="tc"><input class="w60" value="${pd.quotePrice}" maxlength="16" onblur="addTotal()" /></td>
											<td class="tc">${pd.total}</td>
											<td class="tc"><input class="w90" value="${pd.deliveryTime }"/></td>
											<td class="tc"><input class="w60" />${pd.remark}</td>
										</tr>
									</c:if>
								</c:forEach>
							</c:forEach>
							<tr>
								<td class="tr" colspan="2"><b>总金额(元):</b></td>
								<td class="tl" colspan="3"></td>
								<td class="tr" colspan="2"><b>是否到场</b></td>
								<td class="tl" colspan="3">
									<c:if test="${flagButton == false }">
										<select>
											<option>已到场</option>
											<option>未到场</option>
										</select>
									</c:if>
									<c:if test="${flagButton == true }">
										<c:if test="${pdkey.isturnUp eq '1'}">未到场</c:if>
										<c:if test="${pdkey.isturnUp eq '2'}">已到场</c:if>
									</c:if>
								</td>
							</tr>
						</table>
					</div>
				</c:forEach>
			</c:forEach>
		</c:forEach>
		<div class="col-md-12 tc">
		  <c:if test="${flagButton == false }">
			<input class="btn btn-windows save" value="结束唱标" type="button" onclick="eachTable(this)">
		  </c:if>
		</div>
	  </form>
	</div>
  </c:if>
</body>
</html>
