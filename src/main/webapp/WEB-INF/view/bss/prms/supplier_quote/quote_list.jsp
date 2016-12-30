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
  </script>
  <body>
 <c:if test="${status == false }"> 
	    <h2 class="list_title">供应商报价信息</h2>
   		<div class="clear">
<c:set value="1" var ="count"></c:set>
<c:forEach items="${treeMap }" var="treemap" varStatus="vsKey">
	<c:forEach items="${treemap.key }" var="treemapKey" varStatus="vs">
		<div>
			 <h2 onclick="ycDiv(this,'${index}')" class="count_flow spread hand">包名:<span class="f14 blue">${fn:substringBefore(treemapKey, "|")}</span>
			 </h2>
        </div>
        <div class="p0${index}">
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
				<tr>
				    <td class="tc w50">${vs.index+1}</td>
				    <td class="tc">${treemapValue.suppliers.supplierName}</td>
					<td class="tc">${treemapValue.total}</td>
			    </tr>
		</c:forEach>
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
	</div>
  </c:if>

  </body>
</html>
