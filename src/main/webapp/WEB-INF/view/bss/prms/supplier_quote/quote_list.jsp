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
  	$(function() {
  		//获取查看或操作权限
       	var isOperate = $('#isOperate', window.parent.document).val();
       	if(isOperate == 0) {
       		//只具有查看权限，隐藏操作按钮
			$(":button").each(function(){ 
				$(this).hide();
            }); 
		}
    })
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
	    var allTable = document.getElementsByTagName("table");
	    if ('${status}' == 'true') {
	    	var packageLength = '${fn:length(packageIds)}';
			for(var i = packageLength; i < allTable.length; i++) {
				var totalMoney = 0;
				for(var j = 1; j < allTable[i].rows.length - 1; j++) { //遍历Table的所有Row
					var num = $(allTable[i].rows).eq(j).find("td").eq("5").text();
					var price = $(allTable[i].rows).eq(j).find("td").eq("6").text();
					$(allTable[i].rows).eq(j).find("td").eq("7").text(parseFloat(price * num).toFixed(2));
					totalMoney += parseFloat(price * num);
					$(allTable[i].rows).eq(allTable[i].rows.length - 1).find("td").eq("1").text(parseFloat(totalMoney).toFixed(2));
					};
			};
	    } else {
	    	for(var i = 1; i < allTable.length; i++) {
				for(var j = 1; j < allTable[i].rows.length; j++) { //遍历Table的所有Row
					$(allTable[i].rows).eq(j).find("td").eq("0").text(j);
				};
			};
	    }
	});

	function ycDivmingxi(obj, index){
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

	function quoteAgain(projectId, packId, status){
	    if (status ==1 ) {
			//window.location.href="${pageContext.request.contextPath}/open_bidding/changtotal.html?projectId="+projectId+"&packId="+packId + "&flowDefineId=${flowDefineId}";
			//count 的作用是为了分别 是唱标还是报价 ,报价的第一个页签有N个table，所以循环的时候要从N开始
	    	$("#tab-3").load("${pageContext.request.contextPath}/open_bidding/changtotal.html?projectId="+projectId+"&packId="+packId + "&flowDefineId=${flowDefineId}" + "&count=1");
	    } else {
	    	//window.location.href="${pageContext.request.contextPath}/open_bidding/changmingxi.html?projectId="+projectId+"&packId="+packId + "&flowDefineId=${flowDefineId}";
	    	$("#tab-3").load("${pageContext.request.contextPath}/open_bidding/changmingxi.html?projectId="+projectId+"&packId="+packId + "&flowDefineId=${flowDefineId}" + "&count=1");
	    }
	}

	function showQuoteHistory(projectId, packId, data) {
		//window.location.href="${pageContext.request.contextPath}/open_bidding/viewChangtotal.html?timestamp=" + data + "&projectId=" + projectId + "&packId=" + packId+ "&flowDefineId=${flowDefineId}";
		var json = {"timestamp":data};
		$("#ifs").attr("src","${pageContext.request.contextPath}/open_bidding/viewChangtotalByPackId.html?projectId=" + projectId + "&packId=" + packId+ "&flowDefineId=${flowDefineId}&timestamp="+data);
	}
	
	function showQuoteHistoryMingxi(projectId, packId, data) {
		//window.location.href="${pageContext.request.contextPath}/open_bidding/viewMingxi.html?timestamp=" + data + "&projectId=" + projectId + "&packId=" + packId+ "&flowDefineId=${flowDefineId}";
		var json = {"timestamp":data};
		$("#tab-3").load("${pageContext.request.contextPath}/open_bidding/viewMingxi.html?projectId=" + projectId + "&packId=" + packId+ "&flowDefineId=${flowDefineId}", json);
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
			 	<h2 onclick="ycDiv(this,'${vsKey.index}')" class="count_flow spread hand">包名:<span class="f14 blue">${fn:substringBefore(treemapKey, "|")}</span>
			 		<c:if test="${mapPackageName[fn:substringBefore(treemapKey, '|')] eq 'YZZ'}"><span class="star_red">[该包已终止]</span></c:if>
			 		<c:if test="${mapPackageName[fn:substringBefore(treemapKey, '|')] eq 'ZJZXTP'}"><span class="star_red">[该包已转竟谈]</span></c:if>
			 		<c:if test="${mapPackageMeth[fn:substringBefore(treemapKey, '|')] eq 'OPEN_ZHPFF' && mapPackageZhpf[fn:substringBefore(treemapKey, '|')]!=1}"><span class="star_red">[请先完善经济技术评审]</span></c:if>
			 	</h2>
			 </c:if>
			 <c:if test="${vsKey.index != 0}">
			 	<h2  onclick="ycDiv(this,'${vsKey.index}')" class="count_flow shrink hand">包名:<span class="f14 blue">${fn:substringBefore(treemapKey, "|")}</span>
			 		<c:if test="${mapPackageName[fn:substringBefore(treemapKey, '|')] eq 'YZZ'}"><span class="star_red">[该包已终止]</span></c:if>
			 		<c:if test="${mapPackageName[fn:substringBefore(treemapKey, '|')] eq 'ZJZXTP'}"><span class="star_red">[该包已转竟谈]</span></c:if>
			 	  <c:if test="${mapPackageMeth[fn:substringBefore(treemapKey, '|')] eq 'OPEN_ZHPFF' && mapPackageZhpf[fn:substringBefore(treemapKey, '|')]!=1}"><span class="star_red">[请先完善经济技术评审]</span></c:if>
			 	</h2>
			 </c:if>
        </div>
        <c:if test="${mapPackageName[fn:substringBefore(treemapKey, '|')] ne 'YZZ' && mapPackageName[fn:substringBefore(treemapKey, '|')] ne 'ZJZXTP' && ((mapPackageMeth[fn:substringBefore(treemapKey, '|')] eq 'OPEN_ZHPFF' && mapPackageZhpf[fn:substringBefore(treemapKey, '|')]==1)||mapPackageMeth[fn:substringBefore(treemapKey, '|')] ne 'OPEN_ZHPFF')}">
        <div  class="p0${vsKey.index}">
		<table class="table table-bordered table-condensed mt5">
			<%-- <thead>
				<tr>
					<th class="w50 info">序号</th>
				  	<th class="info">供应商名称</th>
				  	<th class="info w200">报价(万元)</th>
				  	<th class="info">交货期限</th>
				  	<c:if test="${dd.code eq 'JZXTP' || dd.code eq 'DYLY'}">
				  	<th class="info w100">状态</th>
					<th class="info">放弃原因</th>
					</c:if>
			    </tr>
			</thead> --%>
			<c:forEach items="${packLis}" var="packLis" varStatus="vs">
	     <c:if test="${packLis.name==fn:substringBefore(treemapKey, '|')}">
	     <c:set value="${packLis.isEndPrice}" var="isEndPrice"></c:set>
	     </c:if>
	  </c:forEach>
		<c:forEach items="${treemap.value}" var="treemapValue" varStatus="vs">
				<c:set value="${count+1 }" var="index"></c:set>
				<c:set value="${treemapValue.packages}" var="packId"></c:set>
				<c:if test="${not empty treemapValue.total or treemapValue.isRemoved eq '放弃报价' }">
					<%-- <tr>
					    <td class="tc w50">${vs.index+1}${treemapValue.removedReason }</td>
					    <td class="tl">${treemapValue.suppliers.supplierName}</td>
						<td class="tr">${treemapValue.total}</td>
						<td class="tc">${treemapValue.deliveryTime}</td>
						<c:if test="${dd.code eq 'JZXTP' || dd.code eq 'DYLY'}">
						<td class="tc">${treemapValue.isRemoved}</td>
						<td class="tc">${treemapValue.removedReason}</td>
						</c:if>
				    </tr> --%>
				</c:if>
		</c:forEach>
		<c:if test="${dd.code eq 'JZXTP' || dd.code eq 'DYLY'}">
	        <c:if test="${isEndPrice !=1 }">
        		 <button class="btn" onclick = "quoteAgain('${project.id}','${packId}',1)">再次报价</button>
        		 <span class="ml50">查看历史报价:</span>
				 <select onchange="showQuoteHistory('${project.id}','${packId}',this.options[this.options.selectedIndex].value)">
						<c:if test="${empty treemap.value[0].dataList or fn:length(treemap.value[0].dataList) == 1}">
							<option value=''>暂无报价历史</option>
						</c:if>
						<c:set value="${fn:length(treemap.value[0].dataList)}" var="length"></c:set>
						<c:if test="${fn:length(treemap.value[0].dataList) > 1}">
							<c:forEach items="${treemap.value[0].dataList}" var="ld" varStatus="vs">
								<c:set value="${length - vs.index}" var="result"></c:set>
								<option value='<fmt:formatDate value="${ld}" pattern="YYYY-MM-dd HH:mm:ss"/>'>第${result}轮报价</option>
							</c:forEach>
						</c:if>
			 	 </select>
        	</c:if>
        	 <c:if test="${isEndPrice ==1 }">
        		 <button class="btn">已结束唱标</button>
        		 <span class="ml50">查看历史报价:</span>
				 <select onchange="showQuoteHistory('${project.id}','${packId}',this.options[this.options.selectedIndex].value)">
						<c:if test="${empty treemap.value[0].dataList or fn:length(treemap.value[0].dataList) == 1 }">
							<option value=''>暂无报价历史</option>
						</c:if>
						<c:set value="${fn:length(treemap.value[0].dataList)}" var="length"></c:set>
						<c:if test="${fn:length(treemap.value[0].dataList) > 1}">
							<c:forEach items="${treemap.value[0].dataList}" var="ld" varStatus="vs">
								<c:set value="${length - vs.index}" var="result"></c:set>
								<option value='<fmt:formatDate value="${ld}" pattern="YYYY-MM-dd HH:mm:ss"/>'>第${result}轮报价</option>
							</c:forEach>
						</c:if>
			 	 </select>
        	</c:if>
		 </c:if>
		</table>
		 <iframe src="${pageContext.request.contextPath}/open_bidding/viewChangtotalByPackId.html?projectId=${project.id}&packId=${packId}&flowDefineId=${flowDefineId}&timestamp=<fmt:formatDate value="${treemap.value[0].dataList[0]}" pattern="YYYY-MM-dd HH:mm:ss"/>" id="ifs" class="table table-bordered table-condensed mt5" height="380"></iframe>
		</div>
		</c:if>
	</c:forEach>
</c:forEach>
</div>
</c:if>
   <c:if test="${status == true }">
   <h2 class="list_title">供应商报价信息</h2>
       <div class="clear">
		<input id="priceStr" name="priceStr" type="hidden" />
		<input id="projectId" name="projectId" value="${projectId }" type="hidden" />
		<c:forEach items="${listPackage}" var="listPackage" varStatus="vs">
		<c:set value="${vs.index}" var="index"></c:set>
			   <div>
				 <c:if test="${vs.index == 0 }">
				 	<h2 onclick="ycDivmingxi(this,'${index}')" class="count_flow spread hand">包名:<span class="f14 blue">${listPackage.name }</span></h2>
				 </c:if>
				 <c:if test="${vs.index != 0 }">
				 	<h2 onclick="ycDivmingxi(this,'${index}')" class="count_flow shrink hand">包名:<span class="f14 blue">${listPackage.name }</span></h2>
				 </c:if>
				 <c:if test="${dd.code eq 'JZXTP' || dd.code eq 'DYLY'}">
			        	<c:if test="${listPackage.isEndPrice != 1 }">
			        		<button class="btn" onclick = "quoteAgain('${project.id}','${listPackage.id}')">再次报价</button>
			        	</c:if>
			        	 <c:if test="${listPackage.isEndPrice ==1 }">
			        		<button class="btn">已结束唱标</button>
			        	</c:if>
			        	<span class="ml50">查看历史报价:</span>
						 <select onchange="showQuoteHistoryMingxi('${project.id}','${listPackage.id}',this.options[this.options.selectedIndex].value)">
						 	<c:set value="${fn:length(listPackage.dataList)}" var="length"></c:set>
						 	<c:if test="${empty listPackage.dataList or fn:length(listPackage.dataList) == 1 }">
						 		<option value=''>暂无报价历史</option>
						 	</c:if>
						 	<c:if test="${not empty listPackage.dataList and fn:length(listPackage.dataList) > 1}">
								<c:forEach items="${listPackage.dataList}" var="ld" varStatus="vs">
									<c:set value="${length - vs.index}" var="result"></c:set>
									<option value='<fmt:formatDate value="${ld}" pattern="YYYY-MM-dd HH:mm:ss"/>'>第${result}轮报价</option>
								</c:forEach>
							</c:if>
					 	 </select>
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
							<c:forEach items="${suList.quoteList}" var="quoteList" varStatus="vs">
										<tr id="${pdList.id }" class="hand">
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
  </c:if>

</body>
</html>
