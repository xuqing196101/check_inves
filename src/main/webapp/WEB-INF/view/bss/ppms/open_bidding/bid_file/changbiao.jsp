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

	var count = '${count}';
	var jsonStr = [];
	function addTotal() {
		var allTable = document.getElementsByTagName("table");
		var i = 0;
		if (count > 0) {
			i = count;
		}
		for(i; i < allTable.length; i++) {
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
		var error = 0;
		var i = 0;
		if (count > 0) {
			i = count;
		}
		for(i; i < allTable.length; i++) {
			for(var j = 1; j < allTable[i].rows.length - 1; j++) { //遍历Table的所有Row
			    var supplierId = $(allTable[i]).attr('id');
			    var productId = $(allTable[i].rows).eq(j).attr('id');
				var num = $(allTable[i].rows).eq(j).find("td").eq("5").text();
				var price = $(allTable[i].rows).eq(j).find("td").eq("6").find("input").val();
				var total = $(allTable[i].rows).eq(j).find("td").eq("7").text();
				var deliveryTime = $(allTable[i].rows).eq(j).find("td").eq("8").find("input").val();
				var remark = $(allTable[i].rows).eq(j).find("td").eq("9").find("input").val();
				if(remark == "" || remark.trim() == "") {
					remark = null;
				}
				if(deliveryTime == "") {
					 layer.msg("表单未填写完整,单价和交货时间必须填写,请检查表单",{offset: [y, x]});
					return;
				}
				if(price == "" || price.trim() == "") {
					layer.msg("表单未填写完整,单价和交货时间必须填写,请检查表单",{offset: [y, x]});
					return;
					error++;
				} else {
					var json = {"price":price, "total":total, "deliveryTime":deliveryTime, "remark":remark, "supplierId":supplierId, "productId":productId};
					jsonStr.push(json);
					//console.log(jsonStr); 
				};
			};
		}
		if(error == 0) {
			var projectId = $("#projectId").val();
			 $.ajax({
			        type: "POST",
			        url: "${pageContext.request.contextPath}/open_bidding/savemingxi.html?projectId="+projectId + "&packId=${packId}" + "&flowDefineId=${flowDefineId}",
			        data: {quoteList:JSON.stringify(jsonStr)},
			        dataType: "json",
			        success: function (message) {
			        	if ('${packId}' == null || '${packId}' == "") {
			        		window.location.href="${pageContext.request.contextPath}/open_bidding/viewMingxi.html?projectId="+projectId;
			        		//$("#tab-3").load("${pageContext.request.contextPath}/open_bidding/viewMingxi.html?projectId="+projectId);
			        	} else {
			        		//window.location.href="${pageContext.request.contextPath}/packageExpert/auditManage.html?projectId="+projectId + "&flowDefineId=${flowDefineId}";
			        		$("#tab-3").load("${pageContext.request.contextPath}/packageExpert/toSupplierQuote.html?projectId=${projectId}&flowDefineId=${flowDefineId}");
			        	}
			        }
			    });
		};
	}

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
</script>
</head>
<body onload="addTotal()">
<!-- 表格开始-->  
       <div class="clear">
       	<c:if test="${not empty count}">
		<h2 class="tc">第${listDate + 1}轮报价</h2>
		</c:if>
		<input id="projectId" name="projectId" value="${projectId}" type="hidden" />
		<c:forEach items="${listPackage}" var="listPackage" varStatus="vs">
		<c:set value="${vs.index}" var="index"></c:set>
			   <div>
				 <c:if test="${vs.index == 0 }">
				 	<h2 onclick="ycDiv(this,'${index}')" class="count_flow spread hand">包名:<span class="f14 blue">${listPackage.name }</span>
				 	<span>项目预算报价(万元)：${listPackage.projectBudget}</span>
				 	</h2>
				 </c:if>
				  <c:if test="${vs.index != 0 }">
				 	<h2 onclick="ycDiv(this,'${index}')" class="count_flow shrink hand">包名:<span class="f14 blue">${listPackage.name }</span>
				 	<span>项目预算报价(万元)：${listPackage.projectBudget}</span>
				 	</h2>
				 </c:if>
               </div>
			<c:forEach items="${listPackage.suList}" var="suList" varStatus="vs">
				<div class="p0${index}">
					<span class="fl">供应商名称：<span class="f14 blue">${suList.supplierName}</span></span>
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
						<c:forEach items="${suList.pdList}" var="pdList" varStatus="vs">
								<tr id="${pdList.id }" class="hand">
									<td class="tc w50">${vs.index + 1}</td>
									<td class="tl">${pdList.goodsName}</td>
									<td class="tl">${pdList.stand}</td>
									<td class="tl w200">${pdList.qualitStand}</td>
									<td class="tc w50">${pdList.item}</td>
									<td class="tc w50">${pdList.purchaseCount}</td>
									<td class="tr w50"><input class="w60" value="${pdList.quotePrice}" maxlength="16" onblur="addTotal()" /></td>
									<td class="tr w50">${pdList.total}</td>
									<td class="tc w80"><input class="w90" value="${pdList.deliveryTime }"/></td>
									<td class="tc"><input class="w60" />${pdList.remark}</td>
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
		<div class="col-md-12 tc">
		    	<c:if test="${not empty listDate}">
		    	<input class="btn btn-windows save" value="结束报价" type="button" onclick="eachTable(this)">
		    	<input class="btn btn-windows reset" value="返回" type="button" onclick="back()">
		    	</c:if>
		    	<c:if test="${empty listDate}">
		    	<input class="btn btn-windows save" value="结束唱标" type="button" onclick="eachTable(this)">
		    	</c:if>
		</div>
	</div>
</body>
</html>
