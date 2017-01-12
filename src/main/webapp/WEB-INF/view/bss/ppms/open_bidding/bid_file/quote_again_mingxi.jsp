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
const ONE = "1";
const FIVE = "5";
const SIX = "6";
const SEVEN = "7";
const EIGHT = "8";
const NINE = "9";
var jsonStr = [];
function addTotal() {
	var allTable = document.getElementsByTagName("table");
	for(var i = 0; i < allTable.length; i++) {
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

function eachTable(obj) {
    //根据保存按钮显示提示信息
 	var x,y;  
    oRect = obj.getBoundingClientRect();  
    x=oRect.left - 150;  
    y=oRect.top - 150;  
	var allTable = document.getElementsByTagName("table");
	var priceStr = "";
	var error = 0;
	for(var i = 0; i < allTable.length; i++) {
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
				//priceStr += price + "," + total + "," + deliveryTime + "," + remark + "," + supplierId + "," + productId + "," + isTurnUp + ",";
				var json = {"price":price, "total":total, "deliveryTime":deliveryTime, "remark":remark, "supplierId":supplierId, "productId":productId, "isTurnUp":isTurnUp};
				jsonStr.push(json);
				console.log(jsonStr); 
			};
		};
	}
	if(error == 0) {
		/* $("#priceStr").val(priceStr);
		var priceStr = $("#priceStr").val();
		var projectId = $("#projectId").val();
		$.ajax({
			url:"${pageContext.request.contextPath}/open_bidding/savemingxi.html?priceStr=" + priceStr + "&projectId="+ projectId + "&packId=${packId}",
			success:function(data){
				layer.alert("报价成功",{offset: [y, x], shade:0.01});
				window.location.href="${pageContext.request.contextPath}/packageExpert/auditManage.html?projectId=${projectId}&flowDefineId=${flowDefineId}";
			}
		}); */
		var projectId = $("#projectId").val();
		 $.ajax({
		        type: "POST",
		        url: "${pageContext.request.contextPath}/open_bidding/savemingxi.html?projectId="+projectId+ "&packId=${packId}",
		        data: {quoteList:JSON.stringify(jsonStr)},
		        dataType: "json",
		        success: function (message) {
		        },
		        error: function (message) {
		        }
		    });
		    window.location.href="${pageContext.request.contextPath}/packageExpert/auditManage.html?projectId=${projectId}&flowDefineId=${flowDefineId}";
		//form.submit();
	};
}

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
</script>
</head>
<body>
<!-- 表格开始-->  
       	<div class="clear">
       <form id="form" action="${pageContext.request.contextPath}/open_bidding/savemingxi.html" method="post">
		<input id="priceStr" name="priceStr" type="hidden" />
		<input id="projectId" name="projectId" value="${projectId }" type="hidden" />
		<c:forEach items="${listPd }" var="listProDel" varStatus="vs">
		<c:set value="${vs.index}" var="index"></c:set>
			   <div>
				 <h2 onclick="ycDiv(this,'${index}')" class="count_flow shrink hand">包名:<span class="f14 blue">${listPackage[index].name }</span>
				 	<span>项目预算报价(万元)：${listPackage[index].projectBudget}</span>
				 </h2>
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
											<td class="tc"><input class="w60"  maxlength="16" onblur="addTotal()" /></td>
											<td class="tc"></td>
											<td class="tc"><input class="w90" /></td>
											<td class="tc"><input class="w60" /></td>
										</tr>
									</c:if>
								</c:forEach>
							</c:forEach>
							<tr>
								<td class="tr" colspan="2"><b>总金额(元):</b></td>
								<td class="tl" colspan="3"></td>
								<td class="tr" colspan="2"><b>是否到场</b></td>
								<td class="tl" colspan="3">
									<select>
											<option>已到场</option>
											<option>未到场</option>
									</select>
								</td>
							</tr>
						</table>
					</div>
				</c:forEach>
			</c:forEach>
		</c:forEach>
		<div class="col-md-12 tc">
			<input class="btn btn-windows save" value="结束报价" type="button" onclick="eachTable(this)">
			<input class="btn btn-windows reset" value="返回" type="button" onclick="history.go(-1)">
		</div>
	  </form>
	</div>
	
</body>
</html>
