<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'expert_list.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  <script type="text/javascript">
	//查看供应商报价
	 function supplierView(supplierId){
	    var projectId=$("#projectId").val();
		location.href="${pageContext.request.contextPath}/packageExpert/supplierQuote.html?projectId="+projectId+"&supplierId="+supplierId;
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

$(function(){
    if ('${status}' == 'true') {
    	var packageLength = '${fn:length(packageIds)}';
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
    }
});

function ycDivmingxi(obj, index){
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

function quoteAgain(projectId, packId, status){
	var flowDefineId = $("#flowDefineId").val();
    if (status ==1 ) {
		window.location.href="${pageContext.request.contextPath}/open_bidding/quoteAgainTotal.html?projectId="+projectId+"&packId="+packId + "&flowDefineId=" +flowDefineId;
    } else {
    	window.location.href="${pageContext.request.contextPath}/open_bidding/quoteAgainMingxi.html?projectId="+projectId+"&packId="+packId + "&flowDefineId=" +flowDefineId;
    }
}

	function showQuoteHistory(projectId, packId, data) {
	    var flowDefineId = $("#flowDefineId").val();
		window.location.href="${pageContext.request.contextPath}/open_bidding/viewquoteAgainTotal.html?timestamp=" + data + "&projectId=" + projectId + "&packId=" + packId+ "&flowDefineId=" +flowDefineId;
	}
	
	function showQuoteHistoryMingxi(projectId, packId, data) {
	    var flowDefineId = $("#flowDefineId").val();
		window.location.href="${pageContext.request.contextPath}/open_bidding/viewquoteAgainTotalMingxi.html?timestamp=" + data + "&projectId=" + projectId + "&packId=" + packId+ "&flowDefineId=" +flowDefineId;
	}
	$(function(){
		for (var i = 1; i < 20; i++) {
			$(".p0" + i).addClass("hide");
		};
	});
  </script>
  <body>
 <c:if test="${status == false }"> 
<h2 class="list_title">供应商报价信息</h2>
  <div class="clear">
<c:set value="1" var ="count"></c:set>
<c:forEach items="${treeMap }" var="treemap" varStatus="vsKey">
	<c:forEach items="${treemap.key }" var="treemapKey" varStatus="vs">
		<div>
			 <c:if test="${vsKey.index == 0}">
			 	<h2 onclick="ycDiv(this,'${vsKey.index}')" class="count_flow spread hand">包名:<span class="f14 blue">${fn:substringBefore(treemapKey, "|")}</span></h2>
			 </c:if>
			 <c:if test="${vsKey.index != 0}">
			 	<h2 onclick="ycDiv(this,'${vsKey.index}')" class="count_flow shrink hand">包名:<span class="f14 blue">${fn:substringBefore(treemapKey, "|")}</span></h2>
			 </c:if>
        </div>
        <div class="p0${vsKey.index}">
		<table class="table table-bordered table-condensed mt5">
			<thead>
				<tr>
					<th class="w50 info">序号</th>
				  	<th class="info">供应商名称</th>
				  	<th class="info">报价(单位：万元)</th>
			    </tr>
			</thead>
		<c:forEach items="${treemap.value}" var="treemapValue" varStatus="vs">
				<c:set value="${count+1 }" var="index"></c:set>
				<c:set value="${treemapValue.packages}" var="packId"></c:set>
				<c:set value="${treemapValue.isEndPrice}" var="isEndPrice"></c:set>
				<tr>
				    <td class="tc w50">${vs.index+1}</td>
				    <td class="tc">${treemapValue.suppliers.supplierName}</td>
					<td class="tc">${treemapValue.total}</td>
			    </tr>
			    
		</c:forEach>
		<c:if test="${dd.code eq 'JZXTP' || dd.code eq 'DYLY'}">
			<tr>
			        <c:if test="${isEndPrice ==0 }">
		        		<td class="tc" colspan="2"><button class="btn" onclick = "quoteAgain('${project.id}','${packId}',1)">再次报价</button></td>
		        		<td class="tc">
						 <select onchange="showQuoteHistory('${project.id}','${packId}',this.options[this.options.selectedIndex].value)">
								<c:if test="${empty treemap.value[0].dataList}">
									<option value=''>暂无报价历史</option>
								</c:if>
								<c:set value="${fn:length(treemap.value[0].dataList)}" var="length"></c:set>
								<c:forEach items="${treemap.value[0].dataList}" var="ld" varStatus="vs">
									<c:set value="${length - vs.index}" var="result"></c:set>
									<option value='<fmt:formatDate value="${ld}" pattern="YYYY-MM-dd HH:mm:ss"/>'>第${result}次报价</option>
								</c:forEach>
					 	 </select>
				    </td>
		        	</c:if>
		        	 <c:if test="${isEndPrice ==1 }">
		        		<td class="tc" colspan="2"><button class="btn">已结束唱标</button></td>
		        		<td class="tc">
						 <select onchange="showQuoteHistory('${project.id}','${packId}',this.options[this.options.selectedIndex].value)">
								<c:if test="${empty treemap.value[0].dataList}">
									<option value=''>暂无报价历史</option>
								</c:if>
								<c:set value="${fn:length(treemap.value[0].dataList)}" var="length"></c:set>
								<c:forEach items="${treemap.value[0].dataList}" var="ld" varStatus="vs">
									<c:set value="${length - vs.index}" var="result"></c:set>
									<option value='<fmt:formatDate value="${ld}" pattern="YYYY-MM-dd HH:mm:ss"/>'>第${result}次报价</option>
								</c:forEach>
					 	 </select>
				    </td>
		        	</c:if>
					
		        </tr>
		 </c:if>
		</table>
		</div>
	</c:forEach>
</c:forEach>
</div>
</c:if>
   <c:if test="${status == true }">
       <div class="clear">
		<input id="priceStr" name="priceStr" type="hidden" />
		<input id="projectId" name="projectId" value="${projectId }" type="hidden" />
		<c:forEach items="${listPd }" var="listProDel" varStatus="vs">
		<c:set value="${vs.index}" var="index"></c:set>
			   <div>
				 <h2 onclick="ycDivmingxi(this,'${index}')" class="count_flow jbxx hand">包名:<span class="f14 blue">${listPackage[index].name }</span></h2>
		 			<c:if test="${dd.code eq 'JZXTP' || dd.code eq 'DYLY'}">
					        	
					        	 <c:if test="${listPackage[index].isEndPrice ==0 }">
					        		<button class="btn" onclick = "quoteAgain('${project.id}','${listPackage[index].id}')">再次报价</button>
					        	</c:if>
					        	 <c:if test="${listPackage[index].isEndPrice ==1 }">
					        		<button class="btn">已结束唱标</button>
					        	</c:if>
					        	
					        	
								 <select onchange="showQuoteHistoryMingxi('${project.id}','${listPackage[index].id}',this.options[this.options.selectedIndex].value)">
								 	<c:set value="${fn:length(listPackage[index].dataList)}" var="length"></c:set>
									<c:forEach items="${listPackage[index].dataList}" var="ld" varStatus="vs">
										<c:set value="${length - vs.index}" var="result"></c:set>
										<option value='<fmt:formatDate value="${ld}" pattern="YYYY-MM-dd HH:mm:ss"/>'>第${result}次报价</option>
									</c:forEach>
							 	 </select>
					 </c:if>
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
	</div>
  </c:if>

</body>
</html>
