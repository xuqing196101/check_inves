<%@ page language="java" import="java.util.*,java.net.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<head>
	<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>打印结果展示页面</title>
	<script type="text/javascript">
		function printWord(){
			window.location.href="${pageContext.request.contextPath}/ob_project/printResult.html?print=print&&id=${obProject.id}";
		}
	</script>
</head>

<body>
<div class="container">
<%-- <table  class="table table-bordered">
		<tbody>
			<tr>
				<td class="tc" colspan="6" align="center"><br/>${ obProject.name }<br/>竞价结果信息表<br/><br/></td>
			</tr> 
			<tr>
				<td class="tc" class="info">项目名称</td>
				<td colspan="5">${ obProject.name }</td>
			</tr>
			<c:forEach items="${ obProductInfoList }" var="pInfo" varStatus="vs">
				<tr>
				  <c:if test="${ vs.index==0 }">
					  <td rowspan="${ obProductInfoList.size() }" class="tc" class="info">产品名称</td>
				  </c:if>
				  <td class="tc">${ pInfo.obProduct.name }</td>
				  <td class="tc" class="info">数量</td>
				  <td class="tc">${ pInfo.purchaseCount }</td>
				  <td class="tc" class="info">预算</td>
				  <td class="tc">${ pInfo.limitedPrice }</td>
				</tr>
			</c:forEach>
			<tr>
				<td class="tc" class="info">需求部门</td>
				<td class="tc" colspan="5">${ orgName }</td>
			</tr>
			<tr>
				<td class="tc" class="info">运杂费</td>
				<td class="tc" colspan="5">${ obProject.transportFees }</td>
			</tr> 
			<tr>
				<td class="tc" class="info">交货地点</td>
				<td class="tc" colspan="2">${ obProject.deliveryAddress }</td>
				<td class="tc" class="info">交货时间</td>
				<td class="tc" colspan="2"><fmt:formatDate value="${ obProject.deliveryDeadline }" pattern="yyyy-MM-dd HH:ss:mm"/></td>
			</tr> 
			<tr>
				<td colspan="6" style="border: none;" >
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td>名次</td>
								<td class="tc">供应商名称</td>
								<td class="tc">自报单价（元）</td>
		  						<td class="tc">成交单价（元）</td>
		  						<td class="tc">成交数量</td>
		  						<td class="tc">成交总价（元）</td>
								<td class="tc">操作状态</td>
							</tr>
							<c:forEach items="${info.list }" var="result" varStatus="vs">
								<tr>
							  		<td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
							  		<td class="tc">${result.supplier.supplier.supplierName }</td>
							  		<td class="tc">${result.countOfferPrice}</td>
							  		<td class="tc">${countOfferPricebyOne}</td>
							  		<td class="tc">${result.countresultCount}</td>
							  		<td class="tc">${result.countTotalAmount }</td>
							  		<td class="tc">
							  			<c:if test="${result.status == 0}">未确认</c:if>
							  			<c:if test="${result.status == 1}">已确认</c:if>
							  		</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</td>
			</tr> 
			<tr>
				<td align="center" colspan="6">供应商确认中标数量总量为${chengjiao}，预定采购数量为${count}，剩余采购数量为${count-chengjiao}.</td>
			</tr>
			</tbody>
		   </table> --%>
	  <table class="table table-bordered mt10">
	    <tbody>
	      <tr>
			<td class="tc" colspan="6" align="center"><br/><b>${ obProject.name }</b><br/>竞价结果信息表<br/><br/></td>
		  </tr> 
		  <tr>
		    <td class="info"><b>竞价项目编号</b></td>
		    <td>${ obProject.projectNumber }</td>
		    <td class="info"><b>竞价项目名称</b></td>
		    <td>${ obProject.name }</td>
		  </tr>
		  <tr>
		    <td class="info"><b>交货地点</b></td>
		    <td>${ obProject.deliveryAddress }</td>
		     <td class="info"><b>交货时间</b></td>
		    <td><fmt:formatDate value="${ obProject.deliveryDeadline }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		  </tr>
		  <tr>
		    <td class="info"><b>运杂费</b></td>
		    <td>${ transportFees }</td>
		    <c:if test="">
			    <td colspan="2"></td>
		    </c:if>
		    <c:if test="${ empty obProject.transportFeesPrice }">
			    <td colspan="2"></td>
		    </c:if>
		    <c:if test="${ !empty obProject.transportFeesPrice }">
			    <td class="info"><b>运杂费用（元）</b></td>
			    <td>${obProject.transportFeesPrice}</td>
		    </c:if>
		  </tr>
		  <tr>
		    <td class="info"><b>需求单位</b></td>
		    <td>${ demandUnit }</td>
		    <td class="info"><b>采购机构</b></td>
		    <td>${ orgName }</td>
		  </tr>
		  <tr>
		    <td class="info"><b>需求联系人</b></td>
		    <td>${ obProject.contactName }</td>
		    <td class="info"><b>采购联系人：</b></td>
		    <td>${ obProject.orgContactName }</td>
		  </tr>
		  <tr>
		    <td class="info"><b>需求联系电话</b></td>
		    <td>${ obProject.contactTel }</td>
		    <td class="info"><b>采购联系电话</b></td>
		    <td>${ obProject.orgContactTel }</td>
		  </tr>
		</tbody>
	</table>
	<%@ include file ="/WEB-INF/view/bss/ob/supplier/supplierCommon.jsp" %>
	<div align="center">
		<span><font size="3">供应商确认中标比例为<b>${countProportion }%</b>，未中标比例为<b>${100 - countProportion }%</b>.</font></span>
	</div>
	</div>
	   <div class="col-md-12 clear tc mt10">
    	<button class="btn btn-windows print" onclick="printWord()">打印</button>
    	<button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
    </div>
</body>
</html>